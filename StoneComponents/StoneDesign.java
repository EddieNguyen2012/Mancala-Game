/**
 * Group Project: Mancala Game
 *
 *
 *
 */
package mancalagame;

import java.awt.*;
import java.awt.geom.*;

/**
 *
 * This is the Stone design class that will handle the visuals of the stones 
 * which will be used to show animations
 *  
 */

public class StoneDesign extends SelectableStoneDesign{
    
    private int x;
    private int y;
    private int width;
    
    public StoneDesign (int x, int y, int width) { 
        this.x = x; 
        this.y = y; 
        this.width = width;
    }
    
    
    // Drawing a simple stone illustration 
    public void draw(Graphics2D g2) { 
        Ellipse2D.Double stone = new Ellipse2D.Double(x, y + width, width, width);
        g2.draw(stone);
    }
    
    public boolean contains(Point2D aPoint2D)
    {
        return x <= aPoint2D.getX() && aPoint2D.getX() <= x + width && y <= aPoint2D.getY() && aPoint2D.getY() <= y + 2 * width;
    }
    
    public void translate(int dx, int dy)
    {
        x += dx;
        y += dy;
    }

}
