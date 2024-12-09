package com.advent.code.days.day2;

import com.advent.code.days.Day;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day2 extends Day {
    public Day2() {
        super(2);
    }

    @Override
    public Object process(boolean isTest) throws IOException {
        int count = 0;

        try (FileReader file = new FileReader(isTest ? fileNameTest : fileName);
             BufferedReader buffer = new BufferedReader(file)) {

            final List<List<Integer>> reportsLevelsList = buffer.lines()
                    .map(report -> Arrays.stream(report.split(" ")))
                    .map(stringStream -> stringStream.map(Integer::parseInt)
                            .collect(Collectors.toList()))
                    .collect(Collectors.toList());

            for (List<Integer> levels : reportsLevelsList) {
                boolean safe = verifyIfLevelsSafe(levels);
                if (safe) count++;
            }
        }
        return String.valueOf(count);
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

    private static int distance(int i, int j) {
        return i >= j ? i-j : j-i;
    }
}
