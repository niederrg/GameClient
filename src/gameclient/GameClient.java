
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
    private int playerNumber;
    private int opponentNumber;
    public static GameGateway gateway;
    private static boolean gameStarted = false;
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        
        gateway = new GameGateway();
        
        playerNumber = gateway.getClientNumber();
        if(playerNumber == 1) {
            opponentNumber = 2;
        }
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLLobby.fxml"));
        Parent root = (Parent)loader.load();
        
        Scene scene = new Scene(root);
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("Game Lobby");
        primaryStage.setOnCloseRequest(event->System.exit(0));
        primaryStage.show();
        FXMLLobbyController controller = (FXMLLobbyController) loader.getController();
        controller.setPlayerNumber(playerNumber);
        controller.hideReady(playerNumber);
        
        new Thread(() -> {
            while(gameStarted != true) {
                int ready = 0;
                try {
                    int newReady = gateway.getReady();
                    if(newReady != ready) {
                        if(newReady == 1) {
                            controller.setReady(playerNumber);
                        } else { controller.setNotReady(playerNumber); }
                    }
                    ready = newReady;
                } catch(Exception ex) {ex.printStackTrace(); }
            }
        }).start();
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
        
        new Thread(new CheckPositions(sim)).start();
        new Thread(new CheckScore(sim)).start();
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
            
            if (p1score >=10){
                gateway.endGame(1); //PUT OTHER END GAME CODE HERE
            } else if (p2score >= 10){
                gateway.endGame(2); // PUT OTHER END GAME CODE HERE
            } 
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


