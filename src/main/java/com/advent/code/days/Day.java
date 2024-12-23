package com.advent.code.days;

import java.io.IOException;

public abstract class Day {
    protected int nbDay;
    protected String fileName;
    protected String fileNameTest;
    public static final String PREFIX_URL =
            "C:\\Users\\vmigot\\Documents\\DEVELOPPEMENT\\Advent of code\\Advent_of_code_2024\\src\\main\\java\\com\\advent\\code\\files\\";
    public static final String EXTENSION = ".txt";
    public abstract Object process(boolean isTest) throws IOException;
    public String getName() {
        return PREFIX_URL + "DAY_" + nbDay + "_" + nbDay + EXTENSION;
    }
    public String getNameTest() {
        return PREFIX_URL + "DAY_" + nbDay + "_test" + nbDay + EXTENSION;
    }
    protected Day(int nbDay) {
        this.nbDay = nbDay;
        this.fileName = getName();
        this.fileNameTest = getNameTest();
    }
    public int getNbDay() {
        return nbDay;
    }
    public void setNbDay(int nbDay) {
        this.nbDay = nbDay;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
