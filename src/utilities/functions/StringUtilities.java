package utilities.functions;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.xml.bind.DatatypeConverter;

/**
 * The {@code StringUtilities} class contains methods relating to 
 * {@code String}s that are small, efficient, and commonly used.
 *
 * @author Oliver Abdulrahim
 */
public final class StringUtilities {
    
    /**
     * Field that provides for a list of messages that may be retrieved randomly
     * in a message-of-the-day (MoTD) style.
     */
    public static final List<String> MESSAGE_LIST = 
        Collections.unmodifiableList(Arrays.asList(randomStringArray(10, 10)));
    
    /**
     * Don't let anyone instantiate this class.
     */
    private StringUtilities() {
    
    }
    
    /**
     * Processes a given {@code String} to imitate the literary style that Apple
     * Inc. utilizes when describing its products.
     * 
     * <p> As an example, the following call to this method
     * 
     * <blockquote><pre>
     * appleify("We are profoundly passionate about music.");</pre>
     * </blockquote>
     * 
     * would produce the following result: 
     *   
     * <blockquote><pre>
     * "We. Are. Profoundly. Passionate. About. Music".</pre>
     * </blockquote>
     * 
     * @param str The {@code String} to "appleify."
     * @return The parodied version of the given {@code String}.
     */
    public static String appleify(String str) {
        // Removes any existing periods - may cause issues with written currency
        String[] words = extractWords(str.replaceAll("[\\.\\,]", ""));
        StringBuilder apple = new StringBuilder();
        for (String word : words) {
            if (isAppleProduct(word)) {
                apple.append(word);
            }
            else {
                apple.append(Character.toUpperCase(word.charAt(0)))
                     .append(word.substring(1));
            }
            apple.append('.').append(' ');
        }
        return apple.toString();
    }
    
