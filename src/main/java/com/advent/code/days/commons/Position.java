package com.advent.code.days.commons;

import java.util.Objects;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
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

    public int distanceH(Position p) {
        return p.y - this.y;
    }

    public int distanceW(Position p) {
        return p.x - this.x;
    }

    public int absValue(int x) {
        return Math.abs(x);
    }

    public Position plus(int x, int y) {
        return new Position(this.x + x, this.y + y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Position position = (Position) obj;
        return this.x == position.x && this.y == position.y;
    }
}
