package seurarekisteri;

import java.io.OutputStream;
import java.io.PrintStream;


/**
 * Ylläpitää jasenID:tä, tietää jäsenen kentät (nimi, sotu, osoite, jne.), 
 * osaa tarkistaa tietyn kentän oikeellisuuden, jne.
 * @author jailklee
 * @version 10 Mar 2021
 *
 */
public class Jasen {
    private int jasenID;
    private static int seuraavaNro = 1;
    private String nimi = "";
    private String sotu = "";
    private String osoite = "";
    private String postinro = "";
    private String postitoimipaikka = "";
    private String puhnro = "";
    private String sahkoposti = "";
    private String muuta = "";
    
    
    /**
     * Luo jäsenelle jasenID:n, joka pitää huolta siitä että jokainen seuran jäsenistä on uniikki.
     * @return jäsenen ID
     * @example
     * <pre name="test">
     * Jasen jasen1 = new Jasen();
     *  jasen1.getJasenID() === 0;
     *  jasen1.rekisteroi();
     *  Jasen jasen2 = new Jasen();
     *  jasen2.rekisteroi();
     *  int n1 = jasen1.getJasenID();
     *  int n2 = jasen2.getJasenID();
     *  n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        jasenID = seuraavaNro;
        seuraavaNro++;
        return jasenID;
    }
    
    
    /**
     * Palautetaan nimi.
     * @return jäsenen nimi
     */
    public String getNimi() {
        return nimi;
    }
    
    
    /**
     * Jäsenen tunnuksen palautus
     * @return jäsenen ID
     */
    public int getJasenID() {
        return jasenID;
    }
    
    
    /**
     * Täytetään testijäseniä joilla ei olisi samoja tietoja.
     */
    public void taytaJasen() {
        int max = 1000000100;
        int min = 1000000000;
        int range = max - min + 1;
        int randnum = (int)(Math.random() * range) + min;
        String rand = String.valueOf(randnum);
        taytaJasen(rand);
    }
    
    
    /**
     * Täytetään testijäseniä joilla ei olisi samoja tietoja.
     * @param rand random-numero jottei olisi samoja tietoja
     */
    public void taytaJasen(String rand) {
        nimi = "Mallikas Mikko";
        sotu = "010245-123U";
        osoite = "Kotikuja 6";
        postinro = "12345";
        postitoimipaikka = "Kettula";
        puhnro = rand;
        sahkoposti = "m.mallikas@huhuu.com";
        muuta = "";
    }
    
    
    /**
     * Tulostetaan henkilön tiedot.
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", jasenID) + "  " + nimi + "  "
                + sotu);
        out.println("  " + osoite + "  " + postinro + " " + postitoimipaikka);
        out.println("  puh: " + puhnro);
        out.println("  e-mail: " + sahkoposti);
        out.println("  " + muuta);
    }
    
    
    /**
     * Tulostetaan henkilön tiedot.
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }

    
    /**
     * Testiohjelma jäsenelle.
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Jasen jasen1 = new Jasen();
        Jasen jasen2 = new Jasen();
        jasen1.rekisteroi();
        jasen2.rekisteroi();
        jasen1.tulosta(System.out);
        jasen2.tulosta(System.out);
        jasen1.taytaJasen();
        jasen2.taytaJasen();
        jasen1.tulosta(System.out);
        jasen2.tulosta(System.out);
    }

}