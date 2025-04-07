package interfaces;

/**
 * Observer Interface used to replace the built-in Observer interface
 * The built-in Observer interface is redundant
 *
 * @author Fraser Holman
 */

public interface Observer {
    /**
     * Notify a subject of a change
     */
    void update();
}
