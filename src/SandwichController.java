/*
@author Yao Sheng Huang
 */
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;

public class SandwichController {

    @FXML private RadioButton rbBagel, rbWheatBread, rbSourdough;
    @FXML private RadioButton rbBeef, rbSalmon, rbChicken;
    @FXML private CheckBox cbLettuce, cbTomato, cbCheese, cbOnion;
    @FXML private TextField priceField;
    @FXML private ComboBox<Integer> quantityCombo;
    @FXML private Button btnAddToOrder, btnMainMenu;

    private ToggleGroup breadGroup;
    private ToggleGroup proteinGroup;

    // Price constants
    private static final double BEEF_PRICE = 12.99;
    private static final double CHICKEN_PRICE = 10.99;
    private static final double SALMON_PRICE = 14.99;
    private static final double CHEESE_PRICE = 1.00;
    private static final double VEGGIE_PRICE = 0.30;

    @FXML
    public void initialize() {
        // Create toggle groups programmatically
        breadGroup = new ToggleGroup();
        rbBagel.setToggleGroup(breadGroup);
        rbWheatBread.setToggleGroup(breadGroup);
        rbSourdough.setToggleGroup(breadGroup);

        proteinGroup = new ToggleGroup();
        rbBeef.setToggleGroup(proteinGroup);
        rbSalmon.setToggleGroup(proteinGroup);
        rbChicken.setToggleGroup(proteinGroup);

        // Set up quantity combo box (1-10)
        for (int i = 1; i <= 10; i++) {
            quantityCombo.getItems().add(i);
        }
        quantityCombo.setValue(1);

        // Add listeners to all controls for dynamic price update
        rbBagel.setOnAction(e -> updatePrice());
        rbWheatBread.setOnAction(e -> updatePrice());
        rbSourdough.setOnAction(e -> updatePrice());

        rbBeef.setOnAction(e -> updatePrice());
        rbSalmon.setOnAction(e -> updatePrice());
        rbChicken.setOnAction(e -> updatePrice());

        cbLettuce.setOnAction(e -> updatePrice());
        cbTomato.setOnAction(e -> updatePrice());
        cbCheese.setOnAction(e -> updatePrice());
        cbOnion.setOnAction(e -> updatePrice());

        quantityCombo.setOnAction(e -> updatePrice());

        // Set up button actions
        btnAddToOrder.setOnAction(e -> handleAddToOrder());
        btnMainMenu.setOnAction(e -> handleMainMenu());

        // Initial price calculation
        updatePrice();
    }

    private void updatePrice() {
        double basePrice = 0.0;

        // Get protein price
        if (rbBeef.isSelected()) {
            basePrice = BEEF_PRICE;
        } else if (rbChicken.isSelected()) {
            basePrice = CHICKEN_PRICE;
        } else if (rbSalmon.isSelected()) {
            basePrice = SALMON_PRICE;
        }

        // Add add-ons
        double addOnPrice = 0.0;
        if (cbLettuce.isSelected()) addOnPrice += VEGGIE_PRICE;
        if (cbTomato.isSelected()) addOnPrice += VEGGIE_PRICE;
        if (cbOnion.isSelected()) addOnPrice += VEGGIE_PRICE;
        if (cbCheese.isSelected()) addOnPrice += CHEESE_PRICE;

        // Calculate total with quantity
        int quantity = quantityCombo.getValue();
        double total = (basePrice + addOnPrice) * quantity;

        // Update price field
        priceField.setText(String.format("$%.2f", total));
    }

    @FXML
    private void handleAddToOrder() {
        String bread = "";
        if (rbBagel.isSelected()) bread = "Bagel";
        else if (rbWheatBread.isSelected()) bread = "Wheat Bread";
        else if (rbSourdough.isSelected()) bread = "Sourdough";

        String protein = "";
        if (rbBeef.isSelected()) protein = "Beef";
        else if (rbChicken.isSelected()) protein = "Chicken";
        else if (rbSalmon.isSelected()) protein = "Salmon";

        if (bread.isEmpty() || protein.isEmpty()) {
            showAlert("Please select both bread and protein!");
            return;
        }

        showAlert("Sandwich added to order!\nBread: " + bread +
                "\nProtein: " + protein +
                "\nPrice: " + priceField.getText());

        resetForm();
    }

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

    private void resetForm() {
        rbWheatBread.setSelected(true);
        rbSalmon.setSelected(true);
        cbLettuce.setSelected(false);
        cbTomato.setSelected(false);
        cbCheese.setSelected(false);
        cbOnion.setSelected(false);
        quantityCombo.setValue(1);
        updatePrice();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sandwich Order");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}