package edu.sjsu.cs151.monymancala;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;


/**
 * MancalaView represents the graphical user interface (GUI) for the Mancala game.
 * It handles the rendering of the board, user interaction components (buttons, pits, labels),
 * and interfaces with the MancalaModel and MancalaController for game logic and behavior.
 * Implements ChangeListener to respond to changes in the game state.
 */
public class MancalaView extends JFrame implements ChangeListener {
    private MancalaModel model;
    private MancalaController controller;
    private BoardStyle style;
    private PitComponent selectedPit = null; // To keep track of the selected pit component
    private JFrame welcomeFrame;
    private JFrame initialCountFrame;
    private JPanel backgroundPanel;
    private JPanel centerPanel;
    private JPanel westPanel;
    private JPanel eastPanel;
    private JPanel southPanel;
    private JPanel topLabelPanel;
    private JPanel bottomLabelPanel;
    private JPanel pitPanel;
    private ArrayList<PitComponent> componentList;
    private JLabel playerText;
    private JButton undoButton;
    private JButton endTurnButton;


    /**
     * Author: Brandon Sanchez, Marco Lopez
     *
     * Constructs the MancalaView by initializing the welcome and initial setup windows,
     * and setting the title and layout for the main game frame.
     *
     * @param model the MancalaModel containing the game data
     * @param controller the MancalaController managing game actions
     */
    public MancalaView(MancalaModel model, MancalaController controller) {
        this.model = model;
        this.controller = controller;
        // Welcome Window
        welcomeWindow();

        initialCountWindow();
        componentList = new ArrayList<>();

        //Frame
        setTitle("Mancala");
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    /**
     * Author: Brandon Sanchez, Marco Lopez, Danny Nguyen
     *
     * Initializes and arranges the components of the main game board including
     * center, side panels, and control buttons.
     */
    private void buildComponents() {
        this.style = model.getStyle();

        componentList = new ArrayList<>();

        //Center Panel for the pits
        centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        buildCenterPanel();

        //West panel for mancala
        westPanel = new JPanel(new BorderLayout());

        //East panel for mancala
        eastPanel = new JPanel(new BorderLayout());
        buildSidePanels();

        backgroundPanel = new BackgroundPanel(style);
        backgroundPanel.add(westPanel, BorderLayout.WEST);
        backgroundPanel.add(centerPanel, BorderLayout.CENTER);
        backgroundPanel.add(eastPanel, BorderLayout.EAST);

        southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));
        endTurnButton = new JButton("End Turn");
        southPanel.add(endTurnButton);
        undoButton = new JButton("Undo");
        southPanel.add(undoButton);
        playerText = new JLabel("Player: 1");
        southPanel.add(playerText);

