package fxUrheiluseurarekisteri;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import seurarekisteri.Urheiluseurarekisteri;
import seurarekisteri.Valine;
import seurarekisteri.SailoException;
import seurarekisteri.Laina;

/**
 * Välineikkunan kontrolleri
 * @author jailklee
 * @version 07 Apr 2021
 *
 */
public class SeuraValineetController implements ModalControllerInterface<Urheiluseurarekisteri> {
    @FXML private ListChooser<Valine> listChooserValineet;


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
    private Urheiluseurarekisteri urheiluseurarekisteri;
    
    /**
     * Välineen lisäys, luodaan väline jota voidaan muokata
     */
    private String valineenLisays() {
        Valine valine = new Valine();
        valine.rekisteroi();
        valine.taytaValine();
        urheiluseurarekisteri.lisaa(valine);
        try {
            urheiluseurarekisteri.tallennaValineet();
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Error! " + ex.getMessage());
            return ex.getMessage();
        }
        hae(valine.getValineID());
        return null;
    }
    
    
    /**
     * Välineen hakeminen
     * param nro välineen numero
     */
    private void hae(int nro) {
        listChooserValineet.clear();
        int in = 0;
        for (int i=0; i<urheiluseurarekisteri.getValineita(); i++) {
            Valine valine = urheiluseurarekisteri.annaValine(i);
            if (valine.getValineID() == nro) in = i;
            listChooserValineet.add(valine.getValineenNimi(), valine);
        }
        listChooserValineet.setSelectedIndex(in);
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
            urheiluseurarekisteri.tallennaLainat();
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
            urheiluseurarekisteri.lueValineetTiedostosta();
            hae(0);
            urheiluseurarekisteri.lueLainatTiedostosta();
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
        lueTiedostosta();
    }
}