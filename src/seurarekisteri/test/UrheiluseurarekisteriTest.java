package seurarekisteri.test;
// Generated by ComTest BEGIN
import seurarekisteri.SailoException;
import java.io.File;
import java.util.Iterator;
import static org.junit.Assert.*;
import org.junit.*;
import seurarekisteri.*;
import java.util.Collection;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2021.04.15 23:09:51 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class UrheiluseurarekisteriTest {


  // Generated by ComTest BEGIN  // Urheiluseurarekisteri: 12
   private Urheiluseurarekisteri urheiluseurarekisteri; 
   private Jasen jasen1; 
   private Jasen jasen2; 
   private int jid1; 
   private int jid2; 
   private Valine valine1; 
   private Valine valine2; 
   private int vid1; 
   private int vid2; 
   private Laina laina1; 
   private Laina laina2; 
   private int lid1; 
   private int lid2; 


   public void alustaUrheiluseurarekisteri() {
     urheiluseurarekisteri = new Urheiluseurarekisteri(); 
     jasen1 = new Jasen(); jasen1.jasenenTaytto(); jasen1.rekisteroi(); 
     jasen2 = new Jasen(); jasen2.jasenenTaytto(); jasen2.rekisteroi(); 
     jid1 = jasen1.getJasenID(); 
     jid2 = jasen2.getJasenID(); 
     valine1 = new Valine(); valine1.taytaValine(); valine1.rekisteroi(); 
     valine2 = new Valine(); valine2.taytaValine(); valine2.rekisteroi(); 
     vid1 = valine1.getValineID(); 
     vid2 = valine2.getValineID(); 
     laina1 = new Laina(jid1, vid1); laina1.rekisteroi(); 
     laina2 = new Laina(jid2, vid2); laina2.rekisteroi(); 
     lid1 = laina1.getLainaID(); 
     lid2 = laina2.getLainaID(); 
     try {
     urheiluseurarekisteri.lisaa(jasen1); 
     urheiluseurarekisteri.lisaa(jasen2); 
     urheiluseurarekisteri.lisaa(valine1); 
     urheiluseurarekisteri.lisaa(valine2); 
     urheiluseurarekisteri.lisaa(laina1); 
     urheiluseurarekisteri.lisaa(laina2); 
     } catch ( Exception e) {
        System.err.println(e.getMessage()); 
     }
   }
  // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testGetJasenNakyyNro75 
   * @throws Exception when error
   */
  @Test
  public void testGetJasenNakyyNro75() throws Exception {    // Urheiluseurarekisteri: 75
    alustaUrheiluseurarekisteri(); 
    assertEquals("From: Urheiluseurarekisteri line: 78", 0, urheiluseurarekisteri.getJasenNakyyNro()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testGetValineID91 
   * @throws Exception when error
   */
  @Test
  public void testGetValineID91() throws Exception {    // Urheiluseurarekisteri: 91
    alustaUrheiluseurarekisteri(); 
    assertEquals("From: Urheiluseurarekisteri line: 94", false, urheiluseurarekisteri.getValineID(1)); 
    assertEquals("From: Urheiluseurarekisteri line: 95", false, urheiluseurarekisteri.getValineID(2)); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testEtsi109 
   * @throws CloneNotSupportedException when error
   * @throws SailoException when error
   */
  @Test
  public void testEtsi109() throws CloneNotSupportedException, SailoException {    // Urheiluseurarekisteri: 109
    alustaUrheiluseurarekisteri(); 
    Jasen jasen3 = new Jasen(); jasen3.rekisteroi(); 
    jasen3.aseta(1,"Jussi Jussinen"); 
    urheiluseurarekisteri.lisaa(jasen3); 
    Collection<Jasen> loytyneet = urheiluseurarekisteri.etsi("*Jussi*",1); 
    assertEquals("From: Urheiluseurarekisteri line: 120", 1, loytyneet.size()); 
    Iterator<Jasen> it = loytyneet.iterator(); 
    assertEquals("From: Urheiluseurarekisteri line: 122", true, it.next() == jasen3); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testEtsiValine137 
   * @throws CloneNotSupportedException when error
   * @throws SailoException when error
   */
  @Test
  public void testEtsiValine137() throws CloneNotSupportedException, SailoException {    // Urheiluseurarekisteri: 137
    alustaUrheiluseurarekisteri(); 
    Valine valine3 = new Valine(); valine3.rekisteroi(); 
    valine3.aseta(1,"Sukset #3"); 
    urheiluseurarekisteri.lisaa(valine3); 
    Collection<Valine> loytyneet = urheiluseurarekisteri.etsiValine("*Sukset*",1); 
    assertEquals("From: Urheiluseurarekisteri line: 148", 3, loytyneet.size()); 
    Iterator<Valine> it = loytyneet.iterator(); 
    assertEquals("From: Urheiluseurarekisteri line: 150", false, it.next() == valine3); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLisaa162 
   * @throws SailoException when error
   */
  @Test
  public void testLisaa162() throws SailoException {    // Urheiluseurarekisteri: 162
    Urheiluseurarekisteri rekisteri = new Urheiluseurarekisteri(); 
    Jasen jouko1 = new Jasen(), jouko2 = new Jasen(); 
    assertEquals("From: Urheiluseurarekisteri line: 166", 0, rekisteri.getJasenia()); 
    rekisteri.lisaa(jouko1); assertEquals("From: Urheiluseurarekisteri line: 167", 1, rekisteri.getJasenia()); 
    rekisteri.lisaa(jouko2); assertEquals("From: Urheiluseurarekisteri line: 168", 2, rekisteri.getJasenia()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testKorvaaTaiLisaa181 
   * @throws SailoException when error
   */
  @Test
  public void testKorvaaTaiLisaa181() throws SailoException {    // Urheiluseurarekisteri: 181
    alustaUrheiluseurarekisteri(); 
    assertEquals("From: Urheiluseurarekisteri line: 184", 2, urheiluseurarekisteri.etsi("*",0).size()); 
    urheiluseurarekisteri.korvaaTaiLisaa(jasen1); 
    assertEquals("From: Urheiluseurarekisteri line: 186", 2, urheiluseurarekisteri.etsi("*",0).size()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLisaa198 
   * @throws SailoException when error
   */
  @Test
  public void testLisaa198() throws SailoException {    // Urheiluseurarekisteri: 198
    Urheiluseurarekisteri rekisteri = new Urheiluseurarekisteri(); 
    Valine pallo1 = new Valine(), pallo2 = new Valine(); 
    assertEquals("From: Urheiluseurarekisteri line: 202", 0, rekisteri.getValineita()); 
    rekisteri.lisaa(pallo1); assertEquals("From: Urheiluseurarekisteri line: 203", 1, rekisteri.getValineita()); 
    rekisteri.lisaa(pallo2); assertEquals("From: Urheiluseurarekisteri line: 204", 2, rekisteri.getValineita()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testKorvaaTaiLisaa217 
   * @throws SailoException when error
   */
  @Test
  public void testKorvaaTaiLisaa217() throws SailoException {    // Urheiluseurarekisteri: 217
    alustaUrheiluseurarekisteri(); 
    assertEquals("From: Urheiluseurarekisteri line: 220", 2, urheiluseurarekisteri.etsi("*",0).size()); 
    urheiluseurarekisteri.korvaaTaiLisaa(valine1); 
    assertEquals("From: Urheiluseurarekisteri line: 222", 2, urheiluseurarekisteri.etsi("*",0).size()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLisaa234 
   * @throws SailoException when error
   */
  @Test
  public void testLisaa234() throws SailoException {    // Urheiluseurarekisteri: 234
    Urheiluseurarekisteri rekisteri = new Urheiluseurarekisteri(); 
    Laina laina1 = new Laina(1,1), laina2 = new Laina(2,2); 
    assertEquals("From: Urheiluseurarekisteri line: 238", 0, rekisteri.getLainoja()); 
    rekisteri.lisaa(laina1); assertEquals("From: Urheiluseurarekisteri line: 239", 1, rekisteri.getLainoja()); 
    rekisteri.lisaa(laina2); assertEquals("From: Urheiluseurarekisteri line: 240", 2, rekisteri.getLainoja()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testGetValine275 */
  @Test
  public void testGetValine275() {    // Urheiluseurarekisteri: 275
    alustaUrheiluseurarekisteri(); 
    assertEquals("From: Urheiluseurarekisteri line: 277", null, urheiluseurarekisteri.getValine(1)); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLueTiedostosta300 
   * @throws SailoException when error
   */
  @Test
  public void testLueTiedostosta300() throws SailoException {    // Urheiluseurarekisteri: 300
    Urheiluseurarekisteri rekisteri = new Urheiluseurarekisteri(); 
    Jasen jasen1 = new Jasen(), jasen2 = new Jasen(); 
    jasen1.jasenenTaytto(); 
    jasen2.jasenenTaytto(); 
    Valine valine1 = new Valine(), valine2 = new Valine(); 
    valine1.taytaValine(); 
    valine2.taytaValine(); 
    rekisteri.lueTiedostosta(); 
    rekisteri.tallenna(); 
    Urheiluseurarekisteri rekisteri2 = new Urheiluseurarekisteri(); 
    Jasen jasen12 = new Jasen(), jasen22 = new Jasen(); 
    jasen12.jasenenTaytto(); 
    jasen22.jasenenTaytto(); 
    rekisteri2.lueTiedostosta(); 
    rekisteri2.tallenna(); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testPoista365 
   * @throws Exception when error
   */
  @Test
  public void testPoista365() throws Exception {    // Urheiluseurarekisteri: 365
    alustaUrheiluseurarekisteri(); 
    assertEquals("From: Urheiluseurarekisteri line: 368", 2, urheiluseurarekisteri.etsi("*",0).size()); 
    urheiluseurarekisteri.poista(jasen1); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testPoista382 
   * @throws Exception when error
   */
  @Test
  public void testPoista382() throws Exception {    // Urheiluseurarekisteri: 382
    alustaUrheiluseurarekisteri(); 
    assertEquals("From: Urheiluseurarekisteri line: 385", 2, urheiluseurarekisteri.etsi("*",0).size()); 
    urheiluseurarekisteri.poista(valine1); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testPoista399 
   * @throws Exception when error
   */
  @Test
  public void testPoista399() throws Exception {    // Urheiluseurarekisteri: 399
    alustaUrheiluseurarekisteri(); 
    assertEquals("From: Urheiluseurarekisteri line: 402", 2, urheiluseurarekisteri.etsi("*",0).size()); 
    urheiluseurarekisteri.poista(laina1); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testPoistaLaina416 
   * @throws Exception when error
   */
  @Test
  public void testPoistaLaina416() throws Exception {    // Urheiluseurarekisteri: 416
    alustaUrheiluseurarekisteri(); 
    assertEquals("From: Urheiluseurarekisteri line: 419", 2, urheiluseurarekisteri.etsi("*",0).size()); 
    urheiluseurarekisteri.poistaLaina(1); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testPalautaEkaLainat433 
   * @throws Exception when error
   */
  @Test
  public void testPalautaEkaLainat433() throws Exception {    // Urheiluseurarekisteri: 433
    alustaUrheiluseurarekisteri(); 
    assertEquals("From: Urheiluseurarekisteri line: 436", 2, urheiluseurarekisteri.etsi("*",0).size()); 
    urheiluseurarekisteri.palautaEkaLainat(1); 
  } // Generated by ComTest END
}