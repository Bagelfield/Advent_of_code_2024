package com.advent.code.days.day9;

import com.advent.code.days.Day;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.logging.Logger.getGlobal;

public class Day9 extends Day {

    static Logger logger = getGlobal();

    public Day9() {
        super(9);
    }

    @Override
    public Object process(boolean isTest) throws IOException {
        long count = 0;
        try (FileReader file = new FileReader(isTest ? fileNameTest : fileName);
             BufferedReader buffer = new BufferedReader(file)) {
            List<Map<Long, List<String>>> lines = buffer.lines()
                    .map(line -> {
                        Map<Long, List<String>> temp = new HashMap<>();
                        String[] tab = line.split(": ");
                        List<String> lineFormated = Arrays.stream(tab[1].split(" ")).collect(Collectors.toList());
                        temp.put(Long.valueOf(tab[0]), lineFormated);
                        return temp;
                    })
                    .collect(Collectors.toList());
            return String.valueOf(count);
        }
    }

}

