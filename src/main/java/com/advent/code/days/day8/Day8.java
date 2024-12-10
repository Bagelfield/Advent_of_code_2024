package com.advent.code.days.day8;

import com.advent.code.days.Day;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.logging.Logger.getGlobal;

public class Day8 extends Day {

    static Logger logger = getGlobal();

    public Day8() {
        super(8);
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

