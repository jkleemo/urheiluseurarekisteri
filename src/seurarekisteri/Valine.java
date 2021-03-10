package seurarekisteri;

import java.io.OutputStream;
import java.io.PrintStream;


/**
 * Valine-luokka. Tietää välineiden kentät, osaa tarkistaa tietyn kentän oikeellisuuden, osaa muuttaa 
 * 5|Hiihtosauvat|One Way|2018| - merkkijonon välineen tiedoiksi, osaa antaa merkkijonona i:n kentän tiedot,
 * osaa laittaa merkkijonon i:neksi kentäksi.
 * @author jailklee
 * @version 10 Mar 2021
 *
 */
public class Valine {
    private int valineID;
    private static int seuraavaNro = 1;
    private String valineenNimi = "";
    private String valmistaja = "";
    private String hankintavuosi = "";
    
    
    /**
     * Antaa välineelle välineID:n.
     * @return välineen valineID
     * @example
     * <pre name="test">
     * Valine valine1 = new Valine();
     *  valine1.getValineID() === 0;
     *  valine1.rekisteroi();
     *  Valine valine2 = new Valine();
     *  valine2.rekisteroi();
     *  int n1 = valine1.getValineID();
     *  int n2 = valine2.getValineID();
     *  n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        valineID = seuraavaNro;
        seuraavaNro++;
        return valineID;
    }
    
    
    /**
     * Palautetaan väline.
     * @return välineen nimi
     */
    public String getValineenNimi() {
        return valineenNimi;
    }
    
    
    /**
     * Palautetaan valineID.
     * @return välineen valineID
     */
    public int getValineID() {
        return valineID;
    }
    
    
    /**
     * Testivälineen tietojen täyttäminen.
     */
    public void taytaValine() {
        valineenNimi = "Sukset";
        valmistaja = "Fischer";
        hankintavuosi = "2018";
    }
    
    
    /**
     * Tulostetaan välineen tiedot.
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", valineID) + "  " + valineenNimi + "  " + valmistaja + " " + hankintavuosi);
    }
    
    
    /**
     * Tulostetaan välineen tiedot.
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    

    /**
     * Testiohjelma välineelle.
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Valine valine1 = new Valine();
        Valine valine2 = new Valine();
        
        valine1.rekisteroi();
        valine2.rekisteroi();
        
        valine1.tulosta(System.out);
        valine2.tulosta(System.out);
        
        valine1.taytaValine();
        valine2.taytaValine();
        
        valine1.tulosta(System.out);
        valine2.tulosta(System.out);
    }
}
