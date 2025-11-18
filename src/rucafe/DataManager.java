package rucafe;

/**
 * Singleton class to manage shared data across all controllers.
 * Provides access to the current order being built and all store orders.
 *
 * @author Yao Sheng Huang
 */
public class DataManager {

    private static DataManager instance;
    private Order order;
    private StoreOrders storeOrders;
    private int next;

    /**
     * Private constructor for singleton pattern.
     */
    private DataManager() {
        this.storeOrders = new StoreOrders();
        this.next = 1;
        this.order = new Order(next);
    }

    /**
     * Gets the singleton instance of DataManager.
     *
     * @return the DataManager instance
     */
    public static DataManager getInstance() {
        if (instance == null) instance = new DataManager();
        return instance;
    }

    /**
     * Gets the current order being built.
     *
     * @return current order
     */
    public Order getCurrentOrder() { return order; }

    /**
     * Gets the store orders collection.
     *
     * @return store orders
     */
    public StoreOrders getStoreOrders() {
        return storeOrders;
    }

    /**
     * Places the current order and creates a new empty order.
     */
    public void placeCurrentOrder() {
        if (order.getItems().isEmpty()) return;
        storeOrders.add(order);
        next++;
        order = new Order(next);
    }

    /**
     * Clears the current order without placing it.
     */
    public void clearCurrentOrder() {
        order = new Order(next);
    }
}