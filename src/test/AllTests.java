package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite kerho-ohjelmalle
 * @author jailklee
 * @version 10.3.2021
 */
@RunWith(Suite.class)
@SuiteClasses({
    seurarekisteri.test.JasenTest.class
    //kerho.test.JasenetTest.class,
    //kerho.test.KirjaTest.class,
    //kerho.test.LainaTest.class
    })
public class AllTests {
 //
}