    private static boolean isAppleProduct(String str) {
        final String[] appleProducts = { 
            "iPod", "iPhone", "iPad", "iCloud", "iOS"
        };
        for (String appleProduct : appleProducts) {
            if (appleProduct.equals(str)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Returns all the words in a given {@code String} allocated into an array
     * of {@code String}s. A word is considered any text that has any space
     * character(s) preceding and/or succeeding it.
     * 
     * @param str The {@code String} to split words from.
     * @return An array containing all the words in the given {@code String}.
     */
    public static String[] extractWords(String str) {
        return str.split("\\s+");
    }
    
    /**
     * "Expands" a given {@code String} by adding spaces between its characters
     * and adding each of its characters underneath the first in a column 
     * format.
     * 
     * <p> As an example, the following call to this method
     * 
     * <blockquote><pre>
     * expand("A B CD");</pre>
     * </blockquote>
     * 
     * would produce the following result: 
     *   
     * <blockquote><pre>
     * "A B C D 
     *  B 
     *  C 
     *  D".</pre>
     * </blockquote>
     * 
     * @param str The {@code String} to expand using this algorithm.
     * @return The expanded form of the {@code String} argument.
     */
    public static String expand(String str) {
        String result = normalize(str);
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < result.length(); i++) {
            sb.append('\n').append(result.charAt(i));
        }
        return result.replace("", " ").trim() + sb.toString();
    }
    
    /**
     * Normalizes a given {@code String} by removing any and all space 
     * characters and converting it to upper case. Returns a trimmed version of
     * this processed {@code String}.
     * 
     * <p> As an example, the following call to this method
     * 
     * <blockquote><pre>
     * normalize("A b CD");</pre>
     * </blockquote>
     * 
     * would produce the following result: 
     *   
     * <blockquote><pre>
     * "ABCD"</pre>
     * </blockquote>
     * 
     * @param str The {@code String} to normalize.
     * @return The normalized form of {@code str}.
     */
    public static String normalize(String str) {
        return str.replace("\\s+", "") // \s+ regex matches all spaces
                  .toUpperCase();
    }
    
    /**
     * Reverses and returns a given {@code String} object's characters.
     * 
     * @param str The {@code String} to reverse.
     * @return A reversed version of the given argument.
     */
    public static String reverse(String str) {
        return new StringBuilder(str).reverse().toString();
    }
    
    /**
     * Retrieves a random {@code String} literal from {@link #MESSAGE_LIST} 
     * field.
     *
     * @return The random message from {@link #MESSAGE_LIST} field.
     */
    public static String getMessage() {
        return MESSAGE_LIST.get(Utilities.r.nextInt(MESSAGE_LIST.size()));
    }
    
    /**
     * Converts a {@code String} to hexadecimal.
     * 
     * @param str The {@code String} to convert.
     * @return A {@code String} containing a hexadecimal equivalent of the given
     *         lexical argument.
     */
    public static String stringToHex(String str) {
        byte bytes[] = str.getBytes();
        return DatatypeConverter.printHexBinary(bytes);
    }
    
    /**
     * Converts a hexadecimal {@code String} to the equivalent lexical 
     * representation.
     * 
     * @param str The {@code String} to convert.
     * @return A {@code String} containing a lexical representation of the given
     *         hexadecimal argument.
     */
    public static String hexToString(String str) {
        return new String(DatatypeConverter.parseHexBinary(str));
    }
    
    /**
     * Creates and returns a {@code String} with a single repeating character 
     * based on the specified arguments.
     * 
     * @param length The length of the {@code String} to create.
     * @param c The character to repeat in the {@code String}.
     * @return A generated {@code String} with the specified arguments.
     */
    public static String createRepeating(int length, char c) {
        char[] chars = new char[length];
        Arrays.fill(chars, c);
        return new String(chars);
    }
    
    /**
     * Formats a {@code String} with a given delimiter in between each 
     * individual character. 
     * 
     * <p> As an example, the following call to this method
     * 
     * <blockquote><pre>
     * delimit("abc", " ");</pre>
     * </blockquote>
     * 
     * would produce the following result: 
     *   
     * <blockquote><pre>
     * "a b c"</pre>
     * </blockquote>
     * 
     * @param str The {@code String} to format.
     * @param delimiter The {@code String} to insert after each character.
     * @return A formatted version of the given {@code String} with the 
     *         {@code delimiter} argument placed after each character.
     */
    public static String delimit(String str, String delimiter) {
        return str.replace("", delimiter).trim();
    }
    
    /**
     * Formats a {@code String} with a given delimiter in between each 
     * individual character. 
     * 
     * <p> As an example, the following call to this method
     * 
     * <blockquote><pre>
     * delimit("abc", ' ');</pre>
     * </blockquote>
     * 
     * would produce the following result: 
     *   
     * <blockquote><pre>
     * "a b c"</pre>
     * </blockquote>
     * 
     * @param str The {@code String} to format.
     * @param delimiter The {@code char} to insert after each character.
     * @return A formatted version of the given {@code String} with the 
     *         {@code delimiter} argument placed after each character.
     */
    public static String delimit(String str, char delimiter) {
        return delimit(str, String.valueOf(delimiter));
    }
    
    /**
     * Sorts the characters of a given {@code String} in ascending order.
     * 
     * @param str The {@code String} to sort.
     * @return A sorted version of the given {@code String}.
     */
    public static String sort(String str) {
        char[] chars = str.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }
    
    /**
     * Formats a {@code String} as if it were the first word in a sentence.
     * 
     * <p> As an example, the following call to this method
     * 
     * <blockquote><pre>
     * asSentence("a bC");</pre>
     * </blockquote>
     * 
     * would produce the following result: 
     *   
     * <blockquote><pre>
     * "A bc"</pre>
     * </blockquote>
     * 
     * @param str The {@code String} to format.
     * @return The formatted {@code String} argument.    
     */
    public static String asSentence(String str) {
        if (str.isEmpty()) {
            return "";
        }
        String result = str.toLowerCase().trim();
        result = Character.toUpperCase(result.charAt(0)) 
               + result.substring(1, result.length());
        return result;
    }
    
    /**
     * Generates a pseudorandom {@code char} value, from {@code 0} to 
     * {@code 65535} inclusive.
     * 
     * @return A random {@code char}.
     */
    public static char randomChar() {
        return randomChar(Character.MIN_VALUE, Character.MAX_VALUE);
    }
    
    /**
     * Generates a pseudorandom {@code char} value, from the given lower bound
     * to the given upper bound, inclusive.
     * 
     * @param lower Lower bound to generate character.
     * @param upper Upper bound to generate character.
     * @return A random {@code char}.
     */
    public static char randomChar(int lower, int upper) {
        return (char) (Utilities.r.nextInt((upper - lower) + 1) + lower);
    }
    
    /**
     * Generates a random array of {@code String}s with the given arguments.
     * 
     * @param arrayLength The length of the array to generate.
     * @param stringLength The length of the {@code String} elements to 
     *        populate the array.
     * @return A generated array of {@code String} objects with the 
     *         specified arguments.
     * @see #random(int) 
     */
    public static String[] randomStringArray(int arrayLength, 
            int stringLength) 
    {
        String[] array = new String[arrayLength];
        for (int i = 0; i < array.length; i++) {
            array[i] = random(Utilities.r.nextInt(stringLength) + 1);
        }
        return array;
    }
    
    /**
     * Generates a pseudorandom {@code String} object. May include {@code char}
     * values from {@code 0} to {@code 65535} inclusive.
     * 
     * @param length The length of the {@code String} to generate.
     * @return A random {@code String} object with the given length.
     * @see #randomChar()
     */
    public static String random(int length) {
        return random(Character.MIN_VALUE, Character.MAX_VALUE, length);
    }
    
    /**
     * Generates a pseudorandom {@code String} object. May include {@code char}
     * values from {@code lower} to {@code upper} inclusive.
     * 
     * @param lower Lower bound, inclusive.
     * @param upper Upper bound, inclusive.
     * @param length The length of the {@code String} to generate.
     * @return A random {@code String} object with the given length.
     * @see #randomChar()
     */
    public static String random(char lower, char upper, int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("length : " + length + " < 0 !");
        }
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(randomChar(lower, upper));
        }
        return sb.toString();
    }
    
