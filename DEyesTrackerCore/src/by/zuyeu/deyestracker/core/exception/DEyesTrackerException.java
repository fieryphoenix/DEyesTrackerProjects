/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.exception;

/**
 *
 * @author Fieryphoenix
 */
public class DEyesTrackerException extends Exception {

    private DEyesTrackerExceptionCode code;

    public DEyesTrackerException() {
    }

    public DEyesTrackerException(String message) {
        super(message);
    }

    public DEyesTrackerException(String message, Throwable cause) {
        super(message, cause);
    }

    public DEyesTrackerException(Throwable cause) {
        super(cause);
    }

    public DEyesTrackerException(DEyesTrackerExceptionCode code) {
        this.code = code;
    }

    public DEyesTrackerException(DEyesTrackerExceptionCode code, String message) {
        super(message);
        this.code = code;
    }

    public DEyesTrackerException(DEyesTrackerExceptionCode code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public DEyesTrackerException(DEyesTrackerExceptionCode code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    @Override
    public String toString() {
        return "DEyesTrackerException{" + "code=" + code + '}';
    }

}
