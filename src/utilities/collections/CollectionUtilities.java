package utilities.collections;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The {@code CollectionUtilities} class contains utility methods related to the
 * {@link Collection} hierarchy.
 * 
 * @author Oliver Abdulrahim
 * @see Collection
 */
public final class CollectionUtilities {
    
    /**
     * The maximum allocable size of array. This value is one full bit less than
     * (2<sup>31</sup> - 1). Any attempts to allocate an array length larger 
     * than this value may exceed the VM limit and result in an 
     * {@link java.lang.OutOfMemoryError}.
     */
    public static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    
    /**
     * Defines an empty {@code Object} array for convenience.
     */
    public static final Object[] EMPTY_OBJECT_ARRAY = {};
    
    /**
     * Don't let anyone instantiate this class.
     */
    private CollectionUtilities() {
        throw new UnsupportedOperationException("Can't instantiate this class");
    }

    /**
     * Ensures that a given {@code Collection} is not {@code null} and contains
     * no {@code null} elements.
     * 
     * @param c The {@code Collection} to test for non-nullity.
     * @throws NullPointerException if the given {@code Collection} refers to
     *         {@code null} or contains {@code null} a element or elements.
     */
    public static void requireNonNullCollection(Collection<?> c) {
        Objects.requireNonNull(c, "Invalid null Collection!");
        boolean containsNullObject = c.stream().anyMatch(Objects :: isNull);
        if (containsNullObject) {
            throw new NullPointerException("Invald null element in c");
        }
    }
    
    /**
     * Ensures that a given {@code Collection} returns an appropriate array of
     * objects from its {@link Collection#toArray()} method. This method is
     * typically used in the case of an invalid {@code toArray()} implementation
     * to prevent any unexpected or undesired behavior.
     * 
     * @param c The {@code Collection} to convert to a {@code Object} array.
     * @return An array containing the {@link Collection#toArray()} of the given
     *         argument.
     */
    @SuppressWarnings("unchecked")
    public static Object[] ensureValidToArray(Collection<?> c) {
        Object[] elements = c.toArray();
        if (elements.getClass() != Object[].class) { 
            elements = Arrays.copyOf(elements, elements.length, Object[].class);
        }
        return elements;
    }
    
    /**
     * Trims the capacity of a given {@code Object} array to the given capacity
     * argument.
     * 
     * @param elements The array of objects whose length to trim to the desired 
     *                 capacity.
     * @param desiredCapacity The desired new length of the given array.
     * @return The trimmed version of the given array.
     */
    public static Object[] trimToSize(Object[] elements, int desiredCapacity) {
        Object[] trimmed = elements;
        if (elements.length > desiredCapacity) {
            trimmed = (desiredCapacity == 0)
                    ? EMPTY_OBJECT_ARRAY
                    : Arrays.copyOf(elements, desiredCapacity);
        }
        return trimmed;
    }
    
    /**
     * Generates a {@code String} representation of the range of a given indexed
     * array.
     * 
     * @param array The array to generate a range {@code String} for.
     * @return A {@code String} representing the range of the given array.
     */
    public static String generateArrayRange(Object[] array) {
        return "[0, " + array.length + ']';
    }
    
    /**
     * Generates a standard error message intended for use as a detail message
     * for any thrown {@code IndexOutOfBoundsException}.
     * 
     * @param c The {@code Collection} to generate a message for.
     * @param index The invalid index to generate a message with.
     * @return A formatted {@code String} for use as a detail message for an 
     *         {@code IndexOutOfBoundsException}.
     */
    public static String outOfBoundsMessage(Collection<?> c, int index) {
        Objects.requireNonNull(c, "Invalid null Collection!");
        return outOfBoundsMessage(ensureValidToArray(c), index);
    }
    
    /**
     * Generates a standard error message intended for use as a detail message
     * for any thrown {@code IndexOutOfBoundsException}.
     * 
     * @param array The array to generate a message for.
     * @param index The invalid index to generate a message with.
     * @return A formatted {@code String} for use as a detail message for an 
     *         {@code IndexOutOfBoundsException}.
     */
    public static String outOfBoundsMessage(Object[] array, int index) {
        Objects.requireNonNull(array, "Invalid null array!");
        return "Index = " + index + ", Capacity = " + array.length;
    }
    
    /**
     * Returns a {@code Set} containing all keys that match a given value in the
     * given map. If there are no keys that match the value, then the returned 
     * set will be empty.
     * 
     * @param <K> The type of key that is stored in the given map and type of 
     *        object stored in the returned set.
     * @param <V> The type of value stored in the given map.
     * @param map The map whose keys to retrieve from the given value.
     * @param value The value that the desired keys are mapped to.
     * @return A {@code Set} containing all keys that match a given value in the
     *         given map.
     */
    public static <K, V> Set<K> getKeysByValue(Map<K, V> map, V value) {
        return map.entrySet().stream()
                  .filter(entry -> Objects.equals(entry.getValue(), value))
                  .map(Map.Entry :: getKey)
                  .collect(Collectors.toSet()); // Terminal operation
    }
    
    /**
     * Returns the first key in the given map that matches the given value, or 
     * {@code null} if there is no such key.
     * 
     * @param <K> The type of key that is stored in the given map and the type
     *        of the return value.
     * @param <V> The type of value stored in the given map.
     * @param map The map whose key to attempt to match to the given value.
     * @param value The value that the desired key is mapped to.
     * @return The first key in the given map that matches the given value, or
     *         {@code null} if there is no such key.
     */
    public static <K, V> K getKeyByValue(Map<K, V> map, V value) {
        return map.entrySet().stream()
                  .filter(entry -> Objects.equals(entry.getValue(), value))
                  .map(Map.Entry :: getKey)
                  .findFirst() // Terminal operation
                  .orElse(null);
    }
    
}
