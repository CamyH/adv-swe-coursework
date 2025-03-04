package interfaces;

import exceptions.InvalidOrderException;

/**
 * @author Fraser Holman
 *
 * Interface used for the Order and Item List Classes
 */

public interface EntityList<T, R> {
    Boolean add(T item) throws InvalidOrderException;

    Boolean remove(R ID);
}
