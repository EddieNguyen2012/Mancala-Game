/**
 * Group Project: Mancala Game
 *
 *
 *
 */
package mancalagame;

import javax.swing.event.*;
import javax.swing.*;

/**
 *
 * This is the Controller class.
 *  
 */
public class MancalaController implements ChangeListener{
    
    private MancalaModel model;
    private MancalaView view;
    
    
    // Constructor
    public MancalaController(MancalaModel model, MancalaView view) {
        this.model = model;
        this.view = view;
        
        model.addChangeListener(this);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    // Function that will notify the listeners
        @Override
    public void stateChanged(ChangeEvent e) {
        updateView();
    }
    
    // Function that will update the frame
    private void updateView() {
        view.updateViewFrame();
    }
    
}
