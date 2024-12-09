package com.advent.code.days.day7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.logging.Logger.getGlobal;

public class Day7 {

    static Logger logger = getGlobal();

    public Day7() {}

    public String process(String fileName) throws IOException {
        long count=0;
        try (FileReader file = new FileReader(fileName);
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

            for (Map<Long, List<String>> line : lines) {
                long nbToFind = line.keySet().stream().findFirst().orElseThrow();
                var nbList = line.get(nbToFind);
                var nbOperationToTest = nbList.size() - 1;

                List<String> combinations = generateCombinations(nbOperationToTest);

                // all possible combinations
                for (String combination : combinations) {
                    String resultStr = nbList.get(0);
                    long result = Long.parseLong(resultStr);
                    for (int numberIndex = 1; numberIndex < nbList.size(); numberIndex++) {
                        switch (String.valueOf(combination.charAt(numberIndex-1))) {
                            case "*":
                                result*=Long.parseLong(nbList.get(numberIndex));
                                break;
                            case "+":
                                result+=Long.parseLong(nbList.get(numberIndex));
                                break;
                            case "|":
                                result = Long.parseLong(result + nbList.get(numberIndex));
                                break;
                            default:
                                break;
                        }
                    }
                    if (result==nbToFind) {
                        count += nbToFind;
                        break;
                    }
                }
            }
        }
        return String.valueOf(count);
    }

    // Method to generate all combinations of "+" and "*" for a given number of cases.
    public static List<String> generateCombinations(int nb) {
        List<String> result = new ArrayList<>();
        generateCombinationsHelper(nb, "", result);
        return result;
    }

    // Helper method to recursively build the combinations.
    private static void generateCombinationsHelper(int nb, String current, List<String> result) {
        // If we have added 'nb' operations, add the current combination to the result list.
        if (current.length() == nb) {
            result.add(current);
            return;
        }

        // Add the "+" operation and recursively call the helper method.
        generateCombinationsHelper(nb, current + "+", result);
        // Add the "*" operation and recursively call the helper method.
        generateCombinationsHelper(nb, current + "*", result);
        // Add the "*" operation and recursively call the helper method.
        generateCombinationsHelper(nb, current + "|", result);
    }
}

