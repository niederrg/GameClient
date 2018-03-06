/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclient;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author henry
 */
public class FXMLLobbyController implements Initializable {

    @FXML
    private Label lobbyLabel; 
    @FXML
    private Label player1;
    @FXML
    private Label player2;
    
    @FXML
    private Rectangle player1Color;
    @FXML
    private Rectangle player2Color;
    
    @FXML
    private Button player1Ready;
    @FXML
    private Button player2Ready;
    
    
    private int p1Ready = 0;
    private int p2Ready = 0;
    private int playerNumber;
    
    public void setPlayerNumber(int i) {
        playerNumber = i;
    }
    
    
    
    public void hideReady(int i) {
        if(i == 1) {
            player2Ready.setVisible(false);
        } else {
            player1Ready.setVisible(false);
        }
    }
    
    @FXML
    private void setP1Ready() {
        if(p1Ready == 1) {
            p1Ready = 0;
            player1Ready.setText("Ready");
            player1Color.setFill(Color.WHITE);
        } else {
            p1Ready = 1;
            player1Ready.setText("Unready");
            player1Color.setFill(Color.BLUE);
        }  
        if(playerNumber == 1) {
            GameClient.gateway.sendReady(p1Ready);
        }
    }
    
    @FXML
    private void setP2Ready() {
        if(p2Ready == 1) {
            p2Ready = 0;
            player2Ready.setText("Ready");
            player2Color.setFill(Color.WHITE);
        } else {
            p2Ready = 1;
            player2Ready.setText("Unready");
            player2Color.setFill(Color.RED);
        }
        if(playerNumber == 2) {
            GameClient.gateway.sendReady(p2Ready);
        }
    }
    
    public void setNotReady(int playerNumber) {
        if(playerNumber == 1) {
            p1Ready = 0;
            player1Ready.setText("Ready");
            player1Color.setFill(Color.WHITE);
        } else {
            p2Ready = 0;
            player2Ready.setText("Ready");
            player2Color.setFill(Color.WHITE);
        }
    }
    
    public void setReady(int playerNumber) {
        if(playerNumber == 1) {
            p1Ready = 1;
            player1Ready.setText("Unready");
            player1Color.setFill(Color.BLUE);
        } else {
            p2Ready = 1;
            player2Ready.setText("Unready");
            player2Color.setFill(Color.RED);
        }
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            //TODO
    }
    
    private void startGame(){
        
    }
}
