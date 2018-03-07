package simulation;

import gameclient.GameGateway;
import physics.*;
import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class BoxDisplay {
    private Rectangle r;
    public int x;
    public int y;
    public int width;
    public int height;
    
    // Set outward to true if you want a box with outward pointed normals
    public BoxDisplay(int x,int y,int width,int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public Shape getShape()
    {
        r = new Rectangle(x, y, width, height);
        r.setFill(Color.WHITE);
        r.setStroke(Color.BLACK);
        return r;
    }
}
