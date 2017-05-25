
import java.net.URL;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PickAStudentV2 extends Application {
    
    private UserInterface gui;
    
    @Override
    public void start(Stage primaryStage) {
        
        BorderPane root = new BorderPane();
        
        Scene scene = new Scene(root);
        
        gui = new UserInterface(root, primaryStage);
        
        primaryStage.setTitle("Pick a Student v2.0");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
        
        gui.start();
        
    }
    
    //--------------------------------------------------------------------------
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
