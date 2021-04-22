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
     * Jäsenen poisto
     * @param id poistettavan jäsenen jäsenID 
     * @return 1 jos poistettiin, 0 jos ei löydy
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     * Jasenet jasenet = new Jasenet(); 
     * Jasen jasen1 = new Jasen(), jasen2 = new Jasen(), jasen3 = new Jasen(); 
     * jasen1.rekisteroi(); jasen2.rekisteroi(); jasen3.rekisteroi(); 
     * int id1 = jasen1.getJasenID(); 
     * jasenet.lisaa(jasen1); jasenet.lisaa(jasen2); jasenet.lisaa(jasen3); 
     * jasenet.poista(id1+1) === 1; 
     * </pre> 
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
     * <pre name="test"> 
     * #THROWS SailoException  
     * Jasenet jasenet = new Jasenet(); 
     * Jasen jasen1 = new Jasen(), jasen2 = new Jasen(), jasen3 = new Jasen(); 
     * jasen1.rekisteroi(); jasen2.rekisteroi(); jasen3.rekisteroi(); 
     * int id1 = jasen1.getJasenID(); 
     * jasenet.lisaa(jasen1); jasenet.lisaa(jasen2); jasenet.lisaa(jasen3); 
     * jasenet.etsiId(id1+1) === 1; 
     * jasenet.etsiId(id1+2) === 2; 
     * </pre> 
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
     * <pre name="test">
     * #THROWS SailoException,CloneNotSupportedException
     * #PACKAGEIMPORT
     * Jasenet jasenet = new Jasenet();
     * Jasen jasen1 = new Jasen(), jasen2 = new Jasen();
     * jasen1.rekisteroi(); jasen2.rekisteroi();
     * jasenet.getLkm() === 0;
     * jasenet.korvaaTaiLisaa(jasen1); jasenet.getLkm() === 1;
     * jasenet.korvaaTaiLisaa(jasen2); jasenet.getLkm() === 2;
     * Jasen jasen3 = jasen1.clone();
     * jasen3.aseta(3,"kkk");
     * Iterator<Jasen> it = jasenet.iterator();
     * it.next() == jasen1 === true;
     * jasenet.korvaaTaiLisaa(jasen3); jasenet.getLkm() === 2;
     * it = jasenet.iterator();
     * Jasen j0 = it.next();
     * j0 === jasen3;
     * j0 == jasen3 === true;
     * j0 == jasen1 === false;
     * </pre>
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
     * #import java.util.Iterator;
     *  Jasenet jasenet = new Jasenet();
     *  Jasen jasen1 = new Jasen(), jasen2 = new Jasen();
     *  jasen1.jasenenTaytto();
     *  jasen2.jasenenTaytto();
     *  jasenet.lueTiedostosta();
     *  jasenet.tallenna();
     *  jasenet = new Jasenet();    
     *  jasenet.lueTiedostosta(); 
     *  jasenet.tallenna();
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
     *  jasenet.anna(0).getJasenID() === jouko1.getJasenID();
     *  jasenet = new Jasenet();           
     *  jasenet.lueTiedostosta();  
     *  jasenet.lisaa(jouko2);
     *  jasenet.tallenna();
     *  jasenet.anna(1).getJasenID() === jouko2.getJasenID();
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
     *   Jasen jasen1 = new Jasen(); jasen1.parse("1|Jouko Joukkio|212198-1234|Kärkikuja 1|40740|Jyväskunta|0449221122|jouko@gmail.com|kalju"); 
     *   Jasen jasen2 = new Jasen(); jasen2.parse("2|Pirkka Pirkkonen|232323-12323|Kärkikuja 3|40740|Jyväsmetro|033232323|pirkka@pirkkonen.com|ei kalju"); 
     *   Jasen jasen3 = new Jasen(); jasen3.parse("3|Jouko Joukkio|212198-1234|Kärkikuja 1|40740|Jyväskunta|0449221122|jouko@gmail.com|kalju"); 
     *   Jasen jasen4 = new Jasen(); jasen4.parse("4|Pirkka Pirkkonen|232323-12323|Kärkikuja 3|40740|Jyväsmetro|033232323|pirkka@pirkkonen.com|ei kalju"); 
     *   Jasen jasen5 = new Jasen(); jasen5.parse("5|Pirkka Pirkkonen|232323-12323|Kärkikuja 3|40740|Jyväsmetro|033232323|pirkka@pirkkonen.com|ei kalju"); 
     *   jasenet.lisaa(jasen1); jasenet.lisaa(jasen2); jasenet.lisaa(jasen3); jasenet.lisaa(jasen4); jasenet.lisaa(jasen5);
     *   List<Jasen> loytyneet;  
     *   loytyneet = (List<Jasen>)jasenet.etsi(null,-1);  
     *   loytyneet.size() === 5;  
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
     * Jasen kolmas = new Jasen();
     * Jasen neljas = new Jasen();
     * Jasen viides = new Jasen();
     * Jasen kuudes = new Jasen();
     * Jasen seiska = new Jasen();
     * Jasen kasi = new Jasen();
     * Jasen ysi = new Jasen();
     * Jasen kymppi = new Jasen();
     * Jasen ykstoista = new Jasen();
     * jasenet.getLkm() === 0;
     * jasenet.lisaa(eka); jasenet.getLkm() === 1;
     * jasenet.lisaa(toka); jasenet.getLkm() === 2;
     * jasenet.lisaa(kolmas); jasenet.getLkm() === 3;
     * jasenet.lisaa(neljas); jasenet.getLkm() === 4;
     * jasenet.lisaa(viides); jasenet.getLkm() === 5;
     * jasenet.lisaa(kuudes); jasenet.getLkm() === 6;
     * jasenet.lisaa(seiska); jasenet.getLkm() === 7;
     * jasenet.lisaa(kasi); jasenet.getLkm() === 8;
     * jasenet.lisaa(ysi); jasenet.getLkm() === 9;
     * jasenet.lisaa(kymppi); jasenet.getLkm() === 10;
     * jasenet.lisaa(ykstoista); jasenet.getLkm() === 11;
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