package dev.kenuki.dijkstrafx.util;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class GridRectangle extends Rectangle {
    public int x, y;
    public GridRectangle(int x, int y, int w, int h, Paint p) {
        super(w,h,p);
        this.x = x;
        this.y = y;

    }
}
