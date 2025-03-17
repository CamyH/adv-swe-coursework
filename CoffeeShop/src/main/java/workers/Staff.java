package workers;

import interfaces.Observer;

import java.util.UUID;

/**
 * Staff superclass
 *
 * This class uses a Factory Design Pattern
 *
 * Used by different staffs to ensure correct implementation
 *
 * @author Fraser Holman
 */
public abstract class Staff extends Thread implements Observer {
    /** Name of the staff member */
    private String name;

    protected double defaultDelay = 2000.0;

    /**
     * Value between 0 and 1 that will represent how quickly they can complete orders
     * value is between 0 and 1 - lower values are more experienced
     * */
    private double experience;

    /** Staff members ID */
    private UUID ID;

    /**
     * Constructor to instantiate a new staff member
     *
     * @param name Name of the staff member
     * @param experience Experience level of the staff member
     */
    public Staff(String name, double experience) {
        this.name = name;
        this.experience = experience;
        this.ID = UUID.randomUUID();
    }

    /**
     * Returns workers name
     *
     * @return String representing workers name
     */
    public String getWorkerName() {
        return name;
    }

    /**
     * Returns the experience level of the worker
     *
     * value is between 0 and 1 - lower values are more experienced
     *
     * @return Double representing the experience level of the worker
     */
    public double getExperience() {
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
    public void setExperience(double experience) {
        this.experience = experience;
    }

    /**
     * Method to adjust the workers speed of operation. Used to adjust speed of simulation
     *
     * @param defaultDelay double representing the delay in completing orders
     */
    public void setDefaultDelay(double defaultDelay) {
        this.defaultDelay = defaultDelay;
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

}
