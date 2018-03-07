
package gameclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import physics.Point;

public class GameGateway implements game.GameConstants {
    private PrintWriter outputToServer;
    private BufferedReader inputFromServer;
    
    public GameGateway () {
        try {
            // Create a socket to connect to the server
            Socket socket = new Socket("localhost", 8000);

            // Create an output stream to send data to the server
            outputToServer = new PrintWriter(socket.getOutputStream());

            // Create an input stream to read data from the server
            inputFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException ex) {
            //Platform.runLater(() -> textArea.appendText("Exception in gateway constructor: " + ex.toString() + "\n"));
        } 
    }
    
    public void sendMovement(int dX,int dY){
        outputToServer.println(SEND_DXDY);
        outputToServer.println(dX);
        outputToServer.println(dY);
        outputToServer.flush();
    }
    
    public ArrayList<Point> getPoints(boolean opponent){
        outputToServer.println(GET_POINTS);
        outputToServer.println(opponent);
        outputToServer.flush();
        ArrayList<Point> points = new ArrayList<>();
        try{
            for (int i=0; i<4; i++){
                double x = Double.parseDouble(inputFromServer.readLine());
                double y = Double.parseDouble(inputFromServer.readLine());
                points.add(new Point(x,y));
            }
        } catch(Exception ex) { ex.printStackTrace(); }
        
        return points;
    }
    
    
    
    public void sendReady(int b) {
        outputToServer.println(SEND_READY);
        outputToServer.println(b);
        outputToServer.flush();
    }
    
    public int getReady() {
        outputToServer.println(GET_READY);
        outputToServer.flush();
        
        int ready = 0;
        try {
            ready = Integer.parseInt(inputFromServer.readLine());
            
        } catch (Exception ex) {ex.printStackTrace(); }
        return ready;
    }
    
    public void evolve(double time){
        outputToServer.println(EVOLVE);
        outputToServer.println(time);
        outputToServer.flush();
    }
     
    public int getClientNumber(){
        outputToServer.println(GET_CLIENT_NUM);
        outputToServer.flush();
        try {
            return Integer.parseInt(inputFromServer.readLine());
        } catch (Exception ex) { ex.printStackTrace(); }
        return 0;
    }
    
    public Boolean getGameSignal() {
        outputToServer.println(START_GAME_SIGNAL);
        outputToServer.flush();
        try {
            int ready = Integer.parseInt(inputFromServer.readLine());
            if(ready == 1) {
                return true;
            }
        } catch(Exception ex) { ex.printStackTrace(); }
        return false;
    }
    
    public double getBallX() {
        outputToServer.println(GET_BALL_X);
        outputToServer.flush();
        
        try{
            return Double.parseDouble(inputFromServer.readLine());
        } catch(Exception ex) { ex.printStackTrace(); }
        return 0;
    }
    
    public double getBallY() {
        outputToServer.println(GET_BALL_Y);
        outputToServer.flush();
        
        try{
            return Double.parseDouble(inputFromServer.readLine());
        } catch(Exception ex) { ex.printStackTrace(); }
        return 0;
    }
    
    public int getScore(int playerNum){
        outputToServer.println(GET_SCORE);
        outputToServer.println(playerNum);
        outputToServer.flush();
        try{
            return (int)Double.parseDouble(inputFromServer.readLine());
        }catch (Exception ex) { ex.printStackTrace(); }
        return 0;
    }
    
    public void endGame(int winner){
        outputToServer.println(END_GAME);
        outputToServer.println(winner);
        outputToServer.flush();
    }
}
