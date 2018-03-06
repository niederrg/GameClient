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
    Label player1Name;
    @FXML
    private Label player2Name;
    
    @FXML
    private Rectangle player1Color;
    @FXML
    private Rectangle player2Color;
    
    @FXML
    private Button player1Ready;
    @FXML
    private Button player2Ready;
    
    
    private Boolean p1Ready = false;
    private Boolean p2Ready = false;
    
//    @FXML
//    private void setP1Ready() {
//        if(p1Ready) {
//            p1Ready = false;
//            player1Ready.setText("Ready");
//            player1Color.setFill(Color.WHITE);
//        } else {
//            p1Ready = true;
//            player1Ready.setText("Unready");
//            player1Color.setFill(Color.BLUE);
//        }
//        if(p1Ready && p2Ready) {
//            startGame();
//        }
//        
//    }
//    
//    @FXML
//    private void setP2Ready() {
//        if(p2Ready) {
//            p2Ready = false;
//            player2Ready.setText("Ready");
//            player2Color.setFill(Color.WHITE);
//        } else {
//            p2Ready = true;
//            player2Ready.setText("Unready");
//            player2Color.setFill(Color.RED);
//        }
//        
//        if(p1Ready && p2Ready) {
//            startGame();
//        }
//    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            //TODO
    }    
    
    @FXML
    public void setName(String name, int i) {
        if(i==1) {
            player1Name.setText(name);
            player1Color.setFill(Color.WHITE);
        } else if(i==2) {
            player2Name.setText(name);
            player2Color.setFill(Color.WHITE);
        }
    }
    
    
    
    private void startGame(){
        
    }
}
