package rucafe;

/**
 * Interface for adding and removing customizable components
 * (add-ins, extras, items in an order, etc.).
 */
public interface Customizable {

    /**
     * Adds the given object to this customizable item.
     *
     * @param obj object to add
     * @return true if successfully added, false otherwise
     */
    boolean add(Object obj);

    /**
     * Removes the given object from this customizable item.
     *
     * @param obj object to remove
     * @return true if successfully removed, false otherwise
     */
    boolean remove(Object obj);
}