package seurarekisteri;


/**
 * Poikkeuksien hoito
 * @author jailklee
 * @version 10 Mar 2021
 *
 */
public class SailoException extends Exception {
    private static final long serialVersionUID = 1L;
    
    /**
     * Poikkeuksen muodostaja -> tuodaan käytettävä viesti
     * @param viesti poikkeusviesti
     */
    public SailoException(String viesti) {
        super(viesti);
    }
}
