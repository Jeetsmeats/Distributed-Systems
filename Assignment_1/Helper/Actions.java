package Helper;

import javax.swing.*;

/**
 * Action being sent from client.
 */
public class Actions {


    /**
     * Actionable items for the client.
     */
    private ActionType actionType;

    /**
     * Word to apply action to.
     */
    private String word;

    /**
     * Meaning to apply action to.
     */
    private String meaning;

    /**
     * Previous meaning to update.
     */
    private String prevMeaning;

    /**
     * Action constructor for strictly updating meaning.
     * @param word actionable word.
     * @param meaning actionable meaning.
     * @param prevMeaning actionable previous meaning.
     */
    public Actions(String word, String meaning, String prevMeaning) {

        this.word = word;
        this.meaning = meaning;
        this.prevMeaning = prevMeaning;
        this.actionType = ActionType.UPDATE_MEANING;
    }

    /**
     * Action constructor for strictly adding meaning to dictionary.
     * @param word actionable word
     * @param meaning actionable meaning
     */
    public Actions(String word, String meaning, ActionType actionType) {

        this.word = word;
        this.meaning = meaning;
        this.actionType = actionType;
    }

    /**
     * Action constructor for strictly word related actions.
     * @param word actionable word
     * @param actionType action type
     */
    public Actions(String word, ActionType actionType) {

        this.word = word;
        this.actionType = actionType;
    }

    /**
     * Action constructor for strictly retrieving the dictionary.
     * @param actionType action type
     */
    public Actions() {

        this.actionType = ActionType.GET_DICTIONARY;
    }

    public String getWord() {

        return this.word;
    }

    public String getMeaning() {

        return this.meaning;
    }

    public String getPrevMeaning() {

        return this.prevMeaning;
    }

    public ActionType getActionType() {

        return this.actionType;
    }
}
