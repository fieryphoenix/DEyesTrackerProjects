/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.util;

import by.zuyeu.deyestracker.core.exception.DEyesTrackerException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Fieryphoenix
 */
public class OpenCVLibraryLoaderTest {

    public OpenCVLibraryLoaderTest() {
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
     * Test of loadCoreIfNeed method, of class OpenCVLibraryLoader.
     */
    @Test
    public void testLoadCoreIfNeed() throws DEyesTrackerException {
        System.out.println("loadCoreIfNeed");
        OpenCVLibraryLoader.loadCoreIfNeed();
    }

}
