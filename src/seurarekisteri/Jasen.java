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
     * Jäsenen tunnuksen palautus
     * @return jäsenen ID
     */
    public int getJasenID() {
        return jasenID;
    }
    
    
    /**
     * Nimen palautus
     * @return jäsenen nimi
     */
    public String getNimi() {
        return nimi;
    }  
    
    
    /**
     * Testijäsenen tietojen täyttö, pidetään huoli ettei kahta samanlaista. 
     */
    public void jasenenTaytto() {
        int max = 10000100;
        int min = 10000000;
        int range = max - min + 1;
        int randnum = (int)(Math.random() * range) + min;
        String rand = String.valueOf(randnum);
        jasenenTaytto(rand);
    }
    
    
    /**
     * Testijäsenen tietojen täyttö, pidetään huoli ettei kahta samanlaista.
     * @param rand satunnainen nro -> ei samoja tietoja
     */
    public void jasenenTaytto(String rand) {
        nimi = "Jouko Joukkio";
        sotu = "121212-2323";
        osoite = "Kylpylätie 1";
        postinro = "14144";
        postitoimipaikka = "Jyväskunta";
        puhnro = rand;
        sahkoposti = "jj@jj.fi";
        muuta = "";
    }
    
    
    /**
     * Jäsenen tietojen tulostus.
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", jasenID) + "  " + nimi + "  "
                + sotu);
        out.println("  " + osoite + "  " + postinro + " " + postitoimipaikka);
        out.println("  puhelin: " + puhnro);
        out.println("  sähköposti: " + sahkoposti);
        out.println("  " + muuta);
    }
    
    
    /**
     * Jäsenen tietojen tulostus.
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
        jasen1.jasenenTaytto();
        jasen2.jasenenTaytto();
        jasen1.tulosta(System.out);
        jasen2.tulosta(System.out);
    }

}