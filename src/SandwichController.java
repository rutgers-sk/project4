/**
 * Controller for the Sandwich ordering view.
 * Handles sandwich customization including bread, protein, and add-on selections.
 *
 * @author Yao Sheng Huang
 */
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import rucafe.*;

public class SandwichController {

    @FXML private RadioButton rbBagel, rbWheatBread, rbSourdough;
    @FXML private RadioButton rbBeef, rbSalmon, rbChicken;
    @FXML private CheckBox cbLettuce, cbTomato, cbCheese, cbOnion;
    @FXML private TextField priceField;
    @FXML private ComboBox<Integer> quantityCombo;
    @FXML private Button btnAddToOrder, btnMainMenu;

    private ToggleGroup breadGroup;
    private ToggleGroup proteinGroup;

    private static final double BEEF_PRICE = 12.99;
    private static final double CHICKEN_PRICE = 10.99;
    private static final double SALMON_PRICE = 14.99;
    private static final double CHEESE_PRICE = 1.00;
    private static final double VEGGIE_PRICE = 0.30;

    /**
     * Initialize the controller after FXML is loaded.
     * Sets up toggle groups, combo boxes, listeners, and initial price display.
     */
    @FXML
    public void initialize() {
        breadGroup = new ToggleGroup();
        rbBagel.setToggleGroup(breadGroup);
        rbWheatBread.setToggleGroup(breadGroup);
        rbSourdough.setToggleGroup(breadGroup);

        proteinGroup = new ToggleGroup();
        rbBeef.setToggleGroup(proteinGroup);
        rbSalmon.setToggleGroup(proteinGroup);
        rbChicken.setToggleGroup(proteinGroup);

        for (int i = 1; i <= 10; i++) {
            quantityCombo.getItems().add(i);
        }
        quantityCombo.setValue(1);

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

        btnAddToOrder.setOnAction(e -> handleAddToOrder());
        btnMainMenu.setOnAction(e -> handleMainMenu());

        updatePrice();
    }

    /**
     * Calculate and update the price based on current selections.
     * Price is calculated as (base price + add-ons) * quantity.
     */
    private void updatePrice() {
        double basePrice = 0.0;

        if (rbBeef.isSelected()) {
            basePrice = BEEF_PRICE;
        } else if (rbChicken.isSelected()) {
            basePrice = CHICKEN_PRICE;
        } else if (rbSalmon.isSelected()) {
            basePrice = SALMON_PRICE;
        }

        double addOnPrice = 0.0;
        if (cbLettuce.isSelected()) addOnPrice += VEGGIE_PRICE;
        if (cbTomato.isSelected()) addOnPrice += VEGGIE_PRICE;
        if (cbOnion.isSelected()) addOnPrice += VEGGIE_PRICE;
        if (cbCheese.isSelected()) addOnPrice += CHEESE_PRICE;

        int quantity = quantityCombo.getValue();
        double total = (basePrice + addOnPrice) * quantity;

        priceField.setText(String.format("$%.2f", total));
    }

    /**
     * Handle the Add to Order button click.
     * Creates a Sandwich object with selected options and adds it to the current order.
     */
    @FXML
    private void handleAddToOrder() {
        Bread bread = null;
        if (rbBagel.isSelected()) bread = Bread.BAGEL;
        else if (rbWheatBread.isSelected()) bread = Bread.WHEAT;
        else if (rbSourdough.isSelected()) bread = Bread.SOURDOUGH;

        Protein protein = null;
        if (rbBeef.isSelected()) protein = Protein.BEEF;
        else if (rbChicken.isSelected()) protein = Protein.CHICKEN;
        else if (rbSalmon.isSelected()) protein = Protein.SALMON;

        if (bread == null || protein == null) {
            showAlert("Please select both bread and protein!");
            return;
        }

        int quantity = quantityCombo.getValue();
        Sandwich sandwich = new Sandwich(bread, protein, quantity);

        if (cbLettuce.isSelected()) sandwich.add(SandwichExtra.LETTUCE);
        if (cbTomato.isSelected()) sandwich.add(SandwichExtra.TOMATOES);
        if (cbOnion.isSelected()) sandwich.add(SandwichExtra.ONIONS);
        if (cbCheese.isSelected()) sandwich.add(SandwichExtra.CHEESE);

        DataManager.getInstance().getCurrentOrder().add(sandwich);

        showAlert("Sandwich added to order!\n" + sandwich.toString() +
                "\nPrice: $" + String.format("%.2f", sandwich.price()));

        resetForm();
    }

    /**
     * Navigate back to main menu.
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
     * Reset form to default selections.
     */
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

    /**
     * Show alert dialog.
     *
     * @param message the message to display
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sandwich Order");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}