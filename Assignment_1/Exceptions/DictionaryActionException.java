package Assignment_1.Exceptions;

/**
 * Action exception for unsuccessful action.
 */
public class DictionaryActionException extends Exception {

    public DictionaryActionException(String eMessage) {

        super(eMessage);
    }
}