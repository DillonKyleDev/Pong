module edu.srjc.finalproject.kyle.dillon.pong {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens edu.srjc.finalproject.kyle.dillon.pong to javafx.fxml;
    exports edu.srjc.finalproject.kyle.dillon.pong;
}