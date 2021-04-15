package fxUrheiluseurarekisteri;

import java.awt.Desktop;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import static fxUrheiluseurarekisteri.JasenenTiedotController.getFieldId;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import seurarekisteri.Urheiluseurarekisteri;
import seurarekisteri.Jasen;
import seurarekisteri.SailoException;

/**
 * Pääikkunan kontrolleri
 * @author jailklee
 * @version 14 Apr 2021
 *
 */
public class UrheiluseurarekisteriGUIController {
    @FXML private ListChooser<Jasen> listChooserJasenisto;
    @FXML private TextField haettava;
    @FXML private ScrollPane scrollPaneJasen;
    @FXML private GridPane gridPaneJasen;
    
    
    
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
        jasenenMuokkaus(1);
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
        hae(0);
    }
    

    @FXML
    void handleLainaukseen() {
        lainaamaan();
    }

    //=============================================================================

    private Urheiluseurarekisteri urheiluseurarekisteri;
    private Jasen jasenNakyy;
    private static int jasenNro;
    private TextField edits[];
    private int kentta = 0;
    
    /**
     * Tiedoston avaaminen
     */
    public void avaa() {
        alusta();
        lueTiedosto();
    }
    
    
    /**
     * Pääikkunan alustaminen
     */
    protected void alusta() {
        listChooserJasenisto.clear();
        listChooserJasenisto.addSelectionListener(e -> naytaJasen());
        edits = JasenenTiedotController.luoKentat(gridPaneJasen); 
        for (TextField edit: edits) 
             if (edit != null) { 
                 edit.setEditable(false); 
                 edit.setOnMouseClicked(e -> { if ( e.getClickCount() > 1 ) muokkaaJasen(getFieldId(e.getSource(),0)); }); 
                 edit.focusedProperty().addListener((a,o,n) -> kentta = getFieldId(edit,kentta)); 
             }   
    }
    
    
    /**
     * Jäsentietojen muokkaaminen
     */
    private void muokkaaJasen(int i) {
        if (jasenNakyy == null) return;
        try {
            Jasen jasen;
            jasen = JasenenTiedotController.kysyJasen(null, jasenNakyy.clone(), i);
            if ( jasen == null ) return;
            urheiluseurarekisteri.korvaaTaiLisaa(jasen);
            hae(jasen.getJasenID());
        } catch (CloneNotSupportedException e) { 
            //
        } catch (SailoException e) { 
            Dialogs.showMessageDialog(e.getMessage()); 
        } 
    }
    
    
    /**
     * Alustaa kerhon lukemalla sen valitun nimisestä tiedostosta
     * @return null sen onnistuessa, muuten ilmoittaa virheestä
     */
    protected String lueTiedosto() {
        try {
            urheiluseurarekisteri.lueJasenetTiedostosta();
            hae(0);
            return null;
        } catch (SailoException e) {
            hae(0);
            String virhe = e.getMessage(); 
            if ( virhe != null ) Dialogs.showMessageDialog(virhe);
            return virhe;
        }
    }
    
    
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
     * Jäsenen lisäys, luodaan jäsen jota voidaan muokata
     */
    private void jasenenLisays() {
        Jasen jasen = new Jasen();
        jasen = JasenenTiedotController.kysyJasen(null, jasen, 1); 
        if ( jasen == null ) return; 
        jasen.rekisteroi(); 
        urheiluseurarekisteri.lisaa(jasen);
        try {
            urheiluseurarekisteri.tallennaJasenet();
        } catch (SailoException e) {
            e.printStackTrace();
        }
        hae(jasen.getJasenID());
    }
    
    
    /**
     * Näytetään jäsenen tietoja, nyt menevät vielä väliaikaisesti luotuun textAreaan.
     */
    private void naytaJasen() {
        jasenNakyy = listChooserJasenisto.getSelectedObject();
        if (jasenNakyy == null) return;
        JasenenTiedotController.naytaJasen(edits, jasenNakyy);
    } 
    
    
    /**
     * Ohjelman sulkeminen
     */
    private void lopeta() {
        jasenenTallennus();
        System.exit(0);
    }
    
    
    /**
     * Jäsenen tietojen muokkaaminen
     */
    private void jasenenMuokkaus(int i) {
        if (jasenNakyy == null) return;
        try {
            Jasen jasen;
            jasen = JasenenTiedotController.kysyJasen(null, jasenNakyy.clone(), i);
            if ( jasen == null ) return;
            urheiluseurarekisteri.korvaaTaiLisaa(jasen);
            hae(jasen.getJasenID());
        } catch (CloneNotSupportedException e) { 
            //
        } catch (SailoException e) { 
            Dialogs.showMessageDialog(e.getMessage()); 
        } 
        
    }
    
    
    /**
     * Jäsenen poisto
     */
    private void jasenenPoisto() {
        Jasen jasen = jasenNakyy;
        if ( jasen == null ) return;
        if ( !Dialogs.showQuestionDialog("Jasenen poisto", "Haluatko varmasti poistaa jäsenen " + jasen.getJasenID()+ ": " + jasen.getNimi(), "Kyllä", "Ei") )
            return;
        if (urheiluseurarekisteri.palautaEkaLainat(jasen.getJasenID()) == 1) {
            Dialogs.showMessageDialog("Palauta ensin lainat!");
            return;
        }
        urheiluseurarekisteri.poista(jasen);
        try {
            urheiluseurarekisteri.tallennaJasenet();
            urheiluseurarekisteri.tallennaLainat();
        } catch (SailoException e) {
            e.printStackTrace();
        }
        int indeksi = listChooserJasenisto.getSelectedIndex();
        hae(0);
        listChooserJasenisto.setSelectedIndex(indeksi);
        
    }
    
    
    /**
     * Hakee jäsenen tiedot listaan
     * @param numero jäsenen nro
     */
    protected void hae(int numero) {
        int jasNro = numero;
        if (jasNro <= 0) {
            Jasen kohdalla = jasenNakyy;
            if (kohdalla != null) jasNro = kohdalla.getJasenID();
        }
        String hehto = haettava.getText();
        if (hehto.indexOf('*')<0) hehto = "*" + hehto + "*";
        listChooserJasenisto.clear();
        int in = 0;
        Collection<Jasen> jasenet; 
        try {
            jasenet = urheiluseurarekisteri.etsi(hehto, 1);
            int i = 0; 
            for (Jasen jasen:jasenet) { 
                if (jasen.getJasenID() == numero) in = i; 
                listChooserJasenisto.add(jasen.getNimi(), jasen); 
                i++; 
            }
        }
        catch (SailoException ex) {
            Dialogs.showMessageDialog("Jäsenen haku ei onnistu! " + ex.getMessage());  
        }
        listChooserJasenisto.getSelectionModel().select(in);
    }
    
    
    /**
     * Jäsenen tietojen tallennus
     */
    private String jasenenTallennus() {
        try {
            urheiluseurarekisteri.tallenna();
            return null;
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Error! " + ex.getMessage());
            return ex.getMessage();
        }
        
    }
    
    
    /**
     * Tulostetaan tiedot
     * @param os mihin tulostetaan
     * @param jasen kenen tiedot tulostetaan
     */
    public void tulosta(PrintStream os, final Jasen jasen) {
        os.println("----------------------------------------------------------------------------------------------");
        jasen.tulosta(os);
        os.println("----------------------------------------------------------------------------------------------");
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
     * Palautusikkunaan siirtyminen
     */
    private void palautukseen() {
        haeLainat();
        if (jasenNakyy == null) return;
        jasenNro = jasenNakyy.getJasenID();
        urheiluseurarekisteri.setJasenNakyyNro(jasenNro);
        ModalController.showModal(SeuraLainatController.class.getResource("SeuraLainatView.fxml"), "Lainat",  null, urheiluseurarekisteri);
    }
    
    
    private void haeLainat() {
        if (jasenNakyy == null) return;
        jasenNro = jasenNakyy.getJasenID();
        urheiluseurarekisteri.setJasenNakyyNro(jasenNro);
        ModalController.showModal(LainatHakuController.class.getResource("LainatHakuView.fxml"), "",  null, urheiluseurarekisteri);
    }

    
    /**
     * Välineikkunaan siirtyminen
     */
    private void lainaamaan() {
        if (jasenNakyy == null) return;
        jasenNro = jasenNakyy.getJasenID();
        urheiluseurarekisteri.setJasenNakyyNro(jasenNro);
        ModalController.showModal(SeuraValineetController.class.getResource("SeuraValineetView.fxml"), "Välineet",  null, urheiluseurarekisteri);
    }
} 