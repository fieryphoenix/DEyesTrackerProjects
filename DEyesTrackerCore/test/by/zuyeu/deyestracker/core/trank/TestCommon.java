/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.trank;

import java.io.InputStream;
import org.junit.Test;

/**
 *
 * @author Fieryphoenix
 */
public class TestCommon {

    public TestCommon() {
    }

    @Test
    public void testLoad() {
        InputStream in = TestCommon.class.getResourceAsStream("/lib/x64/opencv_java249.dll");
        System.out.println("in = " + in);
    }
}
