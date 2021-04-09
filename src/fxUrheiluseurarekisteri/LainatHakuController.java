package fxUrheiluseurarekisteri;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import seurarekisteri.Urheiluseurarekisteri;
import seurarekisteri.SailoException;

/**
 * @author jailklee
 * @version 07 Apr 2021
 *
 */
public class LainatHakuController implements ModalControllerInterface<Urheiluseurarekisteri> {
    @FXML private BorderPane borderPaneSulje;
        
        @FXML
        void handleOK() {
            painaOK();
        }

    //<==============================================================>
    private Urheiluseurarekisteri urheiluseurarekisteri;
    
    
    /**
     * Palataan takaisin pääikkunaan
     */
    private void painaOK() {
        ModalController.closeStage(borderPaneSulje);
    }
    
    
    /**
     * Alustaa urheiluseurarekisterin lukemalla sen tiedostosta
     * @return null onnistuessa, muuten virheestä ilmoitetaan tekstillä
     */
    protected String lueTiedostosta() {
        try {
            urheiluseurarekisteri.lueLainatTiedostosta();
            urheiluseurarekisteri.lueValineetTiedostosta();
            return null;
        } catch (SailoException e) {
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
     * Haku-ikkunan kontrollerin alustus
     * @param urheiluseurarekisteri seurarekisteri
     */
    @Override
    public void setDefault(Urheiluseurarekisteri urheiluseurarekisteri) {
        this.urheiluseurarekisteri = urheiluseurarekisteri;
        lueTiedostosta();
    }
}