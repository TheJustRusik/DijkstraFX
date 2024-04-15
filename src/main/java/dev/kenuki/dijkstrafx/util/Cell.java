package dev.kenuki.dijkstrafx.util;

public class Cell {
    public int x, y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Cell(GridRectangle gridRectangle) {
        x = gridRectangle.x;
        y = gridRectangle.y;
    }
}