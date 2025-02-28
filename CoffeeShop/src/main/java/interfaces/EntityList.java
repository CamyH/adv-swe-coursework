package interfaces;

import exceptions.DuplicateItemIDException;

/**
 * @author Fraser Holman
 *
 * Interface used for the Order and Item List Classes
 */

public interface EntityList<T, R> {
    void add(T item) throws DuplicateItemIDException;

    void remove(R ID);
}
