package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite kerho-ohjelmalle
 * @author jailklee
 * @version 08 Apr 2021
 */
@RunWith(Suite.class)
@SuiteClasses({
    seurarekisteri.test.JasenTest.class,
    seurarekisteri.test.JasenetTest.class,
    seurarekisteri.test.ValineTest.class,
    seurarekisteri.test.ValineetTest.class,
    seurarekisteri.test.LainaTest.class,
    seurarekisteri.test.LainatTest.class,
    seurarekisteri.test.UrheiluseurarekisteriTest.class
    })
public class AllTests {
 //
}