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
 * @version 2021.04.08 11:10:53 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class JasenetTest {



  // Generated by ComTest BEGIN
  /** 
   * testLueTiedostosta48 
   * @throws SailoException when error
   */
  @Test
  public void testLueTiedostosta48() throws SailoException {    // Jasenet: 48
    Jasenet jasenet = new Jasenet(); 
    Jasen jouko1 = new Jasen(), jouko2 = new Jasen(); 
    jouko1.jasenenTaytto(); 
    jouko2.jasenenTaytto(); 
    jasenet.lueTiedostosta(); 
    jasenet.lisaa(jouko1); 
    jasenet.lisaa(jouko2); 
    jasenet.tallenna(); 
    jasenet = new Jasenet(); 
    jasenet.lueTiedostosta(); 
    jasenet.lisaa(jouko2); 
    jasenet.tallenna(); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testJasenetIterator158 
   * @throws SailoException when error
   */
  @Test
  public void testJasenetIterator158() throws SailoException {    // Jasenet: 158
    Jasenet jasenet = new Jasenet(); 
    Jasen jouko1 = new Jasen(), jouko2 = new Jasen(); 
    jouko1.rekisteroi(); jouko2.rekisteroi(); 
    jasenet.lisaa(jouko1); 
    jasenet.lisaa(jouko2); 
    jasenet.lisaa(jouko1); 
    StringBuffer ids = new StringBuffer(30); 
    for (Jasen jasen:jasenet)
    ids.append(" "+jasen.getJasenID()); 
    String tulos = " " + jouko1.getJasenID() + " " + jouko2.getJasenID() + " " + jouko1.getJasenID(); 
    assertEquals("From: Jasenet line: 176", tulos, ids.toString()); 
    ids = new StringBuffer(30); 
    for (Iterator<Jasen>  i=jasenet.iterator(); i.hasNext(); ) {
    Jasen jasen = i.next(); 
    ids.append(" "+jasen.getJasenID()); 
    }
    assertEquals("From: Jasenet line: 184", tulos, ids.toString()); 
    Iterator<Jasen>  i=jasenet.iterator(); 
    assertEquals("From: Jasenet line: 187", true, i.next() == jouko1); 
    assertEquals("From: Jasenet line: 188", true, i.next() == jouko2); 
    assertEquals("From: Jasenet line: 189", true, i.next() == jouko1); 
    try {
    i.next(); 
    fail("Jasenet: 191 Did not throw NoSuchElementException");
    } catch(NoSuchElementException _e_){ _e_.getMessage(); }
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testEtsi245 
   * @throws SailoException when error
   */
  @Test
  public void testEtsi245() throws SailoException {    // Jasenet: 245
    Jasenet jasenet = new Jasenet(); 
    Jasen jouko1 = new Jasen(); jouko1.parse("1|Anu Anttila|11111-111|Kotikatu 1|"); 
    Jasen jouko2 = new Jasen(); jouko2.parse("2|Jouko Joukkio||22222-2222|"); 
    Jasen jouko3 = new Jasen(); jouko3.parse("3|Hemmu Heppola|333333-33333||2222222|Jyväsmetsä"); 
    jasenet.lisaa(jouko1); jasenet.lisaa(jouko2); jasenet.lisaa(jouko3); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLisaa269 
   * @throws SailoException when error
   */
  @Test
  public void testLisaa269() throws SailoException {    // Jasenet: 269
    Jasenet jasenet = new Jasenet(); 
    Jasen eka = new Jasen(); 
    Jasen toka = new Jasen(); 
    assertEquals("From: Jasenet line: 274", 0, jasenet.getLkm()); 
    jasenet.lisaa(eka); assertEquals("From: Jasenet line: 275", 1, jasenet.getLkm()); 
    jasenet.lisaa(toka); assertEquals("From: Jasenet line: 276", 2, jasenet.getLkm()); 
  } // Generated by ComTest END
}