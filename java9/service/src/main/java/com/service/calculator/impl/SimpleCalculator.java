package com.service.calculator.impl;

import com.service.calculator.Calculator;
import com.service.util.SimpleLogger;

public class SimpleCalculator implements Calculator{
	private final static SimpleLogger simpleLogger = new SimpleLogger(SimpleCalculator.class.getName());

	@Override
	public int sum(int a, int b) {
		simpleLogger.log("sum called!");
		return a + b;
	}
}
