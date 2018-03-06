
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
    private String playerName;
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Start Chat");
        dialog.setHeaderText(null); 
        dialog.setContentText("Enter a handle:");
        Optional<String> result = dialog.showAndWait();
        
        String playerName = "Player 1";
        result.ifPresent(name -> setName(name));
        //String name = result.get();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLLobby.fxml"));
        Parent root = (Parent)loader.load();
        GameGateway gateway = new GameGateway();
        Scene scene = new Scene(root);
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("Game Lobby");
        primaryStage.setOnCloseRequest(event->System.exit(0));
        primaryStage.show();
        FXMLLobbyController controller = (FXMLLobbyController) loader.getController();
        controller.setName(playerName, gateway.getClientNumber());
    }
    
    private void setName(String name) {
        this.playerName = name;
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
