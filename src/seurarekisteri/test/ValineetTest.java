package seurarekisteri.test;
// Generated by ComTest BEGIN
import seurarekisteri.*;
import java.io.File;
import java.util.Iterator;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2021.04.22 10:56:54 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class ValineetTest {



  // Generated by ComTest BEGIN
  /** testGetValine42 */
  @Test
  public void testGetValine42() {    // Valineet: 42
    Valineet valineet = new Valineet(); 
    Valine valine1 = new Valine(), valine2 = new Valine(); 
    valine1.rekisteroi(); valine2.rekisteroi(); 
    assertEquals("From: Valineet line: 46", null, valineet.getValine(1)); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testKorvaaTaiLisaa69 
   * @throws SailoException when error
   * @throws CloneNotSupportedException when error
   */
  @Test
  public void testKorvaaTaiLisaa69() throws SailoException,CloneNotSupportedException {    // Valineet: 69
    Valineet valineet = new Valineet(); 
    Valine valine1 = new Valine(), valine2 = new Valine(); 
    valine1.rekisteroi(); valine2.rekisteroi(); 
    assertEquals("From: Valineet line: 75", 0, valineet.getLkm()); 
    valineet.korvaaTaiLisaa(valine1); assertEquals("From: Valineet line: 76", 1, valineet.getLkm()); 
    valineet.korvaaTaiLisaa(valine2); assertEquals("From: Valineet line: 77", 2, valineet.getLkm()); 
    Valine valine3 = valine1.clone(); 
    valine3.aseta(3,"kkk"); 
    Iterator<Valine> it = valineet.iterator(); 
    assertEquals("From: Valineet line: 81", true, it.next() == valine1); 
    valineet.korvaaTaiLisaa(valine3); assertEquals("From: Valineet line: 82", 2, valineet.getLkm()); 
    it = valineet.iterator(); 
    Valine v0 = it.next(); 
    assertEquals("From: Valineet line: 85", valine3, v0); 
    assertEquals("From: Valineet line: 86", true, v0 == valine3); 
    assertEquals("From: Valineet line: 87", false, v0 == valine1); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testTallenna115 
   * @throws SailoException when error
   */
  @Test
  public void testTallenna115() throws SailoException {    // Valineet: 115
    Valineet valineet = new Valineet(); 
    Valine jouko1 = new Valine(), jouko2 = new Valine(); 
    jouko1.taytaValine(); 
    jouko2.taytaValine(); 
    valineet.lueTiedostosta(); 
    valineet.lisaa(jouko1); 
    valineet.lisaa(jouko2); 
    valineet.tallenna(); 
    assertEquals("From: Valineet line: 127", jouko1.getValineID(), valineet.anna(0).getValineID()); 
    valineet = new Valineet(); 
    valineet.lueTiedostosta(); 
    valineet.lisaa(jouko2); 
    valineet.tallenna(); 
    String hakemisto = "testi"; 
    String tiedNimi = hakemisto+"/nimet"; 
    File ftied = new File(tiedNimi+".dat"); 
    File dir = new File(hakemisto); 
    dir.mkdir(); 
    ftied.delete(); 
    assertEquals("From: Valineet line: 138", false, ftied.delete()); 
    File fbak = new File(tiedNimi+".bak"); 
    fbak.delete(); 
    assertEquals("From: Valineet line: 141", false, fbak.delete()); 
    assertEquals("From: Valineet line: 142", true, dir.delete()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLisaa181 
   * @throws SailoException when error
   */
  @Test
  public void testLisaa181() throws SailoException {    // Valineet: 181
    Valineet valineet = new Valineet(); 
    Valine eka = new Valine(); 
    Valine toka = new Valine(); 
    assertEquals("From: Valineet line: 186", 0, valineet.getLkm()); 
    valineet.lisaa(eka); assertEquals("From: Valineet line: 187", 1, valineet.getLkm()); 
    valineet.lisaa(toka); assertEquals("From: Valineet line: 188", 2, valineet.getLkm()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testPoista202 
   * @throws SailoException when error
   */
  @Test
  public void testPoista202() throws SailoException {    // Valineet: 202
    Valineet valineet = new Valineet(); 
    Valine valine1 = new Valine(), valine2 = new Valine(), valine3 = new Valine(); 
    valine1.rekisteroi(); valine2.rekisteroi(); valine3.rekisteroi(); 
    int id1 = valine1.getValineID(); 
    valineet.lisaa(valine1); valineet.lisaa(valine2); valineet.lisaa(valine3); 
    assertEquals("From: Valineet line: 209", 1, valineet.poista(id1+1)); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testEtsiId225 
   * @throws SailoException when error
   */
  @Test
  public void testEtsiId225() throws SailoException {    // Valineet: 225
    Valineet valineet = new Valineet(); 
    Valine valine1 = new Valine(), valine2 = new Valine(), valine3 = new Valine(); 
    valine1.rekisteroi(); valine2.rekisteroi(); valine3.rekisteroi(); 
    int id1 = valine1.getValineID(); 
    valineet.lisaa(valine1); valineet.lisaa(valine2); valineet.lisaa(valine3); 
    assertEquals("From: Valineet line: 232", 1, valineet.etsiId(id1+1)); 
    assertEquals("From: Valineet line: 233", 2, valineet.etsiId(id1+2)); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLueTiedostosta247 
   * @throws SailoException when error
   */
  @Test
  public void testLueTiedostosta247() throws SailoException {    // Valineet: 247
    Valineet valineet = new Valineet(); 
    Valine valine1 = new Valine(), valine2 = new Valine(); 
    valine1.taytaValine(); 
    valine2.taytaValine(); 
    valineet.lueTiedostosta(); 
    valineet.tallenna(); 
    valineet = new Valineet(); 
    valineet.lueTiedostosta(); 
    valineet.tallenna(); 
    String hakemisto = "testi"; 
    String tiedNimi = hakemisto+"/nimet"; 
    File ftied = new File(tiedNimi+".dat"); 
    File dir = new File(hakemisto); 
    dir.mkdir(); 
    ftied.delete(); 
    assertEquals("From: Valineet line: 266", false, ftied.delete()); 
    File fbak = new File(tiedNimi+".bak"); 
    fbak.delete(); 
    assertEquals("From: Valineet line: 269", false, fbak.delete()); 
    assertEquals("From: Valineet line: 270", true, dir.delete()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testValineetIterator322 
   * @throws SailoException when error
   */
  @Test
  public void testValineetIterator322() throws SailoException {    // Valineet: 322
    Valineet valineet = new Valineet(); 
    Valine pallo1 = new Valine(), pallo2 = new Valine(); 
    pallo1.rekisteroi(); pallo2.rekisteroi(); 
    valineet.lisaa(pallo1); 
    valineet.lisaa(pallo2); 
    valineet.lisaa(pallo1); 
    StringBuffer ids = new StringBuffer(30); 
    for (Valine valine:valineet)
    ids.append(" "+valine.getValineID()); 
    String tulos = " " + pallo1.getValineID() + " " + pallo2.getValineID() + " " + pallo1.getValineID(); 
    assertEquals("From: Valineet line: 340", tulos, ids.toString()); 
    ids = new StringBuffer(30); 
    for (Iterator<Valine>  i=valineet.iterator(); i.hasNext(); ) {
    Valine valine = i.next(); 
    ids.append(" "+valine.getValineID()); 
    }
    assertEquals("From: Valineet line: 348", tulos, ids.toString()); 
    Iterator<Valine>  i=valineet.iterator(); 
    assertEquals("From: Valineet line: 351", true, i.next() == pallo1); 
    assertEquals("From: Valineet line: 352", true, i.next() == pallo2); 
    assertEquals("From: Valineet line: 353", true, i.next() == pallo1); 
    try {
    i.next(); 
    fail("Valineet: 355 Did not throw NoSuchElementException");
    } catch(NoSuchElementException _e_){ _e_.getMessage(); }
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testEtsi407 
   * @throws SailoException when error
   */
  @Test
  public void testEtsi407() throws SailoException {    // Valineet: 407
    Valineet valineet = new Valineet(); 
    Valine valine1 = new Valine(); valine1.parse("1|Sukset #1|Fischer|2021|Ei lainassa"); 
    Valine valine2 = new Valine(); valine2.parse("2|Sukset #1|Fischer|2021|Ei lainassa"); 
    Valine valine3 = new Valine(); valine3.parse("3|Sukset #1|Fischer|2021|Ei lainassa"); 
    Valine valine4 = new Valine(); valine4.parse("4|Sukset #1|Fischer|2021|Ei lainassa"); 
    Valine valine5 = new Valine(); valine5.parse("5|Sukset #1|Fischer|2021|Ei lainassa"); 
    valineet.lisaa(valine1); valineet.lisaa(valine2); valineet.lisaa(valine3); valineet.lisaa(valine4); valineet.lisaa(valine5); 
    List<Valine> loytyneet; 
    loytyneet = (List<Valine>)valineet.etsi(null,-1); 
    assertEquals("From: Valineet line: 418", 5, loytyneet.size()); 
  } // Generated by ComTest END
}