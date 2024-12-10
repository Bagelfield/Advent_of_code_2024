package com.advent.code.days.day4.data;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class XmasTable {
    private XmasCase[] xMasTable;
    private int linesNb;
    private int columnsNb;

    public XmasTable() {}

    public XmasTable(Stream<String> fileLineContent) {
        List<String> lines = fileLineContent.collect(Collectors.toList());
        linesNb = lines.size();
        columnsNb = lines.stream().findFirst().orElseThrow().length();
        xMasTable = new XmasCase[linesNb*columnsNb];

        AtomicInteger index = new AtomicInteger();
        IntStream
                .range(0, lines.size())
                .forEach(posX -> IntStream.range(0, lines.get(posX).length())
                        .forEach(posY -> {
                            xMasTable[index.get()] = new XmasCase(
                                    new Pos(posX, posY),
                                    String.valueOf(lines.get(posX).charAt(posY))
                            );
                            index.getAndIncrement();
                        }
                        )
                );
    }

    public String display() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < this.linesNb*this.columnsNb; i++) {
            var xmasCase = xMasTable[i];
            if (i % this.columnsNb == 0) {
                sb.append("\n");
            }
            sb
                .append(xmasCase.getContent())
                .append(" ");
        }
        return sb.toString();
    }

    public XmasCase[] getxMasTable() {
        return xMasTable;
    }

    public void setxMasTable(XmasCase[] xMasTable) {
        this.xMasTable = xMasTable;
    }

    public int getLinesNb() {
        return linesNb;
    }

    public void setLinesNb(int linesNb) {
        this.linesNb = linesNb;
    }

    public int getColumnsNb() {
        return columnsNb;
    }

    public void setColumnsNb(int columnsNb) {
        this.columnsNb = columnsNb;
    }
}
