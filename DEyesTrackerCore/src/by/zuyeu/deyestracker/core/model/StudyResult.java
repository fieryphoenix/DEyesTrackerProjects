/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.model;

import java.util.Objects;

/**
 *
 * @author Fieryphoenix
 */
public class StudyResult {

    public final DetectFaceInfoModel topLeft;
    public final DetectFaceInfoModel bottomLeft;
    public final DetectFaceInfoModel topRight;
    public final DetectFaceInfoModel bottomRight;

    public StudyResult(final DetectFaceInfoModel topLeft, final DetectFaceInfoModel bottomLeft, final DetectFaceInfoModel topRight, final DetectFaceInfoModel bottomRight) {
        this.topLeft = topLeft;
        this.bottomLeft = bottomLeft;
        this.topRight = topRight;
        this.bottomRight = bottomRight;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.topLeft);
        hash = 83 * hash + Objects.hashCode(this.bottomLeft);
        hash = 83 * hash + Objects.hashCode(this.topRight);
        hash = 83 * hash + Objects.hashCode(this.bottomRight);
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
        return "StudyResult{" + "topLeft=" + topLeft + ", bottomLeft=" + bottomLeft + ", topRight=" + topRight + ", bottomRight=" + bottomRight + '}';
    }

}
