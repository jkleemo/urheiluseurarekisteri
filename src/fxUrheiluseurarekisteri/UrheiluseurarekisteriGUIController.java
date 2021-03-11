package fxUrheiluseurarekisteri;

import java.awt.Desktop;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import seurarekisteri.Urheiluseurarekisteri;
import seurarekisteri.Jasen;

/**
 * Pääikkunan kontrolleri
 * @author jailklee
 * @version 19.2.2021
 *
 */
public class UrheiluseurarekisteriGUIController {
    @FXML private ListChooser<Jasen> listChooserJasenisto;
    @FXML private TextArea textAreaNayta;
    
    
    
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

    private Urheiluseurarekisteri urheiluseurarekisteri;
    private Jasen jasenNakyy;  
    
    
    /**
     * Asetetaan urheiluseurarekisteri
     * @param urheiluseurarekisteri mikä näytetään
     */
    public void setUrheiluseurarekisteri(Urheiluseurarekisteri urheiluseurarekisteri) {
        this.urheiluseurarekisteri = urheiluseurarekisteri;
        hae(0);
        naytaJasen();
    }
    
    
    /**
     * Jäsenen haku
     * param nro jäsenen numero
     */
    private void hae(int nro) {
        listChooserJasenisto.clear();
        int in = 0;
        for (int i=0; i<urheiluseurarekisteri.getJasenia(); i++) {
            Jasen jasen = urheiluseurarekisteri.annaJasen(i);
            if (jasen.getJasenID() == nro) in = i;
            listChooserJasenisto.add(jasen.getNimi(), jasen);
        }
        listChooserJasenisto.setSelectedIndex(in);
        listChooserJasenisto.addSelectionListener(e -> naytaJasen());
    }
    
 
    
    /**
     * Jäsenen lisäys, luodaan jäsen jota voidaan muokata
     */
    private void jasenenLisays() {
        Jasen jasen = new Jasen();
        jasen.rekisteroi();
        jasen.jasenenTaytto();
        urheiluseurarekisteri.lisaa(jasen);
        hae(jasen.getJasenID());
    }
    
    
    /**
     * Näytetään jäsenen tietoja, nyt menevät vielä väliaikaisesti luotuun textAreaan.
     */
    private void naytaJasen() {
        jasenNakyy = listChooserJasenisto.getSelectedObject();
        if (jasenNakyy == null) return;
        textAreaNayta.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(textAreaNayta)) {
            tulosta(os,jasenNakyy); 
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
     * Tulostetaan tiedot
     * @param os mihin tulostetaan
     * @param jasen kenen tiedot tulostetaan
     */
    public void tulosta(PrintStream os, final Jasen jasen) {
        jasen.tulosta(os);
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
        if (jasenNakyy == null) return;
        ModalController.showModal(SeuraValineetController.class.getResource("SeuraValineetView.fxml"), "Välineet",  null, urheiluseurarekisteri);
    }
} 