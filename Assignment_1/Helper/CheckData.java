package Assignment_1.Helper;

/**
 * Helper Function object.
 */
public static class CheckData {

    /* Regular expression for only digits */
    private static String regex = "[0-9]+";

    /**
     * Check if a string has only digits.
     * Code retrieved from:
     * Author: GeeksforGeeks
     * Date: 26/11/2022
     * URL: https://www.geeksforgeeks.org/how-to-check-if-string-contains-only-digits-in-java/
     * @param input description
     * @return word has only digits
     */
    private static boolean onlyDigits(String description)
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