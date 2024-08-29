// imports
import java.util.HashMap;
import Assignment_1.Exceptions.*
import Assignment_1.Helper.CheckData;

/***
 * Word Dictionary Data Structure
 */
private class Dictionary {

    /**
     * Word Dictionary Hashmap
     */
    private HashMap<String, WordDescription> WordDictionary;

    /**
     * Get the word meanings
     * @param word
     * @throws
     * @return
     */
    public WordDescription getMeaning(String word) {

        this.WordDictionary.get(word) != null ? return this.WordDictionary.get(word) : throw new WordNullException("Word is not in the dictionary.");
    }

    /**
     *
     * @param word
     * @throws WordNullException
     * @throws DictionaryActionException
     * @return
     */
    public String removeWord(String word) {

        // check word validity
        checkWord(word);
        this.WordDictionary.remove(word) ? return "Successfully removed word!" : throw new DictionaryActionException("Word could not be removed from the dictionary.");
    }

    /**
     * Add word to the dictionary.
     * @param word
     * @param description
     */
    public void addWord(String word, String description) {

        // check word validity
        checkWord(word);

        this.WordDictionary.put(word, new WordDescription(word, description));
    }

    /**
     * Check if the word is valid
     * @param word
     * @throws WordNullException
     * @throws InvalidWordException
     */
    private static void checkWord(String word) {

        if (CheckData.onlyDigits(word)) {

            throw new InvalidWordException("Word cannot be only numbers.");
        } else if (!this.WordDictionary.containsKey(word)) {

            throw new WordNullException("Word is not in the ditionary.");
        }
    }
}