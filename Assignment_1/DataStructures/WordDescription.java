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
    private ArrayList<String> descriptionList;

    public WordDescription(String word, String description) {

        this.word = word;

        // description validity check
        try {

            checkDescription(description);
        } catch (InvalidDescription e) {

            e.printStackTrace();
        }

        this.descriptionList = new ArrayList<String>();
        this.descriptionList.add(description);          // add the description to word
    }

    /**
     * Word getter
     * @return
     */
    public String getWord() {

        return word;
    }

    /**
     * @return list of word descriptions.
     */
    public ArrayList<String> getWordDescription() {

        return this.descriptionList;
    }

    /**
     * Add description to word in dictionary
     * @param description word description
     * @throws InvalidDescription entered description already exists
     */
    public void addDescription(String description) throws InvalidDescription {

        try {

            // check description validity
            checkDescription(description);
        } catch (InvalidDescription e) {

            e.printStackTrace();
        }

        // check if the description already exists
        if (checkDescriptionExists(description) >= 0) throw new InvalidDescription("Description already exists, give a new description.");
        // add description
        this.descriptionList.add(description);
    }

    /**
     * Update the word description
     * @param newDescription updated description for word.
     * @throws DescriptionNullException
     */
    public void updateDescription(String newDescription) throws DescriptionNullException {

        // check the description
        // description validity check
        try {

            checkDescription(newDescription);

            // get the description index in array
            int descIdx = checkDescriptionExists(newDescription);

            if (descIdx == -1) throw new DescriptionNullException("Description does not exist.");

            // update description
            this.descriptionList.set(descIdx, newDescription);
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
    private int checkDescriptionExists(String description) {

        int i = 0;
        for (String desc: this.descriptionList) {

            if (desc.equals(description)) return i;
            i++;
        }

        return -1;
    }
}