    /**
     * Generates a discrete, pseudorandom {@code String} object with specified 
     * {@code length} using a {@link HashSet} implementation. May include 
     * {@code char} values from {@code 0} to {@code 65535} inclusive with no 
     * repeating characters. 
     * 
     * @param length The length of the {@code String} to generate.
     * @return A random {@code String} object with the given length.
     * @throws IllegalArgumentException if the given argument is an invalid
     *         length.
     * @see #randomChar()
     */
    public static String randomUnique(int length) {
        if (length <= 0 || length > Character.MAX_VALUE) {
            String error = "Length argument is " + ((length < 0) 
                            ? "negative: "  + length + "< 0"
                            : "too targe: " + length + "> Character.MAX_VALUE");
            throw new IllegalArgumentException(error);
        }
        HashSet<Character> uniques = new HashSet<>(length);
        StringBuilder sb = new StringBuilder(length);
        
        int i = 0;
        while (i < length) {
            char rand = randomChar();
            if (!uniques.contains(rand)) {
                uniques.add(rand);
                sb.append(rand);
                i++;
            }
        }
        return sb.toString();
    }    
    
    /**
     * Returns a formatted version of the given {@code String}, delimiting each
     * character with a comma and space, and the final character with a period.
     * 
     * <p> As an example, the following call to this method
     * 
     * <blockquote><pre>
     * formattedToString("Hello World");</pre>
     * </blockquote>
     * 
     * would produce the following result: 
     *   
     * <blockquote><pre>
     * "H, e, l, l, o, W, o, r, l, d."</pre>
     * </blockquote>
     * 
     * @param str The {@code String} to format.
     * @return A formatted version of the given {@code String}.
     */
    public static String formattedToString(String str) {
        String sanitized = str.replace(" ", "");
        StringBuilder formatted = new StringBuilder(sanitized.length() * 3);
        for (int i = 0; i < sanitized.length(); i++) {
            formatted.append(sanitized.charAt(i))
                     .append((i < sanitized.length() - 1) ? ", " : '.');
        }
        return formatted.toString();
    }
    
    /**
     * Returns a percent-formatted {@code String} of the given fractional value.
     * 
     * <p> As an example, the following call to this method
     * 
     * <blockquote><pre>
     * doubleAsPercent(3 / 4.0d)</pre>
     * </blockquote>
     * 
     * would produce the following result: 
     *   
     * <blockquote><pre>
     * "75%"</pre>
     * </blockquote>
     * 
     * @param fraction
     * @return 
     */
    public static String doubleAsPercent(double fraction) {
        if (Double.isNaN(fraction)) {
            return "0%";
        }
        return NumberFormat.getPercentInstance().format(fraction);
    }
    
    /**
     * Reduces a given {@code String} to its unique characters, returning an
     * order-preserved resultant {@code String}.
     * 
     * <p> As an example, the following call to this method
     * 
     * <blockquote><pre>
     * reduceToUniques("Hello World")</pre>
     * </blockquote>
     * 
     * would produce the following result: 
     *   
     * <blockquote><pre>
     * Helo Wrd</pre>
     * </blockquote>
     * 
     * @param str The {@code String} to reduce.
     * @return A reduced version of the given {@code String}.
     */
    public static String reduceToUniques(String str) {
        Set<Character> uniques = new LinkedHashSet<>();
        for (char c : str.toCharArray()) {
            uniques.add(c);
        }
        StringBuilder sb = new StringBuilder();
        uniques.stream().forEach((character) -> {
            sb.append(character);
        });
        return sb.toString();
    }
    
    /**
     * Tests if a given {@code char} occurs at least once in a given 
     * {@code String}.
     * 
     * @param str The {@code String} to test.
     * @param key The character to look for in the given {@code String}.
     * @return {@code true} if the given character is contained in the given 
     *         {@code String}.
     */
    public static boolean contains(String str, char key) {
        for (char c : str.toCharArray()) {
            if (key == c) {
                return true;
            }
        }
        return false;
    }
    
}
