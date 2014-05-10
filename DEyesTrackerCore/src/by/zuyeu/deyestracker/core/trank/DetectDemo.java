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

    public static void main(String arg[]) throws DEyesTrackerException, InterruptedException, ExecutionException {
        // Load the native library.
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String window_name = "Capture - Face detection";
        JFrame frame = new JFrame(window_name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        FaceDetector detector = new FaceDetector();
        My_Panel my_panel = new My_Panel();
        frame.setContentPane(my_panel);
        frame.setVisible(true);
        //-- 2. Read the video stream
        IFrameCapture capture = new CameraFrameCapture();
        new Thread(capture).start();
        Rect[] faces = new Rect[0];
        final Scalar color = new Scalar(0, 255, 0);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        FutureTask<Rect[]> detectFutureTask = new FutureTask<>(new DetectTask(detector, null));
        executorService.execute(detectFutureTask);

        while (true) {
            Mat webcam_image = capture.getNextFrame();
            if (webcam_image != null && !webcam_image.empty()) {
                if (detectFutureTask.isDone()) {
                    System.out.println("detect done!");
                    faces = detectFutureTask.get();
                    detectFutureTask = new FutureTask<>(new DetectTask(detector, webcam_image));
                    executorService.execute(detectFutureTask);
                }
                frame.setSize(webcam_image.width() + 40, webcam_image.height() + 60);
                for (Rect face : faces) {
                    Core.rectangle(webcam_image, new Point(face.x, face.y), new Point(face.x + face.width, face.y + face.height), color);
                }
                //-- 4. Display the image
                my_panel.MatToBufferedImage(webcam_image); // We could look at the error...
                my_panel.repaint();
            }
        }
    }
}

class DetectTask implements Callable<Rect[]> {

    private final FaceDetector detector;
    private final Mat frame;

    public DetectTask(FaceDetector detector, Mat frame) {
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
        System.out.println(String.format("faces detected = %d", result.length));
        System.out.println(String.format("detection time: %.2f ms", (float) (endTime - startTime) / 1000000));
        return result;
    }
}
