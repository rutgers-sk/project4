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
import java.io.IOException;
import rucafe.MenuItem;
import rucafe.Order;
import rucafe.StoreOrders;
import rucafe.DataManager;

public class StoreOrdersController {

    @FXML private ComboBox<String> comboBox;
    @FXML private TextField total;
    @FXML private ListView<String> listView;
    @FXML private Button cancel, export, back;

    /**
     * Initialize the controller after FXML is loaded
     */
    @FXML
    public void initialize() {
        loadOrders();
        comboBox.setOnAction(e -> displaySelectedOrder());
        cancel.setOnAction(e -> handleCancelOrder());
        export.setOnAction(e -> handleExportOrders());
        back.setOnAction(e -> handleMainMenu());

        if (!comboBox.getItems().isEmpty()) {
            comboBox.setValue(comboBox.getItems().get(0));
            displaySelectedOrder();
        }
    }

    /**
     * Load all orders from StoreOrders
     */
    private void loadOrders() {
        StoreOrders storeOrders = DataManager.getInstance().getStoreOrders();
        ObservableList<String> orderNumbers = FXCollections.observableArrayList();
        for (Order order : storeOrders.getOrders()) {
            orderNumbers.add(String.valueOf(order.getOrderNumber()));
        }
        comboBox.setItems(orderNumbers);
    }

    /**
     * Display the selected order's details
     */
    private void displaySelectedOrder() {
        String s = comboBox.getValue();
        if (s == null) return;

        int orderNum = Integer.parseInt(s);
        StoreOrders storeOrders = DataManager.getInstance().getStoreOrders();

        Order selectedOrder = null;
        for (Order order : storeOrders.getOrders()) {
            if (order.getOrderNumber() == orderNum) {
                selectedOrder = order;
                break;
            }
        }

        if (selectedOrder != null) {
            total.setText(String.format("%.2f", selectedOrder.getTotal()));

            ObservableList<String> items = FXCollections.observableArrayList();
            for (MenuItem item : selectedOrder.getItems()) {
                items.add(item.toString());
            }
            listView.setItems(items);
        }
    }

    /**
     * Handle Cancel Order button - removes the selected order
     */
    @FXML
    private void handleCancelOrder() {
        String s = comboBox.getValue();
        if (s == null) {
            showAlert("Please select an order to cancel!");
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Cancel Order");
        confirmation.setHeaderText("Are you sure you want to cancel this order?");
        confirmation.setContentText("Order #" + s);

        if (confirmation.showAndWait().get() == ButtonType.OK) {
            int orderNum = Integer.parseInt(s);
            StoreOrders storeOrders = DataManager.getInstance().getStoreOrders();

            Order toRemove = null;
            for (Order order : storeOrders.getOrders()) {
                if (order.getOrderNumber() == orderNum) {
                    toRemove = order;
                    break;
                }
            }

            if (toRemove != null) {
                storeOrders.remove(toRemove);
            }

            loadOrders();

            if (!comboBox.getItems().isEmpty()) {
                comboBox.setValue(comboBox.getItems().get(0));
                displaySelectedOrder();
            } else {
                total.setText("0.00");
                listView.getItems().clear();
            }

            showAlert("Order #" + orderNum + " has been cancelled.");
        }
    }

    /**
     * Handle Export Orders button - saves all orders to a text file
     */
    @FXML
    private void handleExportOrders() {
        StoreOrders storeOrders = DataManager.getInstance().getStoreOrders();

        if (storeOrders.getOrders().isEmpty()) {
            showAlert("No orders to export!");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Orders");
        fileChooser.setInitialFileName("orders.txt");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );

        File file = fileChooser.showSaveDialog(export.getScene().getWindow());

        if (file != null) {
            try {
                storeOrders.exportToFile(file);
                showAlert("Orders exported successfully to:\n" + file.getAbsolutePath());
            } catch (IOException e) {
                showAlert("Error exporting orders:\n" + e.getMessage());
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
            Stage stage = (Stage) back.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("RU Cafe - Main Menu");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error loading main menu!");
        }
    }

    /**
     * Show alert dialog
     *
     * @param message the message to display
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Store Orders");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}