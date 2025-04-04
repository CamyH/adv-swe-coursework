package interfaces;

import java.util.LinkedList;
import java.util.List;

/**
 * Subject superclass used to keep track of Observers
 *
 * @author Fraser Holman
 */
public abstract class Subject {

    private List<Observer> registeredObservers = new LinkedList<>();

    /**
     * Method used to register observers
     *
     * @param obs The observer to be added to the list of observers
     */
    public void registerObserver(Observer obs) {
        registeredObservers.add(obs);
    }

    /**
     * Method used to remove observers
     *
     * @param obs The observer to be removed from the list of observers
     */
    public void removeObserver(Observer obs) {
        registeredObservers.remove(obs);
    }

    /**
     * Method used to notify observers
     */
    public synchronized void notifyObservers() {
        for(Observer obs : registeredObservers) {
            obs.update();
        }
    }
}
