package com.advent.code.days.commons.day24;

public enum OperationEnum {
    AND,
    XOR,
    OR;

    public boolean execute(Operand one, Operand two) {
        switch (this) {
            case AND:
                return one.getValue() && two.getValue();
            case XOR:
                return one.getValue() != two.getValue();
            case OR:
                return one.getValue() || two.getValue();
            default:
                return false;
        }
    }
}
