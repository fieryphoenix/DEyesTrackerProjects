/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.teacher.scenario;

import by.zuyeu.deyestracker.teacher.rule.JavaFXThreadingRule;
import javafx.beans.property.Property;
import javafx.beans.property.StringPropertyBase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

/**
 *
 * @author Fieryphoenix
 */
public class WelcomeScenarioTest {

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    public WelcomeScenarioTest() {
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of call method, of class WelcomeScenario.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testCall() throws Exception {
        Property<String> st = new StringPropertyBase() {

            @Override
            public Object getBean() {
                return null;
            }

            @Override
            public String getName() {
                return null;
            }
        };
        WelcomeScenario instance = new WelcomeScenario(st);
        Void expResult = null;
        Void result = instance.call();
        Assert.assertEquals(expResult, result);
    }

}
