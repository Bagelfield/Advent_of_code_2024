package com.advent.code.days.day5;

import com.advent.code.days.Day;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class Day5 extends Day {
    protected Day5() {
        super(5);
    }

    @Override
    public Object process(boolean isTest) throws IOException {
        int count = 0;

        try (FileReader file = new FileReader(isTest ? fileNameTest : fileName);
             BufferedReader buffer = new BufferedReader(file)) {
            AtomicBoolean firstPart = new AtomicBoolean(true);
            Map<Integer, List<Integer>> rulesMap = new HashMap<>();
            List<List<Integer>> printings = new ArrayList<>();

            var nullable = buffer.lines()
                    .map(line -> {
                        if (line.isEmpty()) {
                            firstPart.set(false);
                        } else if (firstPart.get()) {
                            String[] rule = line.split("\\|");
                            List<Integer> listToAdd = new ArrayList<>();
                            if (!rulesMap.containsKey(Integer.parseInt(rule[0]))) {
                                rulesMap.put(Integer.parseInt(rule[0]), listToAdd);
                            }
                            rulesMap.get(Integer.parseInt(rule[0])).add(Integer.parseInt(rule[1]));
                        } else {
                            List<Integer> printing = Arrays.stream(line.split(",")).map(Integer::parseInt).collect(Collectors.toList());
                            printings.add(printing);
                        }
                        return null;
                    })
                    .collect(Collectors.toList());

            List<List<Integer>> printingsToFix = new ArrayList<>();
            for (List<Integer> printing : printings) {
                if (isRespectingRule(printing, rulesMap)) {
                    count += getMidleValueOf(printing);
                } else {
                    printingsToFix.add(printing);
                }
            }

            count = 0;

            for (List<Integer> toFix : printingsToFix) {
                count += getMidleValueOf(transformUnprintable(toFix, rulesMap));
            }
        }
        return String.valueOf(count);
    }

    private int getMidleValueOf(List<Integer> printing) {
        return printing.get(printing.size() % 2 == 0 ? (printing.size() / 2) - 1 : ((printing.size() + 1) / 2) - 1);
    }

    private boolean isRespectingRule(List<Integer> printing, Map<Integer, List<Integer>> rulesMap) {
        boolean isOk = true;

        List<Integer> reversedPrinting = new LinkedList<>(printing);
        List<Integer> valuesToPrintBefore = new LinkedList<>(printing);
        Collections.reverse(reversedPrinting);
        Collections.reverse(valuesToPrintBefore);

        for (Integer valueToPrint : valuesToPrintBefore) {
            reversedPrinting.remove(valueToPrint);
            if (rulesMap.containsKey(valueToPrint)) {
                if (rulesMap.get(valueToPrint).stream().anyMatch(reversedPrinting::contains)) {
                    isOk = false;
                }
            }
        }
        return isOk;
    }

    private List<Integer> transformUnprintable(List<Integer> unprintable, Map<Integer, List<Integer>> rulesMap) {
        List<Integer> reversedPrinting = new LinkedList<>(unprintable);
        List<Integer> valuesToPrintBefore = new LinkedList<>(unprintable);
        Collections.reverse(reversedPrinting);
        Collections.reverse(valuesToPrintBefore);

        List<Integer> valuesAlreadyAdded = new LinkedList<>();

        List<Integer> printablelist = new LinkedList<>();
        for (Integer valueToPrint : valuesToPrintBefore) {
            if (valuesToPrintBefore.size() == printablelist.size()) {
                break;
            }
            if (!valuesAlreadyAdded.contains(valueToPrint)) {
                reversedPrinting.remove(valueToPrint);
                if (rulesMap.containsKey(valueToPrint)) {
                    var rule = rulesMap.get(valueToPrint);
                    if (rule.stream().anyMatch(reversedPrinting::contains)) {
                        var allProblemNb = rule
                                .stream()
                                .filter(reversedPrinting::contains)
                                .collect(Collectors.toList());
                        for (Integer valueToAdd : allProblemNb) {
                            printablelist.add(valueToAdd);
                            reversedPrinting.remove(valueToAdd);
                            valuesAlreadyAdded.add(valueToAdd);
                        }
                        printablelist.add(valueToPrint);
                    } else {
                        printablelist.add(valueToPrint);
                    }
                }
            }
        }
        Collections.reverse(printablelist);
        while (!isRespectingRule(printablelist, rulesMap)) {
            printablelist = transformUnprintable(printablelist, rulesMap);
        }
        return printablelist;
    }
}
