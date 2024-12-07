package com.advent.code.day4.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class XmasCase {
    private Pos pos;
    private String content;

    public String display() {
        return "(" + getPos().getX()
                + ", " + getPos().getY()
                + ") : " + getContent();
    }
}
