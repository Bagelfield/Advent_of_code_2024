package com.advent.code.days.day8;

import com.advent.code.days.Day;
import com.advent.code.days.commons.MipMap;
import com.advent.code.days.commons.Position;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day8 extends Day {
    public Day8() {
        super(8);
    }

    @Override
    public Object process(boolean isTest) throws IOException {
        long count;
        try (FileReader file = new FileReader(isTest ? fileNameTest : fileName);
             BufferedReader buffer = new BufferedReader(file)) {
            var streamLines = buffer.lines();
            var mipmap = new MipMap(streamLines);
            setNodesFrom(mipmap.getAntennas(), mipmap);
            count = mipmap.getNodes().size();
        }
        return count;
    }

    private void setNodesFrom(Map<String, List<Position>> antennas, MipMap mipMap) {
        for (var entry : antennas.entrySet()) {
            var antennaName = entry.getKey();
            var antennaPositions = entry.getValue();

            if (antennaPositions.size() > 1) {
                List<Position> antennasToCompare = new ArrayList<>(antennaPositions);
                for (var position : antennaPositions) {
                    mipMap.getNodes().add(position);
                    antennasToCompare.remove(position);
                    for (var position1 : antennasToCompare) {
                        var distanceH = position.distanceH(position1);
                        var distanceW = position.distanceW(position1);
                        var linearMultiple = 1;
                        Position node1 = new Position(
                                position.getX() - distanceW,
                                position.getY() - distanceH
                        );
                        Position node2 = new Position(
                                position1.getX() + distanceW,
                                position1.getY() + distanceH
                        );
                        while(mipMap.isInOfMipMap(node1)) {
                            linearMultiple++;
                            mipMap.getNodes().add(node1);
                            node1 = new Position(
                                    position.getX() - linearMultiple*distanceW,
                                    position.getY() - linearMultiple*distanceH
                            );
                        }
                        linearMultiple = 1;
                        while(mipMap.isInOfMipMap(node2)) {
                            linearMultiple++;
                            mipMap.getNodes().add(node2);
                            node2 = new Position(
                                    position1.getX() + linearMultiple*distanceW,
                                    position1.getY() + linearMultiple*distanceH
                            );
                        }
                    }
                }
            }
        }
    }
}

