package interfaces;

import exceptions.InvalidOrderException;

/**
 * @author Fraser Holman
 *
 * Interface used for the Order and Item List Classes
 */

public interface EntityList<T, R> {
    boolean add(T item) throws InvalidOrderException;

    boolean remove(R ID);
}
