/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.trank;

/*
 * Captures the camera stream with OpenCV
 * Search for the faces
 * Display a circle around the faces using Java
 */
import by.zuyeu.deyestracker.core.detection.EyesDetector;
import by.zuyeu.deyestracker.core.detection.FaceDetector;
import by.zuyeu.deyestracker.core.exception.DEyesTrackerException;
import by.zuyeu.deyestracker.core.video.CameraFrameCapture;
import by.zuyeu.deyestracker.core.video.IFrameCapture;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class My_Panel extends JPanel {

    private static final long serialVersionUID = 1L;

    private BufferedImage image;

    // Create a constructor method
    public My_Panel() {
        super();
    }

    /**
     * Converts/writes a Mat into a BufferedImage.
     *
     * @param matrix Mat of type CV_8UC3 or CV_8UC1
     * @return BufferedImage of type TYPE_3BYTE_BGR or TYPE_BYTE_GRAY
     */
    public boolean MatToBufferedImage(Mat matBGR) {
        long startTime = System.nanoTime();
        int width = matBGR.width(), height = matBGR.height(), channels = matBGR.channels();
        byte[] sourcePixels = new byte[width * height * channels];
        matBGR.get(0, 0, sourcePixels);
        // create new image and get reference to backing data
        image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(sourcePixels, 0, targetPixels, 0, sourcePixels.length);
        long endTime = System.nanoTime();
        //System.out.println(String.format("Elapsed time: %.2f ms", (float) (endTime - startTime) / 1000000));
        return true;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.image == null) {
            return;
        }
        g.drawImage(this.image, 10, 10, this.image.getWidth(), this.image.getHeight(), null);
        //g.drawString("This is my custom Panel!",10,20);
    }
}

public class DetectDemo {

    private static final Logger logger = LoggerFactory.getLogger(DetectDemo.class);

