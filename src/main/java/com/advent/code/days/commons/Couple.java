package com.advent.code.days.commons;

public class Couple {
    private Computer computerA;
    private Computer computerB;

    public Couple(Computer computerA, Computer computerB) {
        this.computerA = computerA;
        this.computerB = computerB;
    }

    public Computer getComputerA() {
        return computerA;
    }

    public void setComputerA(Computer computerA) {
        this.computerA = computerA;
    }

    public Computer getComputerB() {
        return computerB;
    }

    public void setComputerB(Computer computerB) {
        this.computerB = computerB;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Couple couple = (Couple) o;
        return (couple.getComputerA().equals(this.computerA)
                && couple.getComputerB().equals(this.computerB))
                || (couple.getComputerA().equals(this.computerB)
                && couple.getComputerB().equals(this.computerA));
    }

    @Override
    public int hashCode() {
        return computerA.hashCode() + computerB.hashCode();
    }
}
