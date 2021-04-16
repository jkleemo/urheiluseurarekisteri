package fxUrheiluseurarekisteri;


import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import static fxUrheiluseurarekisteri.ValineenTiedotController.getFieldId;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import seurarekisteri.Urheiluseurarekisteri;
import seurarekisteri.Valine;
import seurarekisteri.SailoException;
import seurarekisteri.Laina;

/**
 * Välineikkunan kontrolleri
 * @author jailklee
 * @version 14 Apr 2021
 *
 */
public class SeuraValineetController implements ModalControllerInterface<Urheiluseurarekisteri> {
    @FXML private ListChooser<Valine> listChooserValineet;
    @FXML private ScrollPane scrollPanelValine;
    @FXML private GridPane gridPaneValine;


    @FXML
    void handleLainaaValine() {
        lainaa();
    }

    @FXML
    void handleLisaaValine() {
        valineenLisays();
    }
    
    @FXML
    void handleTakaisin() {
        valineenTallennus();
        ModalController.closeStage(listChooserValineet);

    }

    @FXML
    void handleLopeta() {
        lopeta();
    }

    @FXML
    void handleMuokkaaValine() {
        muokkaaValine(1);
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
    private Urheiluseurarekisteri urheiluseurarekisteri;
    private Valine valineNakyy;
    private TextField edits[];
    private int kentta = 0;
    
    
    /**
     * Välineikkunan alustaminen
     */
    protected void alusta() {
        listChooserValineet.clear();
        listChooserValineet.addSelectionListener(e -> naytaValine());
        edits = ValineenTiedotController.luoKentat(gridPaneValine); 
        for (TextField edit: edits) 
             if (edit != null) { 
                 edit.setEditable(false); 
                 edit.setOnMouseClicked(e -> { if ( e.getClickCount() > 1 ) muokkaaValine(getFieldId(e.getSource(),0)); }); 
                 edit.focusedProperty().addListener((a,o,n) -> kentta = getFieldId(edit,kentta)); 
             }   
    }
    
    
    /**
     * Välineen tietojen näyttäminen.
     */
    protected void naytaValine() {
        valineNakyy = listChooserValineet.getSelectedObject();
        if (valineNakyy == null) return;
        ValineenTiedotController.naytaValine(edits, valineNakyy);
    }
    
    
    
    /**
     * Välineen tietojen muokkaaminen
     */
    private void muokkaaValine(int i) {
        if (valineNakyy == null) return;
        try {
            Valine valine;
            valine = ValineenTiedotController.kysyValine(null, valineNakyy.clone(), i);
            if (valine == null) return;
            urheiluseurarekisteri.korvaaTaiLisaa(valine);
            hae(valine.getValineID());
        } catch (CloneNotSupportedException e) { 
            //
        } catch (SailoException e) { 
            Dialogs.showMessageDialog(e.getMessage()); 
        } 
    }
    
    
    /**
     * Välineen lisäys, luodaan väline jota voidaan muokata
     */
    private void valineenLisays() {
        Valine valine = new Valine();
        valine = ValineenTiedotController.kysyValine(null, valine, 1); 
        if (valine == null) return; 
        valine.rekisteroi(); 
        urheiluseurarekisteri.lisaa(valine);
        try {
            urheiluseurarekisteri.tallenna();
        } catch (SailoException e) {
            e.printStackTrace();
        }
        hae(valine.getValineID());
    }
    
    
    /**
     * Välineen hakeminen
     * @param nro välineen numero
     */
    protected void hae(int nro) {
        listChooserValineet.clear();
        int in = 0;
        Collection<Valine> valineet; 
        try {
            valineet = urheiluseurarekisteri.etsiValine("", 1); 
            int i = 0; 
            for (Valine valine:valineet) { 
                if (valine.getValineID() == nro) in = i; 
                listChooserValineet.add(valine.getValineenNimi(), valine); 
                i++; 
            }
        }
        catch (SailoException ex) {
            Dialogs.showMessageDialog("Välineen haku ei onnistu! " + ex.getMessage());  
        }
        listChooserValineet.getSelectionModel().select(in);
    }
    
    
    /**
     * Suljetaan ohjelma
     */
    private void lopeta() {
        valineenTallennus();
        System.exit(0);
    }
    
    
    /**
     * Välineen poisto
     */
    private void valineenPoisto() {
        Valine valine = valineNakyy;
        if (valine == null) return;
        if (!Dialogs.showQuestionDialog("Välineen poistaminen", "Poistetaanko väline: " + valine.getValineenNimi(), "Kyllä", "Ei") )
            return;
        int vid = valine.getValineID();
        urheiluseurarekisteri.poistaLaina(vid);
        urheiluseurarekisteri.poista(valine);
        try {
            urheiluseurarekisteri.tallenna();
        } catch (SailoException e) {
            e.printStackTrace();
        }
        int indeksi = listChooserValineet.getSelectedIndex();
        hae(0);
        listChooserValineet.setSelectedIndex(indeksi);
    }
    
    
    /**
     * Välineen tallentaminen
     */
    private String valineenTallennus() {
        try {
            urheiluseurarekisteri.tallenna();
            return null;
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Error! " + ex.getMessage());
            return ex.getMessage();
        }
        
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
     * Lainausominaisuus. Ilmoittaa onnistuneesta lainasta sekä siitä jos väline on jo lainassa.
     */
    private String lainaa() {
        if (listChooserValineet.getSelectedObject() == null) return null;
        int vid = listChooserValineet.getSelectedObject().getValineID();
        if (urheiluseurarekisteri.getValineID(vid) == true) {
            Dialogs.showMessageDialog("Väline on jo lainassa!");
            return null;
        }
        Laina laina = new Laina(urheiluseurarekisteri.getJasenNakyyNro(), listChooserValineet.getSelectedObject().getValineID());
        laina.rekisteroi();
        urheiluseurarekisteri.lisaa(laina);
        try {
            urheiluseurarekisteri.tallenna();
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Error! " + ex.getMessage());
            return ex.getMessage();
        }
        Dialogs.showMessageDialog(listChooserValineet.getSelectedObject().getValineenNimi() 
                + " on nyt lainattu jäsenelle " + urheiluseurarekisteri.getJasenNakyyNro() + ".");
        return null;
    }

    
    /**
     * Alustus tiedostosta lukemalla
     * @return null onnistuessa, muuten virheilmoitus
     */
    protected String lueTiedostosta() {
        try {
            urheiluseurarekisteri.lueTiedostosta();
            hae(0);
            urheiluseurarekisteri.lueTiedostosta();
            return null;
        } catch (SailoException e) {
            hae(0);
            String virhe = e.getMessage(); 
            if ( virhe != null ) Dialogs.showMessageDialog(virhe);
            return virhe;
        }
     }
    
    
    @Override
    public Urheiluseurarekisteri getResult() {
        // TODO Auto-generated method stub
        return null;
    }

    
    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }
    
    
    /**
     * Väline-ikkunan kontorllerin alustaminen
     * @param urheiluseurarekisteri avattu urheiluseurarekisteri
     * 
     */
    @Override
    public void setDefault(Urheiluseurarekisteri urheiluseurarekisteri) {
        this.urheiluseurarekisteri = urheiluseurarekisteri;
        alusta();
        lueTiedostosta();
    }
}