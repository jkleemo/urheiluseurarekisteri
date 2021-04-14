package seurarekisteri;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;


/**
 * Valine-luokka. Tietää välineiden kentät, osaa tarkistaa tietyn kentän oikeellisuuden, osaa muuttaa 
 * 5|Hiihtosauvat|One Way|2018| - merkkijonon välineen tiedoiksi, osaa antaa merkkijonona i:n kentän tiedot,
 * osaa laittaa merkkijonon i:neksi kentäksi.
 * @author jailklee
 * @version 14 Apr 2021
 *
 */
public class Valine implements Cloneable{
    private int valineID;
    private static int seuraavaNro = 1;
    private String valineenNimi = "";
    private String valmistaja = "";
    private String hankintavuosi = "";
    private String palautettava = "Ei lainassa";
    
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
     * Välineen kloonaus
     * @return Object väline joka kloonattiin
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException 
     *   Valine valine = new Valine();
     *   valine.parse("   1  |  Sukset   | Fischer");
     *   Valine clone = valine.clone();
     *   clone.toString() === valine.toString();
     *   valine.parse("   4  |  Sauvat   | One Way");
     *   clone.toString().equals(valine.toString()) === false;
     * </pre>
     */
    @Override
    public Valine clone() throws CloneNotSupportedException {
        Valine eka;
        eka = (Valine) super.clone();
        return eka;
    }
    
    
    /**
     * Kenttien määrän palauttaminen
     * @return kenttien määrä
     */
    public int getKentat() {
        return 5;
    }
    
    
    /**
     * Ekan kentän palauttaminen
     * @return 1. kenttä
     */
    public int ekaKentta() {
        return 1;
    }
    
    
    /**
     * Alustetaan väline.
     */
    public Valine() {
        //
    }
    
    
    /**
     * Antaa i:n kentän sisällön merkkijonona
     * @param i monenenko kentän sisältö palautetaan
     * @return kentän sisältö merkkijonona
     */
    public String anna(int i) {
        switch (i) {
        case 0: return "" + valineID;
        case 1: return "" + valineenNimi;
        case 2: return "" + valmistaja;
        case 3: return "" + hankintavuosi;
        case 4: return "" + palautettava;
        default: return "höh";
        }
    }
    
    
    /**
     * Asettaa i:n kentän arvoksi parametrina tuodun merkkijonon arvon
     * @param i kuinka monennen kentän arvo asetetaan
     * @param jono jonoa joka asetetaan kentän arvoksi
     * @return null onnistuessa, muuten virheilmoitus
     * @example
     * <pre name="test">
     *   Valine valine = new Valine();
     *   valine.aseta(1,"Sukset") === null;
     *   valine.aseta(3,"heppu") === "Hankintavuodessa on vain numeroita!";
     *   valine.aseta(3,"2018") === null; 
     * </pre>
     */
    public String aseta(int i, String jono) {
        String tjono = jono.trim();
        StringBuffer sb = new StringBuffer(tjono);
        switch (i) {
        case 0:
            setValineID(Mjonot.erota(sb, '§', getValineID()));
            return null;
        case 1:
            valineenNimi = tjono;
            return null;
        case 2:
            valmistaja = tjono;
            return null;
        case 3:
            if (tjono.matches("[0-9]+") == false) return "Hankintavuodessa on vain numeroita!";
             hankintavuosi = tjono;
            return null;
        case 4:
            palautettava = tjono;
            return null;
        default:
            return "";
        }
    }
    
    
    /**
     * Palauttaa i:tta välineen kenttää vastaavan kysymyksen
     * @param i kuinka monennen kentän kysymys palautetaan (0-alkuinen)
     * @return i:n kenttää vastaava kysymys
     */
    public String getKysymys(int i) {
        switch (i) {
        case 0: return "Väline ID";
        case 1: return "Välineen nimi: ";
        case 2: return "Valmistaja: ";
        case 3: return "Hankintavuosi: ";
        case 4: return "Palautettava: ";
        default: return "höh";
        }
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
     * Hankintavuoden palauttaminen
     * @return hankintavuosi
     */
    public String getHankintavuosi() {
        return hankintavuosi;
    }
    
    
    /**
     * Välineen lainastatus?
     * @return lainastatus
     */
    public String getLainassa() {
        return palautettava;
    }
    
    
    /**
     * Onko lainassa?
     * @param nimi joko lainassa tai ei lainassa
     */
    public void setLainassa(String nimi) {
        palautettava = nimi;
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
        palautettava = "Ei lainassa";
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
