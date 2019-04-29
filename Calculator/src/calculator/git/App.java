package calculator.git;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Creates the scene and sets the stage.
 * 
 * @author Magdalena Zych
 *
 */
public class App extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("layout.fxml"));
		Controller controller = new Controller();
		loader.setController(controller);
		Parent root = loader.load();
		
		Scene scene = new Scene(root);
		
        stage.setScene(scene);
		stage.setTitle("Calculator");
		stage.setResizable(false);
        stage.show();
	}
}