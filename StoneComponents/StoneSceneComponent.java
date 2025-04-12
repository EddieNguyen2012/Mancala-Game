/**
 * Group Project: Mancala Game
 *
 *
 *
 */
package mancalagame;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

import java.util.*;

/**
 *
 * This is the stone scene component class.
 *  
 */

public class StoneSceneComponent extends JComponent{
    
    private ArrayList<SceneStoneDesign> stones;
    private Point mousePoint;
    
    
    public StoneSceneComponent() { 
        stones = new ArrayList<>();
        MouseListeners listeners = new MouseListeners();
        addMouseListener(listeners);
        addMouseMotionListener(listeners);
    }
    
    
    private class MouseListeners extends MouseAdapter {
        
        @Override
        public void mousePressed(MouseEvent event) {
            mousePoint = event.getPoint(); 
            for (SceneStoneDesign aStone : stones) {
                if (aStone.contains(mousePoint))
                    aStone.setSelected(!aStone.isSelected());
            }
            repaint();
        }
        
        
        
        @Override
        public void mouseDragged(MouseEvent event)
        {
            Point lastMousePoint = mousePoint;
            mousePoint = event.getPoint();
            for (SceneStoneDesign aStone : stones) {
                if (aStone.isSelected()) {
                    double dx = mousePoint.getX() - lastMousePoint.getX();
                    double dy = mousePoint.getY() - lastMousePoint.getY();
                    aStone.translate((int) dx, (int) dy);
                }
            }
            
            repaint();
        }
        
    }
    
    public void add(SceneStoneDesign aStone) {
        stones.add(aStone);
        repaint();
        }
    
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D design2D = (Graphics2D) g;
        for (SceneStoneDesign aStone : stones) {
            aStone.draw(design2D);
            if (aStone.isSelected()) {
                aStone.drawSelection(design2D);
            }
        }
    }
    
    
}
