package nsu.fit.railway.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

public class MainViewController {

    @FXML
    private Button backBtn;

    @FXML
    private Button forwardBtn;
    @FXML
    private void forwardBtnClicked() {

    }
    @FXML
    private AnchorPane mainPane;
    @FXML
    private MenuBar menuBar;

    @FXML
    private Button restartBtn;

    @FXML
    private MenuItem menuOpenTopology;
    @FXML
    private void openTopology() {
        //TODO
    }
    @FXML
    private MenuItem menuOpenTimetable;
    @FXML
    private void openTimetable() {
        //TODO
    }
    @FXML
    private MenuItem menuClose;

    @FXML
    private void restartBtnClicked() {
        //TODO
    }

}