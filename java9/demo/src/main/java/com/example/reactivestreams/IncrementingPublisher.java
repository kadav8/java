package com.example.reactivestreams;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A publisher that produces a potentially infinite stream of consecutive positive integers.
 *
 * The publisher makes a best effort to start each subscription's (partial) series
 * with the smallest value that has not yet been requested by all active subscribers.
 * (This item could be seen to be the oldest that was not yet processed by all existing
 * subscriptions.)
 */
public class IncrementingPublisher implements Flow.Publisher<Integer> {

	private final ExecutorService executor = Executors.newFixedThreadPool(4);
	private final Set<Sub> subscriptions = Collections.newSetFromMap(new ConcurrentHashMap<>());
	private final AtomicInteger subscriptionCount = new AtomicInteger();
	private final Runnable end;

	public IncrementingPublisher(Runnable end) {
		this.end = end;
	}

	@Override
	public void subscribe(Flow.Subscriber<? super Integer> subscriber) {
		int startValue = subscriptions.stream()
				.mapToInt(sub -> sub.nextValue.get())
				.min().orElse(0);

		Sub subscription = new Sub(subscriber, startValue);

		subscriptions.add(subscription);
		subscriptionCount.incrementAndGet();

		subscriber.onSubscribe(subscription);
	}

	private class Sub implements Flow.Subscription {

		private final Flow.Subscriber<? super Integer> subscriber;
		private final AtomicInteger nextValue;
		private final AtomicBoolean canceled;

		public Sub(Flow.Subscriber<? super Integer> subscriber, int startValue) {
			this.subscriber = subscriber;
			this.nextValue = new AtomicInteger(startValue);
			this.canceled = new AtomicBoolean(false);
		}

		@Override
		public void request(long n) {
			if (canceled.get()) return;

			if (n < 0)
				reportIllegalArgument();
			else
				publishItems(n);
		}

		private void reportIllegalArgument() {
			executor.execute(() -> subscriber.onError(new IllegalArgumentException()));
		}

		private void publishItems(long n) {
			for (long i = n; i > 0; i--) {
				executor.execute(() -> subscriber.onNext(nextValue.getAndIncrement()));
			}
		}

		@Override
		public void cancel() {
			canceled.set(true);
			// we do not cancel already requested items;
			// instead we unregister check whether we want to shut down
			boolean wasLast = unregisterSubscriptionAndCheckIfLast(this);
			if (wasLast) shutdown();
		}
	}

	private boolean unregisterSubscriptionAndCheckIfLast(Sub subscription) {
		subscriptions.remove(subscription);
		return subscriptionCount.decrementAndGet() == 0;
	}

	private void shutdown() {
		System.out.println("Shutting down executor service...");
		executor.shutdown();
		Executors.newSingleThreadExecutor().submit(() -> {
			try {
				executor.awaitTermination(0, TimeUnit.SECONDS);
			} catch (InterruptedException ex) {
				// if waiting gets interrupted, we simply declare the publisher
				// to be terminated
			}
			System.out.println("Shutdown complete.");
			end.run();
		});
	}
}
