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
import seurarekisteri.Jasen;

/**
 * @author jailklee
 * @version 14 Apr 2021
 *
 */
public class JasenenTiedotController implements ModalControllerInterface<Jasen>,Initializable  {

    @FXML private Label labelV;
    @FXML private ScrollPane panelJ;
    @FXML private GridPane gridJ;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();  
    }
    
    @FXML private void handleOK() {
        if ( jasenNakyy != null && jasenNakyy.getNimi().trim().equals("") ) {
            naytaVirhe("Anna jäsenen nimi!");
            return;
        }
        if ( jasenNakyy != null && jasenNakyy.getPostinro().trim().matches("[0-9]+") == false) {
            naytaVirhe("Käytä numeroita!");
            return;
        }
        if ( jasenNakyy != null && jasenNakyy.getPuhelinNro().trim().matches("[0-9]+") == false) {
            naytaVirhe("Käytä numeroita!");
            return;
        }
        ModalController.closeStage(labelV);
    }

    
    @FXML private void handlePeruuta() {
        jasenNakyy = null;
        ModalController.closeStage(labelV);
    }

    //<==============================================================>
    
    private Jasen jasenNakyy;
    private static Jasen jasenApu = new Jasen();
    private TextField[] edits; 
    private int kentta = 0; 
    
    /**
     * Tekstikenttien tyhjentäminen
     * @param edits kentät, jotka tyhjennetään
     */
    public static void tyhjenna(TextField[] edits) {
        for (TextField edit : edits)
            if (edit != null) edit.setText(""); 
    }
    
    
    /**
     * Jäsentietojen kenttien luominen
     * @param gridJ kenttä, mihin tietojen lisääminen tapahtuu
     * @return kentät, jotka luotiin
     */
    public static TextField[] luoKentat(GridPane gridJ) {
        gridJ.getChildren().clear();
        TextField[] edits = new TextField[jasenApu.getKentat()];
        for (int i=0, k=jasenApu.ekaKentta(); k<jasenApu.getKentat(); k++, i++) {
            Label label = new Label(jasenApu.getKysymys(k));
            gridJ.add(label, 0, i);
            TextField edit = new TextField();
            edits[k] = edit;
            edit.setId("e"+k);
            gridJ.add(edit, 1, i);
        }
        return edits;
    }
    
    
    /**
     * Objektin tunnuksen palauttaminen
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
        edits = luoKentat(gridJ);
        for (TextField edit : edits)
            if (edit != null)
                  edit.setOnKeyReleased( e -> kasitteleMuutosJaseneen((TextField)(e.getSource())));
        panelJ.setFitToHeight(true);
    }
    
    
    /**
     * Käsitellään jäsenen muutos.
     * @param edit muutettu kenttä
     */
    protected void kasitteleMuutosJaseneen(TextField edit) {
        if (jasenNakyy == null) return;
        int k = getFieldId(edit,jasenApu.ekaKentta());
        String s = edit.getText();
        String virhe = null;
        virhe = jasenNakyy.aseta(k,s); 
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
    public void setDefault(Jasen jasen) {
        jasenNakyy = jasen;
        naytaJasen(edits, jasenNakyy);
    }

    
    @Override
    public Jasen getResult() {
        return jasenNakyy;
    }
    
    
    private void setKentta(int kentta) {
        this.kentta = kentta;
    }
    
    
    /**
     * Kun dialogi on näytetty
     */
    @Override
    public void handleShown() {
        kentta = Math.max(jasenApu.ekaKentta(), Math.min(kentta, jasenApu.getKentat()-1)); 
        edits[kentta].requestFocus(); 
    }
    
    
    /**
     * Jäsenen tietojen näyttäminen kentissä
     * @param edits näytettävät kentät
     * @param jasen jäsen, joka näytetään
     */
    public static void naytaJasen(TextField[] edits, Jasen jasen) {
        if (jasen == null) return;
        for (int k=jasen.ekaKentta(); k<jasen.getKentat(); k++) {
            edits[k].setText(jasen.anna(k));
        }
    }
    
    
    /**
     * Jäsenen kysymysdialogin luominen
     * @param modalityStage taso
     * @param oletus mitä näytetään oletuksena
     * @param kentta kenttä, joka näytetään
     * @return null peruuttaessa
     */
    public static Jasen kysyJasen(Stage modalityStage, Jasen oletus, int kentta) {
        return ModalController.<Jasen, JasenenTiedotController>showModal(
                    JasenenTiedotController.class.getResource("JasenenTiedot.fxml"),
                    "Urheiluseurarekisteri",
                    modalityStage, oletus, ctrl -> ctrl.setKentta(kentta));
    }
}
