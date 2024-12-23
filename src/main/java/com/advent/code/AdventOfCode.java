package com.advent.code;

import com.advent.code.days.Day;
import com.advent.code.days.day18.Day18;
import com.advent.code.days.day23.Day23;

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
			if (t instanceof String || t instanceof Integer
			) {
				sb.append(t);
			} else {
				display((Collection<T>) t);
			}
		}
		String str = sb.toString();
		logger.info(str);
	}

	public static void display(char[][] data) {
		StringBuilder sb = new StringBuilder();

		sb.append("\n");
        for (char[] datum : data) {
            for (int j = 0; j < data[0].length; j++) {
                sb.append(datum[j]);
            }
			sb.append("\n");
        }
		String str = sb.toString();
		logger.info(str);
	}

	public static void main(String[] args) throws IOException {
		logger.setLevel(Level.FINER);

		Day day = new Day23();
		logger.info(day.process(false).toString());
	}
}
