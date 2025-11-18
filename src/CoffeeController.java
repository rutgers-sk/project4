/**
 * Controller for the Coffee ordering view.
 * Handles coffee customization including size selection and add-ins.
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

public class CoffeeController {

    @FXML private CheckBox cbSweetCream, cbMocha, cbFrenchVanilla, cbCaramel, cbIrishCream;
    @FXML private ComboBox<String> cupSizeCombo;
    @FXML private ComboBox<Integer> quantityCombo;
    @FXML private TextField priceField;
    @FXML private Button btnAddToOrder, btnMainMenu;

    private static final double SHORT_PRICE = 2.39;
    private static final double SIZE_INCREMENT = 0.60;
    private static final double ADDIN_PRICE = 0.25;

    /**
     * Initialize the controller after FXML is loaded.
     * Sets up combo boxes, listeners, and initial price display.
     */
    @FXML
    public void initialize() {
        cupSizeCombo.getItems().addAll("Short", "Tall", "Grande", "Venti");
        cupSizeCombo.setValue("Short");
        for (int i = 1; i <= 10; i++) quantityCombo.getItems().add(i);
        quantityCombo.setValue(1);
        cbSweetCream.setOnAction(e -> updatePrice());
        cbMocha.setOnAction(e -> updatePrice());
        cbFrenchVanilla.setOnAction(e -> updatePrice());
        cbCaramel.setOnAction(e -> updatePrice());
        cbIrishCream.setOnAction(e -> updatePrice());
        cupSizeCombo.setOnAction(e -> updatePrice());
        quantityCombo.setOnAction(e -> updatePrice());
        btnAddToOrder.setOnAction(e -> handleAddToOrder());
        btnMainMenu.setOnAction(e -> handleMainMenu());
        updatePrice();
    }

    /**
     * Calculate and update the price based on current selections.
     * Price is calculated as (base price + add-ins) * quantity.
     */
    private void updatePrice() {
        double basePrice = SHORT_PRICE;
        String size = cupSizeCombo.getValue();

        if (size.equals("Tall")) {
            basePrice += SIZE_INCREMENT;
        } else if (size.equals("Grande")) {
            basePrice += SIZE_INCREMENT * 2;
        } else if (size.equals("Venti")) {
            basePrice += SIZE_INCREMENT * 3;
        }
        int addInCount = 0;
        if (cbSweetCream.isSelected()) addInCount++;
        if (cbMocha.isSelected()) addInCount++;
        if (cbFrenchVanilla.isSelected()) addInCount++;
        if (cbCaramel.isSelected()) addInCount++;
        if (cbIrishCream.isSelected()) addInCount++;

        double addInTotal = addInCount * ADDIN_PRICE;
        int quantity = quantityCombo.getValue();
        double total = (basePrice + addInTotal) * quantity;
        priceField.setText(String.format("%.2f", total));
    }

    /**
     * Handle the Add to Order button click.
     * Creates a Coffee object with selected options and adds it to the current order.
     */
    @FXML
    private void handleAddToOrder() {
        CupSize size = null;
        String sizeStr = cupSizeCombo.getValue();
        if (sizeStr.equals("Short")) size = CupSize.SHORT;
        else if (sizeStr.equals("Tall")) size = CupSize.TALL;
        else if (sizeStr.equals("Grande")) size = CupSize.GRANDE;
        else if (sizeStr.equals("Venti")) size = CupSize.VENTI;

        int quantity = quantityCombo.getValue();
        Coffee coffee = new Coffee(size, quantity);

        if (cbSweetCream.isSelected()) coffee.add(CoffeeAddIn.WHIPPED_CREAM);
        if (cbMocha.isSelected()) coffee.add(CoffeeAddIn.MOCHA);
        if (cbFrenchVanilla.isSelected()) coffee.add(CoffeeAddIn.VANILLA);
        if (cbCaramel.isSelected()) coffee.add(CoffeeAddIn.CARAMEL);
        if (cbIrishCream.isSelected()) coffee.add(CoffeeAddIn.TWO_PERCENT_MILK);

        DataManager.getInstance().getCurrentOrder().add(coffee);

        showAlert("Coffee added to order!\n" + coffee.toString() +
                "\nPrice: $" + String.format("%.2f", coffee.price()));

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
     * Show alert dialog.
     *
     * @param message the message to display
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Coffee Order");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}