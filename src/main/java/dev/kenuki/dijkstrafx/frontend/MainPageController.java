package dev.kenuki.dijkstrafx.frontend;

import dev.kenuki.dijkstrafx.backend.Engine;
import dev.kenuki.dijkstrafx.backend.algorithm.BFS;
import dev.kenuki.dijkstrafx.util.Block;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

import java.util.Arrays;

public class MainPageController {
    @FXML
    private AnchorPane body;
    @FXML
    private VBox styleClear;
    @FXML
    private VBox styleWall;
    @FXML
    private VBox styleStart;
    @FXML
    private VBox styleFinish;
    @FXML
    private Button playBtn;
    @FXML
    private Button nextBtn;
    @FXML
    private Button autoPlayBtn;

    private final int ROWS = 20, COLUMNS = 20, SIZE = 25;
    private Engine engine;
    private boolean nowAutoPlay = false;
    @FXML
    private void onAutoPlayBtnClicked() {
        if(autoPlayBtn.getStyle().contains("#01b075")) {
            nowAutoPlay = true;
            autoPlayBtn.setStyle("-fx-background-radius:50;-fx-background-color:#ea5645");
            Thread autoPlay = new Thread(() -> {
                while (nowAutoPlay) {
                    engine.nextIteration();
                    fieldController.updateFrame();
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            autoPlay.start();
        }else {
            autoPlayBtn.setStyle("-fx-background-radius:50;-fx-background-color:#01b075");
            nowAutoPlay = false;
        }
    }
    @FXML
    private void onNextBtnClicked() {
        engine.nextIteration();
        fieldController.updateFrame();
    }

    @FXML
    private void onSearchBtnClicked() throws Exception {
        if(playBtn.getText().equals("Search!")) {
            playBtn.setText("Restart!");
            nextBtn.setVisible(true);
            autoPlayBtn.setVisible(true);
            fieldController.lockDrawing();
            engine = new Engine(fieldController.gameField, fieldController.getStartCell(), fieldController.getFinishCell());
            engine.launch();
        } else {
            body.getChildren().clear();
            nextBtn.setVisible(false);
            autoPlayBtn.setVisible(false);
            fieldController = new FieldController(ROWS,COLUMNS,SIZE);
            GridPane field = fieldController.getField();
            AnchorPane.setTopAnchor(field, 0.0);
            AnchorPane.setBottomAnchor(field, 0.0);
            AnchorPane.setLeftAnchor(field, 0.0);
            AnchorPane.setRightAnchor(field, 0.0);
            body.getChildren().add(field);

            playBtn.setText("Search!");
        }
    }
    @FXML
    private void onStyleClearClicked() {
        styleClear.setStyle("-fx-border-color: white; -fx-border-radius: 5; -fx-border-width: 2;");
        styleStart.setStyle("");
        styleFinish.setStyle("");
        styleWall.setStyle("");

        fieldController.setCurrentPen(Block.AIR);
    }
    @FXML
    private void onStyleWallClicked() {
        styleWall.setStyle("-fx-border-color: white; -fx-border-radius: 5; -fx-border-width: 2;");
        styleStart.setStyle("");
        styleFinish.setStyle("");
        styleClear.setStyle("");

        fieldController.setCurrentPen(Block.WALL);
    }
    @FXML
    private void onStyleStartClicked() {
        styleStart.setStyle("-fx-border-color: white; -fx-border-radius: 5; -fx-border-width: 2;");
        styleClear.setStyle("");
        styleFinish.setStyle("");
        styleWall.setStyle("");

        fieldController.setCurrentPen(Block.START);
    }
    @FXML
    private void onStyleFinishClicked() {
        styleFinish.setStyle("-fx-border-color: white; -fx-border-radius: 5; -fx-border-width: 2;");
        styleStart.setStyle("");
        styleClear.setStyle("");
        styleWall.setStyle("");

        fieldController.setCurrentPen(Block.FINISH);
    }


    private FieldController fieldController;


    public void initialize() {

        fieldController = new FieldController(ROWS,COLUMNS,SIZE);//Good combinations: 50,50,10 | 20,20,25 | 10,10,50
        GridPane field = fieldController.getField();
        onStyleWallClicked();

        AnchorPane.setTopAnchor(field, 0.0);
        AnchorPane.setBottomAnchor(field, 0.0);
        AnchorPane.setLeftAnchor(field, 0.0);
        AnchorPane.setRightAnchor(field, 0.0);



        body.getChildren().add(field);
    }
}