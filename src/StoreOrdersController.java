/*
@author Yao Sheng Huang
*/
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StoreOrdersController {

    @FXML private ComboBox<String> orderNumberCombo;
    @FXML private TextField totalAmountField;
    @FXML private ListView<String> orderDetailsListView;
    @FXML private Button btnCancelOrder, btnExportOrders, btnMainMenu;

    // TODO: Replace with Person A's StoreOrders class
    // This is a mock implementation for testing
    private List<MockOrder> allOrders = new ArrayList<>();

    /**
     * Mock Order class for testing (replace with Person A's Order class)
     */
    private static class MockOrder {
        int orderNumber;
        double total;
        List<String> items;

        MockOrder(int orderNumber, double total, List<String> items) {
            this.orderNumber = orderNumber;
            this.total = total;
            this.items = items;
        }
    }

    /**
     * Initialize the controller after FXML is loaded
     */
    @FXML
    public void initialize() {
        // Load orders from Person A's StoreOrders class
        loadOrders();

        // Set up order number combo box listener
        orderNumberCombo.setOnAction(e -> displaySelectedOrder());

        // Set up button actions
        btnCancelOrder.setOnAction(e -> handleCancelOrder());
        btnExportOrders.setOnAction(e -> handleExportOrders());
        btnMainMenu.setOnAction(e -> handleMainMenu());

        // Display first order if available
        if (!orderNumberCombo.getItems().isEmpty()) {
            orderNumberCombo.setValue(orderNumberCombo.getItems().get(0));
            displaySelectedOrder();
        }
    }

    /**
     * Load all orders from StoreOrders
     * TODO: Replace with Person A's implementation
     */
    private void loadOrders() {
        // Mock data for testing - Replace with Person A's StoreOrders.getAllOrders()
        allOrders.add(new MockOrder(1, 6.37, List.of("sugar(1)", "glazed(2)")));
        allOrders.add(new MockOrder(2, 15.59, List.of("Salmon Sandwich (Wheat Bread, Lettuce, Tomato) x1")));
        allOrders.add(new MockOrder(3, 2.99, List.of("Tall Coffee (no add-ins) x1")));

        // Populate order number combo box
        ObservableList<String> orderNumbers = FXCollections.observableArrayList();
        for (MockOrder order : allOrders) {
            orderNumbers.add(String.valueOf(order.orderNumber));
        }
        orderNumberCombo.setItems(orderNumbers);
    }

    /**
     * Display the selected order's details
     */
    private void displaySelectedOrder() {
        String selectedOrderNum = orderNumberCombo.getValue();
        if (selectedOrderNum == null) return;

        int orderNum = Integer.parseInt(selectedOrderNum);

        // Find the order
        MockOrder selectedOrder = null;
        for (MockOrder order : allOrders) {
            if (order.orderNumber == orderNum) {
                selectedOrder = order;
                break;
            }
        }

        if (selectedOrder != null) {
            // Display total amount
            totalAmountField.setText(String.format("%.2f", selectedOrder.total));

            // Display order items
            ObservableList<String> items = FXCollections.observableArrayList(selectedOrder.items);
            orderDetailsListView.setItems(items);
        }
    }

    /**
     * Handle Cancel Order button - removes the selected order
     */
    @FXML
    private void handleCancelOrder() {
        String selectedOrderNum = orderNumberCombo.getValue();
        if (selectedOrderNum == null) {
            showAlert("Please select an order to cancel!");
            return;
        }

        // Confirm cancellation
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Cancel Order");
        confirmation.setHeaderText("Are you sure you want to cancel this order?");
        confirmation.setContentText("Order #" + selectedOrderNum);

        if (confirmation.showAndWait().get() == ButtonType.OK) {
            int orderNum = Integer.parseInt(selectedOrderNum);

            // Remove from list
            allOrders.removeIf(order -> order.orderNumber == orderNum);

            // TODO: Remove from Person A's StoreOrders class
            // StoreOrders.removeOrder(orderNum);

            // Refresh display
            loadOrders();

            if (!orderNumberCombo.getItems().isEmpty()) {
                orderNumberCombo.setValue(orderNumberCombo.getItems().get(0));
                displaySelectedOrder();
            } else {
                totalAmountField.setText("0.00");
                orderDetailsListView.getItems().clear();
            }

            showAlert("Order #" + orderNum + " has been cancelled.");
        }
    }

    /**
     * Handle Export Orders button - saves all orders to a text file
     */
    @FXML
    private void handleExportOrders() {
        if (allOrders.isEmpty()) {
            showAlert("No orders to export!");
            return;
        }

        // Open file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Orders");
        fileChooser.setInitialFileName("orders.txt");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );

        File file = fileChooser.showSaveDialog(btnExportOrders.getScene().getWindow());

        if (file != null) {
            try {
                exportOrdersToFile(file);
                showAlert("Orders exported successfully to:\n" + file.getAbsolutePath());
            } catch (IOException e) {
                showAlert("Error exporting orders:\n" + e.getMessage());
            }
        }
    }

    /**
     * Export all orders to a text file
     */
    private void exportOrdersToFile(File file) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            for (MockOrder order : allOrders) {
                writer.write("Order #" + order.orderNumber + "\n");
                writer.write("Total: $" + String.format("%.2f", order.total) + "\n");
                writer.write("Items:\n");
                for (String item : order.items) {
                    writer.write("  - " + item + "\n");
                }
                writer.write("\n");
            }
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
        alert.setTitle("Store Orders");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}