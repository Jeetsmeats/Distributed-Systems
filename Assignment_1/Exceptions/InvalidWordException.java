package Exceptions;

/**
 * Invalid word exception for incorrect word inputs.
 * Author: Gunjeet Singh - 1170248
 */
public class InvalidWordException extends Exception {

    public InvalidWordException(String eMessage) {

        super(eMessage);
    }
}