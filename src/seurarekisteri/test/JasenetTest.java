package seurarekisteri.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import seurarekisteri.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2021.03.10 13:45:19 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class JasenetTest {


  // Generated by ComTest BEGIN
  /** 
   * testLisaa70 
   * @throws SailoException when error
   */
  @Test
  public void testLisaa70() throws SailoException {    // Jasenet: 70
    Jasenet jasenet = new Jasenet(); 
    Jasen eka = new Jasen(); 
    Jasen toka = new Jasen(); 
    assertEquals("From: Jasenet line: 75", 0, jasenet.getLkm()); 
    jasenet.lisaa(eka); assertEquals("From: Jasenet line: 76", 1, jasenet.getLkm()); 
    jasenet.lisaa(toka); assertEquals("From: Jasenet line: 77", 2, jasenet.getLkm()); 
  } // Generated by ComTest END
}