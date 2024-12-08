package com.advent.code.days;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Day6 {

    public Day6() {}

    public String process(String fileName) throws IOException {
        long count = 1;

        MapGuard mapGuard;

        try (FileReader file = new FileReader(fileName);
             BufferedReader buffer = new BufferedReader(file)) {
            String[][] lines = buffer.lines()
                    .map(line -> line.split(""))
                    .toArray(String[][]::new);
            mapGuard = new MapGuard(lines.length, lines[0].length, lines);

            mapGuard.displayMap();
            while(guardIsInsideMap(mapGuard)) {
                count += moveGuard(mapGuard, count);
            }
        }
        return "Guard moved " + count + " times. There is " + nbVisited(mapGuard) + " visited cases";
    }

    private long nbVisited(MapGuard mapGuard) {
        long nbVisited = 0;
        for (int x = 0; x < mapGuard.getMap().length; x++) {
            for (int y = 0; y < mapGuard.getMap()[x].length; y++) {
                if ((mapGuard.getGuardPosition().getX() == x && mapGuard.getGuardPosition().getY() == y)
                        || mapGuard.getMap()[x][y].isVisited()) {
                    nbVisited++;
                }
            }
        }
        return nbVisited;
    }

    private long moveGuard(MapGuard mapGuard, long countMoves) {
        countMoves++;
        var guardPos = mapGuard.getGuardPosition();
        var guardDirection = mapGuard.getGuardDirection();

        switch (guardDirection) {
            case NORTH:
                if (guardIsBlocked(mapGuard)) {
                    mapGuard.setGuardDirection(Direction.EAST);
                    if (guardIsInsideMap(mapGuard)) {
                        moveGuard(mapGuard, countMoves);
                    } else {
                        return countMoves;
                    }
                } else {
                    mapGuard.getMap()[guardPos.getX()][guardPos.getY()].setContent("X");
                    mapGuard.getMap()[guardPos.getX()][guardPos.getY()].setVisited(true);
                    mapGuard.setGuardPosition(mapGuard.getMap()[guardPos.getX()][guardPos.getY() - 1]);
                    mapGuard.getMap()[guardPos.getX()][guardPos.getY()-1].setContent("^");
                }
                break;
            case SOUTH:
                if (guardIsBlocked(mapGuard)) {
                    mapGuard.setGuardDirection(Direction.WEST);
                    if (guardIsInsideMap(mapGuard)) {
                        moveGuard(mapGuard, countMoves);
                    } else {
                        return countMoves;
                    }
                    moveGuard(mapGuard, countMoves);
                } else {
                    mapGuard.getMap()[guardPos.getX()][guardPos.getY()].setContent("X");
                    mapGuard.getMap()[guardPos.getX()][guardPos.getY()].setVisited(true);
                    mapGuard.setGuardPosition(mapGuard.getMap()[guardPos.getX()][guardPos.getY() + 1]);
                    mapGuard.getMap()[guardPos.getX()][guardPos.getY() + 1].setContent("^");
                }
                break;
            case EAST:
                if (guardIsBlocked(mapGuard)) {
                    mapGuard.setGuardDirection(Direction.SOUTH);
                    if (guardIsInsideMap(mapGuard)) {
                        moveGuard(mapGuard, countMoves);
                    } else {
                        return countMoves;
                    }
                    moveGuard(mapGuard, countMoves);
                } else {
                    mapGuard.getMap()[guardPos.getX()][guardPos.getY()].setContent("X");
                    mapGuard.getMap()[guardPos.getX()][guardPos.getY()].setVisited(true);
                    mapGuard.setGuardPosition(mapGuard.getMap()[guardPos.getX() + 1][guardPos.getY()]);
                    mapGuard.getMap()[guardPos.getX() + 1][guardPos.getY()].setContent("^");
                }
                break;
            case WEST:
                if (guardIsBlocked(mapGuard)) {
                    mapGuard.setGuardDirection(Direction.NORTH);
                    if (guardIsInsideMap(mapGuard)) {
                        moveGuard(mapGuard, countMoves);
                    } else {
                        return countMoves;
                    }
                    moveGuard(mapGuard, countMoves);
                } else {
                    mapGuard.getMap()[guardPos.getX()][guardPos.getY()].setContent("X");
                    mapGuard.getMap()[guardPos.getX()][guardPos.getY()].setVisited(true);
                    mapGuard.setGuardPosition(mapGuard.getMap()[guardPos.getX() - 1][guardPos.getY()]);
                    mapGuard.getMap()[guardPos.getX() - 1][guardPos.getY()].setContent("^");
                }
                break;
            default:
                break;
        }
        mapGuard.displayMap();
        return countMoves;
    }

    private boolean guardIsInsideMap(MapGuard mapGuard) {
        var guardPos = mapGuard.getGuardPosition();
        var guardDirection = mapGuard.getGuardDirection();

        boolean isOutside = false;

        switch (guardDirection) {
            case NORTH:
                if (guardPos.getY() - 1 == -1) {
                    isOutside = true;
                }
                break;
            case SOUTH:
                if (guardPos.getY() + 1 == mapGuard.getHeight()) {
                    isOutside = true;
                }
                break;
            case EAST:
                if (guardPos.getX() + 1 == mapGuard.getWidth()) {
                    isOutside = true;
                }
                break;
            case WEST:
                if (guardPos.getX() - 1 == -1) {
                    isOutside = true;
                }
                break;
            default:
                break;
        }
        return !isOutside;
    }

    private boolean guardIsBlocked(MapGuard mapGuard) {
        var guardPos = mapGuard.getGuardPosition();
        var guardDirection = mapGuard.getGuardDirection();

        boolean isBlocked = false;

        switch (guardDirection) {
            case NORTH:
                if (Objects.equals("#", mapGuard.getMap()[guardPos.getX()][guardPos.getY() - 1].getContent())) {
                    isBlocked = true;
                }
                break;
            case SOUTH:
                if (Objects.equals("#", mapGuard.getMap()[guardPos.getX()][guardPos.getY() + 1].getContent())) {
                    isBlocked = true;
                }
                break;
            case EAST:
                if (Objects.equals("#", mapGuard.getMap()[guardPos.getX() + 1][guardPos.getY()].getContent())) {
                    isBlocked = true;
                }
                break;
            case WEST:
                if (Objects.equals("#", mapGuard.getMap()[guardPos.getX() - 1][guardPos.getY()].getContent())) {
                    isBlocked = true;
                }
                break;
            default:
                break;
        }
        return isBlocked;
    }
}

