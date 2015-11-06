package utilities.collections;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * The {@code CollectionUtilities} class contains utility methods related to the
 * {@link Collection} hierarchy.
 * 
 * @author Oliver Abdulrahim
 * @see java.util.Collection
 */
public final class CollectionUtilities {

    /**
     * Don't let anyone instantiate this class.
     */
    private CollectionUtilities() {
        throw new InstantiationError();
    }
    
    /**
     * Returns a random element from the given {@code List}.
     * 
     * @param <T> The type of the element contained in the collection, and the
     *            type of the object to return.
     * @param list The {@code List} to select a random element from.
     * @return A random element from the given {@code List}.
     */
    public static <T> T randomElementFrom(List<? extends T> list) {
        int index = ThreadLocalRandom.current().nextInt(list.size());
        return list.get(index);
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
        boolean containsNullObject = c.stream().anyMatch(Objects :: isNull);
        if (containsNullObject) {
            throw new NullPointerException("Invald null element in collection");
        }
    }

    /**
     * Return a standard error message intended for use as a detail message
     * for any thrown {@code IndexOutOfBoundsException}.
     * 
     * @param c The {@code List} to generate a message for.
     * @param index The invalid index to generate a message with.
     * @return A formatted {@code String} for use as a detail message for an 
     *         {@code IndexOutOfBoundsException}.
     */
    public static String outOfBoundsMessage(List<?> c, int index) {
        return "Index = " + index + ", Capacity = " + c.size();
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
                  .collect(Collectors.toSet());
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
                  .findFirst()
                  .orElse(null);
    }
    
}
