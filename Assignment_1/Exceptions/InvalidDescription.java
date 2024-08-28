package Assignment_1.Exceptions;

/**
 * Invalid word description exception
 */
public class InvalidDescription extends Exception {

    /**
     * Call invalid description exception
     */
    public InvalidDescription(String eMessage) {

        super(eMessage);
    }
}