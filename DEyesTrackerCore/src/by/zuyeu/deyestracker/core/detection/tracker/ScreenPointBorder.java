/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.detection.tracker;

/**
 *
 * @author Fieryphoenix
 */
public class ScreenPointBorder {

    private double leftX;
    private double rightX;
    private double topY;
    private double bottomY;

    public double getWidth() {
        return rightX - leftX;
    }

    public double getHeight() {
        return bottomY - topY;
    }

    public double getLeftX() {
        return leftX;
    }

    public void setLeftX(double leftX) {
        this.leftX = leftX;
    }

    public double getRightX() {
        return rightX;
    }

    public void setRightX(double rightX) {
        this.rightX = rightX;
    }

    public double getTopY() {
        return topY;
    }

    public void setTopY(double topY) {
        this.topY = topY;
    }

    public double getBottomY() {
        return bottomY;
    }

    public void setBottomY(double bottomY) {
        this.bottomY = bottomY;
    }

    @Override
    public String toString() {
        return "ScreenPointBorder{" + "leftX=" + leftX + ", rightX=" + rightX + ", topY=" + topY + ", bottomY=" + bottomY + '}';
    }
}
