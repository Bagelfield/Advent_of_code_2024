package com.advent.code.days.day6.data;

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

    @Override
    public boolean equals(Point p) {
        return super.equals(p);
    }

    public boolean equals(PointWithDirection p) {
        return super.equals(p) && p.getDirection().equals(direction);
    }
}
