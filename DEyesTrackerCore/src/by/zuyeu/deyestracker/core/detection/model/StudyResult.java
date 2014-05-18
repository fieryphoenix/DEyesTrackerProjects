/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.detection.model;

import java.util.Objects;

/**
 *
 * @author Fieryphoenix
 */
public class StudyResult {

    private final DetectFaceSample topLeft;
    private final DetectFaceSample bottomLeft;
    private final DetectFaceSample topRight;
    private final DetectFaceSample bottomRight;

    public StudyResult(final DetectFaceSample topLeft, final DetectFaceSample bottomLeft, final DetectFaceSample topRight, final DetectFaceSample bottomRight) {
        this.topLeft = topLeft;
        this.bottomLeft = bottomLeft;
        this.topRight = topRight;
        this.bottomRight = bottomRight;
    }

    /**
     * @return the topLeft
     */
    public DetectFaceSample getTopLeft() {
        return topLeft;
    }

    /**
     * @return the bottomLeft
     */
    public DetectFaceSample getBottomLeft() {
        return bottomLeft;
    }

    /**
     * @return the topRight
     */
    public DetectFaceSample getTopRight() {
        return topRight;
    }

    /**
     * @return the bottomRight
     */
    public DetectFaceSample getBottomRight() {
        return bottomRight;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.getTopLeft());
        hash = 83 * hash + Objects.hashCode(this.getBottomLeft());
        hash = 83 * hash + Objects.hashCode(this.getTopRight());
        hash = 83 * hash + Objects.hashCode(this.getBottomRight());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StudyResult other = (StudyResult) obj;
        if (!Objects.equals(this.topLeft, other.topLeft)) {
            return false;
        }
        if (!Objects.equals(this.bottomLeft, other.bottomLeft)) {
            return false;
        }
        if (!Objects.equals(this.topRight, other.topRight)) {
            return false;
        }
        if (!Objects.equals(this.bottomRight, other.bottomRight)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "StudyResult{" + "topLeft=" + getTopLeft() + ", bottomLeft=" + getBottomLeft() + ", topRight=" + getTopRight() + ", bottomRight=" + getBottomRight() + '}';
    }

}
