/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.exception;

/**
 *
 * @author Fieryphoenix
 */
public class DEyesTrackerException extends Exception {

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

}
