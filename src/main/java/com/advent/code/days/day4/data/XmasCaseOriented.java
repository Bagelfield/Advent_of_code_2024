package com.advent.code.days.day4.data;

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

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
