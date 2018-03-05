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
    private Box player1;
    private Box player2;
    private Lock lock;
    private GameGateway gateway;
    
    public Simulation(int width,int height,int dX,int dY)
    {
        gateway = new GameGateway();
        outer = new Box(0,0,width,height,false);
        ball = new Ball(width/2,height/2,dX,dY);
        player1 = new Box(width - 60,height - 40, 40, 20,true);
        player2 = new Box(60, 40, 20, 20, true);
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
        player2.updateShape(gateway, true);
        player1.updateShape(gateway, false);
        ball.updateShape();
    }
}
