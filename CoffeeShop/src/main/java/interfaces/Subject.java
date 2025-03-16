package interfaces;

/**
 * Subject Interface used to keep track of Observers
 *
 * @author Fraser Holman
 */
public interface Subject {

    /**
     * Adds registered observers
     *
     * @param obs The observer to be registered
     */
    void registerObserver(Observer obs);

    /**
     * Removes registered observers
     *
     * @param obs The observer to be removed
     */
    void removeObserver(Observer obs);

    /**
     * Notifies all observers of any updates
     */
    void notifyObservers();

}
