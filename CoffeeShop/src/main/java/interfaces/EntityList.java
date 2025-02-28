package interfaces;

/**
 * @author Fraser Holman
 *
 * Interface used for the Order and Item List Classes
 */

public interface EntityList<T, R> {
    void add(T item);

    void remove(R ID);
}
