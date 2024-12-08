package com.advent.code.days;

public class Point {
    private int x;
    private int y;
    private String content;
    private boolean visited;

    public Point() {}

    public Point(int x, int y, String content) {
        this.x = x;
        this.y = y;
        this.content = content;
        this.visited = false;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}

