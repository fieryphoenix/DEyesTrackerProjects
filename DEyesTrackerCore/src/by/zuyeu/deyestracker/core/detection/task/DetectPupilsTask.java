/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.detection.task;

import java.util.concurrent.Callable;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Fieryphoenix
 */
public class DetectPupilsTask implements Callable<Point> {

    private static final Logger logger = LoggerFactory.getLogger(DetectPupilsTask.class);

    private final Mat frame;

    public DetectPupilsTask(final Mat frame) {
        this.frame = frame;
    }

    @Override
    public Point call() throws Exception {
        long startTime = System.nanoTime();

        final Mat imageHSV = new Mat(frame.size(), Core.DEPTH_MASK_8U);
        Imgproc.cvtColor(frame, imageHSV, Imgproc.COLOR_BGR2GRAY);
        final Size StructureElemSize = new Size(15, 15);
        Imgproc.erode(imageHSV, imageHSV, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, StructureElemSize));
        Imgproc.dilate(imageHSV, imageHSV, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, StructureElemSize));
        Imgproc.GaussianBlur(imageHSV, imageHSV, StructureElemSize, 2);

        Core.MinMaxLocResult mmG = Core.minMaxLoc(imageHSV);

        long endTime = System.nanoTime();
        logger.debug("pupil detected = {}", mmG.minLoc);
        logger.debug("detection time: {} ms", (float) (endTime - startTime) / 1000000);
        return mmG.minLoc;
    }
}
