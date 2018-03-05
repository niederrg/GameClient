
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
    
    public ArrayList<Point> getPoints(){
        outputToServer.println(GET_POINTS);
        ArrayList<Point> points = new ArrayList<>();
        try{
            int num = Integer.parseInt(inputFromServer.readLine());
            for (int i=0; i<num; i++){
                double x = Double.parseDouble(inputFromServer.readLine());
                double y = Double.parseDouble(inputFromServer.readLine());
                points.add(new Point(x,y));
            }
        } catch(Exception ex) {}
        
        return points;
    }
    
    public int getX(boolean opponent){
        outputToServer.println(GET_X);
        outputToServer.println(opponent);
        outputToServer.flush();
        int X = 0;
        try {
            X = Integer.parseInt(inputFromServer.readLine());
        } catch (IOException ex) {
            //Platform.runLater(() -> textArea.appendText("Error in getCommentCount: " + ex.toString() + "\n"));
            ex.printStackTrace();
        }
        return X;
    }
    
    public void evolve(double time){
        outputToServer.println(EVOLVE);
        outputToServer.println(time);
        outputToServer.flush();
    }
    
     public int getY(boolean opponent){
        outputToServer.println(GET_Y);
        outputToServer.println(opponent);
        outputToServer.flush();
        int Y = 0;
        try {
            Y = Integer.parseInt(inputFromServer.readLine());
        } catch (IOException ex) {
            //Platform.runLater(() -> textArea.appendText("Error in getCommentCount: " + ex.toString() + "\n"));
            ex.printStackTrace();
        }
        return Y;
    }
}
