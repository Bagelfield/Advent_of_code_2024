package com.advent.code.days;

public class MapGuard {
    private Point[][] map;
    private Point guardPosition;
    private Point guardInitialPosition;
    private Direction guardDirection;
    private Direction guardInitialDirection;
    private int width;
    private int height;

    public MapGuard() {}

    public MapGuard(int width, int height, String[][] tab) {
        this.width = width;
        this.height = height;
        this.map = new Point[width][height];
        for (int y = 0; y < width; y++) {
            for (int x = 0; x < height; x++) {
                this.map[x][y] = new Point(x,y, tab[y][x]);
                if (tab[y][x].equals("^")) {
                    this.guardPosition = this.map[x][y];
                    this.guardInitialPosition = this.map[x][y];
                    this.guardDirection = Direction.NORTH;
                    this.guardInitialDirection = Direction.NORTH;
                    this.map[x][y] = new Point(x,y, "O");
                    this.map[x][y].setVisited(true);
                }
            }
        }
    }

    public Point[][] getMap() {
        return map;
    }

    public void setMap(Point[][] map) {
        this.map = map;
    }

    public Point getGuardPosition() {
        return guardPosition;
    }

    public void setGuardPosition(Point guardPosition) {
        this.guardPosition = guardPosition;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Direction getGuardDirection() {
        return guardDirection;
    }

    public void setGuardDirection(Direction guardDirection) {
        this.guardDirection = guardDirection;
    }

    public Point getGuardInitialPosition() {
        return guardInitialPosition;
    }

    public void setGuardInitialPosition(Point guardInitialPosition) {
        this.guardInitialPosition = guardInitialPosition;
    }

    public Direction getGuardInitialDirection() {
        return guardInitialDirection;
    }

    public void setGuardInitialDirection(Direction guardInitialDirection) {
        this.guardInitialDirection = guardInitialDirection;
    }

    public void displayMap() {
        for (int y = 0; y < width; y++) {
            for (int x = 0; x < height; x++) {
                System.out.print(this.map[x][y].getContent());
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }
}
