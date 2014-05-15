/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.trank;

/*
 * Captures the camera stream with OpenCV
 * Search for the faces and eyes
 */
import by.zuyeu.deyestracker.core.detection.detector.EyesDetector;
import by.zuyeu.deyestracker.core.detection.detector.FaceDetector;
import by.zuyeu.deyestracker.core.detection.task.DetectEyesTask;
import by.zuyeu.deyestracker.core.detection.task.DetectFaceTask;
import by.zuyeu.deyestracker.core.exception.DEyesTrackerException;
import by.zuyeu.deyestracker.core.util.MatUtils;
import by.zuyeu.deyestracker.core.util.TaskUtils;
import by.zuyeu.deyestracker.core.video.CameraFrameCapture;
import by.zuyeu.deyestracker.core.video.IFrameCapture;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.Arrays;
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
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
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
        final EyesDetector leftEyeDetector = new EyesDetector(EyesDetector.EyesDetectType.LEFT);
        final EyesDetector rightEyeDetector = new EyesDetector(EyesDetector.EyesDetectType.RIGHT);
        My_Panel my_panel = new My_Panel();
        frame.setContentPane(my_panel);
        frame.setVisible(true);

        //-- 2. Read the video stream
        IFrameCapture capture = new CameraFrameCapture();
        new Thread(capture).start();
        Rect face = null;
        Rect[] eyes = null;
        final Scalar faceRegionColor = new Scalar(0, 255, 0);
        final Scalar eyesRegionColor = new Scalar(120, 120, 120);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        FutureTask<Rect> detectFaceTask = null;
        FutureTask<Rect[]> detectEyesTask = null;

        while (true) {
            Mat webcam_image = capture.getNextFrame();
            if (webcam_image != null && !webcam_image.empty()) {

                if (detectFaceTask == null) {
                    detectFaceTask = TaskUtils.wrapFutureAnd(new DetectFaceTask(faceDetector, webcam_image), executorService);
                }
                if (detectFaceTask.isDone()) {
                    face = detectFaceTask.get();
                    if (detectEyesTask == null && face != null) {
                        final Mat faceImage = MatUtils.selectSubmatByRect(face, webcam_image);
                        Mat faceImageForDetection = preProcessFaceRegion(faceImage);
                        detectEyesTask = TaskUtils.wrapFutureAnd(new DetectEyesTask(new EyesDetector[]{leftEyeDetector, rightEyeDetector}, faceImageForDetection), executorService
                        );
                    }
                    if (detectEyesTask != null && detectEyesTask.isDone()) {
                        eyes = detectEyesTask.get();
                        fixRectFromSubimg(face, eyes);
                    }
                    if (face == null || detectEyesTask.isDone()) {
                        detectFaceTask = null;
                        detectEyesTask = null;
                    }
                }
                frame.setSize(webcam_image.width() + 40, webcam_image.height() + 60);

                if (face != null) {
                    addRectangleToImage(face, webcam_image, faceRegionColor);
                }
                if (eyes != null && eyes.length > 0) {

                    addRectangleToImage(eyes, webcam_image, eyesRegionColor);
                    drawContours(webcam_image, eyes);
                }

                //-- 4. Display the image
                my_panel.MatToBufferedImage(webcam_image); // We could look at the error...
                my_panel.repaint();
            }
        }
    }

    private static void fixRectFromSubimg(Rect mainFace, Rect[] eyes) {
        final Point shiftPoint = mainFace != null ? mainFace.tl() : new Point(0, 0);
        Arrays.stream(eyes).forEach(p -> {
            p.x += shiftPoint.x;
            p.y += shiftPoint.y;
        });
    }

    private static void drawContours(Mat webcam_image, Rect[] eyes) {
        for (Rect rect : eyes) {
            Mat img = MatUtils.selectSubmatByRect(rect, webcam_image);
            img = drawPupils(img);

            insertSubmatByRect(img, rect, webcam_image);
        }
    }

    private static Mat preProcessFaceRegion(final Mat faceImage) {
        return faceImage.submat(0, faceImage.rows() / 2, 0, faceImage.cols());
        //return faceImage;
    }

    private static void addRectangleToImage(Rect[] regions, Mat webcam_image, final Scalar color) {
        for (Rect face : regions) {
            addRectangleToImage(face, webcam_image, color);
        }
    }

    private static void addRectangleToImage(Rect face, Mat webcam_image, final Scalar color) {
        Core.rectangle(webcam_image, face.tl(), face.br(), color);
    }

    private static void insertSubmatByRect(Mat subImage, Rect rect, Mat origImage) {
        double colScale = 1.0 * origImage.cols() / origImage.width();
        int colStart = (int) (1.0 * rect.x * colScale);
        double rowScale = 1.0 * origImage.rows() / origImage.height();
        int rowStart = (int) (1.0 * rect.y * rowScale);
        for (int x1 = 0, x2 = colStart; x1 < subImage.cols(); x1++, x2++) {
            for (int y1 = 0, y2 = rowStart; y1 < subImage.rows(); y1++, y2++) {
                final double[] subImgData = subImage.get(y1, x1);
                origImage.put(y2, x2, subImgData);
            }
        }
    }

    private static Mat drawPupils(Mat img) {
        Mat imageHSV = new Mat(img.size(), Core.DEPTH_MASK_8U);
        Imgproc.cvtColor(img, imageHSV, Imgproc.COLOR_BGR2GRAY);
        final Size StructureElemSize = new Size(15, 15);
        Imgproc.erode(imageHSV, imageHSV, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, StructureElemSize));
        Imgproc.dilate(imageHSV, imageHSV, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, StructureElemSize));
        Imgproc.GaussianBlur(imageHSV, imageHSV, StructureElemSize, 2);

        Core.MinMaxLocResult mmG = Core.minMaxLoc(imageHSV);
        Core.circle(img, mmG.minLoc, 3, RED);
        logger.trace("pupil center = {}", mmG.minLoc);
        return img;
    }
    private static final Scalar RED = new Scalar(0, 0, 255);
}
