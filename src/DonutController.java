/*
@author Yao Sheng Huang
 */
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Map;

public class DonutController {

    @FXML private ComboBox<String> typeBox;
    @FXML private ListView<String> flavorList;
    @FXML private ListView<String> selectedList;
    @FXML private Spinner<Integer> qtySpinner;
    @FXML private TextField subTotalField;
    @FXML private Button btnAddOne, btnAddAll, btnRemove, btnAddToOrder, btnMainMenu;
    @FXML private ImageView donutImage;  // Added for dynamic image

    // Price constants
    private static final double YEAST_PRICE = 1.99;
    private static final double CAKE_PRICE = 2.19;
    private static final double HOLE_PRICE = 0.39;
    private static final double SEASONAL_PRICE = 2.49;

    // Flavor options for each donut type
    private final Map<String, String[]> flavorsByType = new HashMap<>();

    // Selected donuts with quantities
    private final ObservableList<String> selectedDonuts = FXCollections.observableArrayList();

    /**
     * Initialize the controller after FXML is loaded
     */
    @FXML
    public void initialize() {
        // Set up flavor options for each type
        flavorsByType.put("Yeast Donuts", new String[]{
                "Cinnamon Sugar", "Chocolate Frosted", "Strawberry Frosted",
                "Sugar", "Lemon Filled", "Boston Creme"
        });
        flavorsByType.put("Cake Donuts", new String[]{
                "Blueberry", "Old Fashioned", "Chocolate"
        });
        flavorsByType.put("Donut Holes", new String[]{
                "Plain", "Glazed", "Powdered Sugar"
        });
        flavorsByType.put("Seasonal", new String[]{
                "Spooky Donuts", "Pumpkin Spice Donuts"
        });

        // Populate donut type combo box
        typeBox.getItems().addAll("Yeast Donuts", "Cake Donuts", "Donut Holes", "Seasonal");
        typeBox.setValue("Yeast Donuts");

        // Set up quantity spinner (1-100)
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        qtySpinner.setValueFactory(valueFactory);

        // Bind selected list to observable list
        selectedList.setItems(selectedDonuts);

        // Add listeners
        typeBox.setOnAction(e -> updateFlavors());
        btnAddOne.setOnAction(e -> addSelectedFlavor());
        btnAddAll.setOnAction(e -> addAllFlavors());
        btnRemove.setOnAction(e -> removeSelected());
        btnAddToOrder.setOnAction(e -> handleAddToOrder());
        btnMainMenu.setOnAction(e -> handleMainMenu());

        // Initialize with yeast donuts
        updateFlavors();
        updateSubtotal();
    }

    /**
     * Update the flavor list and image based on selected donut type
     */
    private void updateFlavors() {
        String selectedType = typeBox.getValue();
        String[] flavors = flavorsByType.get(selectedType);

        flavorList.getItems().clear();
        if (flavors != null) {
            flavorList.getItems().addAll(flavors);
        }

        // Update donut image dynamically
        updateDonutImage(selectedType);
    }

    /**
     * Update the donut image based on selected type
     */
    private void updateDonutImage(String type) {
        String imageName = "";
        if (type.contains("Yeast")) {
            imageName = "yeast.jpg";
        } else if (type.contains("Cake")) {
            imageName = "cake.jpg";
        } else if (type.contains("Hole")) {
            imageName = "holes.jpg";
        } else if (type.contains("Seasonal")) {
            imageName = "seasonal.jpg";
        }

        try {
            Image image = new Image(getClass().getResourceAsStream(imageName));
            donutImage.setImage(image);
        } catch (Exception e) {
            System.err.println("Could not load image: " + imageName);
            e.printStackTrace();
        }
    }

    /**
     * Add selected flavor with quantity to the selected list
     */
    private void addSelectedFlavor() {
        String selectedFlavor = flavorList.getSelectionModel().getSelectedItem();
        if (selectedFlavor == null) {
            showAlert("Please select a flavor to add!");
            return;
        }

        int quantity = qtySpinner.getValue();
        String donutType = typeBox.getValue();

        // Format: "flavor (type) x quantity"
        String item = selectedFlavor + " (" + donutType + ") x" + quantity;
        selectedDonuts.add(item);

        updateSubtotal();
    }

    /**
     * Add all flavors with current quantity to selected list
     */
    private void addAllFlavors() {
        int quantity = qtySpinner.getValue();
        String donutType = typeBox.getValue();

        for (String flavor : flavorList.getItems()) {
            String item = flavor + " (" + donutType + ") x" + quantity;
            selectedDonuts.add(item);
        }

        updateSubtotal();
    }

    /**
     * Remove selected item from the selected list
     */
    private void removeSelected() {
        String selected = selectedList.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Please select an item to remove!");
            return;
        }

        selectedDonuts.remove(selected);
        updateSubtotal();
    }

    /**
     * Calculate and update the subtotal
     */
    private void updateSubtotal() {
        double subtotal = 0.0;

        for (String item : selectedDonuts) {
            String type = extractType(item);
            int quantity = extractQuantity(item);

            double pricePerDonut = getPriceForType(type);
            subtotal += pricePerDonut * quantity;
        }

        subTotalField.setText(String.format("%.2f", subtotal));
    }

    /**
     * Extract donut type from item string
     */
    private String extractType(String item) {
        int start = item.indexOf("(") + 1;
        int end = item.indexOf(")");
        if (start > 0 && end > start) {
            return item.substring(start, end);
        }
        return "";
    }

    /**
     * Extract quantity from item string
     */
    private int extractQuantity(String item) {
        int xIndex = item.indexOf("x");
        if (xIndex != -1) {
            try {
                String qtyStr = item.substring(xIndex + 1).trim();
                return Integer.parseInt(qtyStr);
            } catch (NumberFormatException e) {
                return 1;
            }
        }
        return 1;
    }

    /**
     * Get price based on donut type
     */
    private double getPriceForType(String type) {
        if (type.contains("Yeast")) return YEAST_PRICE;
        if (type.contains("Cake")) return CAKE_PRICE;
        if (type.contains("Hole")) return HOLE_PRICE;
        if (type.contains("Seasonal")) return SEASONAL_PRICE;
        return 0.0;
    }

    /**
     * Handle Add to Order button - adds selected donuts to main order
     */
    @FXML
    private void handleAddToOrder() {
        if (selectedDonuts.isEmpty()) {
            showAlert("Please add some donuts to your selection first!");
            return;
        }

        // TODO: Create Donut objects and add to Order
        // This will depend on Person A's implementation

        showAlert("Donuts added to order!\nSubtotal: $" + subTotalField.getText());

        // Clear the selection
        selectedDonuts.clear();
        updateSubtotal();
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
        alert.setTitle("Donut Order");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}