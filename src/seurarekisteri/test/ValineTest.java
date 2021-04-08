package seurarekisteri.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import seurarekisteri.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2021.04.07 16:47:25 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class ValineTest {



  // Generated by ComTest BEGIN
  /** testRekisteroi29 */
  @Test
  public void testRekisteroi29() {    // Valine: 29
    Valine valine1 = new Valine(); 
    assertEquals("From: Valine line: 31", 0, valine1.getValineID()); 
    valine1.rekisteroi(); 
    Valine valine2 = new Valine(); 
    valine2.rekisteroi(); 
    int n1 = valine1.getValineID(); 
    int n2 = valine2.getValineID(); 
    assertEquals("From: Valine line: 37", n2-1, n1); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testToString50 */
  @Test
  public void testToString50() {    // Valine: 50
    Valine valine = new Valine(); 
    valine.parse("   1  |  Jalkapallo   | Select"); 
    assertEquals("From: Valine line: 53", true, valine.toString().startsWith("1|Jalkapallo|Select|")); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testParse72 */
  @Test
  public void testParse72() {    // Valine: 72
    Valine valine = new Valine(); 
    valine.parse("   1  |  Jalkapallo   | Select"); 
    assertEquals("From: Valine line: 75", 1, valine.getValineID()); 
    assertEquals("From: Valine line: 76", true, valine.toString().startsWith("1|Jalkapallo|Select|")); 
    valine.rekisteroi(); 
    int n = valine.getValineID(); 
    valine.parse(""+(n+22)); 
    valine.rekisteroi(); 
    assertEquals("From: Valine line: 82", n+22+1, valine.getValineID()); 
  } // Generated by ComTest END
}