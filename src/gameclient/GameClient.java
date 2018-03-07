
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
        } else {
            opponentNumber = 1;
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
        
        
        System.out.println("My number is: " + playerNumber + "\nMy opponent's number is: " + opponentNumber);
        new Thread(() -> {
//            int ready = 0;
//            while(true) {
                
//                try {
//                    int newReady = gateway.getReady();                    
//                    //if the opponent ready status is not what it was...
//                    if(newReady != ready) {
//                        System.out.println("Different");
//                        //if they're newly ready...
//                        if(newReady == 1) {
//                            controller.setReady(opponentNumber);
//                        } else { controller.setNotReady(opponentNumber); }
//                    }
//                    ready = newReady;
//                } catch(Exception ex) {ex.printStackTrace(); }
//            }
            try {
                int opponentReady = 0;
                while(opponentReady == 0) {
                    opponentReady = gateway.getReady();

                    Thread.sleep(250);
                }
                controller.setReady(opponentNumber);
                Thread.sleep(3000);

                while(gameStarted == false) {
                    gameStarted = gateway.getGameSignal();
                    Thread.sleep(250);
                }
                controller.quit();
                startGame(primaryStage);
            } catch (Exception ex) { ex.printStackTrace(); }
        }).start();
    }
     
    
    public void startGame(Stage primaryStage){
        GamePane root = new GamePane();
        Simulation sim = new Simulation(300, 250, 2, 2);
        int speed = 10;
        
        Scene scene = new Scene(root, 300, 250);
        root.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case DOWN:
                    sim.moveInner(0, speed);
                    break;
                case UP:
                    sim.moveInner(0, -1 * speed);
                    break;
                case LEFT:
                    sim.moveInner(-1 * speed, 0);
                    break;
                case RIGHT:
                    sim.moveInner(speed, 0);
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


