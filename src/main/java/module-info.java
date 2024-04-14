module dev.kenuki.dijkstrafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens dev.kenuki.dijkstrafx to javafx.fxml;
    exports dev.kenuki.dijkstrafx;
    exports dev.kenuki.dijkstrafx.frontend;
    opens dev.kenuki.dijkstrafx.frontend to javafx.fxml;
}