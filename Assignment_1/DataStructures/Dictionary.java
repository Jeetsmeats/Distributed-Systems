package DataStructures;

// imports
import java.util.HashMap;
import Exceptions.*;
import Helper.*;

/***
 * Word Dictionary Data Structure
 */
public class Dictionary {

    /**
     * Word Dictionary Hashmap
     */
    private HashMap<String, WordDescription> WordDictionary;

    public Dictionary() {

        WordDictionary = new HashMap<String, WordDescription>();
    }


    /**
     * Get the list of word meanings.
     * @param word Selected word
     * @throws Assignment_1.Exceptions.WordNullException
     * @return Get the list of word description.
     */
    public WordDescription getMeaning(String word) throws WordNullException {

        if (this.WordDictionary.containsKey(word)) {

            return this.WordDictionary.get(word);
        } else {

            throw new WordNullException("Word is not in the dictionary.");
        }
    }

    /**
     * Method to remove word from the dictionary.
     * @param word - word to remove from dictionary.
     * @throws Assignment_1.Exceptions.DictionaryActionException
     * @return Status message for remove word.
     */
    public String removeWord(String word) throws DictionaryActionException {

        // check word validity
        try {

            checkWord(word);
        } catch (WordNullException e) {

            e.printStackTrace();
        } catch (InvalidWordException e) {

            e.printStackTrace();
        }

        if (this.WordDictionary.remove(word) != null) {             /* Remove word from dictionary */

            return "Successfully removed word!";
        } else {                                            /* Word not successfully removed from dictionary */

            throw new DictionaryActionException("Word could not be removed from the dictionary.");
        }
    }

    /**
     * Add word to the dictionary.
     * @param word word to add to the dictionary.
     * @param description description to add with word.
     */
    public void addWord(String word, String description) {

        // check word validity
        try {

            checkWord(word);
        } catch (WordNullException e) {

            e.printStackTrace();
        } catch (InvalidWordException e) {

            e.printStackTrace();
        }


        this.WordDictionary.put(word, new WordDescription(word, description));
    }

    /**
     * Check if the word is valid
     * @param word Word to check the validity of.
     * @throws Assignment_1.Exceptions.InvalidWordException
     * @throws Assignment_1.Exceptions.WordNullException
     */
    private void checkWord(String word) throws InvalidWordException, WordNullException {

        CheckData.CheckDataHelper checkDataHelper = new CheckData.CheckDataHelper();
        if (checkDataHelper.onlyDigits(word)) {           /* Check if the word has only digits*/

            throw new InvalidWordException("Word cannot be only numbers.");
        } else if (!this.WordDictionary.containsKey(word)) {            /* Check if the word exists in the dictionary */

            throw new WordNullException("Word is not in the dictionary.");
        }
    }
}