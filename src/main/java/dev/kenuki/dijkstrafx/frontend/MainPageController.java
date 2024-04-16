package dev.kenuki.dijkstrafx.frontend;

import dev.kenuki.dijkstrafx.backend.Engine;
import dev.kenuki.dijkstrafx.util.Block;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

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
    private final int ROWS = 50, COLUMNS = 50, SIZE = 10;
    private Engine engine;
    private Timeline autoPlay;
    @FXML
    private void onAutoPlayBtnClicked() {
        if(autoPlayBtn.getStyle().contains("#01b075")) {
            autoPlayBtn.setStyle("-fx-background-radius:50;-fx-background-color:#ea5645");
            Duration duration = new Duration(16);
            autoPlay = new Timeline(new KeyFrame(duration, actionEvent -> {
                engine.nextIteration();
                fieldController.updateFrame();
            }));
            autoPlay.setCycleCount(Timeline.INDEFINITE);
            autoPlay.play();

        }else {
            autoPlayBtn.setStyle("-fx-background-radius:50;-fx-background-color:#01b075");
            autoPlay.stop();
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