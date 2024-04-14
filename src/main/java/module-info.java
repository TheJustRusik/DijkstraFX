module dev.kenuki.dijkstrafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens dev.kenuki.dijkstrafx to javafx.fxml;
    exports dev.kenuki.dijkstrafx;
}