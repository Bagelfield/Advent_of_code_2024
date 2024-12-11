package com.advent.code.days.commons;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MipMap {
    String[][] mipmap;
    int height;
    int width;
    Set<Position> nodes = new HashSet<>();
    Map<String, List<Position>> antennas  = new HashMap<>();

    public MipMap(Stream<String> lines) {
        var listLines = lines
                .map(line -> line.split(""))
                .collect(Collectors.toList());

        this.height = listLines.size();
        this.width = listLines.get(0).length;
        this.mipmap = new String[this.height][this.width];

        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                this.mipmap[h][w] = listLines.get(h)[w];
                if (!this.mipmap[h][w].equals(".")) {
                    if (this.antennas.containsKey(this.mipmap[h][w])) {
                        this.antennas.get(this.mipmap[h][w]).add(new Position(h, w));
                    } else {
                        this.antennas.put(this.mipmap[h][w], new ArrayList<>());
                        this.antennas.get(this.mipmap[h][w]).add(new Position(h, w));
                    }
                }
            }
        }
    }

    public Set<Position> getNodes() {
        return nodes;
    }

    public void setNodes(Set<Position> nodes) {
        this.nodes = nodes;
    }

    public Map<String, List<Position>> getAntennas() {
        return antennas;
    }

    public boolean isInOfMipMap(Position position) {
        return position.getX() < this.height && position.getX() >= 0
                && position.getY() < this.width && position.getY() >= 0;
    }

    public void setAntennas(Map<String, List<Position>> antennas) {
        this.antennas = antennas;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String[][] getMipmap() {
        return mipmap;
    }

    public void setMipmap(String[][] mipmap) {
        this.mipmap = mipmap;
    }
}
