package com.example.reactivestreams;

import java.util.Random;
import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A subscriber that simply logs to the console whatever happens
 * and creates random delays on certain actions to simulate processing.
 */
public class RandomDelaySubscriber implements Flow.Subscriber<Object> {
	private static final String LOG_MESSAGE_FORMAT = "%s [%s]: %s%n";
	private static final Random RANDOM = new Random();

	private final String name;
	private final AtomicInteger buffer = new AtomicInteger();
	private Flow.Subscription subscription;

	public RandomDelaySubscriber(String name) {
		this.name = name;
	}

	@Override
	public void onSubscribe(Flow.Subscription subscription) {
		log("onSubscribe");
		this.subscription = subscription;
		requestItems();
	}

	@Override
	public void onNext(Object item) {
		boolean bufferAlmostEmpty = buffer.decrementAndGet() == 3;
		unsafeSleepRandomUpToMillis(500);
		log("onNext - %s", item.toString());
		if (bufferAlmostEmpty) {
			requestMoreItemsOrCancelSubscription();
		}
	}

	@Override
	public void onError(Throwable t) {
		log("onError - %s", t);
	}

	@Override
	public void onComplete() {
		log("onComplete");
	}

	private void requestItems() {
		int requestedItemCount = RANDOM.nextInt(4) + 4;
		buffer.addAndGet(requestedItemCount);
		log("Requesting %d new items...", requestedItemCount);
		subscription.request(requestedItemCount);
	}

	private void requestMoreItemsOrCancelSubscription() {
		if (RANDOM.nextBoolean()) {
			requestItems();
		} else {
			log("Cancelling subscription...");
			subscription.cancel();
		}
	}

	private void log(String message, Object... args) {
		String fullMessage = String.format(LOG_MESSAGE_FORMAT,
				name, Thread.currentThread().getName(), message);
		System.out.printf(fullMessage, args);
	}

	public static void unsafeSleepRandomUpToMillis(int maxMillis) {
		try {
			Thread.sleep(RANDOM.nextInt(maxMillis) + 1);
		} catch (InterruptedException ex) {
		}
	}
}
