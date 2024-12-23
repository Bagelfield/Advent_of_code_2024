package com.advent.code.days.day23;

import com.advent.code.days.Day;
import com.advent.code.days.commons.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.logging.Logger.getGlobal;

public class Day23 extends Day {

    static Logger logger = getGlobal();

    public Day23() {
        super(23);
    }

    @Override
    public Object process(boolean isTest) throws IOException {
        try (FileReader file = new FileReader(isTest ? fileNameTest : fileName);
             BufferedReader buffer = new BufferedReader(file)) {
            var computers = new ArrayList<Computer>();
            var couplesComputer = buffer.lines()
                    .map(line -> {
                        var split = line.split("-");
                        var computerA = new Computer(split[0]);
                        var computerB = new Computer(split[1]);
                        if (!computers.contains(computerA)) {
                            computers.add(computerA);
                        } else {
                            computerA = computers.get(computers.indexOf(computerA));
                        }
                        if (!computers.contains(computerB)) {
                            computers.add(computerB);
                        } else {
                            computerB =  computers.get(computers.indexOf(computerB));
                        }
                        if (!computerA.getLinkedComputers().contains(computerB)) {
                            computerA.getLinkedComputers().add(computerB);
                        }
                        if (!computerB.getLinkedComputers().contains(computerA)) {
                            computerB.getLinkedComputers().add(computerA);
                        }
                        return new Couple(computerA, computerB);
                    })
                    .collect(Collectors.toList());

            var computerLinkedByThree = new HashSet<Set<Computer>>();
            for (var computer : computers) {
                if (computer.getLinkedComputers().size() >= 2) {
                    for (int i = 0; i < computer.getLinkedComputers().size()-1; i++) {
                        for (int j = i + 1; j < computer.getLinkedComputers().size(); j++) {
                            if (couplesComputer.contains(
                                    new Couple(
                                            computer.getLinkedComputers().get(i),
                                            computer.getLinkedComputers().get(j)))) {
                                computerLinkedByThree.add(Set.of(
                                        computer.getLinkedComputers().get(i),
                                        computer.getLinkedComputers().get(j),
                                        computer
                                ));
                            }
                        }
                    }
                }
            }
            return computerLinkedByThree
                    .stream()
                    .filter(setComputers -> setComputers
                            .stream()
                            .anyMatch(computer -> computer.beginWith("t")))
                    .collect(Collectors.toSet()).size();
        }
    }
}

