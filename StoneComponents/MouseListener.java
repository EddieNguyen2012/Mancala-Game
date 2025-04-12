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
public interface MouseListener extends EventListener{
    void mouseClicked(MouseEvent event);
    void mousePressed(MouseEvent event);
}
