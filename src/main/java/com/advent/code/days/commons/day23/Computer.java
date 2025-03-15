package com.advent.code.days.commons.day23;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Computer {

    private final String name;

    Map<String, Computer> linkedComputers = new HashMap<>();
    Set<Map<String, Computer>> computersInLanMapsSet = new HashSet<>();

    public Computer(String name) {
        this.name = name;
    }

    public boolean beginWith(String str) {
        return this.name.startsWith(str);
    }

    public void addComputer(Computer computer) {
        this.linkedComputers.put(computer.getName(), computer);
    }

    public void deleteComputer(Computer computer) {
        this.linkedComputers.remove(computer.getName());
    }

    public String getName() {
        return this.name;
    }

    public Map<String, Computer> getLinkedComputers() {
        return linkedComputers;
    }

    public void setLinkedComputers(Map<String, Computer> linkedComputers) {
        this.linkedComputers = linkedComputers;
    }

    public Set<Map<String, Computer>> getComputersInLanMapsSet() {
        return computersInLanMapsSet;
    }

    public void setComputersInLanMapsSet(Set<Map<String, Computer>> computersInLanMapsSet) {
        this.computersInLanMapsSet = computersInLanMapsSet;
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
