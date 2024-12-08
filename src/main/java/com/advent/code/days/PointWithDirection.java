package com.advent.code.days;

public class PointWithDirection extends Point{
    private Direction direction;

    public PointWithDirection(int x, int y, Direction direction, boolean visited) {
        super(x, y);
        this.direction = direction;
        this.setVisited(visited);
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public boolean equals(Point p) {
        return super.equals(p);
    }
}
