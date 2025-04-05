package workers;

import client.SimUIModel;
import interfaces.Observer;
import order.Order;
import java.util.UUID;

/**
 * Staff superclass
 * This class uses a Factory Design Pattern
 * Used by different staffs to ensure correct implementation
 *
 * @author Fraser Holman
 */
public abstract class Staff<T> extends Thread implements Observer {
    private final String name;
    protected double defaultDelay;

    /**
     * Value between 0 and 1 that will represent how quickly they can complete orders
     * value is between 0 and 1 - lower values are more experienced
     */
    private int experience;

    /** Staff members ID */
    private final UUID ID;

    /**
     * Constructor to instantiate a new staff member
     *
     * @param name Name of the staff member
     * @param experience Experience level of the staff member
     */
    public Staff(String name, int experience) {
        this.name = name;
        this.experience = experience;
        this.ID = UUID.randomUUID();
        setDefaultDelay(SimUIModel.getSimSpeed());
    }

    /**
     * Returns workers name
     *
     * @return String representing workers name
     */
    public synchronized String getWorkerName() {
        return name;
    }

    /**
     * Returns the experience level of the worker
     * value is between 0 and 1 - lower values are more experienced
     *
     * @return Double representing the experience level of the worker
     */
    public int getExperience() {
        return experience;
    }

    /**
     * Method to return the ID of the staff member
     *
     * @return ID of staff member
     */
    public UUID getID() {
        return ID;
    }

    /**
     * Sets a new experience level of the staff member
     *
     * @param experience a Double representing the new experience level of the staff member
     */
    public void setExperience(int experience) {
        this.experience = experience;
    }

    /**
     * Method to adjust the workers speed of operation. Used to adjust speed of simulation
     *
     * @param defaultDelay double representing the delay in completing orders
     */
    public void setDefaultDelay(double defaultDelay) {
        this.defaultDelay = 10000.0 - ( defaultDelay / 100.0 * 10000.0 ) + 100.0;
    }

    /**
     * Method to create the thread
     */
    public abstract void run();

    /**
     * Method to get next order in the queue
     */
    public abstract void getOrders();

    /**
     * Method for staff member when order has been completed
     *
     * @return Boolean representing whether the order completed was a success
     */
    public abstract boolean completeCurrentOrder();

    /**
     * Removes staff once they are done
     *
     * This may be by a button click in the GUI
     */
    public abstract void removeStaff();

    public abstract String getRole();

    public abstract String getCurrentOrderDetails();

    public abstract T getCurrentOrder();

    /**
     * Checks if the current order has a client service
     * @return true if the order has a client service, false otherwise
     */
    public static boolean hasClientService(Order order) {
        return order.getClientService() != null;
    }
}
