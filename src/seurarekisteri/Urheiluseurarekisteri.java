package seurarekisteri;

import java.util.Collection;

import fi.jyu.mit.fxgui.Dialogs;


/**
 * Urheiluseurarekisteri-luokka
 * @author jailklee
 * @version 14 Apr 2021
 *
 * @example
 * <pre name="testJAVA">
 * #import seurarekisteri.SailoException;
 *  private Urheiluseurarekisteri urheiluseurarekisteri;
 *  private Jasen jasen1;
 *  private Jasen jasen2;
 *  private int jid1;
 *  private int jid2;
 *  
 *  public void alustaUrheiluseurarekisteri() {
 *    urheiluseurarekisteri = new Urheiluseurarekisteri();
 *    jasen1 = new Jasen(); jasen1.jasenenTaytto(); jasen1.rekisteroi();
 *    jasen2 = new Jasen(); jasen2.jasenenTaytto(); jasen2.rekisteroi();
 *    jid1 = jasen1.getJasenID();
 *    jid2 = jasen2.getJasenID();
 *    try {
 *    urheiluseurarekisteri.lisaa(jasen1);
 *    urheiluseurarekisteri.lisaa(jasen2);
 *    } catch ( Exception e) {
 *       System.err.println(e.getMessage());
 *    }
 *  }
 * </pre>
 */
public class Urheiluseurarekisteri {
    private Jasenet jasenet = new Jasenet();
    private Valineet valineet = new Valineet(); 
    private Lainat lainat = new Lainat(); 
    private int jasenNakyyNro;
    
