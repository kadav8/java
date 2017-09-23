package com.examples.collections;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Map.entry;

public class CollectionFactories {

	public static void main(String args[]) {
		List<String> list = List.of("a", "b", "c"); // immutable list
		System.out.println(list);

		Set<String> set = Set.of("a", "b", "c");
		System.out.println(set);

		Map<String, Integer> mapImmediate = Map.of(
				"one", 1,
				"two", 2,
				"three", 3);
		System.out.println(mapImmediate);

		System.out.println();

		Map<String, Integer> mapEntries = Map.ofEntries(
				entry("one", 1),
				entry("two", 2),
				entry("three", 3));

		Stream.of(list, set, mapImmediate.entrySet(), mapEntries.entrySet())
				.map(CollectionFactories::joinElementsToString)
				.forEach(System.out::print);
	}

	private static String joinElementsToString(Collection<?> collection) {
		return collection.stream()
				.map(Object::toString)
				.collect(Collectors.joining(", ", "", "\n"));
	}
}
