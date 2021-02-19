package fxUrheiluseurarekisteri;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


/**
 * @author jailklee
 * @version 19.2.2021
 *
 */
public class UrheiluseurarekisteriMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("UrheiluseurarekisteriGUIView.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("urheiluseurarekisteri.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args Ei käytössä
     */
    public static void main(String[] args) {
        launch(args);
    }
}