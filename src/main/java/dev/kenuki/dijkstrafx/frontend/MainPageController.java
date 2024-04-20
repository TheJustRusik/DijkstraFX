package dev.kenuki.dijkstrafx.frontend;

import dev.kenuki.dijkstrafx.backend.Engine;
import dev.kenuki.dijkstrafx.util.AlgoTypes;
import dev.kenuki.dijkstrafx.util.Block;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.Random;

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
    @FXML
    private ChoiceBox<Integer> choiceBox;
    @FXML
    private Button randomBtn;
    @FXML
    private Button clearBtn;
    @FXML
    private Slider animationSlider;
    private int ROWS = 20, COLUMNS = 20, SIZE = 25;
    private Engine engine;
    private Timeline autoPlay;
    boolean animationPlaying;
    @FXML
    private void onAutoPlayBtnClicked() {
        if(animationPlaying) {
            turnOffAnimation();
        }else {
            turnOnAnimation();
        }
    }
    @FXML
    private void onAnimationDelayChanged() {
        try {
            if (autoPlay != null)
                autoPlay.stop();
            createTimeLine();
            if (animationPlaying)
                autoPlay.play();
        }catch (Exception ignored) {

        }

    }

    private void createTimeLine() {
        Duration duration = new Duration(animationSlider.getValue());
        autoPlay = new Timeline(new KeyFrame(duration, actionEvent -> {
            System.out.println("TimeLine: " + autoPlay.hashCode());
            engine.nextIteration();
            fieldController.updateFrame();
            if (!engine.hasNextIteration()) {
                turnOffAnimation();

            }


        }));
        autoPlay.setCycleCount(Timeline.INDEFINITE);
    }

    private void turnOffAnimation() {
        animationPlaying = false;
        autoPlayBtn.setStyle("-fx-background-radius:50;-fx-background-color:#01b075");
        autoPlay.stop();
        autoPlay = null;

    }
    private void turnOnAnimation() {
        animationPlaying = true;
        autoPlayBtn.setStyle("-fx-background-radius:50;-fx-background-color:#ea5645");

        createTimeLine();

        autoPlay.play();
    }
    @FXML
    private void onNextBtnClicked() {
        if(engine.hasNextIteration()) {
            engine.nextIteration();
            fieldController.updateFrame();
        }
    }

    @FXML
    private void onSearchBtnClicked() {
        if(playBtn.getText().equals("Search!")) {
            choiceBox.setDisable(true);
            randomBtn.setDisable(true);
            clearBtn.setDisable(true);
            playBtn.setText("Restart!");
            nextBtn.setVisible(true);
            autoPlayBtn.setVisible(true);
            fieldController.lockDrawing();
            engine = new Engine(fieldController.gameField, fieldController.getStartCell(), fieldController.getFinishCell(), AlgoTypes.BFS);
            engine.launch();
        } else {
            fieldSetup();
            if (autoPlay != null)
                autoPlay.stop();
            autoPlayBtn.setStyle("-fx-background-radius:50;-fx-background-color:#01b075");
            nextBtn.setVisible(false);
            choiceBox.setDisable(false);
            autoPlayBtn.setVisible(false);
            randomBtn.setDisable(false);
            clearBtn.setDisable(false);

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
    @FXML
    private void onClearBtnClicked() {
        for(int i = 0; i < fieldController.getHeight(); i++)
            for(int j = 0; j < fieldController.getWidth(); j++)
                fieldController.gameField[i][j] = Block.AIR;
        fieldController.updateFrame();
    }
    @FXML
    private void onRandomBtnClicked() {
        Random random = new Random(System.currentTimeMillis());
        for(int i = 0; i < fieldController.getHeight(); i++) {
            for(int j = 0; j < fieldController.getWidth(); j++) {
                fieldController.gameField[i][j] = Block.AIR;
                if (random.nextInt(1,10) > 6) {
                    fieldController.gameField[i][j] = Block.WALL;
                }
            }
        }
        fieldController.updateFrame();
    }


    private FieldController fieldController;


    public void initialize() {
        animationPlaying = false;
        animationSlider.valueProperty().addListener((observable, oldValue, newValue) -> onAnimationDelayChanged());
        ObservableList<Integer> choices = FXCollections.observableArrayList(5, 10, 20, 50 );
        choiceBox.setItems(choices);
        choiceBox.setValue(20);
        choiceBox.setOnAction(event -> {
            ROWS = choiceBox.getValue();
            COLUMNS = choiceBox.getValue();
            SIZE = 500 / choiceBox.getValue();
            nextBtn.setVisible(false);
            autoPlayBtn.setVisible(false);

            playBtn.setText("Search!");
            fieldSetup();
        });
        fieldSetup();
    }

    private void fieldSetup() {
        fieldController = new FieldController(ROWS,COLUMNS,SIZE);//Good combinations: 50,50,10 | 20,20,25 | 10,10,50
        GridPane field = fieldController.getField();
        onStyleWallClicked();

        AnchorPane.setTopAnchor(field, 0.0);
        AnchorPane.setBottomAnchor(field, 0.0);
        AnchorPane.setLeftAnchor(field, 0.0);
        AnchorPane.setRightAnchor(field, 0.0);


        body.getChildren().clear();
        body.getChildren().add(field);
    }
}