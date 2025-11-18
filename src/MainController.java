/*
@author Yao Sheng Huang
 */
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    private void switchScene(MouseEvent e, String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot load page");
            alert.setContentText("Error loading " + fxml + "\n" + ex.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void openDonut(MouseEvent e) {
        switchScene(e, "DonutView.fxml");
    }

    @FXML
    private void openSandwich(MouseEvent e) {
        System.out.println("Sandwich clicked!"); // Debug line
        switchScene(e, "SandwichView.fxml");
    }

    @FXML
    private void openCoffee(MouseEvent e) {
        switchScene(e, "CoffeeView.fxml");
    }

    @FXML
    private void openCurrentOrder(MouseEvent e) {
        switchScene(e, "CurrentOrderView.fxml");
    }

    @FXML
    private void openStoreOrders(MouseEvent e) {
        switchScene(e, "StoreOrdersView.fxml");
    }
}