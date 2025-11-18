package rucafe;

/**
 * Abstract base class for all menu items in the cafe.
 * Each menu item has a quantity and can compute its own price.
 *
 * @author Sunghyun Kim
 */
public abstract class MenuItem {

    private int quantity;

    /**
     * Default constructor that sets the quantity to 1.
     */
    public MenuItem() {
        this(1);
    }

    /**
     * Constructor that sets the quantity to the given value.
     *
     * @param quantity number of units for this menu item
     */
    public MenuItem(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the quantity for this menu item.
     *
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity for this menu item.
     *
     * @param quantity the new quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Computes the total price for this menu item,
     * usually based on the base price, options, and quantity.
     *
     * @return the total price for this menu item
     */
    public abstract double price();

    /**
     * Returns a string representation of this menu item.
     *
     * @return string representation (subclasses will typically override)
     */
    @Override
    public String toString() {
        return "MenuItem (quantity=" + quantity + ")";
    }
}