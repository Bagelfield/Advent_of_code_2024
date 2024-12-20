package com.advent.code;

import com.advent.code.days.Day;
import com.advent.code.days.day11.Day11;

import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Logger.getGlobal;

public class AdventOfCode {
	static Logger logger = getGlobal();

	public static <T> void display(Collection<T> data) {
		StringBuilder sb = new StringBuilder();

		for (T t : data) {
			if (t instanceof String
					|| t instanceof Integer
					|| t instanceof Long) {
				sb.append(t + " / ");
			} else {
				display((Collection<T>) t);
			}
		}
		String str = sb.toString();
		logger.info(str);
	}

	public static void main(String[] args) throws IOException {
		logger.setLevel(Level.FINER);

		Day day = new Day11();
		logger.info(day.process(true).toString());
	}
}
