package com.service.util;

import java.util.Date;

public class SimpleLogger {

	private final String name;

	public SimpleLogger(String name) {
		this.name = name;
	}

	public void log(String msg) {
		Date date = new Date();
		String log = String.format("[%s] - %s - %s", date.toString(), name, msg);
		System.out.println(log);
	}
}
