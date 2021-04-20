package seurarekisteri;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import fi.jyu.mit.ohj2.WildChars;

/**
 * Urheiluseuran välineiden lisääminen
 * @author jailklee
 * @version 14 Apr 2021
 *
 */
public class Valineet implements Iterable<Valine> {
    private List<Valine> alkiot = new ArrayList<Valine>();
    private String tiedostonPN = "valineet";
    private boolean muutettu = false;
    
    
    
    /**
     * Oletusmuodostaja
     */
    public Valineet() {
    }
    
    
    /**
     * Välineen palauttaminen
     * @param vid välineID
     * @return palautetaan väline tai null
     * <pre name="test">
     * Valineet valineet = new Valineet();
     * Valine valine1 = new Valine(), valine2 = new Valine();
     * valine1.rekisteroi(); valine2.rekisteroi();
     * valineet.getValine(1) === null;
     */
    public Valine getValine(int vid) {
        for (int i=0; i<alkiot.size(); i++) {
            if (alkiot.get(i).getValineID() == vid) return alkiot.get(i);
        }
        return null;
    }
    
    
    /**
     * Palauttaa välineiden lukumäärän
     * @return välineiden lkm
     */
    public int getLkm() {
        return alkiot.size();
    }
    
    
    /**
     * Korvaa välineen tietorakenteessa.  Ottaa välineen omistukseensa.
     * @param valine lisätäävän välineen viite.  Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException tietorakenteen ollessa täynnä
     * <pre name="test">
     * #THROWS SailoException,CloneNotSupportedException
     * #PACKAGEIMPORT
     * Valineet valineet = new Valineet();
     * Valine valine1 = new Valine(), valine2 = new Valine();
     * valine1.rekisteroi(); valine2.rekisteroi();
     * valineet.getLkm() === 0;
     * valineet.korvaaTaiLisaa(valine1); valineet.getLkm() === 1;
     * valineet.korvaaTaiLisaa(valine2); valineet.getLkm() === 2;
     * Valine valine3 = valine1.clone();
     * valine3.aseta(3,"kkk");
     * Iterator<Valine> it = valineet.iterator();
     * it.next() == valine1 === true;
     * valineet.korvaaTaiLisaa(valine3); valineet.getLkm() === 2;
     * it = valineet.iterator();
     * Valine v0 = it.next();
     * v0 === valine3;
     * v0 == valine3 === true;
     * v0 == valine1 === false;
     * </pre>
     */
    public void korvaaTaiLisaa(Valine valine) throws SailoException {
        int id = valine.getValineID();
        for (int i=0; i<alkiot.size(); i++) {
            if (alkiot.get(i).getValineID() == id) {
                alkiot.set(i, valine);
                muutettu = true;
                return;
            }
        }
        lisaa(valine);
    }
    
    
    /**
     * Tiedostoon tallentaminen
     * @throws SailoException tallennuksen epäonnistuessa
     * Tiedoston muoto:
     * <pre>
     * 4
     * 1|Sukset|Fischer|2018|1.1.2022|
     * 2|Sukset|Fischer|2018|1.1.2022|
     * 3|Sukset|Fischer|2018|1.1.2022|
     * 4|Sukset|Fischer|2018|1.1.2022|
     * </pre>
     * @throws SailoException jos talletus epäonnistuu
          * <pre name="test">
     * #THROWS SailoException,CloneNotSupportedException
     * #PACKAGEIMPORT
     * Valineet valineet = new Valineet();
     * Valine valine1 = new Valine(), valine2 = new Valine();
     * valine1.rekisteroi(); valine2.rekisteroi();
     * valineet.getLkm() === 0;
     * valineet.korvaaTaiLisaa(valine1); valineet.getLkm() === 1;
     * valineet.korvaaTaiLisaa(valine2); valineet.getLkm() === 2;
     * Valine valine3 = valine1.clone();
     * valine3.aseta(3,"kkk");
     * Iterator<Valine> it = valineet.iterator();
     * it.next() == valine1 === true;
     * valineet.korvaaTaiLisaa(valine3); valineet.getLkm() === 2;
     * it = valineet.iterator();
     * Valine v0 = it.next();
     * v0 === valine3;
     * v0 == valine3 === true;
     * v0 == valine1 === false;
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
            for (Valine valine : this) {
                fo.println(valine.toString());
            }
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }
        muutettu = false;
    }
    
    
    /**
     * Palauttaa viitteen i:teen välineeseen.
     * @param i minkä välineen viite halutaan
     * @return viite välineeseen
     * @throws IndexOutOfBoundsException jos ei sallitulla alueella
     */
    public Valine anna(int i) throws IndexOutOfBoundsException {
        if (i<0 || i>alkiot.size()) throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot.get(i);
    }
    
    
    /**
     * Uuden välineen lisääminen tietorakenteeseen
     * @param valine lisättävän välineen viite
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * Valineet valineet = new Valineet();
     * Valine eka = new Valine();
     * Valine toka = new Valine();
     * valineet.getLkm() === 0;
     * valineet.lisaa(eka); valineet.getLkm() === 1;
     * valineet.lisaa(toka); valineet.getLkm() === 2;
     * </pre>
     */
    public void lisaa(Valine valine) {
        alkiot.add(valine);
        muutettu = true;
    }
    
    
    /** 
     * Välineen poistaminen
     * @param id välineID
     * @return 1 jos poistettiin, 0 jos ei löydy 
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     * Valineet valineet = new Valineet(); 
     * Valine valine1 = new Valine(), valine2 = new Valine(), valine3 = new Valine(); 
     * valine1.rekisteroi(); valine2.rekisteroi(); valine3.rekisteroi(); 
     * int id1 = valine1.getValineID(); 
     * valineet.lisaa(valine1); valineet.lisaa(valine2); valineet.lisaa(valine3); 
     * valineet.poista(id1+1) === 1; 
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
     * Välineen id:n etsiminen
     * @param id id, joka etsitään
     * @return id löytyneen välineen indeksi tai -1 jos ei löydy
     * <pre name="test"> 
     * #THROWS SailoException  
     * Valineet valineet = new Valineet(); 
     * Valine valine1 = new Valine(), valine2 = new Valine(), valine3 = new Valine(); 
     * valine1.rekisteroi(); valine2.rekisteroi(); valine3.rekisteroi(); 
     * int id1 = valine1.getValineID(); 
     * valineet.lisaa(valine1); valineet.lisaa(valine2); valineet.lisaa(valine3); 
     * valineet.etsiId(id1+1) === 1; 
     * valineet.etsiId(id1+2) === 2; 
     * </pre> 
     */ 
    public int etsiId(int id) { 
        for (int i = 0; i<alkiot.size(); i++) 
            if (id == alkiot.get(i).getValineID()) return i; 
         return -1; 
    } 
    
    
    /**
     * Tiedostosta lukeminen
     * @throws SailoException epäonnistuessa
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     * #import java.util.Iterator;
     *  Valineet valineet = new Valineet();
     *  Valine valine1 = new Valine(), valine2 = new Valine();
     *  valine1.taytaValine();
     *  valine2.taytaValine();
     *  valineet.lueTiedostosta();
     *  valineet.tallenna();
     *  valineet = new Valineet();    
     *  valineet.lueTiedostosta(); 
     *  valineet.tallenna();
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
                Valine valine = new Valine();
                valine.parse(rivi);
                lisaa(valine);
            }
            muutettu = false;
        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
        } catch ( IOException e ) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }
    
    
    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {
        return getTiedostonPN() + ".dat";
    }
    
    
    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tiedoston nimi
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
     * Luokka välineiden iteroimiseksi.
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     * Valineet valineet = new Valineet();
     * Valine pallo1 = new Valine(), pallo2 = new Valine();
     * pallo1.rekisteroi(); pallo2.rekisteroi();
     * valineet.lisaa(pallo1); 
     * valineet.lisaa(pallo2); 
     * valineet.lisaa(pallo1); 
     * 
     * StringBuffer ids = new StringBuffer(30);
     * for (Valine valine:valineet)  
     *   ids.append(" "+valine.getValineID());           
     * 
     * String tulos = " " + pallo1.getValineID() + " " + pallo2.getValineID() + " " + pallo1.getValineID();
     * 
     * ids.toString() === tulos; 
     * 
     * ids = new StringBuffer(30);
     * for (Iterator<Valine>  i=valineet.iterator(); i.hasNext(); ) { 
     *   Valine valine = i.next();
     *   ids.append(" "+valine.getValineID());           
     * }
     * 
     * ids.toString() === tulos;
     * 
     * Iterator<Valine>  i=valineet.iterator();
     * i.next() == pallo1  === true;
     * i.next() == pallo2  === true;
     * i.next() == pallo1  === true;
     * 
     * i.next();  #THROWS NoSuchElementException
     *  
     * </pre>
     */
    public class ValineetIterator implements Iterator<Valine> {
        private int kohdalla = 0;
        /**
         * Onko olemassa vielä seuraavaa välinettä
         * @see java.util.Iterator#hasNext()
         * @return true jos on vielä välineitä
         */
        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }
        /**
         * Annetaan seuraava väline
         * @return seuraava väline
         * @throws NoSuchElementException jos seuraava alkiota ei enää ole
         * @see java.util.Iterator#next()
         */
        @Override
        public Valine next() throws NoSuchElementException {
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
     * Palautetaan iteraattori välineistään.
     * @return väline iteraattori
     */
    @Override
    public Iterator<Valine> iterator() {
        return new ValineetIterator();
    }
    
    
    /**
     * Palauttaa "taulukossa" hakuehtoon vastaavien välineiden viitteet
     * @param hakuehto hakuehto
     * @param k etsittävän kentän indeksi 
     * @return tietorakenteen löytyneistä välineistä
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     *   Valineet valineet = new Valineet(); 
     *   Valine valine1 = new Valine(); valine1.parse("1|Sukset #1|Fischer|2021|Ei lainassa"); 
     *   Valine valine2 = new Valine(); valine2.parse("2|Sukset #1|Fischer|2021|Ei lainassa"); 
     *   Valine valine3 = new Valine(); valine3.parse("3|Sukset #1|Fischer|2021|Ei lainassa"); 
     *   Valine valine4 = new Valine(); valine4.parse("4|Sukset #1|Fischer|2021|Ei lainassa"); 
     *   Valine valine5 = new Valine(); valine5.parse("5|Sukset #1|Fischer|2021|Ei lainassa"); 
     *   valineet.lisaa(valine1); valineet.lisaa(valine2); valineet.lisaa(valine3); valineet.lisaa(valine4); valineet.lisaa(valine5);
     *   List<Valine> loytyneet;  
     *   loytyneet = (List<Valine>)valineet.etsi(null,-1);  
     *   loytyneet.size() === 5;  
     * </pre> 
     */ 
    public Collection<Valine> etsi(String hakuehto, int k) { 
        String ehto = "*"; 
        if ( hakuehto != null && hakuehto.length() > 0 ) ehto = hakuehto; 
        int hk = k; 
        if ( hk < 0 ) hk = 1;
        Collection<Valine> loytyneet = new ArrayList<Valine>(); 
        for (Valine valine : this) { 
            if (WildChars.onkoSamat(valine.anna(hk), ehto)) loytyneet.add(valine);   
        } 
        return loytyneet; 
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
