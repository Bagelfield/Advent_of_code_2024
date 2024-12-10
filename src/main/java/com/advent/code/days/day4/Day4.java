package com.advent.code.days.day4;

import com.advent.code.days.day4.data.Direction;
import com.advent.code.days.day4.data.XmasCase;
import com.advent.code.days.day4.data.XmasCaseOriented;
import com.advent.code.days.day4.data.XmasTable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day4 {

    //@Override
    public Object process(String fileName) throws IOException {
        var result = 0L;

        try (FileReader file = new FileReader(fileName);
            BufferedReader buffer = new BufferedReader(file)) {
            var stream = buffer.lines();
            XmasTable xmasTable = new XmasTable(stream);
            var xmasCases = xmasTable.getxMasTable();
            var xCaseList = Arrays
                    .stream(xmasCases)
                    .filter(xmasCase -> xmasCase.getContent().equals("X"))
                    .collect(Collectors.toList());

            for (var xmasCase : xCaseList) {
                var neighbours = getNeigbourCases(xmasCase, xmasTable);
                for (var neighbour : neighbours) {
                    if (neighbour.getContent().equals("M")) {
                        var subNeighbour = getOrientedNeighbourCase(neighbour, xmasTable);
                        if (subNeighbour!= null && subNeighbour.getContent().equals("A")) {
                            var sub2Neighbour = getOrientedNeighbourCase(subNeighbour, xmasTable);
                            if (sub2Neighbour!= null && sub2Neighbour.getContent().equals("S")) {
                                result++;
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    public Object processPart2(String fileName) throws IOException {
        var result = 0L;

        try (FileReader file = new FileReader(fileName);
             BufferedReader buffer = new BufferedReader(file)) {
            var stream = buffer.lines();
            XmasTable xmasTable = new XmasTable(stream);
            var xmasCases = xmasTable.getxMasTable();
            var aCaseList = Arrays
                    .stream(xmasCases)
                    .filter(xmasCase -> xmasCase.getContent().equals("A"))
                    .collect(Collectors.toList());

            var listeOk = Set.of("M", "S");
            var directionsAdj = Set.of(Direction.DIAG_TR, Direction.DIAG_BL);

            for (var xmasCase : aCaseList) {
                var neighbours = getNeigbourCasesDiagOnly(xmasCase, xmasTable);
                for (var neighbour : neighbours) {
                    switch(neighbour.getDirection()) {
                        case DIAG_TL:
                            if ((listeOk.contains(neighbour.getContent())
                                    && neighbours
                                    .stream()
                                    .filter(xmasCaseAdj -> directionsAdj.contains(xmasCaseAdj.getDirection())
                                            && listeOk.contains(xmasCaseAdj.getContent()))
                                            .count() == 2)
                                    && neighbours
                                    .stream()
                                    .filter(xmasCaseAdj -> Direction.DIAG_BR.equals(xmasCaseAdj.getDirection())
                                            && listeOk.contains(xmasCaseAdj.getContent()))
                                            .count() == 1
                            ) {
                               result++;
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        return result;
    }

    private List<XmasCaseOriented> getNeigbourCasesDiagOnly(XmasCase xmasCase, XmasTable xmasTable) {
        var nbLines = xmasTable.getLinesNb();

        var diagtopleftposXCase = xmasCase.getPos().getX()-1;
        var diagtoprightposXCase = xmasCase.getPos().getX()-1;
        var diagbottomleftposXCase = xmasCase.getPos().getX()+1;
        var diagbottomrightposXCase = xmasCase.getPos().getX()+1;

        var diagtopleftposYCase = xmasCase.getPos().getY()-1;
        var diagtoprightposYCase = xmasCase.getPos().getY()+1;
        var diagbottomleftposYCase = xmasCase.getPos().getY()-1;
        var diagbottomrightposYCase = xmasCase.getPos().getY()+1;

        List<XmasCaseOriented> xmasCases = new ArrayList<>();
        if (diagtopleftposXCase > -1 && diagtopleftposYCase > -1) {
            xmasCases.add(new XmasCaseOriented(
                    xmasTable.getxMasTable()[nbLines * diagtopleftposXCase + diagtopleftposYCase],
                    Direction.DIAG_TL)
            );
        }
        if (diagtoprightposXCase > -1 && diagtoprightposYCase < xmasTable.getColumnsNb()) {
            xmasCases.add(new XmasCaseOriented(
                    xmasTable.getxMasTable()[nbLines * diagtoprightposXCase + diagtoprightposYCase],
                    Direction.DIAG_TR)
            );
        }
        if (diagbottomleftposXCase < xmasTable.getLinesNb() && diagbottomleftposYCase > -1) {
            xmasCases.add(new XmasCaseOriented(
                    xmasTable.getxMasTable()[nbLines * diagbottomleftposXCase + diagbottomleftposYCase],
                    Direction.DIAG_BL)
            );
        }
        if (diagbottomrightposXCase < xmasTable.getLinesNb() && diagbottomrightposYCase < xmasTable.getColumnsNb()) {
            xmasCases.add(new XmasCaseOriented(
                    xmasTable.getxMasTable()[nbLines * diagbottomrightposXCase + diagbottomrightposYCase],
                    Direction.DIAG_BR)
            );
        }
        return xmasCases;
    }
    private List<XmasCaseOriented> getNeigbourCases(XmasCase xmasCase, XmasTable xmasTable) {
        var nbLines = xmasTable.getLinesNb();

        var leftposXCase = xmasCase.getPos().getX();
        var rightposXCase = xmasCase.getPos().getX();
        var bottomposXCase = xmasCase.getPos().getX()+1;
        var topposXCase = xmasCase.getPos().getX()-1;

        var leftposYCase = xmasCase.getPos().getY()-1;
        var rightposYCase = xmasCase.getPos().getY()+1;
        var bottomposYCase = xmasCase.getPos().getY();
        var topposYCase = xmasCase.getPos().getY();

        var diagtopleftposXCase = xmasCase.getPos().getX()-1;
        var diagtoprightposXCase = xmasCase.getPos().getX()-1;
        var diagbottomleftposXCase = xmasCase.getPos().getX()+1;
        var diagbottomrightposXCase = xmasCase.getPos().getX()+1;

        var diagtopleftposYCase = xmasCase.getPos().getY()-1;
        var diagtoprightposYCase = xmasCase.getPos().getY()+1;
        var diagbottomleftposYCase = xmasCase.getPos().getY()-1;
        var diagbottomrightposYCase = xmasCase.getPos().getY()+1;

        List<XmasCaseOriented> xmasCases = new ArrayList<>();
        if (diagtopleftposXCase > -1 && diagtopleftposYCase > -1) {
            xmasCases.add(new XmasCaseOriented(
                    xmasTable.getxMasTable()[nbLines * diagtopleftposXCase + diagtopleftposYCase],
                    Direction.DIAG_TL)
            );
        }
        if (diagtoprightposXCase > -1 && diagtoprightposYCase < xmasTable.getColumnsNb()) {
            xmasCases.add(new XmasCaseOriented(
                    xmasTable.getxMasTable()[nbLines * diagtoprightposXCase + diagtoprightposYCase],
                    Direction.DIAG_TR)
            );
        }
        if (diagbottomleftposXCase < xmasTable.getLinesNb() && diagbottomleftposYCase > -1) {
            xmasCases.add(new XmasCaseOriented(
                    xmasTable.getxMasTable()[nbLines * diagbottomleftposXCase + diagbottomleftposYCase],
                    Direction.DIAG_BL)
            );
        }
        if (diagbottomrightposXCase < xmasTable.getLinesNb() && diagbottomrightposYCase < xmasTable.getColumnsNb()) {
            xmasCases.add(new XmasCaseOriented(
                    xmasTable.getxMasTable()[nbLines * diagbottomrightposXCase + diagbottomrightposYCase],
                    Direction.DIAG_BR)
            );
        }
        if (leftposYCase > -1) {
            xmasCases.add(new XmasCaseOriented(
                    xmasTable.getxMasTable()[nbLines * leftposXCase + leftposYCase],
                    Direction.LEFT)
            );
        }
        if (rightposYCase < xmasTable.getColumnsNb()) {
            xmasCases.add(new XmasCaseOriented(
                    xmasTable.getxMasTable()[nbLines * rightposXCase + rightposYCase],
                    Direction.RIGHT)
            );
        }
        if (bottomposXCase < xmasTable.getLinesNb()) {
            xmasCases.add(new XmasCaseOriented(
                    xmasTable.getxMasTable()[nbLines * bottomposXCase + bottomposYCase],
                    Direction.BOTTOM)
            );
        }
        if (topposXCase > -1) {
            xmasCases.add(new XmasCaseOriented(
                    xmasTable.getxMasTable()[nbLines * topposXCase + topposYCase],
                    Direction.TOP)
            );
        }
    return xmasCases;
}

    private XmasCaseOriented getOrientedNeighbourCase(
            XmasCaseOriented orientedCase,
            XmasTable xmasTable
    ) {
        var nbLines = xmasTable.getLinesNb();
        var nbCols = xmasTable.getColumnsNb();

        XmasCaseOriented xmasCase = null;

        switch (orientedCase.getDirection()) {
            case TOP:
                var topposXCase = orientedCase.getPos().getX()-1;
                var topposYCase = orientedCase.getPos().getY();
                if (topposXCase > -1) {
                    xmasCase =  new XmasCaseOriented(
                            xmasTable.getxMasTable()[nbLines * topposXCase + topposYCase],
                            Direction.TOP
                    );
                }
                break;
            case LEFT:
                var leftposXCase = orientedCase.getPos().getX();
                var leftposYCase = orientedCase.getPos().getY()-1;
                if (leftposYCase > -1) {
                    xmasCase =  new XmasCaseOriented(
                            xmasTable.getxMasTable()[nbLines * leftposXCase + leftposYCase],
                            Direction.LEFT
                    );
                }
                break;
            case RIGHT:
                var rightposXCase = orientedCase.getPos().getX();
                var rightposYCase = orientedCase.getPos().getY()+1;
                if (rightposYCase < xmasTable.getColumnsNb()) {
                    xmasCase = new XmasCaseOriented(
                            xmasTable.getxMasTable()[nbLines * rightposXCase + rightposYCase],
                            Direction.RIGHT
                    );
                }
                break;
            case BOTTOM:
                var bottomposXCase = orientedCase.getPos().getX()+1;
                var bottomposYCase = orientedCase.getPos().getY();
                if (bottomposXCase < xmasTable.getLinesNb()) {
                    xmasCase = new XmasCaseOriented(
                            xmasTable.getxMasTable()[nbLines * bottomposXCase + bottomposYCase],
                            Direction.BOTTOM
                    );
                }
                break;
            case DIAG_BL:
                var blPosXCase = orientedCase.getPos().getX()+1;
                var blPosYCase = orientedCase.getPos().getY()-1;
                if (blPosXCase < xmasTable.getLinesNb() && blPosYCase > -1) {
                    xmasCase = new XmasCaseOriented(
                            xmasTable.getxMasTable()[nbLines * blPosXCase + blPosYCase],
                            Direction.DIAG_BL
                    );
                }
                break;
            case DIAG_BR:
                var brPosXCase = orientedCase.getPos().getX()+1;
                var brPosYCase = orientedCase.getPos().getY()+1;
                if (brPosXCase < xmasTable.getLinesNb() && brPosYCase < nbCols) {
                    xmasCase = new XmasCaseOriented(
                            xmasTable.getxMasTable()[nbLines * brPosXCase + brPosYCase],
                            Direction.DIAG_BR
                    );
                }
                break;
            case DIAG_TL:
                var tlPosXCase = orientedCase.getPos().getX()-1;
                var tlPosYCase = orientedCase.getPos().getY()-1;
                if (tlPosXCase > -1 && tlPosYCase > -1) {
                    xmasCase = new XmasCaseOriented(
                            xmasTable.getxMasTable()[nbLines * tlPosXCase + tlPosYCase],
                            Direction.DIAG_TL
                    );
                }
                break;
            case DIAG_TR:
                var trPosXCase = orientedCase.getPos().getX()-1;
                var trPosYCase = orientedCase.getPos().getY()+1;
                if (trPosXCase > -1 && trPosYCase < nbCols) {
                    xmasCase = new XmasCaseOriented(
                            xmasTable.getxMasTable()[nbLines * trPosXCase + trPosYCase],
                            Direction.DIAG_TR
                    );
                }
                break;
            default:
                break;
        }
        return xmasCase;
    }
}
