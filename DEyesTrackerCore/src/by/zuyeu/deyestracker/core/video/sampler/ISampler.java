/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.video.sampler;

import by.zuyeu.deyestracker.core.exception.DEyesTrackerException;
import by.zuyeu.deyestracker.core.detection.model.DetectFaceSample;
import by.zuyeu.deyestracker.core.video.capture.IFrameCapture;

/**
 *
 * @author Fieryphoenix
 */
public interface ISampler {

    void close();

    IFrameCapture getCapture();

    DetectFaceSample makeSample() throws DEyesTrackerException;

}
