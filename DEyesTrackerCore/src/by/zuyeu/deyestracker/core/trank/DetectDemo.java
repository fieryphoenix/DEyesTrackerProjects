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
import by.zuyeu.deyestracker.core.exception.DEyesTrackerException;
import by.zuyeu.deyestracker.core.model.DetectFaceSample;
import by.zuyeu.deyestracker.core.sampler.FaceInfoSampler;
import by.zuyeu.deyestracker.core.video.IFrameCapture;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.concurrent.ExecutionException;
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

    private static final Logger LOG = LoggerFactory.getLogger(DetectDemo.class);

    public static void main(String arg[]) throws DEyesTrackerException, InterruptedException, ExecutionException {

        String window_name = "Capture - Face detection";
        JFrame frame = new JFrame(window_name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        My_Panel my_panel = new My_Panel();
        frame.setContentPane(my_panel);
        frame.setVisible(true);

        //-- 2. Read the video stream
        final FaceInfoSampler sampler = new FaceInfoSampler();
        final IFrameCapture capture = sampler.getCapture();
        final Scalar faceRegionColor = new Scalar(0, 255, 0);
        final Scalar eyesRegionColor = new Scalar(120, 120, 120);

        while (true) {
            final Mat webcam_image = capture.getNextFrame();
            if (webcam_image != null && !webcam_image.empty()) {
                frame.setSize(webcam_image.width() + 40, webcam_image.height() + 60);

                DetectFaceSample sample = sampler.makeSample();

                if (sample.getFace() != null) {
                    addRectangleToImage(sample.getFace(), webcam_image, faceRegionColor);
                }
                if (sample.getLeftEye() != null) {
                    addRectangleToImage(sample.getLeftEye(), webcam_image, eyesRegionColor);
                }
                if (sample.getRightEye() != null) {
                    addRectangleToImage(sample.getRightEye(), webcam_image, eyesRegionColor);
                }
                drawCircle(webcam_image, sample.getLeftPupil());
                drawCircle(webcam_image, sample.getRightPupil());

                //-- 4. Display the image
                my_panel.MatToBufferedImage(webcam_image); // We could look at the error...
                my_panel.repaint();
            }
        }
    }

    private static void addRectangleToImage(Rect face, Mat webcam_image, final Scalar color) {
        Core.rectangle(webcam_image, face.tl(), face.br(), color);
    }

    private static void drawCircle(Mat img, Point center) {
        if (img != null && center != null) {
            Core.circle(img, center, 3, RED);
        }
    }
    private static final Scalar RED = new Scalar(0, 0, 255);
}
