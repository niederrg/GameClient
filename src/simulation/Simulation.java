package simulation;

import gameclient.GameGateway;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javafx.scene.shape.Shape;
import physics.*;

public class Simulation {
    private Box outer;
    private Ball ball;
    private DiamondDisplay player1;
    private DiamondDisplay player2;
    private Lock lock;
    private GameGateway gateway;
    
    public Simulation(int width,int height,int dX,int dY)
    {
        gateway = new GameGateway();
        outer = new Box(0,0,width,height,false);
        ball = new Ball(width/2,height/2,dX,dY);
        player1 = new DiamondDisplay(width - 60,height - 40, 20);
        player2 = new DiamondDisplay(60, 40, 20);
        lock = new ReentrantLock();
    }
    
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
        newShapes.add(ball.getShape());
        newShapes.add(player2.getShape());
        return newShapes;
    }
    
    public void updateShapes()
    {
        ArrayList<Point> points;
        //getPoints
        points = gateway.getPoints();
        player2.updateShape(points);
        player1.updateShape(points);
        ball.updateShape();
    }
}
