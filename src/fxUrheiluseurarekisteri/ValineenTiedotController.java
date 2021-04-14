package fxUrheiluseurarekisteri;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.ohj2.Mjonot;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import seurarekisteri.Valine;

/**
 * @author jailklee
 * @version 14 Apr 2021
 *
 */
public class ValineenTiedotController implements ModalControllerInterface<Valine>,Initializable  {
    @FXML private Label labelV;
    @FXML private ScrollPane panelValine;
    @FXML private GridPane gridValine;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();  
    }
    
    @FXML private void handleOK() {
        if (valineNakyy != null && valineNakyy.getValineenNimi().trim().equals("")) {
            naytaVirhe("Ei ole nimeä!");
            return;
        }
        if ( valineNakyy != null && valineNakyy.getHankintavuosi().trim().matches("[0-9]+") == false) {
            naytaVirhe("Vain numerot kelpaavat!");
            return;
        }
        ModalController.closeStage(labelV);
    }

    
    @FXML private void handlePeruuta() {
        valineNakyy = null;
        ModalController.closeStage(labelV);
    }

    //<==============================================================>
    
    private Valine valineNakyy;
    private static Valine valineApu = new Valine();
    private TextField[] edits; 
    private int kentta = 0; 
    
    /**
     * Tekstikenttien tyhjennys
     * @param edits kentät, jotka tyhjennetään
     */
    public static void tyhjenna(TextField[] edits) {
        for (TextField edit : edits)
            if (edit != null) edit.setText(""); 
    }
    
    
    /**
     * Välinetietojen kenttien luominen
     * @param gridValine välineen tiedot lisätään
     * @return kentät, jotka luotiin
     */
    public static TextField[] luoKentat(GridPane gridValine) {
        gridValine.getChildren().clear();
        TextField[] edits = new TextField[valineApu.getKentat()];
        for (int i=0, k=valineApu.ekaKentta(); k<valineApu.getKentat(); k++, i++) {
            Label label = new Label(valineApu.getKysymys(k));
            gridValine.add(label, 0, i);
            TextField edit = new TextField();
            edits[k] = edit;
            edit.setId("e"+k);
            gridValine.add(edit, 1, i);
        }
        return edits;
    }
    
    
    /**
     * Objektin tunnuksen palautus
     * @param obj objekti
     * @param oletus oletusarvo
     * @return objektin tunnus
     */
    public static int getFieldId(Object obj, int oletus) {
        if (!( obj instanceof Node)) return oletus;
        Node node = (Node)obj;
        return Mjonot.erotaInt(node.getId().substring(1),oletus);
    }


    /**
     * Ikkunan alustaminen.
     */
    protected void alusta() {
        edits = luoKentat(gridValine);
        for (TextField edit : edits)
            if (edit != null)
                  edit.setOnKeyReleased( e -> valineenMuutos((TextField)(e.getSource())));
        panelValine.setFitToHeight(true);
    }
    
    
    /**
     * Välineen muutoksen hoito
     * @param edit kenttä, joka muutetaan
     */
    protected void valineenMuutos(TextField edit) {
        if (valineNakyy == null) return;
        int k = getFieldId(edit,valineApu.ekaKentta());
        String s = edit.getText();
        String virhe = null;
        virhe = valineNakyy.aseta(k,s); 
        if (virhe == null) {
            Dialogs.setToolTipText(edit,"");
            edit.getStyleClass().removeAll("virhe");
            naytaVirhe(virhe);
        } 
        else {
            Dialogs.setToolTipText(edit,virhe);
            edit.getStyleClass().add("virhe");
            naytaVirhe(virhe);
        }
    }
    
    
    private void naytaVirhe(String virhe) {
        if (virhe == null || virhe.isEmpty()) {
            labelV.setText("");
            labelV.getStyleClass().removeAll("virhe");
            return;
        }
        labelV.setText(virhe);
        labelV.getStyleClass().add("virhe");
    }
    
    
    @Override
    public void setDefault(Valine valine) {
        valineNakyy = valine;
        naytaValine(edits, valineNakyy);
    }

    
    @Override
    public Valine getResult() {
        return valineNakyy;
    }
    
    
    @SuppressWarnings("unused")
    private void setKentta(int kentta) {
        this.kentta = kentta;
    }
    
    
    /**
     * Dialogin näyttyä.
     */
    @Override
    public void handleShown() {
        kentta = Math.max(valineApu.ekaKentta(), Math.min(kentta, valineApu.getKentat()-1)); 
        edits[kentta].requestFocus(); 
    }
    
    
    /**
     * Välineen tietojen näyttäminen
     * @param edits näytettävät kentät taulukkona
     * @param valine väline, joka näytetään
     */
    public static void naytaValine(TextField[] edits, Valine valine) {
        if (valine == null) return;
        for (int k=valine.ekaKentta(); k<valine.getKentat(); k++) {
            edits[k].setText(valine.anna(k));
        }
    }
    
    
    /**
     * Välineen kysymisdialogin luominen
     * @param modalityStage taso
     * @param oletus oletuksena näkyvä
     * @param kentta mitä näytetään
     * @return null peruessa
     */
    public static Valine kysyValine(Stage modalityStage, Valine oletus, int kentta) {
        return ModalController.<Valine, ValineenTiedotController>showModal(
                    ValineenTiedotController.class.getResource("ValineenTiedot.fxml"),
                    "Välineen tiedot",
                    modalityStage, oletus, ctrl -> ctrl.setKentta(kentta));
    }
}