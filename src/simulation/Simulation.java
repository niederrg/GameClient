package simulation;

import gameclient.GameGateway;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javafx.scene.control.Label;
import javafx.scene.shape.Shape;
import physics.*;

public class Simulation {
    private BoxDisplay outer;
    private BallDisplay ball;
    private DiamondDisplay player1;
    private DiamondDisplay player2;
    private Lock lock;
    private GameGateway gateway;
    private Label scoreBox;
    
    public Simulation(int width,int height,int dX,int dY, GameGateway gateway)
    {
        this.gateway = gateway;
        outer = new BoxDisplay(0, 0, width, height);
        ball = new BallDisplay(width/2,height/2);
        player1 = new DiamondDisplay(width - 60,height - 40, 20);
        player2 = new DiamondDisplay(60, 40, 20);
        lock = new ReentrantLock();
        scoreBox = new Label("P1: 0     P2: 0");
    }
    
    public GameGateway getGateway(){ return gateway; }
    
    public List<Shape> setUpShapes()
    {
        ArrayList<Shape> newShapes = new ArrayList<Shape>();
        newShapes.add(outer.getShape());
        newShapes.add(player1.getShape());
        newShapes.add(ball.getShape(gateway.getBallX(),gateway.getBallY()));
        newShapes.add(player2.getShape());
        //newShapes.add(scoreBox.getShape());
        return newShapes;
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
