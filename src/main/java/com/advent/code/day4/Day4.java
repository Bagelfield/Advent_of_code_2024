package com.advent.code.day4;

import com.advent.code.day4.data.XmasTable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day4 {

    //@Override
    public Object process(String fileName) throws IOException {
        int result = 0;

        try (FileReader file = new FileReader(fileName);
             BufferedReader buffer = new BufferedReader(file)) {
            var stream = buffer.lines();
            XmasTable xmasTable = new XmasTable(stream);

            System.out.println(xmasTable.display());
        }
        return result;
    }


}
