package seurarekisteri;


/**
 * Pitää huolen lainoista.
 * @author jailklee
 * @version 10 Mar 2021
 *
 */
public class Lainat {
    private int lkm = 0;
    private String tiedostonNimi = "";
    private Laina alkiot[] = new Laina[10];
    
    /**
     * Oletusmuodostaja
     */
    public Lainat() {
    }
    
    
    /**
     * Lainojen lkm palauttaminen
     * @return lainojen lkm
     */
    public int getLkm() {
        return lkm;
    }
    
    
    /**
     * Tiedostosta lukeminen
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException lukeminen epäonnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + "/lainat.dat";
        throw new SailoException("Ei osata vielä lukea tiedostoa " + tiedostonNimi);
    }
    
    
    /**
     * Tallentaa tiedostoon.
     * @throws SailoException talletus epäonnistuu
     */
    public void tallenna() throws SailoException {
        throw new SailoException("Ei osata vielä tallettaa tiedostoa " + tiedostonNimi);
    }
    
    
    /**
     * Palautetaan lainan viite
     * @param i minkä lainan viite
     * @return lainan viite
     * @throws IndexOutOfBoundsException i ylittää rajan
     */
    public Laina anna(int i) throws IndexOutOfBoundsException {
        if (i<0 || lkm<=i) throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }
    
    
    /**
     * Lisää uuden lainan.
     * @param laina lisättävän lainan viite
     */
    public void lisaa(Laina laina) {
        if (lkm >= alkiot.length) {
            Laina eka[] = new Laina[lkm + 10];
            for (int i=0; i<alkiot.length; i++) {
                eka[i] = alkiot[i];
            }
            alkiot = eka;
        }
        alkiot[lkm] = laina;
        lkm++;
    }
    

    /**
     * Testiohjelma lainoille.
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Lainat lainat = new Lainat();
        
        Laina eka = new Laina(1,2);
        Laina toka = new Laina(2,1);
        
        eka.rekisteroi();
        toka.rekisteroi();
        
        lainat.lisaa(eka);
        lainat.lisaa(toka);
        
        System.out.println("======================================= Testataan lainoja =======================================");
        
        for (int i=0; i<lainat.getLkm(); i++) {
            Laina laina = lainat.anna(i);
            System.out.println("Laina nro: " + i);
            laina.tulosta(System.out);
        }
    }

}
