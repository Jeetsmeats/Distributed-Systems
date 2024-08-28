// imports
import java.util.ArrayList;
import java.util.regex.*;
import Assignment_1.Exceptions.InvalidDescription;

/**
 * Unique word descriptors.
 */
private class WordDescription {

    private String word;

    /* Word description */
    private ArrayList<String> wordDescription;

    /* Regular expression for only digits */
    private static String regex = "[0-9]+"

    public WordDescription(String word) {

        this.word = word;
        this.wordDescription = new ArrayList<String>();
    }

    public WordDescription(String word, ArrayList<String> descriptions) {

        this.word = word;
        this.wordDescription = descriptions;
    }
    /**
     * @return list of word descriptions.
     */
    public ArrayList<String> getWordDescription() {

        return this.wordDescription;
    }

    /**
     * Add description to word in dictionary
     * @param description word description
     */
    public void addDescription(String description) {

        if (onlyDigits(description)) {          /* Description is only numbers exception */

            throw new InvalidDescription("Description cannot be only numbers.");
        } else if (this.wordDescription.contains(description)) {        /* Description already exists */

            throw new InvalidDescription("Description already exists in dictionary.");
        }

        // add description
        this.wordDescription.add(description);
    }

    /**
     * Update the word description
     * @param newDescription updated description for word.
     * @param descriptionNum description number in the list.
     */
    public void updateDescription(String newDescription, int descriptionNum) {

        if (onlyDigits(description)) {          /* Description is only numbers exception */

            throw new InvalidDescription("Description cannot be only numbers.");
        } else if (descriptionNum.equals(this.wordDescription.get(descriptionNum))) {

            throw new InvalidDescription("Description is the same.  Update the description to something new.")
        }

        // update description
        this.wordDescription.set(descriptionNum, newDescription);
    }

    /**
     * Check if a string has only digits.
     * Code retrieved from:
     * Author: GeeksforGeeks
     * Date: 26/11/2022
     * URL: https://www.geeksforgeeks.org/how-to-check-if-string-contains-only-digits-in-java/
     * @param str check for only digits in input string
     * @return only
     */
    private static boolean onlyDigits(String str)
    {
        // Regex to check if string constains only digits
        String regex = "[0-9]+";

        // Compile the regex
        Pattern p = Pattern.compile(regex);

        if (str == null) {      /* Check empty string */

            return false;
        }

        // Compare regex to string
        Matcher m = p.matcher(str);

        // check regex matching
        return m.matches();
    }
}