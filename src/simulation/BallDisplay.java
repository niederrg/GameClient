package simulation;

import gameclient.GameGateway;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import physics.*;

public class BallDisplay {
    private Circle c;
    
    public BallDisplay(int startX,int startY) {
        
    }
    
    public Shape getShape(GameGateway gateway)
    {
        c = new Circle(gateway.getBallX(), gateway.getBallY(), 4);
        c.setFill(Color.RED);
        return c;
    }
    
    public void updateShape(GameGateway gateway)
    {
        c.setCenterX(gateway.getBallX());
        c.setCenterY(gateway.getBallY());
    }
}
