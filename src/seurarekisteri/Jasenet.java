package seurarekisteri;


/**
 * Jäsenien lisääminen seuraan
 * @author jailklee
 * @version 10 Mar 2021
 *
 */
public class Jasenet {
    private int lkm = 0;
    private String tiedostonNimi = "";
    private Jasen alkiot[] = new Jasen[10];
    
    
    /**
     * Oletusmuodostaja
     */
    public Jasenet() {
        // Attribuuttien oma alustus riittää
    }
    
    
    /**
     * Palauttaa seuran jäsenten lukumäärän.
     * @return jäsenten lukumäärä
     */
    public int getLkm() {
        return lkm;
    }
    
    
    /**
     * Hoitaa tiedostosta lukemisen
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException lukeminen epäonnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + "/jasen.dat";
        throw new SailoException("Ei osata vielä lukea tiedostoa " + tiedostonNimi);
    }
    
    
    /**
     * Hoitaa tiedostoon tallentamisen
     * @throws SailoException tallennuksen epäonnistuessa
     */
    public void tallenna() throws SailoException {
        throw new SailoException("Ei osata vielä tallettaa tiedostoa " + tiedostonNimi);
    }
    
    
    /**
     * Jäsenen viitteen palauttaminen
     * @param i monennenko jäsenen viite
     * @return viite jäseneen
     * @throws IndexOutOfBoundsException i ei sallitulla alueella
     */
    public Jasen anna(int i) throws IndexOutOfBoundsException {
        if (i<0 || lkm<=i) 
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }
    
    
    /**
     * Uuden jäsenen lisäys.
     * @param jasen viite
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * Jasenet jasenet = new Jasenet();
     * Jasen eka = new Jasen();
     * Jasen toka = new Jasen();
     * jasenet.getLkm() === 0;
     * jasenet.lisaa(eka); jasenet.getLkm() === 1;
     * jasenet.lisaa(toka); jasenet.getLkm() === 2;
     * </pre>
     */
    public void lisaa(Jasen jasen) {
        if (lkm >= alkiot.length) {
            Jasen eka[] = new Jasen[lkm + 10];
            for (int i=0; i<alkiot.length; i++) {
                eka[i] = alkiot[i];
            }
            alkiot = eka;
        }
        alkiot[lkm] = jasen;
        lkm++;
    }
    

    /**
     * Jäsenistön testiohjelma
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Jasenet jasenet = new Jasenet();
        
        Jasen eka = new Jasen();
        Jasen toka = new Jasen();
        
        eka.rekisteroi();
        eka.jasenenTaytto();
        toka.rekisteroi();
        toka.jasenenTaytto();
        
        jasenet.lisaa(eka);
        jasenet.lisaa(toka);
        
        System.out.println("======================================= Testataan jäseniä =======================================");
        
        for (int i=0; i<jasenet.getLkm(); i++) {
            Jasen jasen = jasenet.anna(i);
            System.out.println("Jäsen nro: " + i);
            jasen.tulosta(System.out);
        }
    }

}