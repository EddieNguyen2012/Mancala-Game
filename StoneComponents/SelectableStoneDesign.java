/**
 * Group Project: Mancala Game
 *
 *
 *
 */
package mancalagame;

import java.awt.*;

/**
 *
 * This is the selectable design class.
 *  
 */

public abstract class SelectableStoneDesign implements SceneStoneDesign{
    private boolean selected;
    
    public void setSelected(boolean b) { 
        selected = b; 
    }
    public boolean isSelected(){ 
        return selected; 
    }
    
    public void drawSelection(Graphics2D g2) {
        draw(g2);
    }
    
    
}
