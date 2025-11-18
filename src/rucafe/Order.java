package rucafe;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single customer order, containing multiple menu items.
 * Provides methods to add/remove items and compute subtotal, tax, and total.
 *
 * @author Sunghyun Kim
 */
public class Order implements Customizable {

    private static final double TAX_RATE = 0.06625; // 예: NJ sales tax, 지침에 맞게 수정

    private int orderNumber;
    private final List<MenuItem> items;

    /**
     * Constructs an Order with the given order number.
     *
     * @param orderNumber unique order number
     */
    public Order(int orderNumber) {
        this.orderNumber = orderNumber;
        this.items = new ArrayList<>();
    }

    /**
     * Gets the order number.
     *
     * @return order number
     */
    public int getOrderNumber() {
        return orderNumber;
    }

    /**
     * Sets the order number.
     *
     * @param orderNumber new order number
     */
    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**
     * Gets an unmodifiable list of items in this order.
     *
     * @return list of menu items
     */
    public List<MenuItem> getItems() {
        return List.copyOf(items);
    }

    /**
     * Adds a menu item to this order.
     *
     * @param obj should be a MenuItem
     * @return true if added, false otherwise
     */
    @Override
    public boolean add(Object obj) {
        if (!(obj instanceof MenuItem)) {
            return false;
        }
        return items.add((MenuItem) obj);
    }

    /**
     * Removes a menu item from this order.
     *
     * @param obj should be a MenuItem
     * @return true if removed, false otherwise
     */
    @Override
    public boolean remove(Object obj) {
        if (!(obj instanceof MenuItem)) {
            return false;
        }
        return items.remove((MenuItem) obj);
    }

    /**
     * Computes the subtotal for all menu items in this order.
     *
     * @return subtotal amount
     */
    public double getSubtotal() {
        double sum = 0;
        for (MenuItem item : items) {
            sum += item.price();
        }
        return sum;
    }

    /**
     * Computes the tax amount based on subtotal.
     *
     * @return tax amount
     */
    public double getTax() {
        return getSubtotal() * TAX_RATE;
    }

    /**
     * Computes the total amount (subtotal + tax).
     *
     * @return total amount
     */
    public double getTotal() {
        return getSubtotal() + getTax();
    }

    @Override
    public String toString() {
        return "Order #" + orderNumber + " (" + items.size() + " items)";
    }
}