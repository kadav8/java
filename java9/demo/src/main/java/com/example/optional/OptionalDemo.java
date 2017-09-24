package com.example.optional;

import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;
import static java.util.Objects.requireNonNull;

public class OptionalDemo {

	public static void main(String[] args) {
		Stream.of("123", "abc", "!§$", "%&/")
				.map(OptionalDemo::load)
				.forEach(System.out::println);

		// stream from optional:
		load("444").stream().forEach(System.out::println);

		Optional.empty().stream().forEach(System.out::println);
	}

	public static Optional load(String id) {
		return fromMemory(id)
				.or(() -> fromDisk(id))
				.or(() -> fromRemote(id));
	}

	public static Optional<Customer> fromMemory(String id) {
		return isDigit(id.charAt(0))
				? Optional.of(new Customer(id))
				: Optional.empty();
	}

	public static Optional<Customer> fromDisk(String id) {
		return isLetter(id.charAt(0))
				? Optional.of(new Customer(id))
				: Optional.empty();
	}

	public static Optional<Customer> fromRemote(String id) {
		return new Random().nextBoolean()
				? Optional.of(new Customer(id))
				: Optional.empty();
	}

	static class Customer {
		final String id;

		Customer(String id) {
			this.id = requireNonNull(id);
		}

		@Override
		public String toString() {
			return "Customer (" + id + ")";
		}
	}
}
