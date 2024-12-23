package com.advent.code.days.day18;

import com.advent.code.AdventOfCode;
import com.advent.code.days.Day;
import com.advent.code.days.commons.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.logging.Logger.getGlobal;

public class Day18 extends Day {

    static Logger logger = getGlobal();

    public Day18() {
        super(18);
    }

    @Override
    public Object process(boolean isTest) throws IOException {
        try (FileReader file = new FileReader(isTest ? fileNameTest : fileName);
             BufferedReader buffer = new BufferedReader(file)) {
            List<Point> points = buffer.lines()
                    .map(line -> {
                        String[] split = line.split(",");
                        return new Point(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
                    })
                    .collect(Collectors.toList());

            int horizontalSize = points.stream().max(Comparator.comparingInt(Point::getX)).orElseThrow().getX();
            int verticalSize = points.stream().max(Comparator.comparingInt(Point::getY)).orElseThrow().getY();

            char[][] tab = new char[verticalSize+1][verticalSize+1];
            for (int i = 0; i < tab[0].length; i++) {
                Arrays.fill(tab[i], '.');
            }
            for (int indexPoint = 0; indexPoint < 1024; indexPoint++) {
                int x = points.get(indexPoint).getX();
                int y = points.get(indexPoint).getY();
                tab[y][x] = 'X';
            }

            AdventOfCode.display(tab);

            List<Node> listeNodeGraph = new LinkedList<>();
            int posX = 0;
            int posY = 0;
            var curentNode = new Node(posX + "," + posY);
            listeNodeGraph.add(curentNode);
            fillNode(tab, listeNodeGraph, posX, posY, curentNode);
            Graph graph = new Graph();
            for (var node : listeNodeGraph) {
                graph.addNode(node);
            }
            Dijkstra.calculateShortestPathFromSource(graph, listeNodeGraph.get(0));

            final String expectedNode = "70,70";

            List<Node> shortestPath = graph
                    .getNodes()
                    .stream()
                    .filter(node -> node.getName().equals(expectedNode))
                    .findAny()
                    .orElseThrow()
                    .getShortestPath();
            for (var node : shortestPath) {
                String[] split = node.getName().split(",");
                tab[Integer.parseInt(split[0])][Integer.parseInt(split[1])] = 'O';
            }
            AdventOfCode.display(tab);
            return String.valueOf(graph
                    .getNodes()
                    .stream()
                    .filter(node -> node.getName().equals(expectedNode))
                    .findAny()
                    .orElseThrow()
                    .getShortestPath()
                    .size());
        }
    }

    private void fillNode(char[][] tab, List<Node> listNodeGraph, int posX, int posY, Node curentNode) {
        List<Position> positionList = getAdjacentPos(posX, posY, tab);
        if (listNodeGraph.size() > 1) {
            positionList = positionList
                    .stream()
                    .filter(position -> listNodeGraph.stream().noneMatch(node -> node.getName()
                            .equals(position.getX() + "," + position.getY())))
                    .collect(Collectors.toList());
        }
        for (var position : positionList) {
            Node newNode = new Node(position.getX() + "," + position.getY());
            if (listNodeGraph.contains(newNode)) {
                newNode = listNodeGraph.get(listNodeGraph.indexOf(newNode));
            } else {
                listNodeGraph.add(newNode);
            }
            curentNode.addDestination(newNode, 1);
            fillNode(tab, listNodeGraph, position.getX(), position.getY(), newNode);
        }
    }

    private List<Position> getAdjacentPos(int indexH, int indexV, char[][] tabOfContent) {
        List<Position> adjacentPos = new ArrayList<>();
        var top = new Position(indexH, indexV - 1);
        if (top.getY() > -1
                && tabOfContent[top.getX()][top.getY()] == '.') {
            adjacentPos.add(top);
        }

        var bottom = new Position(indexH, indexV + 1);
        if (bottom.getY() < tabOfContent[0].length
                && tabOfContent[bottom.getX()][bottom.getY()] == '.') {
            adjacentPos.add(bottom);
        }

        var left = new Position(indexH - 1, indexV);
        if (left.getX() > -1
                && tabOfContent[left.getX()][left.getY()] == '.') {
            adjacentPos.add(left);
        }

        var right = new Position(indexH + 1, indexV);
        if (right.getX() < tabOfContent.length
                && tabOfContent[right.getX()][right.getY()] == '.') {
            adjacentPos.add(right);
        }

        return adjacentPos;
    }

}

