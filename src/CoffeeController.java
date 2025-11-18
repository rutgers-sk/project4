/*
@author Yao Sheng Huang
 */
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;

public class CoffeeController {

    @FXML private CheckBox cbSweetCream, cbMocha, cbFrenchVanilla, cbCaramel, cbIrishCream;
    @FXML private ComboBox<String> cupSizeCombo;
    @FXML private ComboBox<Integer> quantityCombo;
    @FXML private TextField priceField;
    @FXML private Button btnAddToOrder, btnMainMenu;

    // Price constants
    private static final double SHORT_PRICE = 2.39;
    private static final double SIZE_INCREMENT = 0.60;
    private static final double ADDIN_PRICE = 0.25;

    /**
     * Initialize the controller after FXML is loaded
     */
    @FXML
    public void initialize() {
        // Set up cup size combo box
        cupSizeCombo.getItems().addAll("Short", "Tall", "Grande", "Venti");
        cupSizeCombo.setValue("Short");

        // Set up quantity combo box (1-10)
        for (int i = 1; i <= 10; i++) {
            quantityCombo.getItems().add(i);
        }
        quantityCombo.setValue(1);

        // Add listeners for dynamic price update
        cbSweetCream.setOnAction(e -> updatePrice());
        cbMocha.setOnAction(e -> updatePrice());
        cbFrenchVanilla.setOnAction(e -> updatePrice());
        cbCaramel.setOnAction(e -> updatePrice());
        cbIrishCream.setOnAction(e -> updatePrice());
        cupSizeCombo.setOnAction(e -> updatePrice());
        quantityCombo.setOnAction(e -> updatePrice());

        // Set up button actions
        btnAddToOrder.setOnAction(e -> handleAddToOrder());
        btnMainMenu.setOnAction(e -> handleMainMenu());

        // Initial price calculation
        updatePrice();
    }

    /**
     * Calculate and update the price based on current selections
     */
    private void updatePrice() {
        // Get base price based on cup size
        double basePrice = SHORT_PRICE;
        String size = cupSizeCombo.getValue();

        if (size.equals("Tall")) {
            basePrice += SIZE_INCREMENT;
        } else if (size.equals("Grande")) {
            basePrice += SIZE_INCREMENT * 2;
        } else if (size.equals("Venti")) {
            basePrice += SIZE_INCREMENT * 3;
        }

        // Add price for add-ins
        int addInCount = 0;
        if (cbSweetCream.isSelected()) addInCount++;
        if (cbMocha.isSelected()) addInCount++;
        if (cbFrenchVanilla.isSelected()) addInCount++;
        if (cbCaramel.isSelected()) addInCount++;
        if (cbIrishCream.isSelected()) addInCount++;

        double addInTotal = addInCount * ADDIN_PRICE;

        // Calculate total with quantity
        int quantity = quantityCombo.getValue();
        double total = (basePrice + addInTotal) * quantity;

        // Update price field
        priceField.setText(String.format("%.2f", total));
    }

    /**
     * Handle the Add to Order button click
     */
    @FXML
    private void handleAddToOrder() {
        String size = cupSizeCombo.getValue();
        int quantity = quantityCombo.getValue();

        // Build add-ins list
        StringBuilder addIns = new StringBuilder();
        if (cbSweetCream.isSelected()) addIns.append("Sweet Cream, ");
        if (cbMocha.isSelected()) addIns.append("Mocha, ");
        if (cbFrenchVanilla.isSelected()) addIns.append("French Vanilla, ");
        if (cbCaramel.isSelected()) addIns.append("Caramel, ");
        if (cbIrishCream.isSelected()) addIns.append("Irish Cream, ");

        String addInsList = addIns.length() > 0 ?
                addIns.substring(0, addIns.length() - 2) : "None";

        // TODO: Create Coffee object and add to Order
        // This will depend on Person A's implementation

        showAlert("Coffee added to order!\n" +
                "Size: " + size + "\n" +
                "Add-ins: " + addInsList + "\n" +
                "Quantity: " + quantity + "\n" +
                "Price: $" + priceField.getText());

        // Reset form
        resetForm();
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
     * Reset form to default selections
     */
    private void resetForm() {
        cbSweetCream.setSelected(false);
        cbMocha.setSelected(false);
        cbFrenchVanilla.setSelected(false);
        cbCaramel.setSelected(false);
        cbIrishCream.setSelected(false);
        cupSizeCombo.setValue("Short");
        quantityCombo.setValue(1);
        updatePrice();
    }

    /**
     * Show alert dialog
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Coffee Order");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}