package com.advent.code.days.day1;

import com.advent.code.days.Day;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day1 extends Day {
    public Day1() {
        super(1);
    }

    @Override
    public Object process(boolean isTest) throws IOException {
        int result = 0;

        try (FileReader file = new FileReader(isTest ? fileNameTest : fileName);
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
}
