package rucafe;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Stores all placed orders in the cafe.
 * Provides methods to add/remove orders and export them to a text file.
 *
 * @author Sunghyun Kim
 */
public class StoreOrders implements Customizable {

    private final List<Order> orders;

    /**
     * Constructs an empty StoreOrders list.
     */
    public StoreOrders() {
        this.orders = new ArrayList<>();
    }

    /**
     * Gets an unmodifiable list of all stored orders.
     *
     * @return list of orders
     */
    public List<Order> getOrders() {
        return List.copyOf(orders);
    }

    /**
     * Adds an order to the store.
     *
     * @param obj should be an Order
     * @return true if added, false otherwise
     */
    @Override
    public boolean add(Object obj) {
        if (!(obj instanceof Order)) {
            return false;
        }
        return orders.add((Order) obj);
    }

    /**
     * Removes an order from the store.
     *
     * @param obj should be an Order
     * @return true if removed, false otherwise
     */
    @Override
    public boolean remove(Object obj) {
        if (!(obj instanceof Order)) {
            return false;
        }
        return orders.remove((Order) obj);
    }

    /**
     * Exports all orders to the given text file.
     *
     * @param file target file to write to
     * @throws IOException if an I/O error occurs
     */
    public void exportToFile(File file) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            for (Order order : orders) {
                writer.write(order.toString() + System.lineSeparator());
                for (MenuItem item : order.getItems()) {
                    writer.write("\t" + item.toString() + "  $" + String.format("%.2f", item.price()) + System.lineSeparator());
                }
                writer.write(String.format("\tSubtotal: $%.2f%n", order.getSubtotal()));
                writer.write(String.format("\tTax:      $%.2f%n", order.getTax()));
                writer.write(String.format("\tTotal:    $%.2f%n%n", order.getTotal()));
            }
        }
    }
}