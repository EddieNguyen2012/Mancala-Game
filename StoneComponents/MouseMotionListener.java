/**
 * Group Project: Mancala Game
 *
 *
 *
 */
package mancalagame;

import java.awt.event.*;
import java.util.EventListener;
/**
 *
 * This is the mouse motion interface class.
 *  
 */
public interface MouseMotionListener extends EventListener{
    void mouseMoved(MouseEvent event); 
    void mouseDragged(MouseEvent event);
}
