import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import rucafe.*;
import rucafe.MenuItem;
import rucafe.Order;
import rucafe.StoreOrders;
import rucafe.DataManager;

public class CurrentOrderController {

    @FXML private ListView<String> orderItemsListView;
    @FXML private TextField subtotalField;
    @FXML private TextField salesTaxField;
    @FXML private TextField totalField;
    @FXML private Button btnRemoveSelected, btnPlaceOrder, btnMainMenu;

    private ObservableList<String> orderItemsDisplay = FXCollections.observableArrayList();

    /**
     * Initialize the controller after FXML is loaded
     */
    @FXML
    public void initialize() {
        // Set up ListView
        orderItemsListView.setItems(orderItemsDisplay);

        // Set up button actions
        btnRemoveSelected.setOnAction(e -> handleRemoveSelected());
        btnPlaceOrder.setOnAction(e -> handlePlaceOrder());
        btnMainMenu.setOnAction(e -> handleMainMenu());

        // Load current order
        refreshOrder();
    }

    /**
     * Refresh the order display from DataManager
     */
    private void refreshOrder() {
        orderItemsDisplay.clear();

        Order currentOrder = DataManager.getInstance().getCurrentOrder();

        for (MenuItem item : currentOrder.getItems()) {
            orderItemsDisplay.add(item.toString());
        }

        updateTotals();
    }

    /**
     * Update subtotal, sales tax, and total fields
     */
    private void updateTotals() {
        Order currentOrder = DataManager.getInstance().getCurrentOrder();

        double subtotal = currentOrder.getSubtotal();
        double salesTax = currentOrder.getTax();
        double total = currentOrder.getTotal();

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

        Order currentOrder = DataManager.getInstance().getCurrentOrder();
        MenuItem itemToRemove = currentOrder.getItems().get(selectedIndex);
        currentOrder.remove(itemToRemove);

        refreshOrder();
        showAlert("Item removed from order.");
    }

    /**
     * Handle Place Order button - finalizes and places the order
     */
    @FXML
    private void handlePlaceOrder() {
        Order currentOrder = DataManager.getInstance().getCurrentOrder();

        if (currentOrder.getItems().isEmpty()) {
            showAlert("Your order is empty! Please add items before placing an order.");
            return;
        }

        // Confirm order placement
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Place Order");
        confirmation.setHeaderText("Confirm Order");
        confirmation.setContentText("Place order with total: $" + totalField.getText() + "?");

        if (confirmation.showAndWait().get() == ButtonType.OK) {
            DataManager.getInstance().placeCurrentOrder();

            showAlert("Order placed successfully!\nOrder #" + currentOrder.getOrderNumber() +
                    "\nTotal: $" + String.format("%.2f", currentOrder.getTotal()));

            refreshOrder();
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