package fxUrheiluseurarekisteri;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import seurarekisteri.Urheiluseurarekisteri;
import seurarekisteri.Valine;
import seurarekisteri.Laina;
import seurarekisteri.SailoException;

/**
 * @author jailklee
 * @version 14 Apr 2021
 *
 */
public class SeuraLainatController implements ModalControllerInterface<Urheiluseurarekisteri> {
    @FXML private ListChooser<Laina> listChooserLainat;

    @FXML
    void handlePalauta() {
        palauta();
    }

    @FXML
    void handlePeruuta() {
        takaisin();
    }    
    //<==============================================================>
    private Urheiluseurarekisteri urheiluseurarekisteri;
    private Laina lainaNakyy;
    
    /**
     * Lainan palauttaminen
     */
    private void palauta() {
        lainaNakyy = listChooserLainat.getSelectedObject();
        Laina laina = lainaNakyy;
        int vid = lainaNakyy.getValineID();
        Valine valine = urheiluseurarekisteri.getValine(vid);
        if (laina == null) return;
        if (!Dialogs.showQuestionDialog("Palautus", "Haluatko varmasti palauttaa välineen?", "Kyllä", "Ei") )
            return;
        urheiluseurarekisteri.poista(laina);
        valine.setLainassa("Ei lainassa");
        try {
            urheiluseurarekisteri.tallenna();
        } catch (SailoException e) {
            e.printStackTrace();
        }
        int indeksi = listChooserLainat.getSelectedIndex();
        hae();
        listChooserLainat.setSelectedIndex(indeksi);
    }
    
    
    /**
     * Palataan takaisin pääikkunaan
     */
    private void takaisin() {
        tallenna();
        ModalController.closeStage(listChooserLainat);
    }
    

    /**
     * Lainojen haku listaan
     */
    private void hae() {
        listChooserLainat.clear();
        int il = 0;
        int vid;
        for (int i=0; i<urheiluseurarekisteri.getLainoja(); i++) {
            Laina laina = urheiluseurarekisteri.annaLaina(i);
            if (laina.getJasenID() == urheiluseurarekisteri.getJasenNakyyNro()) {
                vid = laina.getValineID();
                for (int j=0; i<urheiluseurarekisteri.getValineita(); j++) {
                    Valine valine = urheiluseurarekisteri.annaValine(j);
                    if (valine.getValineID() == vid) {
                        listChooserLainat.add(valine.getValineenNimi(), laina);
                        break;
                    }
                }
            }
        }
        listChooserLainat.setSelectedIndex(il);
    }
    
    
    /**
     * Muutosten tallentaminen
     */
    private String tallenna() {
        try {
            urheiluseurarekisteri.tallenna();
            return null;
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Tallennus epäonnistui! " + ex.getMessage());
            return ex.getMessage();
        }
    }
    
    
    /**
     * Lainan tietojen näyttäminen
     */
    protected void naytaLaina() {
        lainaNakyy = listChooserLainat.getSelectedObject();
        if (lainaNakyy == null) return;
    }
    
    
    /**
     * Alustaa urheiluseurarekisterin lukemalla sen tiedostosta
     * @return null onnistuessa, muuten virheestä ilmoitetaan tekstillä
     */
    protected String lueTiedostosta() {
        try {
            urheiluseurarekisteri.lueTiedostosta();
            hae();
            urheiluseurarekisteri.lueTiedostosta();
            return null;
        } catch (SailoException e) {
            hae();
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
     * Laina-ikkunan kontrollerin alustus
     * @param urheiluseurarekisteri seurarekisteri
     */
    @Override
    public void setDefault(Urheiluseurarekisteri urheiluseurarekisteri) {
        this.urheiluseurarekisteri = urheiluseurarekisteri;
        lueTiedostosta();
    }
}
