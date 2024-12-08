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
        MapGuard mapGuard;
        MapGuard savedMapGuard;
        List<PointWithDirection> visitedPoints = new ArrayList<>();

        long count;
        try (FileReader file = new FileReader(fileName);
             BufferedReader buffer = new BufferedReader(file)) {
            String[][] lines = buffer.lines()
                    .map(line -> line.split(""))
                    .toArray(String[][]::new);
            mapGuard = new MapGuard(lines.length, lines[0].length, lines);

            mapGuard.displayMap();
            while (guardIsInsideMap(mapGuard)) {
                moveGuard(mapGuard, visitedPoints);
            }
            mapGuard.getMap()[mapGuard.getGuardPosition().getX()][mapGuard.getGuardPosition().getY()].setContent("X");
            mapGuard.getMap()[mapGuard.getGuardPosition().getX()][mapGuard.getGuardPosition().getY()].setVisited(true);
            visitedPoints.add(new PointWithDirection(mapGuard.getGuardPosition().getX(), mapGuard.getGuardPosition().getY(), mapGuard.getGuardDirection(), true));

            count = 0;
            Map<String, Point> positionsToCount = new HashMap<>();
            for (Point visitedPoint : visitedPoints) {
                List<PointWithDirection> visitedPointInTheRun = new LinkedList<>();
                savedMapGuard = new MapGuard(lines.length, lines[0].length, lines);
                savedMapGuard.getMap()[visitedPoint.getX()][visitedPoint.getY()].setContent("#");
                while (guardIsInsideMap(savedMapGuard)) {
                    moveGuard(savedMapGuard, visitedPointInTheRun);
                    if (isGuardInLoop(savedMapGuard, visitedPointInTheRun)) {
                        String key = visitedPoint.getX() + String.valueOf(visitedPoint.getY());
                        positionsToCount.put(key, visitedPoint);
                        break;
                    }
                }
            }
            count = positionsToCount.size();
        }
        return "There is " + nbVisited(mapGuard) + " visited cases and there is " + count + " possible loops";
    }

    private boolean isGuardInLoop(MapGuard mapGuard, List<PointWithDirection> visitedPointInTheRun) {
        return visitedPointInTheRun
                .stream()
                .anyMatch(point -> mapGuard.getGuardPosition().equals(point) && point.getDirection().equals(mapGuard.getGuardDirection()));
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

    private void moveGuard(MapGuard mapGuard, List<PointWithDirection> visitedPoints) {
        var guardPos = mapGuard.getGuardPosition();
        var guardDirection = mapGuard.getGuardDirection();

        switch (guardDirection) {
            case NORTH:
                if (guardIsBlocked(mapGuard)) {
                    mapGuard.setGuardDirection(Direction.EAST);
                    if (guardIsInsideMap(mapGuard)) {
                        moveGuard(mapGuard, visitedPoints);
                    } else {
                        return;
                    }
                } else {
                    mapGuard.getMap()[guardPos.getX()][guardPos.getY()].setContent("X");
                    mapGuard.getMap()[guardPos.getX()][guardPos.getY()].setVisited(true);
                    if (!mapGuard.getMap()[guardPos.getX()][guardPos.getY()].equals(mapGuard.getGuardInitialPosition())) {
                        visitedPoints.add(new PointWithDirection(guardPos.getX(), guardPos.getY(), Direction.NORTH, true));
                    }
                    mapGuard.setGuardPosition(mapGuard.getMap()[guardPos.getX()][guardPos.getY() - 1]);
                    mapGuard.getMap()[guardPos.getX()][guardPos.getY()-1].setContent("^");
                }
                break;
            case SOUTH:
                if (guardIsBlocked(mapGuard)) {
                    mapGuard.setGuardDirection(Direction.WEST);
                    if (guardIsInsideMap(mapGuard)) {
                        moveGuard(mapGuard, visitedPoints);
                    } else {
                        return;
                    }
                    moveGuard(mapGuard, visitedPoints);
                } else {
                    mapGuard.getMap()[guardPos.getX()][guardPos.getY()].setContent("X");
                    mapGuard.getMap()[guardPos.getX()][guardPos.getY()].setVisited(true);
                    visitedPoints.add(new PointWithDirection(guardPos.getX(), guardPos.getY(), Direction.SOUTH, true));
                    mapGuard.setGuardPosition(mapGuard.getMap()[guardPos.getX()][guardPos.getY() + 1]);
                    mapGuard.getMap()[guardPos.getX()][guardPos.getY() + 1].setContent("^");
                }
                break;
            case EAST:
                if (guardIsBlocked(mapGuard)) {
                    mapGuard.setGuardDirection(Direction.SOUTH);
                    if (guardIsInsideMap(mapGuard)) {
                        moveGuard(mapGuard, visitedPoints);
                    } else {
                        return;
                    }
                    moveGuard(mapGuard, visitedPoints);
                } else {
                    mapGuard.getMap()[guardPos.getX()][guardPos.getY()].setContent("X");
                    mapGuard.getMap()[guardPos.getX()][guardPos.getY()].setVisited(true);
                    visitedPoints.add(new PointWithDirection(guardPos.getX(), guardPos.getY(), Direction.EAST, true));
                    mapGuard.setGuardPosition(mapGuard.getMap()[guardPos.getX() + 1][guardPos.getY()]);
                    mapGuard.getMap()[guardPos.getX() + 1][guardPos.getY()].setContent("^");
                }
                break;
            case WEST:
                if (guardIsBlocked(mapGuard)) {
                    mapGuard.setGuardDirection(Direction.NORTH);
                    if (guardIsInsideMap(mapGuard)) {
                        moveGuard(mapGuard,visitedPoints);
                    } else {
                        return;
                    }
                    moveGuard(mapGuard, visitedPoints);
                } else {
                    mapGuard.getMap()[guardPos.getX()][guardPos.getY()].setContent("X");
                    mapGuard.getMap()[guardPos.getX()][guardPos.getY()].setVisited(true);
                    visitedPoints.add(new PointWithDirection(guardPos.getX(), guardPos.getY(), Direction.WEST, true));
                    mapGuard.setGuardPosition(mapGuard.getMap()[guardPos.getX() - 1][guardPos.getY()]);
                    mapGuard.getMap()[guardPos.getX() - 1][guardPos.getY()].setContent("^");
                }
                break;
            default:
                break;
        }
        // mapGuard.displayMap();
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