    /**
     * Valitun jäsenen numeron asettaminen attribuutiksi
     * @param nro jäsenen nro
     */
    public void setJasenNakyyNro(int nro) {
        jasenNakyyNro = nro;
    }
    
    
    /**
     * Viimeisimmäksi asetetun jäsenen numeron palauttaminen
     * @return jäsenen nro
     */
    public int getJasenNakyyNro() {
        return jasenNakyyNro;
    }
    
    
    /**
     * Tarkistetaan onko väline jo lainattu
     * @param vid välineen id
     * @return true jos on lainassa valmiiksi, jos ei niin false
     */
    public boolean getValineID(int vid) {
        return lainat.getValineID(vid);
    }
    
    
    /**
     * Palauttaa "taulukossa" hakuehtoon vastaavien jäsenten viitteet.
     * @param hakuehto hakuehto
     * @param k kentän indeksi
     * @return jäsenet, jotka löytyivät
     * @throws SailoException poikkeus
     */
    public Collection<Jasen> etsi(String hakuehto, int k) throws SailoException { 
        return jasenet.etsi(hakuehto, k); 
    } 
    
    
    /**
     * Palauttaa "taulukossa" hakuehtoon vastaavien välineiden viitteet.
     * @param hakuehto hakuehto
     * @param k kentän indeksi
     * @return jäsnenet, jotka löytyivät
     * @throws SailoException poikkeus
     */
    public Collection<Valine> etsiValine(String hakuehto, int k) throws SailoException { 
        return valineet.etsi(hakuehto, k); 
    }
    
    
    /**
     * Uuden jäsenen lisääminen
     * @param jasen lisättävä jäsen
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Urheiluseurarekisteri rekisteri = new Urheiluseurarekisteri();
     * Jasen jouko1 = new Jasen(), jouko2 = new Jasen();
     * rekisteri.getJasenia() === 0;
     * rekisteri.lisaa(jouko1); rekisteri.getJasenia() === 1;
     * rekisteri.lisaa(jouko2); rekisteri.getJasenia() === 2;
     * </pre>
     */
    public void lisaa(Jasen jasen) {
        jasenet.lisaa(jasen);
    }
    
    
    /**
     * Korvaa jäsenen tietorakenteessa.  Ottaa jäsenen omistukseensa.
     * @param jasen lisätäävän jäsenen viite.  Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException poikkeus
     * 
     */ 
    public void korvaaTaiLisaa(Jasen jasen) throws SailoException { 
        jasenet.korvaaTaiLisaa(jasen); 
    } 
    
    
    /**
     * Uuden välineen lisääminen
     * @param valine lisättävä väline
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Urheiluseurarekisteri rekisteri = new Urheiluseurarekisteri();
     * Valine pallo1 = new Valine(), pallo2 = new Valine();
     * rekisteri.getValineita() === 0;
     * rekisteri.lisaa(pallo1); rekisteri.getValineita() === 1;
     * rekisteri.lisaa(pallo2); rekisteri.getValineita() === 2;
     * </pre>
     */
    public void lisaa(Valine valine) {
        valineet.lisaa(valine);
    }
    
    
    /**
     * Korvaa välineen tietorakenteessa.  Ottaa välineen omistukseensa.
     * @param valine lisätäävän välineen viite.  Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException poikkeus
     * 
     */ 
    public void korvaaTaiLisaa(Valine valine) throws SailoException { 
        valineet.korvaaTaiLisaa(valine); 
    } 
    
    
    /**
     * Uuden lainan lisääminen
     * @param laina lisättävä laina
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Urheiluseurarekisteri rekisteri = new Urheiluseurarekisteri();
     * Laina laina1 = new Laina(1,1), laina2 = new Laina(2,2);
     * rekisteri.getLainoja() === 0;
     * rekisteri.lisaa(laina1); rekisteri.getLainoja() === 1;
     * rekisteri.lisaa(laina2); rekisteri.getLainoja() === 2;
     * </pre>
     */
    public void lisaa(Laina laina) {
        lainat.lisaa(laina);
    }
    
    
    /**
     * Jäsenen viitteen palauttaminen
     * @param i mikä jäsen halutaan
     * @return viite jäseneen
     * @throws IndexOutOfBoundsException indeksirajan ylittyessä
     */
    public Jasen annaJasen(int i) throws IndexOutOfBoundsException {
        return jasenet.anna(i);
    }
    
    
    /**
     * Jäsenen palauttaminen
     * @param id jasenID
     * @return palautetaan jäsen tai null
     */
    public Jasen getJasen(int id) {
        return jasenet.getJasen(id);
    }
    
    
    /**
     * Välineen viitteen palauttaminen
     * @param i mikä väline halutaan
     * @return viite välineeseen
     * @throws IndexOutOfBoundsException indeksirajan ylittyessä
     */
    public Valine annaValine(int i) throws IndexOutOfBoundsException {
        return valineet.anna(i);
    }
    
    
    /**
     * Välineen palauttaminen      
     * @param vid välineID
     * @return palautetaana väline tai null
     */
    public Valine getValine(int vid) {
        return valineet.getValine(vid);
    }
    
    
    /**
     * Lainan viitteen palauttaminen
     * @param i mikä laina halutaan
     * @return viite lainaan
     * @throws IndexOutOfBoundsException indeksirajan ylittyessä
     */
    public Laina annaLaina(int i) throws IndexOutOfBoundsException {
        return lainat.anna(i);
    }
    
    
    /**
     * Luetaan seurarekisterin tiedot.
     * @throws SailoException epäonnistuessa
     */
    public void lueJasenetTiedostosta() throws SailoException {
        jasenet = new Jasenet();
        jasenet.lueTiedostosta();
    }
    
    
    /**
     * Luetaan seurarekisterin tiedot.
     * @throws SailoException epäonnistuessa
     */
    public void lueValineetTiedostosta() throws SailoException {
        valineet = new Valineet();
        valineet.lueTiedostosta();
    }
    
    
    /**
     * Luetaan seurarekisterin tiedot.
     * @throws SailoException epäonnistuessa
     */
    public void lueLainatTiedostosta() throws SailoException {
        lainat = new Lainat();
        lainat.lueTiedostosta();
    }
    
    
    /**
     * Palauttaa seurarekisterin jäsenten lukumäärän.
     * @return jäsenten lukumäärä
     */
    public int getJasenia() {
        return jasenet.getLkm();
    }
    
    
    /**
     * Palauttaa seurarekisterin välineiden lukumäärän.
     * @return välineiden lukumäärä
     */
    public int getValineita() {
        return valineet.getLkm();
    }
    
    
    /**
     * Palauttaa seurarekisterin lainojen lukumäärän.
     * @return lainojen lukumäärä
     */
    public int getLainoja() {
        return lainat.getLkm();
    }
    
    
    /**
     * Jäsenen poistaminen
     * @param jasen joka poistetaan'
     * 
     */
    public void poista(Jasen jasen) {
        if (jasen == null) return;
        jasenet.poista(jasen.getJasenID());  
    }
    
    
    /**
     * Välineen poistaminen
     * @param valine joka poistetaan
     */
    public void poista(Valine valine) {
        if (valine == null) return;
        valineet.poista(valine.getValineID());  
    }
    
    
    /**
     * Lainan poistaminen
     * @param laina joka poistetaan
     */
    public void poista(Laina laina) {
        if (laina == null) return;
        lainat.poista(laina.getLainaID());  
    }
    
    
    /**
     * Lainan poisto samalla, kun väline poistetaan
     * @param vid välineID
     */
    public void poistaLaina(int vid) {
        lainat.poistaLaina(vid);
    }
    
    
    /**
     * Jäsenen kaikkien lainojen poistaminen
     * @param jid jasenID
     * @return onko vai ei
     */
    public int palautaEkaLainat(int jid) {
        if (lainat.palautaEnsinLainat(jid) == 1) return 1;
        return 0;
    }
    
    
    /**
     * Seurarekisterin tietojen tallentaminen tiedostoon
     * @throws SailoException tallennuksen epäonnistuessa
     */
    public void tallenna() throws SailoException {
        jasenet.tallenna();
        valineet.tallenna();
        lainat.tallenna();
        Dialogs.showMessageDialog("Tallennus onnistui!");
    }
    
    
    /**
     * Jäsenten tallennus tiedostoon
     * @throws SailoException jos epäonnistuu
     */
    public void tallennaJasenet() throws SailoException {
        jasenet.tallenna();
    }
    
    
    /**
     * Välineiden tallennus tiedostoon
     * @throws SailoException jos epäonnistuu
     */
    public void tallennaValineet() throws SailoException {
        valineet.tallenna();
    }
    
    
    /**
     * Lainojen tallennus tiedostoon
     * @throws SailoException jos epäonnistuu
     */
    public void tallennaLainat() throws SailoException {
        lainat.tallenna();
    }
    
    
    /**
     * Testiohjelma kerholle.
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        Urheiluseurarekisteri urheiluseurarekisteri = new Urheiluseurarekisteri();
        
        Jasen jasen1 = new Jasen();
        Jasen jasen2 = new Jasen();
        
        jasen1.rekisteroi();
        jasen2.rekisteroi();
        jasen1.jasenenTaytto();
        jasen2.jasenenTaytto();
        
        urheiluseurarekisteri.lisaa(jasen1);
        urheiluseurarekisteri.lisaa(jasen2);
        
        Valine valine1 = new Valine();
        Valine valine2 = new Valine();
        
        valine1.rekisteroi();
        valine2.rekisteroi();
        valine1.taytaValine();
        valine2.taytaValine();
        
        urheiluseurarekisteri.lisaa(valine1);
        urheiluseurarekisteri.lisaa(valine2);
        
        Laina laina1 = new Laina(1,2);
        Laina laina2 = new Laina(2,1);
        
        laina1.rekisteroi();
        laina2.rekisteroi();

        urheiluseurarekisteri.lisaa(laina1);
        urheiluseurarekisteri.lisaa(laina2);
        
        System.out.println("======================================= Testataan urheiluseurarekisteriä =======================================");
        for (int i=0; i<urheiluseurarekisteri.getJasenia(); i++) {
            Jasen jasen = urheiluseurarekisteri.annaJasen(i);
            System.out.println("Jäsen: " + i);
            jasen.tulosta(System.out);
        }
        System.out.println("================================================================================================================");
        for (int i=0; i<urheiluseurarekisteri.getValineita(); i++) {
            Valine valine = urheiluseurarekisteri.annaValine(i);
            System.out.println("Väline: " + i);
            valine.tulosta(System.out);
        }
        System.out.println("================================================================================================================");
        for (int i=0; i<urheiluseurarekisteri.getLainoja(); i++) {
            Laina laina = urheiluseurarekisteri.annaLaina(i);
            System.out.println("Väline: " + i);
            laina.tulosta(System.out);
        }
    }


    /**
     * Tallentaa hiljaisesti
     * Seurarekisterin tietojen tallentaminen tiedostoon
     * @throws SailoException tallennuksen epäonnistuessa
     */
    public void hiljainenTallenna() throws SailoException {
        jasenet.tallenna();
        valineet.tallenna();
        lainat.tallenna();
        
    }

}
