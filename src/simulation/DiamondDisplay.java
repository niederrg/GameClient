package simulation;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import physics.*;

//Henry Killough
//Feb 27, 2018
//IHRTLUHC 

public class DiamondDisplay
{

    private Polygon diamond;
    public int x;
    public int y;
    public int r;
    Point top, bottom, left, right;
    
    
    public DiamondDisplay(int x, int y, int r) {
        this.x = x;
        this.y = y;
        this.r = r;
        top = new Point(x+r, y);
        right = new Point(x+(2*r), y+r);
        left = new Point(x, y+r);
        bottom = new Point(x+r, y+(2*r));
        diamond = new Polygon(top.x, top.y, right.x, right.y, bottom.x, bottom.y, left.x, left.y);
    }
    
    
    
    public void setX(int newX) {
        double deltaX = newX - this.x;
        this.top.x += deltaX;
        this.right.x += deltaX;
        this.bottom.x += deltaX;
        this.left.x += deltaX;
        this.x = newX;
    }
    
    public void setY(int newY) {
        double deltaY = newY - this.y;
        this.top.y += deltaY;
        this.right.y += deltaY;
        this.bottom.y += deltaY;
        this.left.y += deltaY;
        this.y = newY;
    }
    
    public boolean contains(Point p)
    {
//        if(((p.x + p.y) > (this.x + this.y + r)) && ((p.x + p.y) < (this.x + this.y + (3*r))) && (p.x>this.x) && (p.x<(this.x + (2*r)))) {
//            return true;
//        } else {return false; }
        return false;
    }
    
    public Shape getShape()
    {
        diamond = new Polygon(top.x, top.y, right.x, right.y, bottom.x, bottom.y, left.x, left.y);
        diamond.setFill(Color.WHITE);
        diamond.setStroke(Color.BLACK);
        return diamond;
    }
    
    public void updateShape(ArrayList<Point> points)
    {
        this.top = points.get(0);
        this.left = points.get(1);
        this.bottom = points.get(2);
        this.right = points.get(3);
        
        diamond.getPoints().clear();
        
        diamond.getPoints().addAll(
                top.x, top.y,
                right.x, right.y,
                bottom.x, bottom.y,
                left.x, left.y);
    }
}
