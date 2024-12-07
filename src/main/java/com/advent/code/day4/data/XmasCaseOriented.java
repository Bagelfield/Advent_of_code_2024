package com.advent.code.day4.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class XmasCaseOriented extends XmasCase {
    private Direction direction;

    public XmasCaseOriented(int posX, int posY, String content, Direction direction) {
        super(new Pos(posX, posY), content);
        this.direction = direction;
    }

    public XmasCaseOriented(XmasCase xmasCase, Direction direction) {
        super(xmasCase.getPos().getCopy(), xmasCase.getContent());
        this.direction = direction;
    }
}
