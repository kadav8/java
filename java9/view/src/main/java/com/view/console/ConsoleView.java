package com.view.console;

import com.service.calculator.Calculator;
import com.service.calculator.impl.SimpleCalculator;

public class ConsoleView {
	// nincs exportálva a com.service.util package:
	// private final static SimpleLogger simpleLogger = new SimpleLogger(SimpleCalculator.class.getName());

	public static void main(String[] args) {
		int a = 2;
		int b = 3;

		Calculator calculator = new SimpleCalculator();

		String msg = String.format("result: %d",  calculator.sum(a,b));

		System.out.println(msg);
	}
}
