import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class CurrentOrderController {

    @FXML private ListView<String> orderItemsListView;
    @FXML private TextField subtotalField;
    @FXML private TextField salesTaxField;
    @FXML private TextField totalField;
    @FXML private Button btnRemoveSelected, btnPlaceOrder, btnMainMenu;

    // Tax rate for New Jersey
    private static final double TAX_RATE = 0.06625;

    // TODO: Replace with Person A's Order class
    // Mock current order items for testing
    private ObservableList<String> currentOrderItems = FXCollections.observableArrayList();

    // Mock prices for items (in real implementation, get from Order objects)
    private List<Double> itemPrices = new ArrayList<>();

    /**
     * Initialize the controller after FXML is loaded
     */
    @FXML
    public void initialize() {
        // Load current order from Person A's Order class
        loadCurrentOrder();

        // Set up ListView
        orderItemsListView.setItems(currentOrderItems);

        // Set up button actions
        btnRemoveSelected.setOnAction(e -> handleRemoveSelected());
        btnPlaceOrder.setOnAction(e -> handlePlaceOrder());
        btnMainMenu.setOnAction(e -> handleMainMenu());

        // Update totals
        updateTotals();
    }

    /**
     * Load the current order from Person A's Order class
     * TODO: Replace with actual Order implementation
     */
    private void loadCurrentOrder() {
        // Mock data for testing - Replace with Person A's Order.getItems()
        currentOrderItems.add("sugar(1)");
        currentOrderItems.add("glazed(2)");
        currentOrderItems.add("strawberry frosted(3)");

        // Mock prices - Replace with actual item.price() calls
        itemPrices.add(1.99);  // sugar x1
        itemPrices.add(3.98);  // glazed x2
        itemPrices.add(5.97);  // strawberry frosted x3
    }

    /**
     * Update subtotal, sales tax, and total fields
     */
    private void updateTotals() {
        // Calculate subtotal
        double subtotal = 0.0;
        for (double price : itemPrices) {
            subtotal += price;
        }

        // Calculate sales tax
        double salesTax = subtotal * TAX_RATE;

        // Calculate total
        double total = subtotal + salesTax;

        // Update text fields
        subtotalField.setText(String.format("%.2f", subtotal));
        salesTaxField.setText(String.format("%.2f", salesTax));
        totalField.setText(String.format("%.2f", total));
    }

    /**
     * Handle Remove Selected button - removes selected item from order
     */
    @FXML
    private void handleRemoveSelected() {
        int selectedIndex = orderItemsListView.getSelectionModel().getSelectedIndex();

        if (selectedIndex == -1) {
            showAlert("Please select an item to remove!");
            return;
        }

        // Remove from lists
        currentOrderItems.remove(selectedIndex);
        itemPrices.remove(selectedIndex);

        // TODO: Remove from Person A's Order class
        // Order.removeItem(selectedIndex);

        // Update totals
        updateTotals();

        showAlert("Item removed from order.");
    }

    /**
     * Handle Place Order button - finalizes and places the order
     */
    @FXML
    private void handlePlaceOrder() {
        if (currentOrderItems.isEmpty()) {
            showAlert("Your order is empty! Please add items before placing an order.");
            return;
        }

        // Confirm order placement
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Place Order");
        confirmation.setHeaderText("Confirm Order");
        confirmation.setContentText("Place order with total: $" + totalField.getText() + "?");

        if (confirmation.showAndWait().get() == ButtonType.OK) {
            // TODO: Add order to Person A's StoreOrders class
            // StoreOrders.addOrder(currentOrder);

            showAlert("Order placed successfully!\nTotal: $" + totalField.getText());

            // Clear current order
            currentOrderItems.clear();
            itemPrices.clear();

            // TODO: Clear Person A's Order class
            // Order.clear();

            updateTotals();
        }
    }

    /**
     * Navigate back to main menu
     */
    @FXML
    private void handleMainMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnMainMenu.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("RU Cafe - Main Menu");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error loading main menu!");
        }
    }

    /**
     * Show alert dialog
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Current Order");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}