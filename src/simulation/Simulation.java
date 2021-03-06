package simulation;

import gameclient.GameGateway;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import physics.*;

public class Simulation {
    private BoxDisplay outer;
    private BallDisplay ball;
    private DiamondDisplay player1;
    private DiamondDisplay player2;
    private BoxDisplay score;
    private Lock lock;
    private GameGateway gateway;
    private Label scoreBox;
    
    public Simulation(int width,int height,int dX,int dY, GameGateway gateway)
    {
        this.gateway = gateway;
        outer = new BoxDisplay(0, 0, width, height);
        ball = new BallDisplay(width/2-40,height/2);
        player1 = new DiamondDisplay(width - 30,height - 30, 20);
        player1.getShape().setFill(Color.BLUE);
        player2 = new DiamondDisplay(60, 40, 20);
        player2.getShape().setFill(Color.RED);
        scoreBox = new Label("P1: 0     P2: 0");
        score = new BoxDisplay(width/2-5,height/2-5,10,10);
        score.getShape().setFill(Color.GREEN);
        lock = new ReentrantLock();
    }
    
    public GameGateway getGateway(){ return gateway; }
    
    public List<Shape> setUpShapes()
    {
        ArrayList<Shape> newShapes = new ArrayList<Shape>();
        newShapes.add(outer.getShape());
        newShapes.add(player1.getShape());
        newShapes.add(ball.getShape(gateway.getBallX(),gateway.getBallY()));
        newShapes.add(player2.getShape());
        newShapes.add(score.getShape());
        //newShapes.add(scoreBox.getClip());
        return newShapes;
    }
    
    public Label getScoreBox(){
        return scoreBox;
    }
    
    public void setScoreBox(String newLabel){
        scoreBox.setText(newLabel);
    }
    
    public void updateShapes()
    {
        ArrayList<Point> pointsP1;
        ArrayList<Point> pointsP2;
        //getPoints
        pointsP1 = gateway.getPoints(1);
        pointsP2 = gateway.getPoints(2);
        player2.updateShape(pointsP2);
        player1.updateShape(pointsP1);
        ball.updateShape(gateway);
    }
}
