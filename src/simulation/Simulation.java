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
    
    public Simulation(int width,int height,int dX,int dY)
    {
        gateway = new GameGateway();
        outer = new BoxDisplay(0, 0, width, height);
        ball = new BallDisplay(width/2,height/2);
        player1 = new DiamondDisplay(width - 60,height - 40, 20);
        player2 = new DiamondDisplay(60, 40, 20);
        lock = new ReentrantLock();
        scoreBox = new Label("P1: 0     P2: 0");
    }
    
    public GameGateway getGateway(){ return gateway; }
    
    public void evolve(double time)
    {
        gateway.evolve(time);
//        lock.lock();
//        Ray newLoc = player1.bounceRay(ball.getRay(), time);
//        if(newLoc != null)
//            ball.setRay(newLoc);
//        else {
//            newLoc = outer.bounceRay(ball.getRay(), time);
//            if(newLoc != null)
//                ball.setRay(newLoc);
//            else
//                ball.move(time);
//        } 
//        lock.unlock();
    }
    
    public void moveInner(int deltaX,int deltaY)
    {
        gateway.sendMovement(deltaX, deltaY);
        lock.unlock();
    }
    
    public List<Shape> setUpShapes()
    {
        ArrayList<Shape> newShapes = new ArrayList<Shape>();
        newShapes.add(outer.getShape());
        newShapes.add(player1.getShape());
        newShapes.add(ball.getShape(gateway));
        newShapes.add(player2.getShape());
        newShapes.add(scoreBox.getShape());
        return newShapes;
    }
    
    public void updateShapes()
    {
        ArrayList<Point> pointsP1;
        ArrayList<Point> pointsP2;
        //getPoints
        pointsP1 = gateway.getPoints(false);
        pointsP2 = gateway.getPoints(true);
        player2.updateShape(pointsP2);
        player1.updateShape(pointsP1);
        ball.updateShape(gateway);
    }
}
