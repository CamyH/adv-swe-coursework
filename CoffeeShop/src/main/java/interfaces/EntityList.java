package interfaces;

import exceptions.DuplicateOrderException;
import exceptions.InvalidOrderException;

/**
 * @author Fraser Holman
 *
 * Interface used for the Order and Item List Classes
 */

public interface EntityList<T, R> {
    boolean add(T item) throws InvalidOrderException, DuplicateOrderException;

    boolean remove(R ID) throws InvalidOrderException;
}
