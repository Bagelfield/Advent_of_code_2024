package com.advent.code.days.commons.day24;

public class State {
    Integer value;

    void setValue(int value) {
        if (this.value != null) {
            throw new IllegalStateException();
        }
        this.value = value;
    }
}