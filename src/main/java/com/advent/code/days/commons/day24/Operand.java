package com.advent.code.days.commons.day24;

public class Operand {

    private Boolean value;
    private String key;

    public Operand(String key, boolean value) {
        this.value = value;
        this.key = key;
    }

    public Operand(String key) {
        this.key = key;
    }

    public boolean getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operand operand = (Operand) o;
        return key.equals(operand.key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }
}
