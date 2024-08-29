// imports
import java.util.ArrayList;
import java.util.regex.*;
import Assignment_1.Helper.CheckData;
import Assignment_1.Exceptions.InvalidDescription;
import Assignment_1.Exceptions.DescriptionNullException;

/**
 * Unique word descriptors.
 */
private class WordDescription {

    /* Associated word to descriptions. */
    private String word;

    /* Word descriptions */
    private ArrayList<String> WordDescription;

    public WordDescription(String word, String description) {
`
        this.word = word;

        // description validity check
        checkDescription(description);

        this.WordDescription = new ArrayList<String>();
        this.WordDescription.add(description);          // add the description to word
    }

    /**
     * @return Associated word.
     */
    public String getWord() {

        return this.word
    }

    /**
     * @return list of word descriptions.
     */
    public ArrayList<String> getWordDescription() {

        return this.WordDescription;
    }

    /**
     * Get a description of a given word.
     * @param descriptionNum
     * @return
     */
    public String getDescription(int descriptionNum) {

        return this.WordDescription.get(descriptionNum);
    }

    /**
     * Add description to word in dictionary
     * @param description word description
     */
    public void addDescription(String description) {

        // check description validity
        checkDescription(description);

        // check if the description already exists
        if (checkDescriptionExists(description)) throw new InvalidDescription("Description already exists, give a new description.");
        // add description
        this.WordDescription.add(description);
    }

    /**
     * Update the word description
     * @param newDescription updated description for word.
     * @param descriptionNum description number in the list.
     */
    public void updateDescription(String newDescription, int descriptionNum) {

        // check the description
        checkDescription(newDescription);

        // check if description does not exist
        if (!getDescription(descriptionNum).equals(newDescription) || !checkDescriptionExists(newDescription)) throw new DescriptionNullException("Description does not exist.");

        // update description
        this.WordDescription.set(descriptionNum, newDescription);
    }

    /**
     * Check if the description is valid.
     * @param description
     */
    private static void checkDescription(String description, int descriptionNum) {

        if (CheckData.onlyDigits(description)) {          /* Description is only numbers exception */

            throw new InvalidDescription("Description cannot be only numbers.");
        } else if (newDescription.length > 1000) {

            throw new InvalidDescription("The description is too long.  Keep the description less than 1000 characters.");
        }
    }

    private boolean checkDescriptionExists(String description) {

        for (String desc: this.WordDescription) {

            if (desc.equals(description)) return true;
        }

        return false;
    }
}