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
import java.util.NoSuchElementException;

import fi.jyu.mit.ohj2.WildChars;

/**
 * Jäsenien lisääminen seuraan
 * @author jailklee
 * @version 14 Apr 2021
 *
 */
public class Jasenet implements Iterable<Jasen> {
    private int lkm = 0;
    private Jasen alkiot[] = new Jasen[10];
    private String tiedostonPN = "jasenet";
    private boolean muutettu = false;
    
    
    /**
     * Oletusmuodostaja
     */
    public Jasenet() {
    }
    
    
    /**
     * Jäsenten lukumäärän palauttaminen.
     * @return lkm
     */
    public int getLkm() {
        return lkm;
    }
    
    
    /**
     * Jäsenen palauttaminen
     * @param id jasenID
     * @return palautetaan jäsen tai null
     */
    public Jasen getJasen(int id) {
        for (int i=0; i<alkiot.length; i++) {
            if (alkiot[i].getJasenID() == id) return alkiot[i];
        }
        return null;
    }
    
    
    /** 
     * Jäsenen poisto
     * @param id poistettavan jäsenen jäsenID 
     * @return 1 jos poistettiin, 0 jos ei löydy
     */
    public int poista(int id) { 
        int ind = etsiId(id); 
        if (ind < 0) return 0; 
        lkm--; 
        for (int i = ind; i < lkm; i++) 
            alkiot[i] = alkiot[i + 1]; 
        alkiot[lkm] = null; 
        muutettu = true; 
        return 1; 
    }
    
    
    /**
     * Jäsenen id:n etsiminen
     * @param id mitä etsitään
     * @return id jäsenen id
     */
    public int etsiId(int id) { 
        for (int i = 0; i<lkm; i++) 
            if (id == alkiot[i].getJasenID()) return i; 
         return -1; 
    } 
    
    
    /**
     * Korvaa jäsenen tietorakenteessa
     * @param jasen lisättävän jäsenen viite
     * @throws SailoException tietorakenteen ollessa täynnä
     */
    public void korvaaTaiLisaa(Jasen jasen) throws SailoException {
        int id = jasen.getJasenID();
        for (int i = 0; i < lkm; i++) {
            if (alkiot[i].getJasenID() == id) {
                alkiot[i] = jasen;
                muutettu = true;
                return;
            }
        }
        lisaa(jasen);
    }
    
    
    /**
     * Jäsenistön tiedoston luku
     * @throws SailoException lukemisen epäonnistuessa
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     * 
     *  Jasenet jasenet = new Jasenet();
     *  Jasen jouko1 = new Jasen(), jouko2 = new Jasen();
     *  jouko1.jasenenTaytto();
     *  jouko2.jasenenTaytto();
     *  jasenet.lueTiedostosta();
     *  jasenet.lisaa(jouko1);
     *  jasenet.lisaa(jouko2);
     *  jasenet.tallenna();
     *  jasenet = new Jasenet();           
     *  jasenet.lueTiedostosta();  
     *  jasenet.lisaa(jouko2);
     *  jasenet.tallenna();
     * </pre>
     */
    public void lueTiedostosta() throws SailoException {
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {
            String rivi = fi.readLine();
            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Jasen jasen = new Jasen();
                jasen.parse(rivi);
                lisaa(jasen);
            }
            muutettu = false;
        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
        } catch ( IOException e ) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }
    
    
    /**
     * Tiedoston nimen palauttaminen.
     * @return nimi
     */
    public String getTiedostonNimi() {
        return getTiedostonPN() + ".dat";
    }
    
    
    /**
     * Palauttaa tallennukseen käytettävän tiedoston nimen.
     * @return tallennuksen nimi
     */
    public String getTiedostonPN() {
        return tiedostonPN;
    }
    
    
    /**
     * Varakopiotiedoston nimen palauttaminen
     * @return varakopiotiedoston nimi
     */
    public String getBakNimi() {
        return tiedostonPN + ".bak";
    }
    
    
    /**
     * Tiedostoon tallentaminen
     * @throws SailoException talletus epäonnistuu
     * Tiedoston muoto:
     * <pre>
     * 10
     * 1|Jouko Joukkio|121212-2323|Kylpylätie 1|14144|Jyväskunta|10000090|jj@jj.fi|
     * 2|Jouko Joukkio|121212-2323|Kylpylätie 1|14144|Jyväskunta|10000010|jj@jj.fi|
     * </pre>
     * @throws SailoException jos talletus epäonnistuu
     */
    public void tallenna() throws SailoException {
        if ( !muutettu ) return;
        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete();
        ftied.renameTo(fbak);
        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            fo.println(alkiot.length);
            for (Jasen jasen : this) {
                fo.println(jasen.toString());
            }
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }
        muutettu = false;
    }
    
    
    /**
     * Palautetaan viite i:teen jäseneen
     * @param i minkä jäsenen viite
     * @return viite jäseneen
     * @throws IndexOutOfBoundsException kun i ei sallitulla alueella
     */
    public Jasen anna(int i) throws IndexOutOfBoundsException {
        if (i<0 || lkm<=i) throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }
     
    
    /**
     * Luokka jäsenten iteroimiseksi.
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     * Jasenet jasenet = new Jasenet();
     * Jasen jouko1 = new Jasen(), jouko2 = new Jasen();
     * jouko1.rekisteroi(); jouko2.rekisteroi();
     * jasenet.lisaa(jouko1); 
     * jasenet.lisaa(jouko2); 
     * jasenet.lisaa(jouko1); 
     * 
     * StringBuffer ids = new StringBuffer(30);
     * for (Jasen jasen:jasenet)  
     *   ids.append(" "+jasen.getJasenID());           
     * 
     * String tulos = " " + jouko1.getJasenID() + " " + jouko2.getJasenID() + " " + jouko1.getJasenID();
     * 
     * ids.toString() === tulos; 
     * 
     * ids = new StringBuffer(30);
     * for (Iterator<Jasen>  i=jasenet.iterator(); i.hasNext(); ) { 
     *   Jasen jasen = i.next();
     *   ids.append(" "+jasen.getJasenID());           
     * }
     * 
     * ids.toString() === tulos;
     * 
     * Iterator<Jasen>  i=jasenet.iterator();
     * i.next() == jouko1  === true;
     * i.next() == jouko2  === true;
     * i.next() == jouko1  === true;
     * 
     * i.next();  #THROWS NoSuchElementException
     *  
     * </pre>
     */
    public class JasenetIterator implements Iterator<Jasen> {
        private int kohdalla = 0;
        /**
         * Onko olemassa vielä seuraavaa jäsentä
         * @see java.util.Iterator#hasNext()
         * @return true jos on vielä jäseniä
         */
        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }
        /**
         * Annetaan seuraava jäsen
         * @return seuraava jäsen
         * @throws NoSuchElementException jos seuraava alkiota ei enää ole
         * @see java.util.Iterator#next()
         */
        @Override
        public Jasen next() throws NoSuchElementException {
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
     * Palautetaan iteraattori jäsenistään.
     * @return jäsen iteraattori
     */
    @Override
    public Iterator<Jasen> iterator() {
        return new JasenetIterator();
    }
    
    
    /**
     * Palauttaa "taulukossa" hakuehtoon vastaavien jäsenten viitteet
     * @param hakuehto hakuehto
     * @param k etsittävän kentän indeksi 
     * @return tietorakenteen löytyneistä jäsenistä
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     *   Jasenet jasenet = new Jasenet(); 
     *   Jasen jouko1 = new Jasen(); jouko1.parse("1|Anu Anttila|11111-111|Kotikatu 1|"); 
     *   Jasen jouko2 = new Jasen(); jouko2.parse("2|Jouko Joukkio||22222-2222|"); 
     *   Jasen jouko3 = new Jasen(); jouko3.parse("3|Hemmu Heppola|333333-33333||2222222|Jyväsmetsä");  
     *   jasenet.lisaa(jouko1); jasenet.lisaa(jouko2); jasenet.lisaa(jouko3);
     * </pre> 
     */ 
    public Collection<Jasen> etsi(String hakuehto, int k) { 
        String ehto = "*"; 
        if ( hakuehto != null && hakuehto.length() > 0 ) ehto = hakuehto; 
        int hk = k; 
        if ( hk < 0 ) hk = 1;
        Collection<Jasen> loytyneet = new ArrayList<Jasen>(); 
        for (Jasen jasen : this) { 
            if (WildChars.onkoSamat(jasen.anna(hk), ehto)) loytyneet.add(jasen);   
        } 
        return loytyneet; 
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
        muutettu = true;
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