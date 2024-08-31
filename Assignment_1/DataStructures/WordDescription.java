package DataStructures;

// imports
import java.util.ArrayList;
import java.util.regex.*;
import Helper.*;
import Exceptions.InvalidDescription;
import Exceptions.DescriptionNullException;

/**
 * Unique word descriptors.
 */
public class WordDescription {

    /* Associated word to descriptions. */
    private String word;

    /* Word descriptions */
    private ArrayList<String> WordDescription;

    public WordDescription(String word, String description) {

        this.word = word;

        // description validity check
        try {

            checkDescription(description);
        } catch (InvalidDescription e) {

            e.printStackTrace();
        }

        this.WordDescription = new ArrayList<String>();
        this.WordDescription.add(description);          // add the description to word
    }

    /**
     * @return Associated word.
     */
    public String getWord() {

        return this.word;
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
     * @throws Exceptions.InvalidWordException
     */
    public void addDescription(String description) throws InvalidDescription {

        try {

            // check description validity
            checkDescription(description);
        } catch (InvalidDescription e) {

            e.printStackTrace();
        }

        // check if the description already exists
        if (checkDescriptionExists(description)) throw new InvalidDescription("Description already exists, give a new description.");
        // add description
        this.WordDescription.add(description);
    }

    /**
     * Update the word description
     * @param newDescription updated description for word.
     * @param descriptionNum description number in the list.
     * @throws DescriptionNullException
     */
    public void updateDescription(String newDescription, int descriptionNum) throws DescriptionNullException {

        // check the description
        // description validity check
        try {

            checkDescription(newDescription);

            // check if description does not exist
            if (!getDescription(descriptionNum).equals(newDescription) || !checkDescriptionExists(newDescription)) throw new DescriptionNullException("Description does not exist.");

            // update description
            this.WordDescription.set(descriptionNum, newDescription);
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

    private boolean checkDescriptionExists(String description) {

        for (String desc: this.WordDescription) {

            if (desc.equals(description)) return true;
        }

        return false;
    }
}