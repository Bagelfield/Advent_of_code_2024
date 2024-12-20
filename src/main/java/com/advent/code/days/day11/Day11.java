package com.advent.code.days.day11;

import com.advent.code.days.Day;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.advent.code.AdventOfCode.display;

public class Day11 extends Day {
    public Day11() {
        super(11);
    }

    @Override
    public Object process(boolean isTest) throws IOException {
        long count = 0L;
        try (FileReader file = new FileReader(isTest ? fileNameTest : fileName);
             BufferedReader buffer = new BufferedReader(file)) {
            List<Long> numbers = Arrays
                    .stream(buffer.lines()
                            .map(line -> line.split(" "))
                            .collect(Collectors.toList()).get(0))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());

            for (Long number : numbers) {
                count += treatNumber(number, count, 1L);
            }
            return count;
        }
    }

    /*
     * If the stone is engraved with the number 0, it is replaced by a stone engraved with the number 1.
     * If the stone is engraved with a number that has an even number of digits, it is replaced by two stones.
     *  The left half of the digits are engraved on the new left stone, and the right half of the digits
     *  are engraved on the new right stone. (The new numbers don't keep extra leading zeroes:
     *  1000 would become stones 10 and 0.)
     * If none of the other rules apply, the stone is replaced by a new stone; the old stone's number
     *  multiplied by 2024 is engraved on the new stone.
     */
    private Long treatNumber(Long nb, Long count, Long countIteration) {
        if (countIteration > 6) {
            return count;
        }
        if (nb == 0L) {
            countIteration++;
            count++;
            treatNumber(1L, count, countIteration);
        }
        var strValueNb = String.valueOf(nb);
        var lengthNb = strValueNb.length();
        if (lengthNb % 2 == 0) {
            var firstStone = String.valueOf(nb).substring(0, lengthNb/2);
            var secondStone = String.valueOf(nb).substring(lengthNb/2, lengthNb);
            var listResults = Arrays.asList(Long.valueOf(firstStone), Long.valueOf(secondStone));
            countIteration++;
            count+=2;
            treatNumber(listResults.get(0), count, countIteration);
            treatNumber(listResults.get(1), count, countIteration);
        } else {
            countIteration++;
            count++;
            treatNumber(2024L * nb, count, countIteration);
        }
        return count;
    }
}

