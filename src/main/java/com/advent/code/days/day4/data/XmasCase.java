package com.advent.code.days.day4.data;

public class XmasCase {
    private Pos pos;
    private String content;

    public XmasCase(Pos pos, String content) {
        this.pos = pos;
        this.content = content;
    }

    public String display() {
        return "(" + getPos().getX()
                + ", " + getPos().getY()
                + ") : " + getContent();
    }

    public Pos getPos() {
        return pos;
    }

    public void setPos(Pos pos) {
        this.pos = pos;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
