package Helper;

// imports
import java.util.regex.*;

/**
 * Helper Function object.
 */
public class CheckData {

    /* Regular expression for only digits */
    private static String regex = "[0-9]+";

    public static class CheckDataHelper {

        /**
         * Check if a string has only digits.
         * Code retrieved from:
         * Author: GeeksforGeeks
         * Date: 26/11/2022
         * URL: https://www.geeksforgeeks.org/how-to-check-if-string-contains-only-digits-in-java/
         * @param description string containing to check variable.
         * @return word has only digits
         */
        public static boolean onlyDigits(String description)
        {
            // Regex to check if string contains only digits
            String regex = "[0-9]+";

            // Compile the regex
            Pattern p = Pattern.compile(regex);

            if (description == null) {      /* Check empty string */

                return false;
            }

            // Compare regex to string
            Matcher m = p.matcher(description);

            // check regex matching
            return m.matches();
        }
    }
}