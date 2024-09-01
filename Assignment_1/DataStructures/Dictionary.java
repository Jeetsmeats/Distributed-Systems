package DataStructures;

// imports
import java.util.HashMap;
import Exceptions.*;
import Helper.*;
import java.io.*;
import java.util.*;

/***
 * Word Dictionary Data Structure
 */
public class Dictionary implements Serializable {

    /**
     * Serial UID to reuse generated data.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Word Dictionary Hashmap
     */
    private HashMap<String, ArrayList<String>> WordDictionary;

    /**
     * File path of saved data.
     */
    private String filePath;

    public Dictionary() {

        this.WordDictionary = new HashMap<String, ArrayList<String>>();

    }

    public static void main(String[] args) {

        Dictionary tempDictionary = new Dictionary();
        // create sample data
        try {

            tempDictionary.addWord("Novel", "A long, fictional narrative that describes intimate human experiences, typically in prose form.");
            tempDictionary.addDescription("Novel", "New and not resembling something formerly known or used.");
            tempDictionary.addWord("Bank", "The land alongside or sloping down to a river or lake.");
            tempDictionary.addDescription("Bank", "An establishment for the custody, loan, exchange, or issue of money, for the extension of credit, and for facilitating the transmission of funds.");
            tempDictionary.addWord("Aloof", "Not friendly or forthcoming; cool and distant.");
            tempDictionary.addWord("Ephemeral", "Lasting for a very short time");
            tempDictionary.addWord("Serendipity", "The occurrence and development of events by chance in a happy or beneficial way.");
            tempDictionary.addWord("Obfuscate", "To render obscure, unclear, or unintelligible");

        } catch (InvalidDescription e) {

            e.printStackTrace();
        } catch (InvalidWordException e) {

            e.printStackTrace();
        }

        // save data
        saveData(tempDictionary);
    }

    /**
     * Setter for the dictionary.
     * @param wordDictionary HashMap to set as dictionary.
     */
    public void setWordDictionary(HashMap<String, ArrayList<String>> wordDictionary) {

        this.WordDictionary = wordDictionary;
    }

    /**
     * Getter for the dictionary.
     * @return Dictionary hashmap.
     */
    public HashMap<String, ArrayList<String>> getWordDictionary() {

        return this.WordDictionary;
    }

    public ArrayList<String> getWords() {

        return new ArrayList<String>(this.WordDictionary.keySet());
    }

    /**
     * Get the list of word meanings.
     * @param word Selected word
     * @throws WordNullException word in dictionary error
     * @return Get the list of word descriptions.
     */
    public ArrayList<String> getMeaning(String word) throws WordNullException {

        if (this.WordDictionary.containsKey(word)) {

            return this.WordDictionary.get(word);
        } else {

            throw new WordNullException("Word is not in the dictionary.");
        }
    }

    public static void saveData(Dictionary d) {

        // serialise hashmap
        try (FileOutputStream fileOut = new FileOutputStream("Dictionary.ser");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {

            out.writeObject(d);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    /**
     * Method to remove word from the dictionary.
     * @param word word to remove from dictionary.
     * @throws DictionaryActionException error in removing word from dictionary
     * @throws InvalidWordException error in input word.
     * @throws WordNullException error due to word nullity.
     * @return Status message for remove word.
     */
    public String removeWord(String word) throws DictionaryActionException, InvalidWordException, WordNullException {

        checkWordExists(word);
        checkWordIsValid(word);

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
     * @throws InvalidWordException error in input word..
     */
    public void addWord(String word, String description) throws InvalidWordException {

        // check word validity
        checkWordIsValid(word);

        ArrayList<String> descriptions = new ArrayList<String>();
        descriptions.add(description);

        this.WordDictionary.put(word, descriptions);
    }

    /**
     * Add description to word in dictionary
     * @param description word description
     * @throws InvalidDescription entered description already exists
     * @return list of descriptions
     */
    public ArrayList<String> addDescription(String word, String description) throws InvalidDescription {

        try {

            // check description validity
            checkDescription(description);
        } catch (InvalidDescription e) {

            e.printStackTrace();
        }

        // check if the description already exists
        if (checkDescriptionExists(description, word) >= 0) throw new InvalidDescription("Description already exists, give a new description.");

        // add description
        this.WordDictionary.get(word).add(description);

        return this.WordDictionary.get(word);
    }

    /**
     * Update the word description.
     * @param newDescription updated description for word.
     * @param prevDescription previous description for word.
     * @param word the word corresponding to updated description.
     * @throws DescriptionNullException description existence nullity.
     * @throws InvalidDescription error in input description.
     * @return list of descriptions
     */
    public ArrayList<String> updateDescription(String newDescription, String prevDescription, String word) throws DescriptionNullException, InvalidDescription {

        // description validity check
        checkDescription(newDescription);

        // get the description index in array
        int descIdx = checkDescriptionExists(prevDescription, word);

        if (descIdx == -1) throw new DescriptionNullException("Description does not exist.");

        // update description
        this.WordDictionary.get(word).set(descIdx, newDescription);

        return this.WordDictionary.get(word);
    }

    /**
     * Check if the description is valid.
     * @param description description to check validity.
     * @throws Exceptions.InvalidDescription
     */
    private void checkDescription(String description) throws InvalidDescription {

        CheckData.CheckDataHelper checkDataHelper = new CheckData.CheckDataHelper();
        if (checkDataHelper.onlyDigits(description)) {          /* Description is only numbers exception */

            throw new InvalidDescription("Description cannot be only numbers.");
        } else if (description.length() > 1000) {

            throw new InvalidDescription("The description is too long.  Keep the description less than 1000 characters.");
        }
    }

    /**
     * Check if description exist for the word.
     * @param description description to check
     * @return description pointer.
     */
    private int checkDescriptionExists(String description, String word) {

        int i = 0;
        for (String desc: this.WordDictionary.get(word)) {

            if (desc.equals(description)) return i;
            i++;
        }

        return -1;
    }

    /**
     * Check if the word is valid
     * @param word Word to check the validity of.
     * @throws InvalidWordException
     * @throws WordNullException
     */
    private void checkWordIsValid(String word) throws InvalidWordException {

        CheckData.CheckDataHelper checkDataHelper = new CheckData.CheckDataHelper();
        if (checkDataHelper.onlyDigits(word)) {           /* Check if the word has only digits*/

            throw new InvalidWordException("Word cannot be only numbers.");
        }
    }

    /**
     * Check if the word exists.
     * @param word word to check the existence of.
     * @throws WordNullException Word does not exist.
     */
    private void checkWordExists(String word) throws WordNullException {

        if (!this.WordDictionary.containsKey(word)) {            /* Check if the word exists in the dictionary */

            throw new WordNullException("Word is not in the dictionary.");
        }
    }
}