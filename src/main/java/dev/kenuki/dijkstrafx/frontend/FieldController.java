package dev.kenuki.dijkstrafx.frontend;

import dev.kenuki.dijkstrafx.util.Block;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class FieldController {
    private Block currentPen;
    private StackPane startCell;
    private StackPane finishCell;
    private final int width;
    private final int height;
    private boolean canDraw;

    private final GridPane field;

    public GridPane getField() {
        return field;
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
        field.setGridLinesVisible(true);


        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                StackPane cell = new StackPane();
                cell.setMinSize(cellSize,cellSize);

                field.add(cell, col, row);


                cell.setOnMouseClicked(event -> {
                    if (!canDraw) {
                        return;
                    }
                    if (currentPen == Block.AIR) {

                        if(cell == finishCell) {
                            finishCell = null;
                        } else if (cell == startCell) {
                            startCell = null;
                        }

                        cell.getChildren().clear();
                        return;
                    }
                    Rectangle rectangle = new Rectangle(cellSize,cellSize);
                    if (currentPen == Block.WALL) {
                        if(cell == finishCell) {
                            finishCell = null;
                        } else if (cell == startCell) {
                            startCell = null;
                        }

                        rectangle.setFill(Color.web("#ffffff"));
                    } else if (currentPen == Block.START) {
                        if(cell == finishCell) {
                            finishCell = null;
                        } else if (cell == startCell) {
                            startCell = null;
                        }

                        if (startCell != null) {
                            startCell.getChildren().clear();
                            startCell = null;
                        }
                        rectangle.setFill(Color.web("#6cff00"));
                        startCell = cell;
                    } else if (currentPen == Block.FINISH) {
                        if(cell == finishCell) {
                            finishCell = null;
                        } else if (cell == startCell) {
                            startCell = null;
                        }

                        if (finishCell != null) {
                            finishCell.getChildren().clear();
                            finishCell = null;
                        }
                        rectangle.setFill(Color.web("#ff4949"));
                        finishCell = cell;
                    } else {
                        cell.getChildren().clear();
                        return;
                    }
                    cell.getChildren().clear();
                    cell.getChildren().add(rectangle);
                });
            }
        }
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public void lockDrawing() {
        canDraw = false;
    }
}
