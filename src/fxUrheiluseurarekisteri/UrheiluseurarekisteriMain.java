package fxUrheiluseurarekisteri;

import javafx.application.Application;
import javafx.stage.Stage;
import seurarekisteri.Urheiluseurarekisteri;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * Rekisterin käynnistävä pääohjelma
 * @author jailklee
 * @version 19.2.2021
 *
 */
public class UrheiluseurarekisteriMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            final FXMLLoader ldr = new FXMLLoader(getClass().getResource("UrheiluseurarekisteriGUIView.fxml"));
            final Pane root = (Pane)ldr.load();
            final UrheiluseurarekisteriGUIController urheiluseurarekisteriController = (UrheiluseurarekisteriGUIController)ldr.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("urheiluseurarekisteri.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Urheiluseurarekisteri");
            Urheiluseurarekisteri urheiluseurarekisteri = new Urheiluseurarekisteri();
            urheiluseurarekisteriController.setUrheiluseurarekisteri(urheiluseurarekisteri);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Käynnistetään käyttöliittymä
     * @param args Ei käytössä
     */
    public static void main(String[] args) {
        launch(args);
    }
}