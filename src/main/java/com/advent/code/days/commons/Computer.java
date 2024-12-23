package com.advent.code.days.commons;

import java.util.ArrayList;
import java.util.List;

public class Computer {

    private final String name;

    List<Computer> computers = new ArrayList<>();

    public Computer(String name) {
        this.name = name;
    }

    public boolean beginWith(String str) {
        return this.name.startsWith(str);
    }

    public void addComputer(Computer computer) {
        this.computers.add(computer);
    }

    public void deleteComputer(Computer computer) {
        this.computers.remove(computer);
    }

    public String getName() {
        return name;
    }

    public List<Computer> getLinkedComputers() {
        return computers;
    }

    public void setComputers(List<Computer> computers) {
        this.computers = computers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Computer computer = (Computer) o;
        return name.equals(computer.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
