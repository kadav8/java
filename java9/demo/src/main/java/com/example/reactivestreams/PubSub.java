package com.example.reactivestreams;

public class PubSub {

	/*
	 * Uses the new reactive stream interfaces to create a simple publisher/subscriber pipeline.
	 * Note that the JDK only defines a couple of basic interfaces (with the exception of
	 * SubmissionPublisher) for existing reactive stream libraries to integrate with.
	 */

	public static void main(String[] args) throws InterruptedException {

		IncrementingPublisher publisher = new IncrementingPublisher(() -> System.exit(0));

		RandomDelaySubscriber subscriberA = new RandomDelaySubscriber("A");
		RandomDelaySubscriber subscriberB = new RandomDelaySubscriber("B");

		publisher.subscribe(subscriberA);
		publisher.subscribe(subscriberB);

	}

}
