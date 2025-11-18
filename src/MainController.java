import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;

public class MainController {
    private void go(MouseEvent e, String fxml) throws Exception {
        FXMLLoader l = new FXMLLoader(getClass().getResource(fxml));
        Stage s = (Stage)((Node)e.getSource()).getScene().getWindow();
        s.setScene(new Scene(l.load(), 1000, 700));
        s.show();
    }
    private void highlight(MouseEvent e) {
        StackPane pane = (StackPane) e.getSource();
        pane.setStyle("-fx-background-color:#4a90ff; -fx-background-radius:16;");
    }

    private void reset(MouseEvent e) {
        StackPane pane = (StackPane) e.getSource();
        pane.setStyle("-fx-background-color:black; -fx-background-radius:16;");
    }
    public void openDonut(MouseEvent e) throws Exception { go(e, "DonutView.fxml"); }
    public void openSandwich(MouseEvent e) throws Exception { go(e, "SandwichView.fxml"); }
    public void openCoffee(MouseEvent e) throws Exception { go(e, "CoffeeView.fxml"); }
    public void openCurrentOrder(MouseEvent e) throws Exception { go(e, "CurrentOrderView.fxml"); }
    public void openStoreOrders(MouseEvent e) throws Exception { go(e, "StoreOrdersView.fxml"); }
}
