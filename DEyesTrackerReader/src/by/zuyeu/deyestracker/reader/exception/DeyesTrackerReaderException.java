/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.reader.exception;

/**
 *
 * @author Fieryphoenix
 */
public class DeyesTrackerReaderException extends Exception {

    public DeyesTrackerReaderException() {
    }

    public DeyesTrackerReaderException(String message) {
        super(message);
    }

    public DeyesTrackerReaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeyesTrackerReaderException(Throwable cause) {
        super(cause);
    }

}
