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

    private final DEyesTrackerExceptionCode code;

    public DEyesTrackerException(String message) {
        super(message);
        code = DEyesTrackerExceptionCode.UNKNOWN;
    }

    public DEyesTrackerException(String message, Throwable cause) {
        super(message, cause);
        code = DEyesTrackerExceptionCode.UNKNOWN;
    }

    public DEyesTrackerException(Throwable cause) {
        super(cause);
        code = DEyesTrackerExceptionCode.UNKNOWN;
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

    public DEyesTrackerExceptionCode getCode() {
        return code;
    }
}
