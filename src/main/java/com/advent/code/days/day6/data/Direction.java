package com.advent.code.days.day6.data;

public enum Direction {
    NORTH, EAST, SOUTH, WEST;

    public boolean equals(Direction direction) {
        return this.ordinal() == direction.ordinal();
    }
}
