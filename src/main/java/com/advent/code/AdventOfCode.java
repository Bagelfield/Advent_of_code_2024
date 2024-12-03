package com.advent.code;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.logging.Logger.getGlobal;

public class AdventOfCode {
	static Logger logger = getGlobal();
	private static long DAY = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	private static final String PREFIX_URL =
			"D:\\Developpement\\ADVENT_OF_CODE\\advent_of_code\\src\\main\\java\\com\\advent\\code\\files\\";
	private static final String EXTENSION = ".txt";
	private static final Map<Long, String> fileNameDayMap = initProject(false);
	private static Map<Long, String> initProject(boolean isTest) {
		logger.setLevel(Level.FINER);
		Map<Long, String> map = new HashMap<>();

		for (long i = 1; i < 25; i++) {
			String name = PREFIX_URL + "DAY_" + i + "_" + i;
			String nameTest = PREFIX_URL + "DAY_" + i + "_test" + i;
			if (isTest) {
				map.put(i, nameTest + EXTENSION);
			} else {
				map.put(i, name + EXTENSION);
			}
		}
		return map;
	}
	private static String removeSpaces(String data) {
		StringBuilder sb = new StringBuilder();
		for (char c : data.toCharArray()) {
			if (c != ' ') sb.append(c);
		}
		return sb.toString();
	}
	private static <T> void display(List<T> data) {
		StringBuilder sb = new StringBuilder();

		for (T t : data) {
			if (t instanceof String || t instanceof Integer
			) {
				sb.append(t);
			} else {
				display((List<T>) t);
			}
		}
		String str = sb.toString();
		logger.info(str);
	}
	private static int distance(int i, int j) {
		return i >= j ? i-j : j-i;
	}
	private static boolean verifyIfLevelsSafe(List<Integer> levels) {
		boolean save = true;
		Map<Integer, Integer> nbPbsMap = new HashMap<>();

		for (int i = 0; i < levels.size() - 1; i++) {
			nbPbsMap.put(i, 0);
			nbPbsMap.put(i + 1, 0);

			var countCurrentLevelIPbs = 0;
			var countCurrentLevelIPlus1Pbs = 0;
			var nb1 = levels.get(i);
			var nb2 = levels.get(i + 1);
			var increasing = nb2 - nb1 >= 0;
			var distance = distance(nb1, nb2);
			if (i == 0) {
				save = increasing;
			}
			//TESTS
			if (0 == distance || distance > 3) {
				countCurrentLevelIPbs++;
				countCurrentLevelIPlus1Pbs++;
			} else if (save != increasing) {
				countCurrentLevelIPbs++;
			}
			save = increasing;
			nbPbsMap.put(i, nbPbsMap.get(i) + countCurrentLevelIPbs);
			nbPbsMap.put(i + 1, nbPbsMap.get(i + 1) + countCurrentLevelIPlus1Pbs);
		}
   		return true;
	}

	public static void main(String[] args) throws IOException {
		switch((int) DAY-1) {
			case 1:
				var result1 = day1(fileNameDayMap.get(DAY-1));
				logger.info(result1);
				break;
			case 2:
				var result2 = day2(fileNameDayMap.get(DAY-1));
				logger.info(result2);
				break;
			default:
				break;
		}
	}

	private static String day1(String fileName) throws IOException {
		int result = 0;

		try (FileReader file = new FileReader(fileName);
			 BufferedReader buffer = new BufferedReader(file)) {
			final List<Integer> finalListe1 = new ArrayList<>();
			final List<Integer> finalListe2 = new ArrayList<>();
			final List<List<Integer>> listFinale = new ArrayList<>();
			listFinale.add(finalListe1);
			listFinale.add(finalListe2);

			var stream = buffer.lines()
					.map(line -> Arrays.stream(line.split(" {3}")))
					.map(stringStream -> {
						List<String> listeInt = stringStream.collect(Collectors.toList());
						int long1 = Integer.parseInt(listeInt.get(0));
						int long2 = Integer.parseInt(listeInt.get(1));
						finalListe1.add(long1);
						finalListe2.add(long2);
						return stringStream;
					})
					.collect(Collectors.toList());

//			var final1 = finalListe1
//					.stream()
//					.sorted()
//					.collect(Collectors.toList());
//			var final2 = finalListe2
//					.stream()
//					.sorted()
//					.collect(Collectors.toList());
//			for (int i = 0; i < final1.size(); i++) {
//				sum += distance(final1.get(i), final2.get(i));
//			}

			for (int nb : finalListe1) {
				var tempNb = finalListe2.stream().filter(nb2 -> nb2 == nb).count();
				result += tempNb*nb;
			}

		}

		return String.valueOf(result);
	}
	private static String day2(String fileName) throws IOException {
		int count = 0;

		try (FileReader file = new FileReader(fileName);
			 BufferedReader buffer = new BufferedReader(file)) {

			final List<List<Integer>> reportsLevelsList = buffer.lines()
					.map(report -> Arrays.stream(report.split(" ")))
					.map(stringStream -> stringStream.map(Integer::parseInt)
							.collect(Collectors.toList()))
					.collect(Collectors.toList());

			display(reportsLevelsList);
			for (List<Integer> levels : reportsLevelsList) {
				boolean safe = verifyIfLevelsSafe(levels);
				if (safe) count++;
			}
		}
		return String.valueOf(count);
	}
}
