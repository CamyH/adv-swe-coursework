package interfaces;

/**
 * @author Fraser Holman
 *
 * Interface used for the Order and Item List Classes
 */

public interface EntityList<T, R> {
    Boolean add(T item);

    Boolean remove(R ID);
}
