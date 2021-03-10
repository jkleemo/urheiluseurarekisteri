package seurarekisteri;

import java.util.*;

/**
 * Urheiluseuran välineiden lisääminen
 * @author jailklee
 * @version 10 Mar 2021
 *
 */
public class Valineet implements Iterable<Valine> {
    private String tiedostonNimi = "";
    private List<Valine> alkiot = new ArrayList<Valine>();
    
    
    /**
     * Oletusmuodostaja
     */
    public Valineet() {
    }
    
    
    /**
     * Palauttaa välineiden lukumäärän.
     * @return välineiden lukumäärä
     */
    public int getLkm() {
        return alkiot.size();
    }
    
    
    /**
     * Tallentaa tiedostoon.
     * @throws SailoException talletus epäonnistuu
     */
    public void tallenna() throws SailoException {
        throw new SailoException("Ei osata vielä tallettaa tiedostoa " + tiedostonNimi);
    }
    
    
    /**
     * Välineen viitteen palauttaminen
     * @param i minkä välineen viite
     * @return viite välineeseen
     * @throws IndexOutOfBoundsException kun i menee rajan yli
     */
    public Valine anna(int i) throws IndexOutOfBoundsException {
        if (i<0 || i>alkiot.size()) throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot.get(i);
    }
    
    
    /**
     * Uuden välineen lisääminen.
     * @param valine lisättävän välineen viite
     */
    public void lisaa(Valine valine) {
        alkiot.add(valine);
    }
    
    
    /**
     * Lukee tiedostosta.
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException lukeminen epäonnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + "/valineet.dat";
        throw new SailoException("Ei osata vielä lukea tiedostoa " + tiedostonNimi);
    }
    

    /**
     * Iteraattori kaikkien välineiden läpikäymiseen
     * @return välineiteraattori
     * 
     */
    @Override
    public Iterator<Valine> iterator() {
        return alkiot.iterator();
    }
    

    /**
     * Välineiden testiohjelma.
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Valineet valineet = new Valineet();
        Valine valine1 = new Valine();
        Valine valine2 = new Valine();
        
        valine1.rekisteroi();
        valine1.taytaValine();
        
        valine2.rekisteroi();
        valine2.taytaValine();
        
        valineet.lisaa(valine1);
        valineet.lisaa(valine2);
        
        System.out.println("======================================= Testataan välineitä =======================================");
        for (int i=0; i<valineet.getLkm(); i++) {
            Valine valine = valineet.anna(i);
            System.out.println("Välineen nro: " + i);
            valine.tulosta(System.out);
        }
    }

}
