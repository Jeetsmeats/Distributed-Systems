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
     * Word Dictionary Hashmap
     */
    private HashMap<String, ArrayList<String>> WordDictionary;

    public Dictionary() {

        this.WordDictionary = new HashMap<String, ArrayList<String>>();

    }

    public static void main(String[] args) {

        Dictionary tempDictionary = new Dictionary();
        // create sample data
        try {

            tempDictionary.addWord("Novel", "A long, fictional narrative that describes intimate human experiences, typically in prose form.");
            tempDictionary.addDescription("New and not resembling something formerly known or used.", "Novel");
            tempDictionary.addWord("Bank", "The land alongside or sloping down to a river or lake.");
            tempDictionary.addDescription("An establishment for the custody, loan, exchange, or issue of money, for the extension of credit, and for facilitating the transmission of funds.", "Bank");
            tempDictionary.addWord("Aloof", "Not friendly or forthcoming; cool and distant.");
            tempDictionary.addWord("Ephemeral", "Lasting for a very short time");
            tempDictionary.addWord("Serendipity", "The occurrence and development of events by chance in a happy or beneficial way.");
            tempDictionary.addWord("Obfuscate", "To render obscure, unclear, or unintelligible");

        } catch (InvalidDescription e) {

            e.printStackTrace();
        }

        // serialise hashmap
        try (FileOutputStream fileOut = new FileOutputStream("Dictionary.ser");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {

            out.writeObject(tempDictionary);
        } catch (IOException e) {

            e.printStackTrace();
        }
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

    /**
     * Method to remove word from the dictionary.
     * @param word word to remove from dictionary.
     * @throws DictionaryActionException error in removing word from dictionary
     * @return Status message for remove word.
     */
    public String removeWord(String word) throws DictionaryActionException {

        // check word validity
        try {

            checkWordExists(word);
            checkWordIsValid(word);
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

            checkWordIsValid(word);
        } catch (InvalidWordException e) {

            e.printStackTrace();
        }

        ArrayList<String> descriptions = new ArrayList<String>();
        descriptions.add(description);

        this.WordDictionary.put(word, descriptions);
    }

    /**
     * @return list of word descriptions.
     */
    public ArrayList<String> getWordDescription(String word) {

        return this.WordDictionary.get(word);
    }

    /**
     * Add description to word in dictionary
     * @param description word description
     * @throws InvalidDescription entered description already exists
     */
    public void addDescription(String description, String word) throws InvalidDescription {

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
    }

    /**
     * Update the word description
     * @param newDescription updated description for word.
     * @throws DescriptionNullException
     */
    public void updateDescription(String newDescription, String word) throws DescriptionNullException {

        // check the description
        // description validity check
        try {

            checkDescription(newDescription);

            // get the description index in array
            int descIdx = checkDescriptionExists(newDescription, word);

            if (descIdx == -1) throw new DescriptionNullException("Description does not exist.");

            // update description
            this.WordDictionary.get(word).set(descIdx, newDescription);
        } catch (InvalidDescription e) {

            e.printStackTrace();
        }
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