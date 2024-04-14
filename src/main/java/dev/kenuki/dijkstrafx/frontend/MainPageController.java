package dev.kenuki.dijkstrafx.frontend;

import dev.kenuki.dijkstrafx.util.Block;
import javafx.fxml.FXML;
import javafx.scene.layout.*;

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
    private void onSearchBtnClicked() {
        fieldController.lockDrawing();
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

        fieldController = new FieldController(50,50,10);//Good combinations: 50,50,10 | 20,20,25 | 10,10,50
        GridPane field = fieldController.getField();
        onStyleWallClicked();

        AnchorPane.setTopAnchor(field, 0.0);
        AnchorPane.setBottomAnchor(field, 0.0);
        AnchorPane.setLeftAnchor(field, 0.0);
        AnchorPane.setRightAnchor(field, 0.0);



        body.getChildren().add(field);
    }
}