        add(southPanel, BorderLayout.SOUTH);
        add(backgroundPanel);
        controller.setView(this);
        pack();
        setLocationRelativeTo(null);
    }


    /**
     * Author: Brandon Sanchez, Marco Lopez, Danny Nguyen
     *
     * Displays the welcome window with style selection buttons and background image.
     */
    private void welcomeWindow() {

        // Create a frame
        welcomeFrame = new JFrame("Welcome to the Mancala Game App"); // border layout is default
        welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        welcomeFrame.setSize(1200, 800);
        welcomeFrame.setLayout(new BorderLayout());

         // Load the image "MancalaBackground.jpg"
        ImagePanel imagePanel = new ImagePanel("Images/MancalaBackground.jpg");
        imagePanel.setBounds(0, 0, 1200, 800);

        // Creating the buttons
        JButton defaultStyleButton = new JButton("Default Style");
        JButton modernStyleButton = new JButton("Modern Style");
        JButton oceanStyleButton = new JButton("Ocean Style");

        // Set button size
        defaultStyleButton.setPreferredSize(new Dimension(150, 50));
        defaultStyleButton.addActionListener(e -> styleButtonActionPerformed(new DefaultStyle()));

        // Set button size
        modernStyleButton.setPreferredSize(new Dimension(150, 50));
        modernStyleButton.addActionListener(e -> styleButtonActionPerformed(new ModernStyle()));

        // Set button size
        oceanStyleButton.setPreferredSize(new Dimension(150, 50));
        oceanStyleButton.addActionListener(e -> styleButtonActionPerformed(new OceanStyle()));

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(new JLabel("Select Board Style: "));
        buttonPanel.add(defaultStyleButton);
        buttonPanel.add(modernStyleButton);
        buttonPanel.add(oceanStyleButton);
        buttonPanel.setOpaque(false); // Make the button panel transparent

        // Adjusting the welcome frame size and position
        welcomeFrame.add(imagePanel, BorderLayout.CENTER);
        welcomeFrame.add(buttonPanel, BorderLayout.SOUTH);
        welcomeFrame.setLocationRelativeTo(null);
        welcomeFrame.setVisible(true);
        welcomeFrame.revalidate();
        welcomeFrame.repaint();
    }


    /**
     * Author: Marco Lopez, Brandon Sanchez
     *
     * Displays a window to allow the player to choose the initial number of stones per pit.
     */
    private void initialCountWindow() {

        // Create a frame
        initialCountFrame = new JFrame("Starting Stone Count"); // border layout is default
        initialCountFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initialCountFrame.setSize(600, 200);
        initialCountFrame.setLayout(new FlowLayout());

        // // Create a JLabel to describe requirements
        initialCountFrame.add(new JLabel("Number of Stones per Pit: "));

        // Create buttons to submit the input
        JButton stones3Button = new JButton("3 Stones");
        JButton stones4Button = new JButton("4 Stones");

        stones3Button.setPreferredSize(new Dimension(150,50));
        stones4Button.setPreferredSize(new Dimension(150,50));

        // Add action listener to the buttons
        stones3Button.addActionListener(e -> stonesButtonActionPerformed(3));
        stones4Button.addActionListener(e -> stonesButtonActionPerformed(4));

        initialCountFrame.add(stones3Button);
        initialCountFrame.add(stones4Button);

        initialCountFrame.setLocationRelativeTo(null);
        initialCountFrame.revalidate();
        initialCountFrame.repaint();
        initialCountFrame.pack();

    }


    /**
     * Author: Brandon Sanchez
     *
     * Builds the center panel containing player pits and their corresponding labels.
     */
    private void buildCenterPanel() {

        //Top labels for player B pits
        topLabelPanel = new JPanel(new GridLayout(1, 6));
        String[] topLabels = new String[]{"B6", "B5", "B4", "B3", "B2", "B1"};
        for (String label : topLabels) {
            JLabel pitLabel = new JLabel(label, SwingConstants.CENTER);
            pitLabel.setForeground(style.getLabelColor());
            topLabelPanel.add(pitLabel);
        }

        //Bottom labels for player A pits
        bottomLabelPanel = new JPanel(new GridLayout(1, 6));
        String[] bottomLabels = {"A1", "A2", "A3", "A4", "A5", "A6"};
        for (String label : bottomLabels) {
            JLabel pitLabel = new JLabel(label, SwingConstants.CENTER);
            pitLabel.setForeground(style.getLabelColor());
            bottomLabelPanel.add(pitLabel);
        }

        //Pit panels between the labels
        pitPanel = new JPanel();
        pitPanel.setLayout(new GridLayout(2, 6, 10, 10));

        //Player B's row of pits
        for(int i = 12; i >= 7; i--){
            //Pit pit = model.getPit(i);
            PitComponent pitComponent = new PitComponent(model, i, style);
            componentList.add(pitComponent);
            pitPanel.add(pitComponent);
        }

        //Player A's row of pits
        for(int i = 0; i <= 5; i++){
            //Pit pit = model.getPit(i);
            PitComponent pitComponent = new PitComponent(model, i, style);
            componentList.add(pitComponent);
            pitPanel.add(pitComponent);
        }
        centerPanel.setOpaque(false);
        pitPanel.setOpaque(false);
        topLabelPanel.setOpaque(false);
        bottomLabelPanel.setOpaque(false);
        centerPanel.add(topLabelPanel, BorderLayout.NORTH);
        centerPanel.add(pitPanel, BorderLayout.CENTER);
        centerPanel.add(bottomLabelPanel, BorderLayout.SOUTH);
    }


    /**
     * Author: Brandon Sanchez
     *
     * Builds the left and right panels representing each player's Mancala store.
     */
    private void buildSidePanels() {
        MancalaComponent mancalaAComponent = new MancalaComponent(model, 6, style);
        MancalaComponent mancalaBComponent = new MancalaComponent(model, 13, style);

        eastPanel.add(mancalaAComponent, BorderLayout.CENTER);
        westPanel.add(mancalaBComponent, BorderLayout.CENTER);
        eastPanel.setOpaque(false);
        westPanel.setOpaque(false);
        eastPanel.setBorder(BorderFactory.createEmptyBorder(20,10,20,20));
        westPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,10));
    }


    /**
     * Author: Marco Lopez, Brandon Sanchez
     * Highlights the selected pit and deselects the previously selected pit.
     *
     * @param aPit the pit component that was selected
     */
    public void selectPit(PitComponent aPit) {
        // Deselect the previously selected panel
        if (selectedPit != null) {
            selectedPit.setBorder(BorderFactory.createEmptyBorder());                   // Reset border
        }

        // Select the current panel
        selectedPit = aPit;
        selectedPit.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));// Highlight selected panel
        pitPanel.repaint();
    }


    /**
     * Author: Brandon Sanchez
     *
     * Update component when model changed
     * @param e  a ChangeEvent object
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        revalidate();
        repaint();
    }


    // Button Actions
    /**
     * Author: Marco Lopez, Brandon Sanchez
     *
     * Handles the action when a board style is selected. Applies the style and initializes the board.
     *
     * @param aStyle the selected board style
     */
    private void styleButtonActionPerformed(BoardStyle aStyle) {
        this.model.setBoardStyle(aStyle);
        welcomeFrame.dispose();
        buildComponents();
        setVisible(true);
        initialCountFrame.setVisible(true);
        initialCountFrame.setLocationRelativeTo(this);
    }


    /**
     * Author: Marco Lopez
     *
     * Handles the action when a stone count is selected. Applies the number and closes the setup window.
     *
     * @param num number of stones per pit
     */
    private void stonesButtonActionPerformed(int num) {
        this.model.setPitStones(num);
        initialCountFrame.dispose();
    }


    /**
     * Author: Eddie Nguyen
     *
     * Displays an error message in a dialog box.
     *
     * @param errorMsg the error message to display
     */
    public void showErrorMessage(String errorMsg) {
        JOptionPane.showMessageDialog(
                null,
                errorMsg,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    /**
     * Author: Marco Lopez, Eddie Nguyen
     *
     * Show game over message when game ended. Prompts user to choose exit program or start over.
     *
     * @param gameOverMsg the result of the game
     */
    public void showGameOverMessage(String gameOverMsg) {
        // Define an array of custom options for the dialog
        Object[] options = { "Play Again", "Exit" };
        
        JOptionPane.showMessageDialog(
                null,
                gameOverMsg,
                "Game Over",
                JOptionPane.INFORMATION_MESSAGE
        );
        for (PitComponent component : componentList) {
            component.setEnabled(false);
        }

        int option = JOptionPane.showOptionDialog(
            null, // Parent component (null means center on screen)
            "Do you want to play again?", // Message to display
            "Play One More Time...", // Dialog title
            JOptionPane.YES_NO_CANCEL_OPTION, // Option type (Yes, No, Cancel)
            JOptionPane.QUESTION_MESSAGE, // Message type (question icon)
            null, // Custom icon (null means no custom icon)
            options, // Custom options array
            options[0] // Initial selection (default is "Play Again")
        );
        
        if (option == JOptionPane.YES_OPTION) {
            setVisible(false);      // Make main JFrame invisible
            dispose();              // Destroy the main JFrame
            JOptionPane.showMessageDialog(null,"Restarting Game...");
            
            this.model = new MancalaModel();
            this.controller = new MancalaController(model);
            MancalaView view = new MancalaView(model, controller); // Restart Mancala Game
        }
        else if (option == JOptionPane.NO_OPTION) {
            setVisible(false);      // Make main JFrame invisible
            dispose();              // Destroy the main JFrame
            JOptionPane.showMessageDialog(null, "GoodBye.");
        }
    }

    /**
     * Author: Eddie Nguyen
     * Return undo button for controller
     * @return the undo button
     */
    public JButton getUndoButton() {
        return undoButton;
    }

    /**
     * Author: Eddie Nguyen
     *
     * Return end turn button for controller
     * @return the end turn button
     */
    public JButton getEndTurnButton() {
        return endTurnButton;
    }

    /**
     * Author: Eddie Nguyen
     *
     * Return current player indicator for controller
     * @return the player indicator text
     */
    public JLabel getPlayerText() {
        return playerText;
    }

    /**
     * Author: Eddie Nguyen
     *
     * Return pit panel for controller
     * @return the pit panel
     */
    public JPanel getPitPanel() {
        return pitPanel;
    }


    /**
     * Author: Brandon Sanchez
     *
     * Return list of pit components for controller
     * @return the list of pit components
     */
    public ArrayList<PitComponent> getPitComponents() {
        return componentList;
    }
}
