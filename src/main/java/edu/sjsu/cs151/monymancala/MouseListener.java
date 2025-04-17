/**
 * Group Project: Mancala Game
 *
 *
 *
 */
package edu.sjsu.cs151.monymancala;

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