    public static void main(String arg[]) throws DEyesTrackerException, InterruptedException, ExecutionException {
        // Load the native library.
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String window_name = "Capture - Face detection";
        JFrame frame = new JFrame(window_name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        final FaceDetector faceDetector = new FaceDetector();
        final EyesDetector eyesDetector = new EyesDetector();
        My_Panel my_panel = new My_Panel();
        frame.setContentPane(my_panel);
        frame.setVisible(true);
        //-- 2. Read the video stream
        IFrameCapture capture = new CameraFrameCapture();
        new Thread(capture).start();
        Rect[] faces = null;
        Rect[] eyes = null;
        final Scalar faceRegionColor = new Scalar(0, 255, 0);
        final Scalar eyesRegionColor = new Scalar(0, 120, 0);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        FutureTask<Rect[]> detectFaceTask = null;
        FutureTask<Rect[]> detectEyesTask = null;
        Rect mainFace = null;

        while (true) {
            Mat webcam_image = capture.getNextFrame();
            if (webcam_image != null && !webcam_image.empty()) {
                if (detectFaceTask == null) {
                    detectFaceTask = createAndRunTask(new DetectFaceTask(faceDetector, webcam_image), executorService);
                }
                if (detectFaceTask.isDone()) {
                    faces = detectFaceTask.get();
                    if (detectEyesTask == null && faces.length > 0) {
                        mainFace = findMainFace(faces);
                        final Mat faceImage = selectMainFace(mainFace, webcam_image);
                        Mat faceImageForDetection = preProcessFaceRegion(faceImage);
                        detectEyesTask = createAndRunTask(new DetectEyesTask(eyesDetector, faceImageForDetection), executorService);
                    }
                    if (detectEyesTask != null && detectEyesTask.isDone()) {
                        eyes = detectEyesTask.get();
                    }
                    if (faces.length == 0 || detectEyesTask.isDone()) {
                        detectFaceTask = null;
                        detectEyesTask = null;
                    }
                }
                frame.setSize(webcam_image.width() + 40, webcam_image.height() + 60);

                if (mainFace != null) {
                    addRectangleToImage(mainFace, webcam_image, faceRegionColor, null);
                }
                if (eyes != null) {
                    final Point shiftPoint = mainFace != null ? new Point(mainFace.x, mainFace.y) : null;
                    addRectangleToImage(eyes, webcam_image, eyesRegionColor, shiftPoint);
                }

                //-- 4. Display the image
                my_panel.MatToBufferedImage(webcam_image); // We could look at the error...
                my_panel.repaint();
            }
        }
    }

    private static Mat preProcessFaceRegion(final Mat faceImage) {
        //return faceImage.submat(0, faceImage.rows() * 7 / 10, 0, faceImage.cols());
        return faceImage;
    }

    private static void addRectangleToImage(Rect[] regions, Mat webcam_image, final Scalar color, Point shiftPoint) {
        for (Rect face : regions) {
            addRectangleToImage(face, webcam_image, color, shiftPoint);
        }
    }

    private static void addRectangleToImage(Rect face, Mat webcam_image, final Scalar color, Point shiftPoint) {
        double leftX = face.x + (shiftPoint == null ? 0 : shiftPoint.x);
        double highY = face.y + (shiftPoint == null ? 0 : shiftPoint.y);
        Core.rectangle(webcam_image, new Point(leftX, highY), new Point(leftX + face.width, highY + face.height), color);
    }

    private static FutureTask<Rect[]> createAndRunTask(Callable<Rect[]> detectFutureTask, ExecutorService executorService) {
        final FutureTask<Rect[]> futureTask = new FutureTask<>(detectFutureTask);
        executorService.execute(futureTask);
        return futureTask;
    }

    private static Mat selectMainFace(Rect mainFace, Mat image) {
        //TODO: maybe need to change size of face
        double colScale = 1.0 * image.cols() / image.width();
        int colStart = (int) (1.0 * mainFace.x * colScale);
        int colEnd = (int) (1.0 * (mainFace.x + mainFace.width) * colScale);
        double rowScale = 1.0 * image.rows() / image.height();
        int rowStart = (int) (1.0 * mainFace.y * rowScale);
        int rowEnd = (int) (1.0 * (mainFace.y + mainFace.height) * rowScale);
        logger.debug("face region: colStart = {}, colEnd = {}, rowStart = {}, rowEnd = {}", colStart, colEnd, rowStart, rowEnd);
        return image.submat(rowStart, rowEnd, colStart, colEnd);
    }

    private static Rect findMainFace(Rect[] faces) {
        if (faces.length == 0) {
            return null;
        }
        int mainFaceIndex = 0; // default if length = 0
        double maxArea = 0;
        for (int i = 0; (i < faces.length) && (faces.length > 1); i++) {
            final Rect rect = faces[i];
            final double area = rect.area();
            if (area > maxArea) {
                maxArea = area;
                mainFaceIndex = i;
            }
        }
        return faces[mainFaceIndex];
    }
}

class DetectFaceTask implements Callable<Rect[]> {

    private static final Logger logger = LoggerFactory.getLogger(DetectFaceTask.class);

    private final FaceDetector detector;
    private final Mat frame;

    public DetectFaceTask(FaceDetector detector, Mat frame) {
        this.detector = detector;
        this.frame = frame;
    }

    @Override
    public Rect[] call() throws Exception {
        long startTime = System.nanoTime();
        Rect[] result = null;
        if (frame != null && !frame.empty()) {
            result = detector.detectFaces(frame);
        }
        if (result == null) {
            result = new Rect[0];
        }
        long endTime = System.nanoTime();
        logger.debug("faces detected = {}", result.length);
        logger.debug("detection time: {} ms", (float) (endTime - startTime) / 1000000);
        return result;
    }
}

class DetectEyesTask implements Callable<Rect[]> {

    private static final Logger logger = LoggerFactory.getLogger(DetectFaceTask.class);

    private final EyesDetector detector;
    private final Mat frame;

    public DetectEyesTask(EyesDetector detector, Mat frame) {
        this.detector = detector;
        this.frame = frame;
    }

    @Override
    public Rect[] call() throws Exception {
        long startTime = System.nanoTime();
        Rect[] result = null;
        if (frame != null && !frame.empty()) {
            result = detector.detectEyes(frame);
        }
        if (result == null) {
            result = new Rect[0];
        }
        long endTime = System.nanoTime();
        logger.debug("eyes detected = {}", result.length);
        logger.debug("detection time: {} ms", (float) (endTime - startTime) / 1000000);
        return result;
    }
}
