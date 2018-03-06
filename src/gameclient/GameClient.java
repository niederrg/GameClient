
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
    public static String opponentName;
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Start Chat");
        dialog.setHeaderText(null); 
        dialog.setContentText("Enter a handle:");
        Optional<String> result = dialog.showAndWait();
        
        playerName = "Player 1";
        playerName = result.get();
        
        GameGateway gateway = new GameGateway();
        gateway.sendName(playerName);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLLobby.fxml"));
        Parent root = (Parent)loader.load();
        
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
        new Thread(new CheckScore(sim));
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

class CheckScore implements Runnable {
    private int p1score;
    private int p2score;
    private GameGateway gateway;
    
    public CheckScore(Simulation sim){
        p1score = 0;
        p2score = 0;
        gateway = sim.getGateway();
    }
    
    public void run(){
        while(true){
            p1score = gateway.getScore(1);
            p2score = gateway.getScore(2);
            //SEND THESE NUMBERS TO WHEREVER WE ARE SHOWING THEM SOMEHOW HERE!
            try {
                Thread.sleep(250);
            } catch (Exception ex) {}
        }
    }
}

class CheckPositions implements Runnable,game.GameConstants{
    private Simulation sim;
    
    public CheckPositions(Simulation sim){
        this.sim=sim;
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

class LookForOpponent implements Runnable, game.GameConstants {
    GameGateway gateway;
    
    public LookForOpponent(GameGateway gateway) {
        this.gateway = gateway;
    }
    
    public void run() {
        while(GameClient.opponentName.isEmpty()) {
            try {
                String name = gateway.getName(); 
                if(name.isEmpty() == false) {
                    GameClient.opponentName = gateway.getName();
                }
                Thread.sleep(250);
            } catch (Exception ex) {}
        }
    }
}
