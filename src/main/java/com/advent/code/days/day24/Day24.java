package com.advent.code.days.day24;

import com.advent.code.days.Day;
import com.advent.code.days.commons.day23.Computer;
import com.advent.code.days.commons.day24.Calcul;
import com.advent.code.days.commons.day24.Operand;
import com.advent.code.days.commons.day24.OperationEnum;
import com.advent.code.days.commons.day24.Puzzle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Day24 extends Day {

    public Day24() {
        super(24);
    }

    final String regexOperand = "(.{3}): ([0-1])";
    final String regexCal = "(.{3}) ([A-Z]+) (.{3}) -> (.{3})";

    @Override
    public Object process(boolean isTest) throws IOException {
        try (FileReader file = new FileReader(isTest ? fileNameTest : fileName);
             BufferedReader buffer = new BufferedReader(file)) {
            Map<String, Operand> knownOperands = new HashMap<>();
            Map<String, Operand> unknownOperands = new HashMap<>();

            String[] content = buffer.lines().collect(Collectors.joining("\n")).split("\n\n");
            Puzzle puzzle = new Puzzle(content);
            puzzle.solve();
            return "";
            /*
            var calculus = buffer.lines()
                    .map(line -> {
                        // Create a Pattern object
                        final Pattern patternOperand = Pattern.compile(regexOperand, Pattern.MULTILINE);
                        final Pattern patternCalc = Pattern.compile(regexCal, Pattern.MULTILINE);

                        // Now create matcher object.
                        final Matcher matcherOperand = patternOperand.matcher(line);
                        final Matcher matcherCalc = patternCalc.matcher(line);

                        if (matcherOperand.find()) {
                            knownOperands.put(matcherOperand.group(1),
                                    new Operand(
                                            matcherOperand.group(1),
                                            matcherOperand.group(2).equals("1")
                            ));
                        } else if (matcherCalc.find()) {
                            var operand1 = new Operand(matcherCalc.group(1));
                            var operation = OperationEnum.valueOf(matcherCalc.group(2));
                            var operand2 = new Operand(matcherCalc.group(3));
                            var result = new Operand(matcherCalc.group(4));

                            if (!knownOperands.containsKey(operand1.getKey())) {
                                unknownOperands.put(operand1.getKey(), operand1);
                            }
                            if (!knownOperands.containsKey(operand2.getKey())) {
                                unknownOperands.put(operand2.getKey(), operand2);
                            }
                            if (!knownOperands.containsKey(result.getKey())) {
                                unknownOperands.put(result.getKey(), result);
                            }

                            return new Calcul(
                                    operation,
                                    knownOperands.getOrDefault(operand1.getKey(), operand1),
                                    knownOperands.getOrDefault(operand2.getKey(), operand2),
                                    knownOperands.getOrDefault(result.getKey(), result)
                            );
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            return part_one(calculus, knownOperands, unknownOperands);
            */
        }
    }

    private static long part_one(
            Set<Calcul> calculus,
            Map<String, Operand> knownOperands,
            Map<String, Operand> unknownOperands
    ) {
        while (!unknownOperands.isEmpty()) {
            for (var calc : calculus) {
                if (knownOperands.containsKey(calc.getOperand1().getKey())
                    && knownOperands.containsKey(calc.getOperand2().getKey())) {
                    calc.setOperand1(knownOperands.get(calc.getOperand1().getKey()));
                    calc.setOperand2(knownOperands.get(calc.getOperand2().getKey()));
                    calc.getResult().setValue(calc.execute());
                    if (unknownOperands.containsKey(calc.getResult().getKey())) {
                        unknownOperands.remove(calc.getResult().getKey());
                        knownOperands.put(calc.getResult().getKey(), calc.getResult());
                    }
                }
            }
        }
        final String zOperands = knownOperands.keySet()
                .stream()
                .filter(key -> key.startsWith("z"))
                .sorted()
                .map(knownOperands::get)
                .map(Operand::getValue)
                .map(aBoolean -> aBoolean ? "1" : "0")
                .collect(Collectors.joining(""));

        long result = 0;
        for (int i = 0; i < zOperands.length(); i++) {
            var bit = Integer.parseInt(String.valueOf(zOperands.charAt(i)));
            result += (long) (bit*Math.pow(2, i));
        }
        return result;
    }
}

