package seurarekisteri;


/**
 * Urheiluseurarekisteri-luokka
 * @author jailklee
 * @version 11 Mar 2021
 *
 */
public class Urheiluseurarekisteri {
    private final Jasenet jasenet = new Jasenet();
    private final Valineet valineet = new Valineet(); 
    private final Lainat lainat = new Lainat(); 
    
    /**
     * Uuden jäsenen lisääminen
     * @param jasen joka lisätään
     */
    public void lisaa(Jasen jasen) {
        jasenet.lisaa(jasen);
    }
    
    
    /**
     * Jäsenen viitteen palauttaminen.
     * @param i mikä jäsen
     * @return jäsenen viite
     * @throws IndexOutOfBoundsException indeksirajan ylittyessä
     */
    public Jasen annaJasen(int i) throws IndexOutOfBoundsException {
        return jasenet.anna(i);
    }
    
    
    /**
     * Uuden kirjan lisääminen
     * @param valine joka lisätään
     */
    public void lisaa(Valine valine) {
        valineet.lisaa(valine);
    }
    
    
    /**
     * Välineen viitteen palauttaminen.
     * @param i mikä kirja
     * @return kirjan viite
     * @throws IndexOutOfBoundsException indeksirajan ylittyessä
     */
    public Valine annaValine(int i) throws IndexOutOfBoundsException {
        return valineet.anna(i);
    }
    
    
    /**
     * Uuden lainan lisääminen
     * @param laina joka lisätään
     */
    public void lisaa(Laina laina) {
        lainat.lisaa(laina);
    }
    
    
    /**
     * Lainan viitteen palauttaminen
     * @param i mikä laina
     * @return lainan viite
     * @throws IndexOutOfBoundsException indeksirajan ylittyessä
     */
    public Laina annaLaina(int i) throws IndexOutOfBoundsException {
        return lainat.anna(i);
    }
    
    
    /**
     * Urheiluseurarekisterin tietojen luku
     * @param nimi mikä tiedosto?
     * @throws SailoException epäonnistuessa
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        jasenet.lueTiedostosta(nimi);
    }
    
    
    /**
     * Urheiluseurarekisterin jäsenten lukumäärän palauttaminen
     * @return jäsenten lkm
     */
    public int getJasenia() {
        return jasenet.getLkm();
    }
    
    
    /**
     * Välineiden lukumäärän palauttaminen
     * @return välineiden lkm
     */
    public int getValineita() {
        return valineet.getLkm();
    }
    
    
    /**
     * Lainojen lukumäärän palauttaminen
     * @return lainojen lkm
     */
    public int getLainoja() {
        return lainat.getLkm();
    }
    
    
    /**
     * Tiedostoon tallentaminen
     * @throws SailoException epäonnistuessa
     */
    public void tallenna() throws SailoException {
        jasenet.tallenna();
        valineet.tallenna();
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
}
