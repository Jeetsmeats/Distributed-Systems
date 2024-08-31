package Exceptions;

/**
 * Invalid word description exception
 */
public class InvalidDescription extends Exception {

    public InvalidDescription(String eMessage) {

        super(eMessage);
    }
}