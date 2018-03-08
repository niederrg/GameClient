package simulation;

import gameclient.GameGateway;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import physics.*;

public class BallDisplay {
    private Circle c;
    
    public BallDisplay(int startX,int startY) {
        c = new Circle(startX, startY,4);
        c.setFill(Color.GREEN);
    }
    
    public Shape getShape(double x, double y)
    {
        c = new Circle(x, y, 4);
        c.setFill(Color.RED);
        return c;
    }
    
    public void updateShape(GameGateway gateway)
    {
        c.setCenterX(gateway.getBallX());
        c.setCenterY(gateway.getBallY());
    }
}
