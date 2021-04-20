package seurarekisteri;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * Pitää huolen lainoista.
 * @author jailklee
 * @version 14 Apr 2021
 *
 */
public class Lainat implements Iterable<Laina> {
    private List<Laina> alkiot = new ArrayList<Laina>();
    private String tiedostonPN = "lainat";
    private boolean muutettu = false;
    
    
    /**
     * Oletusmuodostaja
     */
    public Lainat() {
    }
    
    
    /**
     * Lainojen lukumäärän palauttaminen
     * @return lainojen lkm
     */
    public int getLkm() {
        return alkiot.size();
    }
    
    
    /**
     * Tiedostosta lukeminen
     * @throws SailoException lukemisen epäonnistuessa
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     * #import java.util.Iterator;
     *  Lainat lainat = new Lainat();
     *  lainat.lueTiedostosta();
     *  lainat.tallenna();
     *  lainat = new Lainat();    
     *  lainat.lueTiedostosta(); 
     *  lainat.tallenna();
     *  String hakemisto = "testi";
     *  String tiedNimi = hakemisto+"/nimet";
     *  File ftied = new File(tiedNimi+".dat");
     *  File dir = new File(hakemisto);
     *  dir.mkdir();
     *  ftied.delete();
     *  ftied.delete() === false;
     *  File fbak = new File(tiedNimi+".bak");
     *  fbak.delete();
     *  fbak.delete() === false;
     *  dir.delete() === true;
     * </pre>
     */
    public void lueTiedostosta() throws SailoException {
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {
            String rivi = fi.readLine();
            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Laina laina = new Laina(1,1);
                laina.parse(rivi);
                lisaa(laina);
            }
            muutettu = false;
        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
        } catch ( IOException e ) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }
    
    
    /**
     * Lainan poisto välineen id:llä.
     * @param vid välineID
     * <pre name="test"> 
     * #THROWS SailoException  
     * Lainat lainat = new Lainat(); 
     * Laina laina1 = new Laina(1,2), laina2 = new Laina(2,3), laina3 = new Laina(3,1); 
     * laina1.rekisteroi(); laina2.rekisteroi(); laina3.rekisteroi(); 
     * int id1 = laina1.getLainaID(); 
     * lainat.lisaa(laina1); lainat.lisaa(laina2); lainat.lisaa(laina3); 
     * lainat.poistaLaina(id1+1); 
     * </pre> 
     */ 
    public void poistaLaina(int vid) {
        for (int i=0; i<alkiot.size(); i++) {
            if (alkiot.get(i).getValineID() == vid) poista(alkiot.get(i).getLainaID());
        }
    }
    
    
    /**
     * Palauta ensin!
     * @param jid jäsenID
     * @return onko vai ei
     * <pre name="test"> 
     * #THROWS SailoException  
     * Lainat lainat = new Lainat(); 
     * Laina laina1 = new Laina(1,2), laina2 = new Laina(2,3), laina3 = new Laina(3,1); 
     * laina1.rekisteroi(); laina2.rekisteroi(); laina3.rekisteroi(); 
     * int id1 = laina1.getLainaID(); 
     * lainat.lisaa(laina1); lainat.lisaa(laina2); lainat.lisaa(laina3); 
     * lainat.palautaEnsinLainat(id1+1) === 0; 
     * </pre> 
     */ 
    public int palautaEnsinLainat(int jid) {
        for (int i=0; i<alkiot.size(); i++) {
            if (alkiot.get(i).getJasenID() == jid) {
                palautaEnsin(alkiot.get(i).getLainaID());
                return 1;
            }
        }
        return 0;
    }
    
    
    /** 
     * Lainan poistaminen
     * @param id lainanID
     * @return 1 jos poistettiin, 0 jos ei löydy
     * <pre name="test"> 
     * #THROWS SailoException  
     * Lainat lainat = new Lainat(); 
     * Laina laina1 = new Laina(1,2), laina2 = new Laina(2,3), laina3 = new Laina(3,1); 
     * laina1.rekisteroi(); laina2.rekisteroi(); laina3.rekisteroi(); 
     * int id1 = laina1.getLainaID(); 
     * lainat.lisaa(laina1); lainat.lisaa(laina2); lainat.lisaa(laina3); 
     * lainat.palautaEnsin(id1+1) === 1; 
     * </pre> 
     */ 
    public int palautaEnsin(int id) { 
        int ind = etsiId(id); 
        if (ind < 0) return 0; 
        muutettu = true; 
        return 1; 
    }
    
    
    /** 
     * Lainan poistaminen
     * @param id lainanID
     * @return 1 jos poistettiin, 0 jos ei löydy
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     * Lainat lainat = new Lainat(); 
     * Laina laina1 = new Laina(1,2), laina2 = new Laina(2,3), laina3 = new Laina(3,1); 
     * laina1.rekisteroi(); laina2.rekisteroi(); laina3.rekisteroi(); 
     * int id1 = laina1.getLainaID(); 
     * lainat.lisaa(laina1); lainat.lisaa(laina2); lainat.lisaa(laina3); 
     * lainat.poista(id1+1) === 1; 
     * </pre> 
     */ 
    public int poista(int id) { 
        int ind = etsiId(id); 
        if (ind < 0) return 0; 
        alkiot.remove(ind);
        muutettu = true; 
        return 1; 
    } 
    
    
    /**
     * Lainan id:n etsiminen
     * @param id id, jota etsitään
     * @return id lainan id
     * <pre name="test"> 
     * #THROWS SailoException  
     * Lainat lainat = new Lainat(); 
     * Laina laina1 = new Laina(1,2), laina2 = new Laina(2,3), laina3 = new Laina(3,1); 
     * laina1.rekisteroi(); laina2.rekisteroi(); laina3.rekisteroi(); 
     * int id1 = laina1.getLainaID(); 
     * lainat.lisaa(laina1); lainat.lisaa(laina2); lainat.lisaa(laina3); 
     * lainat.etsiId(id1+1) === 1; 
     * lainat.etsiId(id1+2) === 2; 
     * </pre> 
     */ 
    public int etsiId(int id) { 
        for (int i = 0; i<alkiot.size(); i++) 
            if (id == alkiot.get(i).getLainaID()) return i; 
         return -1; 
    } 
    
    
    /**
     * Tiedoston nimen palauttaminen
     * @return tallennustiedostontiedoston nimi
     */
    public String getTiedostonNimi() {
        return getTiedostonPN() + ".dat";
    }
    
    
    /**
     * Tiedoston nimen palauttaminen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPN() {
        return tiedostonPN;
    }
    
    
    /**
     * Palauttaa varakopiotiedoston nimen
     * @return varakopiotiedoston nimi
     */
    public String getBakNimi() {
        return tiedostonPN + ".bak";
    }
    
    
    /**
     * Tallentaa tiedostoon.
     * @throws SailoException talletuksen epäonnistuessa
     * Tiedoston muoto:
     * <pre>
     * 3
     * 1|1|6
     * 2|1|3
     * 3|1|10
     * </pre>
     * @throws SailoException jos talletus epäonnistuu
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     * 
     *  Lainat lainat = new Lainat();
     *  Laina jouko1 = new Laina(1,2), jouko2 = new Laina(1,3);
     *  lainat.lueTiedostosta();
     *  lainat.lisaa(jouko1);
     *  lainat.lisaa(jouko2);
     *  lainat.tallenna();
     *  lainat = new Lainat();           
     *  lainat.lueTiedostosta();  
     *  lainat.lisaa(jouko2);
     *  lainat.tallenna();
     *  String hakemisto = "testi";
     *  String tiedNimi = hakemisto+"/nimet";
     *  File ftied = new File(tiedNimi+".dat");
     *  File dir = new File(hakemisto);
     *  dir.mkdir();
     *  ftied.delete();
     *  ftied.delete() === false;
     *  File fbak = new File(tiedNimi+".bak");
     *  fbak.delete();
     *  fbak.delete() === false;
     *  dir.delete() === true;
     * </pre>
     */
    public void tallenna() throws SailoException {
        if ( !muutettu ) return;
        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete();
        ftied.renameTo(fbak);
        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            fo.println(alkiot.size());
            for (Laina laina : this) {
                fo.println(laina.toString());
            }
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }
        muutettu = false;
    }
    
    
    /**
     * Palauttaa viitteen i:teen jäseneen.
     * @param i minkä lainan viite
     * @return viite lainaan
     * @throws IndexOutOfBoundsException i ylittää rajan
     */
    public Laina anna(int i) throws IndexOutOfBoundsException {
        if (i<0 || i>alkiot.size()) throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot.get(i);
    }
    
    
    /**
     * Uuden lainan lisääminen
     * @param laina lainan viite, joka lisätään
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * Lainat lainat = new Lainat();
     * Laina eka = new Laina(1,1);
     * Laina toka = new Laina(2,2);
     * lainat.getLkm() === 0;
     * lainat.lisaa(eka); lainat.getLkm() === 1;
     * lainat.lisaa(toka); lainat.getLkm() === 2;
     * </pre>
     */
    public void lisaa(Laina laina) {
        alkiot.add(laina);
        muutettu = true;
    }
    
    
    /**
     * Tarkistetaan onko väline jo lainattu
     * @param vid välineen id
     * @return true jos laina on jo olemassa, muuten false
     */
    public boolean getValineID(int vid) {
        for (int i=0; i<alkiot.size(); i++) {
            if (alkiot.get(i).getValineID() == vid) return true;
        }
        return false;
    }
    
    
    /**
     * Luokka lainojen iteroimiseksi.
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     * Lainat lainat = new Lainat();
     * Laina laina1 = new Laina(1,1), laina2 = new Laina(2,2);
     * laina1.rekisteroi(); laina2.rekisteroi();
     * lainat.lisaa(laina1); 
     * lainat.lisaa(laina2); 
     * lainat.lisaa(laina1); 
     * 
     * StringBuffer ids = new StringBuffer(30);
     * for (Laina laina:lainat)  
     *   ids.append(" "+laina.getLainaID());           
     * 
     * String tulos = " " + laina1.getLainaID() + " " + laina2.getLainaID() + " " + laina1.getLainaID();
     * 
     * ids.toString() === tulos; 
     * 
     * ids = new StringBuffer(30);
     * for (Iterator<Laina>  i=lainat.iterator(); i.hasNext(); ) { 
     *   Laina laina = i.next();
     *   ids.append(" "+laina.getLainaID());           
     * }
     * 
     * ids.toString() === tulos;
     * 
     * Iterator<Laina>  i=lainat.iterator();
     * i.next() == laina1  === true;
     * i.next() == laina2  === true;
     * i.next() == laina1  === true;
     * 
     * i.next();  #THROWS NoSuchElementException
     *  
     * </pre>
     */
    public class LainatIterator implements Iterator<Laina> {
        private int kohdalla = 0;
        /**
         * Onko olemassa vielä seuraavaa kirjaa
         * @see java.util.Iterator#hasNext()
         * @return true jos on vielä kirjoja
         */
        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }
        /**
         * Annetaan seuraava laina
         * @return seuraava laina
         * @throws NoSuchElementException jos seuraava alkiota ei enää ole
         * @see java.util.Iterator#next()
         */
        @Override
        public Laina next() throws NoSuchElementException {
            if ( !hasNext() ) throw new NoSuchElementException("Ei ole");
            return anna(kohdalla++);
        }
        /**
         * Tuhoamista ei ole toteutettu
         * @throws UnsupportedOperationException aina
         * @see java.util.Iterator#remove()
         */
        @Override
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("Ei poisteta");
        }
    }
    /**
     * Palautetaan iteraattori lainoistaan.
     * @return laina iteraattori
     */
    @Override
    public Iterator<Laina> iterator() {
        return new LainatIterator();
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
