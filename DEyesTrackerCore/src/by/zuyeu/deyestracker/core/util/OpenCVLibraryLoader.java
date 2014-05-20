/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.util;

import by.zuyeu.deyestracker.core.exception.DEyesTrackerException;
import by.zuyeu.deyestracker.core.exception.DEyesTrackerExceptionCode;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Fieryphoenix
 */
public class OpenCVLibraryLoader {

    private static final Logger LOG = LoggerFactory.getLogger(OpenCVLibraryLoader.class);
    private static final String OPENCVMAC_JAVADYLIB = "mac";
    private static final String OPENCVX64_JAVADLL = "win_x64";
    private static final String OPENCVX86_JAVADLL = "win_x86";

    private final AtomicBoolean isLoaded = new AtomicBoolean(false);

    private static class LoaderHandler {

        private static final OpenCVLibraryLoader LOADER = new OpenCVLibraryLoader();
    }

    private OpenCVLibraryLoader() {
    }

    public static void loadCoreIfNeed() throws DEyesTrackerException {
        if (LoaderHandler.LOADER.isLoaded.compareAndSet(false, true)) {
            LoaderHandler.LOADER.loadLibrary();
        }
    }

    private void loadLibrary() throws DEyesTrackerException {
        LOG.info("loadLibrary() - start;");
        try {
            final Properties properties = loadProperties();
            final String rootPath = getRootPath();
            InputStream in = null;
            File fileOut = null;
            final String osName = System.getProperty("os.name");
            LOG.info("rootPath = {}, osName = {}", rootPath, osName);
            if (osName.startsWith("Windows")) {
                int bitness = Integer.parseInt(System.getProperty("sun.arch.data.model"));
                if (bitness == 32) {
                    LOG.info("bit = {}", "32 bit detected");
                    in = new FileInputStream(new File(rootPath + properties.getProperty(OPENCVX86_JAVADLL)));
                    fileOut = File.createTempFile("lib", ".dll");
                } else if (bitness == 64) {
                    LOG.info("bit = {}", "64 bit detected");
                    in = new FileInputStream(new File(rootPath + properties.getProperty(OPENCVX64_JAVADLL)));
                    fileOut = File.createTempFile("lib", ".dll");
                } else {
                    LOG.info("bit = {}", "Unknown bit detected - trying with 32 bit");
                    in = new FileInputStream(new File(rootPath + properties.getProperty(OPENCVX86_JAVADLL)));
                    fileOut = File.createTempFile("lib", ".dll");
                }
            } else if (osName.equals("Mac OS X")) {
                in = new FileInputStream(new File(rootPath + properties.getProperty(OPENCVMAC_JAVADYLIB)));
                fileOut = File.createTempFile("lib", ".dylib");
            }

            OutputStream out = FileUtils.openOutputStream(fileOut);
            IOUtils.copy(in, out);
            in.close();
            out.close();
            System.load(fileOut.toString());

        } catch (NumberFormatException | IOException e) {
            LOG.error("loadLibrary", e);
            throw new DEyesTrackerException(DEyesTrackerExceptionCode.CORE_INIT_FAILURE, "Failed to load opencv native library", e);
        }
        LOG.info("loadLibrary() - end;");
    }

    private static String getRootPath() {
        return new File(".").getAbsolutePath().replace(".", "");
    }

    private Properties loadProperties() throws IOException {
        final Properties properties = new Properties();
        final InputStream in = getClass().getResourceAsStream("opencv.properties");
        properties.load(in);
        return properties;
    }
}
