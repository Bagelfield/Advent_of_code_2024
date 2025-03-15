package com.advent.code.days.day23;

import com.advent.code.days.Day;
import com.advent.code.days.commons.day23.Computer;
import com.advent.code.days.commons.day23.Couple;

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
            final List<Computer> computers = new ArrayList<Computer>();
            buffer.lines().forEach(line -> {
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
                if (!computerA.getLinkedComputers().containsKey(computerB.getName())) {
                    computerA.getLinkedComputers().put(computerB.getName(), computerB);
                }
                if (!computerB.getLinkedComputers().containsKey(computerA.getName())) {
                    computerB.getLinkedComputers().put(computerA.getName(), computerA);
                }
            });

            Map<String, Computer> computerMap = new HashMap<>();
            for (var computer : computers) {
                computerMap.put(computer.getName(), computer);
            }

            return part_two(computerMap);
        }
    }

    private static String part_two(Map<String, Computer> computerMap) {
        Set<Set<Computer>> computerLanSets = new HashSet<>();
        for (var computer : computerMap.values()) {
            if (computer.getLinkedComputers().size() >= 2) {
                for (var computer1 : computer.getLinkedComputers().values()) {
                    var setLan = new HashSet<Computer>();
                    setLan.add(computer1);
                    setLan.add(computer);
                    for (var computer2 : computer.getLinkedComputers().values()) {
                        if (isLinkedComputerInLan(computer2, setLan)) {
                            setLan.add(computer2);
                        }
                    }
                    computerLanSets.add(setLan);
                }
            }
        }
        return computerLanSets
                .stream()
                .max(Comparator.comparingInt(Set::size))
                .orElseThrow()
                .stream()
                .sorted(Comparator.comparing(Computer::getName))
                .map(Computer::getName)
                .collect(Collectors.joining(","));
    }

    private static boolean isLinkedComputerInLan(
            Computer computerToVerify,
            Set<Computer> computersInLan
    ) {
        return computersInLan
                .stream()
                .filter(computerInLan -> computerToVerify.getLinkedComputers().containsKey(computerInLan.getName()))
                .count() == (long) computersInLan.size();
    }

    private static int part_one(Map<String, Computer> computerMap) {
        var computerLinkedByThree = new HashSet<Set<Computer>>();
        for (var computer : computerMap.values()) {
            if (computer.getLinkedComputers().size() >= 2) {
                for (var computer1 : computer.getLinkedComputers().values()) {
                    for (var computer2 : computer.getLinkedComputers().values()) {
                        if (isLinkedComputerInLan(computer2, Set.of(computer1, computer))) {
                            computerLinkedByThree.add(Set.of(
                                    computer1,
                                    computer2,
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

