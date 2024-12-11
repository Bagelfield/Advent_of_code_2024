package com.advent.code.days.day6;

import com.advent.code.days.Day;
import com.advent.code.days.day6.data.Direction;
import com.advent.code.days.day6.data.MapGuard;
import com.advent.code.days.commons.Point;
import com.advent.code.days.commons.PointWithDirection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

import static java.util.logging.Logger.getGlobal;

public class Day6 extends Day {

    static Logger logger = getGlobal();

    public Day6() {
        super(6);
    }

    @Override
    public String process(boolean isTest) throws IOException {
        MapGuard mapGuard;
        List<PointWithDirection> firstVisitedPoints = new LinkedList<>();

        long count;
        try (FileReader file = new FileReader(isTest ? fileNameTest : fileName);
             BufferedReader buffer = new BufferedReader(file)) {
            String[][] lines = buffer.lines()
                    .map(line -> line.split(""))
                    .toArray(String[][]::new);
            mapGuard = new MapGuard(lines.length, lines[0].length, lines);

            mapGuard.displayMap();
            while (guardIsInsideMap(mapGuard)) {
                moveGuard(mapGuard, firstVisitedPoints);
            }
            mapGuard.getMap()[mapGuard.getGuardPosition().getX()][mapGuard.getGuardPosition().getY()].setContent("X");
            mapGuard.getMap()[mapGuard.getGuardPosition().getX()][mapGuard.getGuardPosition().getY()].setVisited(true);
            firstVisitedPoints.add(new PointWithDirection(mapGuard.getGuardPosition().getX(), mapGuard.getGuardPosition().getY(), mapGuard.getGuardDirection(), true));

            MapGuard savedMapGuard;
            // part2
            Map<String, Point> positionsToCount = new HashMap<>();
            for (int index = 1; index < firstVisitedPoints.size(); index++) {
                List<PointWithDirection> visitedPointInTheRun = new LinkedList<>();
                PointWithDirection possibleBlock = firstVisitedPoints.get(index);
                savedMapGuard = new MapGuard(lines.length, lines[0].length, lines);
                savedMapGuard.getMap()[possibleBlock.getX()][possibleBlock.getY()].setContent("#");
                // set position of the guard to 1 ctrl+Z to begin moves
                savedMapGuard.setGuardPosition(firstVisitedPoints.get(index - 1));
                savedMapGuard.setGuardDirection(firstVisitedPoints.get(index - 1).getDirection());
                // then compare every position to the list of the part 1 already visited to know if we are in a loop
                while (guardIsInsideMap(savedMapGuard)) {
                    moveGuard(savedMapGuard, visitedPointInTheRun);
                    if (isGuardInLoop(visitedPointInTheRun)) {
                        String key = possibleBlock.getX() + String.valueOf(possibleBlock.getY());
                        positionsToCount.put(key, possibleBlock);
                        break;
                    }
                }
//                logger.info("Point (" + possibleBlock.getX() + ", " + possibleBlock.getY() + ") is executed");
            }
            count = positionsToCount.size();
        }
        return "There is " + nbVisited(mapGuard) + " visited cases and there is " + count + " possible loops";
    }

    private boolean isGuardInLoop(
            List<PointWithDirection> visitedPointInTheRun
    ) {
        var lastVisitedPoint = visitedPointInTheRun.get(visitedPointInTheRun.size() - 1);
        List<PointWithDirection> lastElements = new LinkedList<>(visitedPointInTheRun);
        lastElements.remove(lastVisitedPoint);

        var nbTimes = (int) lastElements
                .stream()
                .filter(point -> point.equals(lastVisitedPoint)).count();
        return nbTimes > 0;
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
                    visitedPoints.add(new PointWithDirection(guardPos.getX(), guardPos.getY(), Direction.NORTH, true));
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

