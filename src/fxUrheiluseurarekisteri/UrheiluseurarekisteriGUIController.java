package fxUrheiluseurarekisteri;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import fi.jyu.mit.fxgui.Dialogs;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author jailklee
 * @version 19.2.2021
 *
 */
public class UrheiluseurarekisteriGUIController {
    
    
    @FXML
    void handleUusiJasen() {
        jasenenLisays();
    }

    @FXML
    void handleLopeta() {
        lopeta();
    }

    @FXML
    void handleMuokkaaJTietoja() {
        jasenenMuokkaus();
    }
    
    @FXML
    void handlePalautukseen() {
        palautukseen();
    }


    @FXML
    void handlePoistaJ() {
        jasenenPoisto();
    }

    @FXML
    void handleTallenna() {
        jasenenTallennus();
    }

    @FXML
    void handleTietoja() {
        haeTiedot();
    }
    

    @FXML
    void handleHaku() {
        hakemaan();
    }
    

    @FXML
    void handleLainaukseen() {
        lainaamaan();
    }

    //=============================================================================

    
    /**
     * Jäsenen lisäys
     */
    private void jasenenLisays() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("JasenenTiedot.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));  
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Ohjelman sulkeminen
     */
    private void lopeta() {
        System.exit(0);
    }
    
    /**
     * Jäsenen tietojen muokkaaminen
     */
    private void jasenenMuokkaus() {
        Dialogs.showMessageDialog("Ei osata vielä muokata");
        
    }
    
    /**
     * Jäsenen poisto
     */
    private void jasenenPoisto() {
        Dialogs.showMessageDialog("Ei osata vielä poistaa");
        
    }
    
    /**
     * Hakupalkin toiminnot
     */
    private void hakemaan() {
        //ei toimi vielä
    }
    
    /**
     * Jäsenen tietojen tallennus
     */
    private void jasenenTallennus() {
        Dialogs.showMessageDialog("Ei osata vielä tallentaa");
        
    }
    
    
    /**
     * Palautukseen
     */
    private void palautukseen() {
        Dialogs.showMessageDialog("Ei osata vielä palauttaa");
        
    }
    
    /**
     * Siirrytään suunnitelmasivulle
     */
    private void haeTiedot() {
        Desktop desktop = Desktop.getDesktop();
        try {
            URI uri = new URI("https://tim.jyu.fi/view/kurssit/tie/ohj2/2021k/ht/jailklee");
            desktop.browse(uri);
        } catch (URISyntaxException e) {
            return;
        } catch (IOException e) {
            return;
        }
    }
    
    
    
    /**
     * Välineikkunaan siirtyminen
     */
    private void lainaamaan() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SeuraValineetView.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));  
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
} 