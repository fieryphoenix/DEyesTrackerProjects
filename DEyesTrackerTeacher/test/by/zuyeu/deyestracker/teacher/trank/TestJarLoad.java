/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.teacher.trank;

import by.zuyeu.deyestracker.core.exception.DEyesTrackerException;
import by.zuyeu.deyestracker.core.util.OpenCVLibraryLoader;

/**
 *
 * @author Fieryphoenix
 */
public class TestJarLoad {

    public static void main(String[] args) throws DEyesTrackerException {
        OpenCVLibraryLoader.loadCoreIfNeed();
    }
}
