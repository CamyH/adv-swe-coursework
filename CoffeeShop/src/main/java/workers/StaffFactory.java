package workers;

/**
 * Factory Design Pattern
 *
 * Class used to create staff objects
 *
 * @author Fraser Holman
 */
public class StaffFactory {

    /**
     *
     * @param type The staff type (Barista etc)
     * @param name The given name to the staff
     * @param experience The experience level of the staff member
     * @return Returns either a Staff Object or a null object
     */
    public static Staff getStaff(String type, String name, int experience) {
        if (type.equalsIgnoreCase("Barista")) {
            return new Barista(name, experience);
        }

        return null;
    }

}
