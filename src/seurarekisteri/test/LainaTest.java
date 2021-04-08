package seurarekisteri.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import seurarekisteri.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2021.04.08 11:49:23 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class LainaTest {



  // Generated by ComTest BEGIN
  /** testRekisteroi45 */
  @Test
  public void testRekisteroi45() {    // Laina: 45
    Laina eka = new Laina(1,2); 
    assertEquals("From: Laina line: 47", 0, eka.getLainaID()); 
    eka.rekisteroi(); 
    Laina toka = new Laina(2,1); 
    toka.rekisteroi(); 
    int n1 = eka.getLainaID(); 
    int n2 = toka.getLainaID(); 
    assertEquals("From: Laina line: 53", n2-1, n1); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testToString66 */
  @Test
  public void testToString66() {    // Laina: 66
    Laina laina = new Laina(1,1); 
    laina.parse("   1  | 1 "); 
    assertEquals("From: Laina line: 69", true, laina.toString().startsWith("1|1|")); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testParse85 */
  @Test
  public void testParse85() {    // Laina: 85
    Laina laina = new Laina(2,1); 
    laina.parse("   2  |  1  "); 
    assertEquals("From: Laina line: 88", 1, laina.getJasenID()); 
    assertEquals("From: Laina line: 89", true, laina.toString().startsWith("2|1|")); 
    laina.rekisteroi(); 
    int n = laina.getLainaID(); 
    laina.parse(""+(n+15)); 
    laina.rekisteroi(); 
    assertEquals("From: Laina line: 95", n+15+1, laina.getLainaID()); 
  } // Generated by ComTest END
}