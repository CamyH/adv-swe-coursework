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
    public static Staff getStaff(String type, String name, double experience) {
        switch (type.toLowerCase()) {
            case "waiter":
                return new Waiter(name, experience);
            case "barista":
                return new Barista(name, experience);
            case "chef":
                return new Chef(name, experience);
            default:
                return null;
        }

        //return null;
    }

}
