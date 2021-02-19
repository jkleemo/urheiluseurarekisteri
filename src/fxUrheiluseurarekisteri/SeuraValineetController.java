package fxUrheiluseurarekisteri;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import fi.jyu.mit.fxgui.Dialogs;
import javafx.fxml.FXML;

/**
 * @author jailklee
 * @version 19 Feb 2021
 *
 */
public class SeuraValineetController {

    @FXML
    void handleLainaaValine() {
        lainaa();
    }

    @FXML
    void handleLisaaValine() {
        valineenLisays();
    }

    @FXML
    void handleLopeta() {
        lopeta();
    }

    @FXML
    void handleMuokkaaValine() {
        valineenMuokkaus();
    }

    @FXML
    void handlePoistaValine() {
        valineenPoisto();
    }

    @FXML
    void handleTallennaV() {
        valineenTallennus();
    }

    @FXML
    void handleTietoja() {
        tietoja();
    }

    
    //<==============================================================>

    
    /**
     * Välineen lisäys
     */
    private void valineenLisays() {
        Dialogs.showMessageDialog("Ei osata vielä lisätä");
    }
    
    /**
     * Suljetaan ohjelma
     */
    private void lopeta() {
        System.exit(0);
    }
    
    /**
     * Välineiden tietojen muokkaus
     */
    private void valineenMuokkaus() {
        Dialogs.showMessageDialog("Ei osata vielä muokata");
        
    }
    
    /**
     * Välineen poisto
     */
    private void valineenPoisto() {
        Dialogs.showMessageDialog("Ei osata vielä poistaa");
        
    }
    
    
    /**
     * Välineen tallentaminen
     */
    private void valineenTallennus() {
        Dialogs.showMessageDialog("Ei osata vielä tallentaa");
        
    }
    
    /**
     * Siirrytään suunnitelmasivulle
     */
    private void tietoja() {
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
     * Siirrytään lainausikkunaan
     */
    private void lainaa() {
        Dialogs.showMessageDialog("Ei osata vielä lainata");
    }
}