/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.scenario;

import by.zuyeu.deyestracker.core.detection.model.StudyResult;
import by.zuyeu.deyestracker.mock.JavaFXThreadingRule;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
public class TeachingScenarioTest {

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    public TeachingScenarioTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of call method, of class TeachingScenario.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testCall() throws Exception {
        BooleanProperty bp = new SimpleBooleanProperty();
        TeachingScenario instance = new TeachingScenario(bp, bp, bp, bp);
        StudyResult result = instance.call();
        Assert.assertNotNull(result);
    }

}
