/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.detection;

/*
 * Captures the camera stream with OpenCV
 * Search for the faces and eyes
 */
import by.zuyeu.deyestracker.core.detection.model.DetectFaceSample;
import by.zuyeu.deyestracker.core.detection.task.IDetectTask;
import by.zuyeu.deyestracker.core.exception.DEyesTrackerException;
import by.zuyeu.deyestracker.core.util.TaskUtils;
import by.zuyeu.deyestracker.core.video.capture.IFrameCapture;
import by.zuyeu.deyestracker.core.video.sampler.FaceInfoSampler;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
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

class DemoPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private BufferedImage image;

    /**
     * Converts/writes a Mat into a BufferedImage.
     *
     * @param matrix Mat of type CV_8UC3 or CV_8UC1
     * @return BufferedImage of type TYPE_3BYTE_BGR or TYPE_BYTE_GRAY
     */
    public boolean convertMatToBufferedImage(Mat matBGR) {

        int width = matBGR.width(), height = matBGR.height(), channels = matBGR.channels();
        byte[] sourcePixels = new byte[width * height * channels];
        matBGR.get(0, 0, sourcePixels);
        // create new image and get reference to backing data
        image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(sourcePixels, 0, targetPixels, 0, sourcePixels.length);

        return true;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.image == null) {
            return;
        }
        g.drawImage(this.image, 10, 10, this.image.getWidth(), this.image.getHeight(), null);
    }
}

public class DetectDemo {

    private static final Logger LOG = LoggerFactory.getLogger(DetectDemo.class);

    public DetectDemo() {
    }

    public static void main(String arg[]) throws DEyesTrackerException, InterruptedException, ExecutionException {
        LOG.info("main - start;");
        final String windowName = "Capture - Face detection";
        final JFrame frame = new JFrame(windowName);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        final DemoPanel demoPanel = new DemoPanel();
        frame.setContentPane(demoPanel);
        frame.setVisible(true);

        //-- 2. Read the video stream
        final FaceInfoSampler sampler = new FaceInfoSampler();
        final IFrameCapture capture = sampler.getCapture();
        final Scalar faceRegionColor = new Scalar(0, 255, 0);
        final Scalar eyesRegionColor = new Scalar(120, 120, 120);

        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        FutureTask<DetectFaceSample> detectFaceTask = TaskUtils.wrapFutureAnd(new DetectTask(sampler), executorService);
        DetectFaceSample sample = new DetectFaceSample();
        while (true) {
            final Mat webcamImage = capture.getNextFrame();
            if (webcamImage != null && !webcamImage.empty()) {
                frame.setSize(webcamImage.width() + 40, webcamImage.height() + 60);

                if (detectFaceTask.isDone()) {
                    sample = detectFaceTask.get();

                    detectFaceTask = TaskUtils.wrapFutureAnd(new DetectTask(sampler), executorService);
                }

                if (sample.getFace() != null) {
                    addRectangleToImage(sample.getFace(), webcamImage, faceRegionColor);
                }
                if (sample.getLeftEye() != null) {
                    addRectangleToImage(sample.getLeftEye(), webcamImage, eyesRegionColor);
                }
                if (sample.getRightEye() != null) {
                    addRectangleToImage(sample.getRightEye(), webcamImage, eyesRegionColor);
                }
                if (sample.getLeftPupil() != null) {
                    drawCircle(webcamImage, sample.getLeftPupil());
                }
                if (sample.getRightPupil() != null) {
                    drawCircle(webcamImage, sample.getRightPupil());
                }

                //-- 4. Display the image
                demoPanel.convertMatToBufferedImage(webcamImage); // We could look at the error...
                demoPanel.repaint();
            }
        }
    }

    private static void addRectangleToImage(Rect face, Mat webcamImage, final Scalar color) {
        Core.rectangle(webcamImage, face.tl(), face.br(), color);
    }

    private static void drawCircle(Mat img, Point center) {
        if (img != null && center != null) {
            Core.circle(img, center, 3, RED);
        }
    }
    private static final Scalar RED = new Scalar(0, 0, 255);
}

class DetectTask implements IDetectTask<DetectFaceSample> {

    private final FaceInfoSampler sampler;

    public DetectTask(FaceInfoSampler sampler) {
        this.sampler = sampler;
    }

    @Override
    public DetectFaceSample call() throws Exception {
        DetectFaceSample sample = sampler.makeSample();
        return sample;
    }
}
