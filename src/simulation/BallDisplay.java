package simulation;

import gameclient.GameGateway;
import javafx.scene.shape.Circle;
import physics.*;

public class BallDisplay {
    private Circle c;
    
    public BallDisplay(int startX,int startY) {
        
    }
    
    public void updateShape(GameGateway gateway)
    {
        c.setCenterX(gateway.getBallX());
        c.setCenterY(gateway.getBallY());
    }
}
