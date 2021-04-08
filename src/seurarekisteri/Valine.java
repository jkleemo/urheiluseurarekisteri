package seurarekisteri;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;


/**
 * Valine-luokka. Tietää välineiden kentät, osaa tarkistaa tietyn kentän oikeellisuuden, osaa muuttaa 
 * 5|Hiihtosauvat|One Way|2018| - merkkijonon välineen tiedoiksi, osaa antaa merkkijonona i:n kentän tiedot,
 * osaa laittaa merkkijonon i:neksi kentäksi.
 * @author jailklee
 * @version 06 Apr 2021
 *
 */
public class Valine {
    private int valineID;
    private static int seuraavaNro = 1;
    private String valineenNimi = "";
    private String valmistaja = "";
    private String hankintavuosi = "";
    private String palautettava = "";
    
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
     * Palauttaa välineen tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @example
     * <pre name="test">
     *   Valine valine = new Valine();
     *   valine.parse("   1  |  Jalkapallo   | Select");
     *   valine.toString().startsWith("1|Jalkapallo|Select|") === true;
     * </pre>  
     */
    @Override
    public String toString() {
        return "" +
                getValineID() + "|" +
                valineenNimi + "|" +
                valmistaja + "|" +
                hankintavuosi + "|" +
                palautettava;        
    }
    
    
    /**
     * Selvitää välineen tiedot | erotellusta merkkijonosta
     * @param rivi mistä tiedot otetaan
     * 
     * @example
     * <pre name="test">
     *   Valine valine = new Valine();
     *   valine.parse("   1  |  Jalkapallo   | Select");
     *   valine.getValineID() === 1;
     *   valine.toString().startsWith("1|Jalkapallo|Select|") === true;
     *
     *   valine.rekisteroi();
     *   int n = valine.getValineID();
     *   valine.parse(""+(n+22));       
     *   valine.rekisteroi();           
     *   valine.getValineID() === n+22+1;
     *     
     * </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setValineID(Mjonot.erota(sb, '|', getValineID()));
        valineenNimi = Mjonot.erota(sb, '|', valineenNimi);
        valmistaja = Mjonot.erota(sb, '|', valmistaja);
        hankintavuosi = Mjonot.erota(sb, '|', hankintavuosi);
        palautettava = Mjonot.erota(sb, '|', palautettava);
    }
    
    
    /**
     * Palautetaan väline.
     * @return välineen nimi
     */
    public String getValineenNimi() {
        return valineenNimi;
    }
    
    
    /**
     * Asetetaan välineID
     */
    private void setValineID(int nro) {
        valineID = nro;
        if (valineID >= seuraavaNro) seuraavaNro = valineID+1;
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
        palautettava = "1.1.2022";
    }
    
    
    /**
     * Tulostetaan välineen tiedot.
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", valineID) + "  " + valineenNimi + "  " + valmistaja + " " + hankintavuosi
                + " " + palautettava);
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
