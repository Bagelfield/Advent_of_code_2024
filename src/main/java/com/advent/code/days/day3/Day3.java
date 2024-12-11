package com.advent.code.days.day3;

import com.advent.code.days.Day;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day3 extends Day {
    public Day3() {
        super(3);
    }

    @Override
    public Object process(boolean isTest) throws IOException {
        AtomicLong count = new AtomicLong();

        try (FileReader file = new FileReader(isTest ? fileNameTest : fileName);
             BufferedReader buffer = new BufferedReader(file)) {

            var result = buffer.lines()
                    .map(line -> {
                        String regex = "do\\(\\)|don't\\(\\)|mul\\((\\d+),(\\d+)\\)";
                        Pattern pattern = Pattern.compile(regex);
                        Matcher matcher = pattern.matcher(line);
                        boolean executeMul = true;
                        while (matcher.find()) {
                            var group = matcher.group(0);

                            if (group.equals("do()")) {
                                executeMul = true;
                            } else if (group.equals("don't()")) {
                                executeMul = false;
                            } else if (executeMul) {
                                var nb1 = Long.parseLong(matcher.group(1));
                                var nb2 = Long.parseLong(matcher.group(2));
                                count.getAndAdd(nb1*nb2);
                            }
                        }
                        return count;
                    })
                    .collect(Collectors.toList());
        }
        return count.get();
    }
}
