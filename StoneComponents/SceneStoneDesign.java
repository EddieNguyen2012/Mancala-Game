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
 * This is the scene interface class.
 *  
 */

public interface SceneStoneDesign {
    void setSelected(boolean target);
    boolean isSelected();
    void draw(Graphics2D design2D);
    void drawSelection(Graphics2D design2D);
    void translate(int dx, int dy);
    boolean contains(Point2D aPoint);
}
