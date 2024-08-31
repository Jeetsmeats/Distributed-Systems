package Exceptions;
/**
 * Invalid word exception for incorrect word inputs.
 */
public class InvalidWordException extends Exception {

    public InvalidWordException(String eMessage) {

        super(eMessage);
    }
}