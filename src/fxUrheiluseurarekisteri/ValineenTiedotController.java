package fxUrheiluseurarekisteri;

import fi.jyu.mit.fxgui.Dialogs;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;


/**
 * @author jailklee
 * @version 22 Feb 2021
 *
 */
public class ValineenTiedotController {

    @FXML private Label labelV;
    @FXML private ScrollPane panelValine;
    @FXML private GridPane gridValine;

    @FXML
    void handleOK() {
        hyvaksymiseen();
    }

    
    @FXML
    void handlePeruuta() {
        peruutukseen();
    }


    
  //=============================================================================
    
    private void peruutukseen() {
        Dialogs.showMessageDialog("Ei osata vielä peruuttaa");
        
    }
    
    private void hyvaksymiseen() {
        Dialogs.showMessageDialog("Ei osata vielä lisätä");
    }


}