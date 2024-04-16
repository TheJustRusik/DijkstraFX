package dev.kenuki.dijkstrafx.frontend;

import dev.kenuki.dijkstrafx.util.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;

public class FieldController {
    private final Color wallColor = Color.web("#ffffff");
    private final Color airColor = Color.web("#16171b");
    private final Color startColor = Color.web("#6cff00");
    private final Color finishColor = Color.web("#ff4949");
    private final Color roadColor = Color.ORANGE;
    private final Color checkedColor = Color.rgb(0, 255,255, 0.2);

    private Block currentPen;
    private GridRectangle startCell;
    private GridRectangle finishCell;
    private final int width;
    private final int height;
    private boolean canDraw;
    public final Block[][] gameField;
    private final GridRectangle[][] rectangleMatrix;
    private final GridPane field;

    public GridPane getField() {
        return field;
    }

    public Cell getStartCell() {
        return new Cell(startCell);
    }
    public Cell getFinishCell() {
        return new Cell(finishCell);
    }
    public void setCurrentPen(Block block) {
        currentPen = block;
    }
    public FieldController(int rows, int columns, int cellSize) {
        canDraw = true;
        width = columns;
        height = rows;
        currentPen = Block.WALL;
        field = new GridPane();
        rectangleMatrix = new GridRectangle[rows][columns];
        gameField = new Block[rows][columns];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                rectangleMatrix[row][col] = new GridRectangle(col, row, cellSize, cellSize, airColor);
                rectangleMatrix[row][col].setStrokeWidth(1);
                rectangleMatrix[row][col].setStrokeType(StrokeType.INSIDE);
                rectangleMatrix[row][col].setStroke(Color.web("#202124"));
                field.add(rectangleMatrix[row][col], col, row);
                GridRectangle tmp = rectangleMatrix[row][col];
                gameField[row][col] = Block.AIR;

                tmp.setOnMouseClicked(event -> {
                    if (!canDraw) {
                        return;
                    }

                    if (currentPen == Block.AIR) {
                        gameField[tmp.y][tmp.x] = Block.AIR;

                        if (tmp == startCell) {
                            startCell.setStrokeWidth(1);
                            startCell = null;

                        }
                        else if(tmp == finishCell) {
                            finishCell.setStrokeWidth(1);
                            finishCell = null;
                        }

                        tmp.setFill(airColor);
                        tmp.setStrokeWidth(1);
                    }else if (currentPen == Block.WALL) {

                        gameField[tmp.y][tmp.x] = Block.WALL;

                        if (tmp == startCell) {
                            startCell.setStrokeWidth(1);
                            startCell = null;
                        }
                        else if(tmp == finishCell) {
                            finishCell.setStrokeWidth(1);
                            finishCell = null;
                        }

                        tmp.setFill(wallColor);
                        tmp.setStrokeWidth(0);
                        System.out.println("WALL AT: y:" + tmp.y + " x:" + tmp.x);
                    } else if (currentPen == Block.START) {

                        gameField[tmp.y][tmp.x] = Block.START;
                        if(finishCell == tmp) {
                            finishCell.setStrokeWidth(1);
                            finishCell = null;
                        }
                        if(startCell != null) {
                            gameField[startCell.y][startCell.y] = Block.AIR;
                            startCell.setStrokeWidth(1);
                            startCell.setFill(airColor);
                        }
                        startCell = tmp;
                        tmp.setFill(startColor);
                        tmp.setStrokeWidth(0);
                    } else if (currentPen == Block.FINISH) {

                        gameField[tmp.y][tmp.x] = Block.FINISH;
                        if (startCell == tmp) {
                            startCell.setStrokeWidth(1);
                            startCell = null;
                        }
                        if(finishCell != null) {
                            gameField[finishCell.y][finishCell.y] = Block.AIR;
                            finishCell.setStrokeWidth(1);
                            finishCell.setFill(airColor);
                        }
                        finishCell = tmp;
                        tmp.setFill(finishColor);
                        tmp.setStrokeWidth(0);
                    }
                });
            }
        }
    }
    public void updateFrame() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                rectangleMatrix[row][col].setStrokeWidth(0);
                switch (gameField[row][col]) {
                    case WALL    -> rectangleMatrix[row][col].setFill(wallColor);
                    case START   -> rectangleMatrix[row][col].setFill(startColor);
                    case FINISH  -> rectangleMatrix[row][col].setFill(finishColor);
                    case ROAD    -> rectangleMatrix[row][col].setFill(roadColor);
                    case CHECKED -> rectangleMatrix[row][col].setFill(checkedColor);
                    default -> {
                        rectangleMatrix[row][col].setFill(airColor);
                        rectangleMatrix[row][col].setStrokeWidth(1);
                    }
                }
            }
        }
    }

    public void lockDrawing() {
        canDraw = false;
    }
}
