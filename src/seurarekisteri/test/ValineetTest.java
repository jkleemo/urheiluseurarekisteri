package seurarekisteri.test;
// Generated by ComTest BEGIN
import java.io.File;
import seurarekisteri.*;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2021.04.08 11:58:50 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class ValineetTest {



  // Generated by ComTest BEGIN
  /** 
   * testLisaa94 
   * @throws SailoException when error
   */
  @Test
  public void testLisaa94() throws SailoException {    // Valineet: 94
    Valineet valineet = new Valineet(); 
    Valine eka = new Valine(); 
    Valine toka = new Valine(); 
    assertEquals("From: Valineet line: 99", 0, valineet.getLkm()); 
    valineet.lisaa(eka); assertEquals("From: Valineet line: 100", 1, valineet.getLkm()); 
    valineet.lisaa(toka); assertEquals("From: Valineet line: 101", 2, valineet.getLkm()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLueTiedostosta114 
   * @throws SailoException when error
   */
  @Test
  public void testLueTiedostosta114() throws SailoException {    // Valineet: 114
    Valineet valineet = new Valineet(); 
    Valine pallo1 = new Valine(), pallo2 = new Valine(); 
    pallo1.taytaValine(); 
    pallo2.taytaValine(); 
    valineet.lueTiedostosta(); 
    valineet.lisaa(pallo1); 
    valineet.lisaa(pallo2); 
    valineet.tallenna(); 
    valineet = new Valineet(); 
    valineet.lueTiedostosta(); 
    valineet.lisaa(pallo2); 
    valineet.tallenna(); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testValineetIterator181 
   * @throws SailoException when error
   */
  @Test
  public void testValineetIterator181() throws SailoException {    // Valineet: 181
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
    assertEquals("From: Valineet line: 199", tulos, ids.toString()); 
    ids = new StringBuffer(30); 
    for (Iterator<Valine>  i=valineet.iterator(); i.hasNext(); ) {
    Valine valine = i.next(); 
    ids.append(" "+valine.getValineID()); 
    }
    assertEquals("From: Valineet line: 207", tulos, ids.toString()); 
    Iterator<Valine>  i=valineet.iterator(); 
    assertEquals("From: Valineet line: 210", true, i.next() == pallo1); 
    assertEquals("From: Valineet line: 211", true, i.next() == pallo2); 
    assertEquals("From: Valineet line: 212", true, i.next() == pallo1); 
    try {
    i.next(); 
    fail("Valineet: 214 Did not throw NoSuchElementException");
    } catch(NoSuchElementException _e_){ _e_.getMessage(); }
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testEtsi264 
   * @throws SailoException when error
   */
  @Test
  public void testEtsi264() throws SailoException {    // Valineet: 264
    Valineet valineet = new Valineet(); 
    Valine pallo1 = new Valine(); pallo1.parse("1|Sukset|Fischer|"); 
    Valine pallo2 = new Valine(); pallo2.parse("2|Jalkapallo||Select|"); 
    Valine pallo3 = new Valine(); pallo3.parse("3|Sauvat|One way||2018|"); 
    valineet.lisaa(pallo1); valineet.lisaa(pallo2); valineet.lisaa(pallo3); 
  } // Generated by ComTest END
}