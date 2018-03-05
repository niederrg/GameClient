/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private Label player1Name;
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
       
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
