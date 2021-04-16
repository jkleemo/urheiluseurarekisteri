package seurarekisteri;

import java.io.OutputStream;
import java.io.PrintStream;
import fi.jyu.mit.ohj2.Mjonot;

/**
 * Ylläpitää jasenID:tä, tietää jäsenen kentät (nimi, sotu, osoite, jne.), 
 * osaa tarkistaa tietyn kentän oikeellisuuden, jne.
 * @author jailklee
 * @version 14 Apr 2021
 *
 */
public class Jasen implements Cloneable{
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
     * Jäsenen kloonaus
     * @return Object kloonattu verio jäsenestä
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException 
     *   Jasen jasen = new Jasen();
     *   jasen.parse("   1  |  Ville Vallaton   | 1111111");
     *   Jasen clone = jasen.clone();
     *   clone.toString() === jasen.toString();
     *   jasen.parse("   4  |  Ville Vallallinen   | 2222222");
     *   clone.toString().equals(jasen.toString()) === false;
     * </pre>
     */
    @Override
    public Jasen clone() throws CloneNotSupportedException {
        Jasen eka;
        eka = (Jasen) super.clone();
        return eka;
    }
    
    
    /**
     * Jäsenen tunnuksen palautus
     * @return jäsenen ID
     */
    public int getJasenID() {
        return jasenID;
    }
    
    
    /**
     * Kenttien määrän palauttaminen
     * @return kenttien lkm
     */
    public int getKentat() {
        return 9;
    }
    
    
    /**
     * Ensimmäisen kentän palauttaminen
     * @return 1. kenttä
     */
    public int ekaKentta() {
        return 1;
    }
    
    
    /**
     * Alustetaan jäsenen merkkijono-attribuuti tyhjiksi jonoiksi
     * ja jasenID = 0.
     */
    public Jasen() {
        //
    } 
    
    
    /**
     * Antaa i:n kentän sisällön merkkijonona 
     * @param i monenenko kentän sisältö palautetaan
     * @return kentän sisältö merkkijonona 
     */
    public String anna(int i) {
        switch (i) {
        case 0: return "" + jasenID;
        case 1: return "" + nimi;
        case 2: return "" + sotu;
        case 3: return "" + osoite;
        case 4: return "" + postinro;
        case 5: return "" + postitoimipaikka;
        case 6: return "" + puhnro;
        case 7: return "" + sahkoposti;
        case 8: return "" + muuta;
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
     *   Jasen jasen = new Jasen();
     *   jasen.aseta(1,"Jouko Joukkio") === null;
     *   jasen.aseta(4,"hemmo") === "Postinumerossa on vain numeroita!";
     *   jasen.aseta(4,"12345") === null; 
     *   jasen.aseta(6,"hippi") === "Puhelinnumerossa on vain numeroita!";
     *   jasen.aseta(6,"12345") === null;
     * </pre>
     */
    public String aseta(int i, String jono) {
        String tjono = jono.trim();
        StringBuffer sb = new StringBuffer(tjono);
        switch (i) {
        case 0:
            setJasenID(Mjonot.erota(sb, '§', getJasenID()));
            return null;
        case 1:
            nimi = tjono;
            return null;
        case 2:
            sotu = tjono;
            return null;
        case 3:
            osoite = tjono;
            return null;
        case 4:
            if (tjono.matches("[0-9]+") == false) return "Postinumerossa on vain numeroita!";
            postinro = tjono;
            return null;
        case 5:
            postitoimipaikka = tjono;
            return null;
        case 6:
            if (tjono.matches("[0-9]+") == false) return "Puhelinnumerossa on vain numeroita!";
            puhnro = tjono;
            return null;
        case 7:
            sahkoposti = tjono;
            return null;
        case 8:
            muuta = tjono;
            return null;
        default:
            return "";
        }
    }
    
    
    /**
     * Palauttaa i:tta jäsenen kenttää vastaavan kysymyksen
     * @param i mikä kysymys palautetaan
     * @return kysymys
     */
    public String getKysymys(int i) {
        switch (i) {
        case 0: return "Jasen ID";
        case 1: return "Nimi";
        case 2: return "Hetu";
        case 3: return "Osoite";
        case 4: return "Postinumero";
        case 5: return "Postitoimipaikka";
        case 6: return "Puhelinnumero";
        case 7: return "Email";
        case 8: return "Muuta";
        default: return "höh";
        }
    }
    
    
    /**
     * Palauttaa jäsenen tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @example
     * <pre name="test">
     *   Jasen jasen = new Jasen();
     *   jasen.parse("   1  |  Jouko Jii   | 123445-1212");
     *   jasen.toString().startsWith("1|Jouko Jii|123445-1212|") === true;
     * </pre>  
     */
    @Override
    public String toString() {
        return "" +
                getJasenID() + "|" +
                nimi + "|" +
                sotu + "|" +
                osoite + "|" +
                postinro + "|" +
                postitoimipaikka + "|" +
                puhnro + "|" +
                sahkoposti + "|" +
                muuta;           
    }
    
    
    /**
     * Selvitää jäsenen tiedot | erotellusta merkkijonosta
     * @param rivi mistä tiedot otetaan
     * 
     * @example
     * <pre name="test">
     *   Jasen jasen = new Jasen();
     *   jasen.parse("   1  |  Jouko Jii   | 123445-1212");
     *   jasen.getJasenID() === 1;
     *   jasen.toString().startsWith("1|Jouko Jii|123445-1212|") === true;
     *
     *   jasen.rekisteroi();
     *   int n = jasen.getJasenID();
     *   jasen.parse(""+(n+10));       
     *   jasen.rekisteroi();           
     *   jasen.getJasenID() === n+10+1;
     *     
     * </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setJasenID(Mjonot.erota(sb, '|', getJasenID()));
        nimi = Mjonot.erota(sb, '|', nimi);
        sotu = Mjonot.erota(sb, '|', sotu);
        osoite = Mjonot.erota(sb, '|', osoite);
        postinro = Mjonot.erota(sb, '|', postinro);
        postitoimipaikka = Mjonot.erota(sb, '|', postitoimipaikka);
        puhnro = Mjonot.erota(sb, '|', puhnro);
        sahkoposti = Mjonot.erota(sb, '|', sahkoposti);
        muuta = Mjonot.erota(sb, '|', muuta);
    }
    
    
    /**
     * Asetetaan tunnusnumero.
     */
    private void setJasenID(int nro) {
        jasenID = nro;
        if (jasenID >= seuraavaNro) seuraavaNro = jasenID+1;
    }
    
    
    /**
     * Nimen palautus
     * @return jäsenen nimi
     * @example
     * <pre name="test">
     *   Jasen jasen1 = new Jasen();
     *   jasen1.jasenenTaytto();
     *   jasen1.getNimi() =R= "Jouko Joukkio";
     * </pre>
     */
    public String getNimi() {
        return nimi;
    }  
    
    
    /**
     * Palautetaan postinro
     * @return postinro
     * @example
     * <pre name="test">
     *   Jasen jasen1 = new Jasen();
     *   jasen1.jasenenTaytto();
     *   jasen1.getPostinro() =R= "14144";
     * </pre>
     */
    public String getPostinro() {
        return postinro;
    }
    
    
    /**
     * Palautetaan puhelinnro.
     * @return puhelinro
     */
    public String getPuhelinNro() {
        return puhnro;
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