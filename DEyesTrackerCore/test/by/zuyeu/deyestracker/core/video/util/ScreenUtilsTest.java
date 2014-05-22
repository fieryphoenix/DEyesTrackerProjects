/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.video.util;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opencv.core.Size;

/**
 *
 * @author Fieryphoenix
 */
public class ScreenUtilsTest {

    public ScreenUtilsTest() {
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
     * Test of getScreenSize method, of class ScreenUtils.
     */
    @Test
    public void testGetScreenSize() {
        System.out.println("getScreenSize");
        Size expResult = new Size(1366, 768);
        Size result = ScreenUtils.getScreenSize();
        assertEquals(expResult, result);
    }

}
