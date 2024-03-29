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
 * @version 2021.04.22 11:01:32 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class JasenetTest {



  // Generated by ComTest BEGIN
  /** 
   * testPoista51 
   * @throws SailoException when error
   */
  @Test
  public void testPoista51() throws SailoException {    // Jasenet: 51
    Jasenet jasenet = new Jasenet(); 
    Jasen jasen1 = new Jasen(), jasen2 = new Jasen(), jasen3 = new Jasen(); 
    jasen1.rekisteroi(); jasen2.rekisteroi(); jasen3.rekisteroi(); 
    int id1 = jasen1.getJasenID(); 
    jasenet.lisaa(jasen1); jasenet.lisaa(jasen2); jasenet.lisaa(jasen3); 
    assertEquals("From: Jasenet line: 58", 1, jasenet.poista(id1+1)); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testEtsiId77 
   * @throws SailoException when error
   */
  @Test
  public void testEtsiId77() throws SailoException {    // Jasenet: 77
    Jasenet jasenet = new Jasenet(); 
    Jasen jasen1 = new Jasen(), jasen2 = new Jasen(), jasen3 = new Jasen(); 
    jasen1.rekisteroi(); jasen2.rekisteroi(); jasen3.rekisteroi(); 
    int id1 = jasen1.getJasenID(); 
    jasenet.lisaa(jasen1); jasenet.lisaa(jasen2); jasenet.lisaa(jasen3); 
    assertEquals("From: Jasenet line: 84", 1, jasenet.etsiId(id1+1)); 
    assertEquals("From: Jasenet line: 85", 2, jasenet.etsiId(id1+2)); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testKorvaaTaiLisaa99 
   * @throws SailoException when error
   * @throws CloneNotSupportedException when error
   */
  @Test
  public void testKorvaaTaiLisaa99() throws SailoException,CloneNotSupportedException {    // Jasenet: 99
    Jasenet jasenet = new Jasenet(); 
    Jasen jasen1 = new Jasen(), jasen2 = new Jasen(); 
    jasen1.rekisteroi(); jasen2.rekisteroi(); 
    assertEquals("From: Jasenet line: 105", 0, jasenet.getLkm()); 
    jasenet.korvaaTaiLisaa(jasen1); assertEquals("From: Jasenet line: 106", 1, jasenet.getLkm()); 
    jasenet.korvaaTaiLisaa(jasen2); assertEquals("From: Jasenet line: 107", 2, jasenet.getLkm()); 
    Jasen jasen3 = jasen1.clone(); 
    jasen3.aseta(3,"kkk"); 
    Iterator<Jasen> it = jasenet.iterator(); 
    assertEquals("From: Jasenet line: 111", true, it.next() == jasen1); 
    jasenet.korvaaTaiLisaa(jasen3); assertEquals("From: Jasenet line: 112", 2, jasenet.getLkm()); 
    it = jasenet.iterator(); 
    Jasen j0 = it.next(); 
    assertEquals("From: Jasenet line: 115", jasen3, j0); 
    assertEquals("From: Jasenet line: 116", true, j0 == jasen3); 
    assertEquals("From: Jasenet line: 117", false, j0 == jasen1); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLueTiedostosta137 
   * @throws SailoException when error
   */
  @Test
  public void testLueTiedostosta137() throws SailoException {    // Jasenet: 137
    Jasenet jasenet = new Jasenet(); 
    Jasen jasen1 = new Jasen(), jasen2 = new Jasen(); 
    jasen1.jasenenTaytto(); 
    jasen2.jasenenTaytto(); 
    jasenet.lueTiedostosta(); 
    jasenet.tallenna(); 
    jasenet = new Jasenet(); 
    jasenet.lueTiedostosta(); 
    jasenet.tallenna(); 
    String hakemisto = "testi"; 
    String tiedNimi = hakemisto+"/nimet"; 
    File ftied = new File(tiedNimi+".dat"); 
    File dir = new File(hakemisto); 
    dir.mkdir(); 
    ftied.delete(); 
    assertEquals("From: Jasenet line: 156", false, ftied.delete()); 
    File fbak = new File(tiedNimi+".bak"); 
    fbak.delete(); 
    assertEquals("From: Jasenet line: 159", false, fbak.delete()); 
    assertEquals("From: Jasenet line: 160", true, dir.delete()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testTallenna218 
   * @throws SailoException when error
   */
  @Test
  public void testTallenna218() throws SailoException {    // Jasenet: 218
    Jasenet jasenet = new Jasenet(); 
    Jasen jouko1 = new Jasen(), jouko2 = new Jasen(); 
    jouko1.jasenenTaytto(); 
    jouko2.jasenenTaytto(); 
    jasenet.lueTiedostosta(); 
    jasenet.lisaa(jouko1); 
    jasenet.lisaa(jouko2); 
    jasenet.tallenna(); 
    assertEquals("From: Jasenet line: 230", jouko1.getJasenID(), jasenet.anna(0).getJasenID()); 
    jasenet = new Jasenet(); 
    jasenet.lueTiedostosta(); 
    jasenet.lisaa(jouko2); 
    jasenet.tallenna(); 
    assertEquals("From: Jasenet line: 235", jouko2.getJasenID(), jasenet.anna(1).getJasenID()); 
    String hakemisto = "testi"; 
    String tiedNimi = hakemisto+"/nimet"; 
    File ftied = new File(tiedNimi+".dat"); 
    File dir = new File(hakemisto); 
    dir.mkdir(); 
    ftied.delete(); 
    assertEquals("From: Jasenet line: 242", false, ftied.delete()); 
    File fbak = new File(tiedNimi+".bak"); 
    fbak.delete(); 
    assertEquals("From: Jasenet line: 245", false, fbak.delete()); 
    assertEquals("From: Jasenet line: 246", true, dir.delete()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testJasenetIterator284 
   * @throws SailoException when error
   */
  @Test
  public void testJasenetIterator284() throws SailoException {    // Jasenet: 284
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
    assertEquals("From: Jasenet line: 302", tulos, ids.toString()); 
    ids = new StringBuffer(30); 
    for (Iterator<Jasen>  i=jasenet.iterator(); i.hasNext(); ) {
    Jasen jasen = i.next(); 
    ids.append(" "+jasen.getJasenID()); 
    }
    assertEquals("From: Jasenet line: 310", tulos, ids.toString()); 
    Iterator<Jasen>  i=jasenet.iterator(); 
    assertEquals("From: Jasenet line: 313", true, i.next() == jouko1); 
    assertEquals("From: Jasenet line: 314", true, i.next() == jouko2); 
    assertEquals("From: Jasenet line: 315", true, i.next() == jouko1); 
    try {
    i.next(); 
    fail("Jasenet: 317 Did not throw NoSuchElementException");
    } catch(NoSuchElementException _e_){ _e_.getMessage(); }
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testEtsi371 
   * @throws SailoException when error
   */
  @Test
  public void testEtsi371() throws SailoException {    // Jasenet: 371
    Jasenet jasenet = new Jasenet(); 
    Jasen jasen1 = new Jasen(); jasen1.parse("1|Jouko Joukkio|212198-1234|Kärkikuja 1|40740|Jyväskunta|0449221122|jouko@gmail.com|kalju"); 
    Jasen jasen2 = new Jasen(); jasen2.parse("2|Pirkka Pirkkonen|232323-12323|Kärkikuja 3|40740|Jyväsmetro|033232323|pirkka@pirkkonen.com|ei kalju"); 
    Jasen jasen3 = new Jasen(); jasen3.parse("3|Jouko Joukkio|212198-1234|Kärkikuja 1|40740|Jyväskunta|0449221122|jouko@gmail.com|kalju"); 
    Jasen jasen4 = new Jasen(); jasen4.parse("4|Pirkka Pirkkonen|232323-12323|Kärkikuja 3|40740|Jyväsmetro|033232323|pirkka@pirkkonen.com|ei kalju"); 
    Jasen jasen5 = new Jasen(); jasen5.parse("5|Pirkka Pirkkonen|232323-12323|Kärkikuja 3|40740|Jyväsmetro|033232323|pirkka@pirkkonen.com|ei kalju"); 
    jasenet.lisaa(jasen1); jasenet.lisaa(jasen2); jasenet.lisaa(jasen3); jasenet.lisaa(jasen4); jasenet.lisaa(jasen5); 
    List<Jasen> loytyneet; 
    loytyneet = (List<Jasen>)jasenet.etsi(null,-1); 
    assertEquals("From: Jasenet line: 382", 5, loytyneet.size()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLisaa402 
   * @throws SailoException when error
   */
  @Test
  public void testLisaa402() throws SailoException {    // Jasenet: 402
    Jasenet jasenet = new Jasenet(); 
    Jasen eka = new Jasen(); 
    Jasen toka = new Jasen(); 
    Jasen kolmas = new Jasen(); 
    Jasen neljas = new Jasen(); 
    Jasen viides = new Jasen(); 
    Jasen kuudes = new Jasen(); 
    Jasen seiska = new Jasen(); 
    Jasen kasi = new Jasen(); 
    Jasen ysi = new Jasen(); 
    Jasen kymppi = new Jasen(); 
    Jasen ykstoista = new Jasen(); 
    assertEquals("From: Jasenet line: 416", 0, jasenet.getLkm()); 
    jasenet.lisaa(eka); assertEquals("From: Jasenet line: 417", 1, jasenet.getLkm()); 
    jasenet.lisaa(toka); assertEquals("From: Jasenet line: 418", 2, jasenet.getLkm()); 
    jasenet.lisaa(kolmas); assertEquals("From: Jasenet line: 419", 3, jasenet.getLkm()); 
    jasenet.lisaa(neljas); assertEquals("From: Jasenet line: 420", 4, jasenet.getLkm()); 
    jasenet.lisaa(viides); assertEquals("From: Jasenet line: 421", 5, jasenet.getLkm()); 
    jasenet.lisaa(kuudes); assertEquals("From: Jasenet line: 422", 6, jasenet.getLkm()); 
    jasenet.lisaa(seiska); assertEquals("From: Jasenet line: 423", 7, jasenet.getLkm()); 
    jasenet.lisaa(kasi); assertEquals("From: Jasenet line: 424", 8, jasenet.getLkm()); 
    jasenet.lisaa(ysi); assertEquals("From: Jasenet line: 425", 9, jasenet.getLkm()); 
    jasenet.lisaa(kymppi); assertEquals("From: Jasenet line: 426", 10, jasenet.getLkm()); 
    jasenet.lisaa(ykstoista); assertEquals("From: Jasenet line: 427", 11, jasenet.getLkm()); 
  } // Generated by ComTest END
}