package com.advent.code.days.commons.day24;

public enum GateType {
    AND {
        @Override
        Integer apply(Integer a, Integer b) {
            return a & b;
        }
    }, OR {
        @Override
        Integer apply(Integer a, Integer b) {
            return a | b;
        }
    }, XOR {
        @Override
        Integer apply(Integer a, Integer b) {
            return a ^ b;
        }
    };

    abstract Integer apply(Integer a, Integer b);
}