package DataStructures;

// imports
import java.util.HashMap;
import Exceptions.*;
import Helper.*;
import java.io.*;

/***
 * Word Dictionary Data Structure
 */
public class Dictionary {

    /**
     * Word Dictionary Hashmap
     */
    private HashMap<String, WordDescription> WordDictionary;

    public Dictionary() {

        this.WordDictionary = new HashMap<String, WordDescription>();

    }

    public static void main(String[] args) {

        Dictionary tempDictionary = new Dictionary();
        // create sample data
        try {

            WordDescription wd1 = new WordDescription("Novel", "A long, fictional narrative that describes intimate human experiences, typically in prose form.");
            wd1.addDescription("New and not resembling something formerly known or used.");

            WordDescription wd2 = new WordDescription("Bank", "The land alongside or sloping down to a river or lake.");
            wd2.addDescription("An establishment for the custody, loan, exchange, or issue of money, for the extension of credit, and for facilitating the transmission of funds.");

            WordDescription wd3 = new WordDescription("Aloof", "Not friendly or forthcoming; cool and distant.");

            WordDescription wd4 = new WordDescription("Ephemeral", "Lasting for a very short time");

            WordDescription wd5 = new WordDescription("Serendipity", "The occurrence and development of events by chance in a happy or beneficial way.");

            WordDescription wd6 = new WordDescription("Obfuscate", "To render obscure, unclear, or unintelligible");

            tempDictionary.put(wd1.getWord(), wd1);
            tempDictionary.put(wd2.getWord(), wd2);
            tempDictionary.put(wd3.getWord(), wd3);
            tempDictionary.put(wd4.getWord(), wd4);
            tempDictionary.put(wd5.getWord(), wd5);
            tempDictionary.put(wd6.getWord(), wd6);

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
     * Put method for word description data
     * @param wd word description data
     */
    public void put(String word, WordDescription wd) {
        this.WordDictionary.put(word, wd);
    }
    /**
     * Get the list of word meanings.
     * @param word Selected word
     * @throws WordNullException word in dictionary error
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
     * @param word word to remove from dictionary.
     * @throws DictionaryActionException error in removing word from dictionary
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
     * @throws InvalidWordException
     * @throws WordNullException
     */
    private void checkWord(String word) throws InvalidWordException, WordNullException {

        CheckData.CheckDataHelper checkDataHelper = new CheckData.CheckDataHelper();
        if (checkDataHelper.onlyDigits(word)) {           /* Check if the word has only digits*/

            throw new InvalidWordException("Word cannot be only numbers.");
        } else if (!this.WordDictionary.containsKey(word)) {            /* Check if the word exists in the dictionary */

            throw new WordNullException("Word is not in the dictionary.");
        }
    }

//    private void readData() {
//
//        try (FileInputStream fileIn = new FileInputStream("Dictionary.ser");
//             ObjectInputStream in = new ObjectInputStream(fileIn)) {
//
//            this.WordDictionary = (HashMap<String, WordDescription>) in.readObject();
//        } catch (IOException | ClassNotFoundException i) {
//
//            i.printStackTrace();
//        }
//    }
}