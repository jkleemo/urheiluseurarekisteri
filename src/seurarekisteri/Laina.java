package seurarekisteri;

import java.io.OutputStream;
import java.io.PrintStream;


/**
 * Laina-luokka. Yhdistää Väline-luokan ja Jäsen-luokan.
 * @author jailklee
 * @version 10 Mar 2021
 *
 */
public class Laina {
    private int lainaID;
    private static int seuraavaNro = 1;
    private int jasenID;
    private int valineID;
    
    /**
     * Lainan luominen
     * @param jasenID jäsenen ID, joka lainauksen toteuttaa
     * @param valineID lainattavan välineen ID
     */
    public Laina(int jasenID, int valineID) {
        this.jasenID = jasenID;
        this.valineID = valineID;
    }
    
    
    /**
     * Palautetaan jasenID.
     * @return lainan toteuttavan jäsenen id
     */
    public int getJasenID() {
        return jasenID;
    }
    
    
    /**
     * Luo lainalle ID:n
     * @return lainaID
     * @example
     * <pre name="test">
     *  Laina eka = new Laina(1,2);
     *  eka.getLainaID() === 0;
     *  eka.rekisteroi();
     *  Laina toka = new Laina(2,1);
     *  toka.rekisteroi();
     *  int n1 = eka.getLainaID();
     *  int n2 = toka.getLainaID();
     *  n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        lainaID = seuraavaNro;
        seuraavaNro++;
        return lainaID;
    }
    
    
    /**
     * LainaID:n hakeminen
     * @return kyseisen lainan ID
     */
    public int getLainaID() {
        return lainaID;
    }
    
    
    /**
     * VälineID:n hakeminen
     * @return kyseisen välineen ID
     */
    public int getValineID() {
        return valineID;
    }
    
    
    /**
     * Lainan tietojen tulostaminen
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", lainaID) + "  " + "jäsen id " + jasenID + " kirja id " + valineID);
    }
    
    
    /**
     * Tulostetaan lainan tiedot.
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    

    /**
     * Testiohjelma lainalle.
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Laina laina1 = new Laina(12,11);
        Laina laina2 = new Laina(11,22);
        
        laina1.rekisteroi();
        laina2.rekisteroi();
        
        laina1.tulosta(System.out);
        laina2.tulosta(System.out);
        
        laina1.tulosta(System.out);
        laina2.tulosta(System.out);
    
    }

}