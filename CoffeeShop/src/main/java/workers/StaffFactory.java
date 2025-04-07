package workers;

import interfaces.INotificationService;

/**
 * Factory Design Pattern
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
    public static Staff<?> getStaff(String type, String name, int experience, INotificationService notificationService) {
        return switch (type.toLowerCase()) {
            case "waiter" -> new Waiter(name, experience, notificationService);
            case "barista" -> new Barista(name, experience);
            case "chef" -> new Chef(name, experience);
            default -> null;
        };
    }

}
