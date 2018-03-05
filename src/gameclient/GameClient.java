
package gameclient;

import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import simulation.Simulation;

public class GameClient extends Application {

    
    @Override
    public void start(Stage primaryStage) throws Exception{
        
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Start Chat");
        dialog.setHeaderText(null); 
        dialog.setContentText("Enter a handle:");
        Optional<String> result = dialog.showAndWait();
        FXMLLobbyController controller = new FXMLLobbyController();
        //result.ifPresent(name -> controller.setName(name));
        String name = result.get();
       
        Parent root = FXMLLoader.load(getClass().getResource("FXMLLobby.fxml"));
        
        GameGateway gateway = new GameGateway();
        
        Scene scene = new Scene(root);
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("Game Lobby");
        primaryStage.setOnCloseRequest(event->System.exit(0));
        primaryStage.show();
        controller.setName(name, gateway.getClientNumber());
    }

    public void startGame(Stage primaryStage){
        GamePane root = new GamePane();
        Simulation sim = new Simulation(300, 250, 2, 2);
        root.setShapes(sim.setUpShapes());
        
        Scene scene = new Scene(root, 300, 250);
        root.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case DOWN:
                    sim.moveInner(0, 3);
                    break;
                case UP:
                    sim.moveInner(0, -3);
                    break;
                case LEFT:
                    sim.moveInner(-3, 0);
                    break;
                case RIGHT:
                    sim.moveInner(3, 0);
                    break;
            }
        });
        root.requestFocus(); 

        primaryStage.setTitle("Game Physics");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest((event)->System.exit(0));
        primaryStage.show();
        
        new Thread(new CheckPositions(sim));
        // This is the main animation thread
        
        new Thread(() -> {
            while (true) {
                sim.evolve(1.0);
                Platform.runLater(()->sim.updateShapes());
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {

                }
            }
        }).start();
    }
    
    public static void main(String[] args) {
        launch(args);// TODO code application logic here
    }
    
}

class CheckPositions implements Runnable,game.GameConstants{
    private Simulation sim;
    private GameGateway gateway;
    
    public CheckPositions(Simulation sim){
        this.sim=sim;
        gateway = new GameGateway();
    }
    
    public void run(){
        while(true){
            sim.updateShapes();
            try{
                Thread.sleep(250);
            }catch (Exception ex) {}
        }
    }
}
