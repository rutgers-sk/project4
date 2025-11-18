import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override public void start(Stage stage) throws Exception {
        FXMLLoader fx = new FXMLLoader(getClass().getResource("MainView.fxml"));
        stage.setScene(new Scene(fx.load(), 1000, 700));
        stage.setTitle("RU Café");
        stage.show();
    }
    public static void main(String[] args){ launch(); }
}
