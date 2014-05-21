/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.reader.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Fieryphoenix
 */
public class StudyResult2 implements Serializable {

    private DetectFaceSample2 tl;
    private DetectFaceSample2 tr;
    private DetectFaceSample2 bl;
    private DetectFaceSample2 br;

    /**
     * @return the tl
     */
    public DetectFaceSample2 getTl() {
        return tl;
    }

    /**
     * @param tl the tl to set
     */
    public void setTl(DetectFaceSample2 tl) {
        this.tl = tl;
    }

    /**
     * @return the tr
     */
    public DetectFaceSample2 getTr() {
        return tr;
    }

    /**
     * @param tr the tr to set
     */
    public void setTr(DetectFaceSample2 tr) {
        this.tr = tr;
    }

    /**
     * @return the bl
     */
    public DetectFaceSample2 getBl() {
        return bl;
    }

    /**
     * @param bl the bl to set
     */
    public void setBl(DetectFaceSample2 bl) {
        this.bl = bl;
    }

    /**
     * @return the br
     */
    public DetectFaceSample2 getBr() {
        return br;
    }

    /**
     * @param br the br to set
     */
    public void setBr(DetectFaceSample2 br) {
        this.br = br;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.tl);
        hash = 59 * hash + Objects.hashCode(this.tr);
        hash = 59 * hash + Objects.hashCode(this.bl);
        hash = 59 * hash + Objects.hashCode(this.br);
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
        final StudyResult2 other = (StudyResult2) obj;
        if (!Objects.equals(this.tl, other.tl)) {
            return false;
        }
        if (!Objects.equals(this.tr, other.tr)) {
            return false;
        }
        if (!Objects.equals(this.bl, other.bl)) {
            return false;
        }
        if (!Objects.equals(this.br, other.br)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "StudyResult2{" + "tl=" + tl + ", tr=" + tr + ", bl=" + bl + ", br=" + br + '}';
    }
}
