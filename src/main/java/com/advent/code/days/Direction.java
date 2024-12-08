package com.advent.code.days;

public enum Direction {
    NORTH, EAST, SOUTH, WEST;

    public boolean equals(Direction direction) {
        return this.ordinal() == direction.ordinal();
    }
}
