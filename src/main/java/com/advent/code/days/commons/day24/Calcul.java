package com.advent.code.days.commons.day24;

import java.util.List;

public class Calcul {

    private OperationEnum operationEnum;
    private Operand operand1, operand2, result;

    public Calcul(
            OperationEnum operationEnum,
            Operand operand1,
            Operand operand2,
            Operand result
    ) {
        this.operationEnum = operationEnum;
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.result = result;
    }

    public boolean execute() {
        return this.operationEnum.execute(this.operand1, this.operand2);
    }

    public OperationEnum getOperation() {
        return operationEnum;
    }

    public void setOperation(OperationEnum operationEnum) {
        this.operationEnum = operationEnum;
    }

    public Operand getOperand1() {
        return operand1;
    }

    public void setOperand1(Operand operand1) {
        this.operand1 = operand1;
    }

    public Operand getOperand2() {
        return operand2;
    }

    public void setOperand2(Operand operand2) {
        this.operand2 = operand2;
    }

    public Operand getResult() {
        return result;
    }

    public void setResult(Operand result) {
        this.result = result;
    }
}
