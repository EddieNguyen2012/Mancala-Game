package edu.sjsu.cs151.monymancala;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;

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
        JButton defaultStyleButton = new JButton("Style 1");
        JButton modernStyleButton = new JButton("Style 2");
        
        // Set button size
        defaultStyleButton.setPreferredSize(new Dimension(150, 50));
        defaultStyleButton.addActionListener(e -> styleButtonActionPerformed(new DefaultStyle()));
        
        // Set button size
        modernStyleButton.setPreferredSize(new Dimension(150, 50));
        modernStyleButton.addActionListener(e -> styleButtonActionPerformed(new ModernStyle()));
        
        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(new JLabel("Select Board Style: "));
        buttonPanel.add(defaultStyleButton);
        buttonPanel.add(modernStyleButton);
        buttonPanel.setOpaque(false); // Make the button panel transparent
        
        // Adjusting the welcome frame size and position
        welcomeFrame.add(imagePanel, BorderLayout.CENTER);
        welcomeFrame.add(buttonPanel, BorderLayout.SOUTH);
        welcomeFrame.setLocationRelativeTo(null);
        welcomeFrame.setVisible(true);
        welcomeFrame.revalidate();
        welcomeFrame.repaint();        
    }
    
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

    // Updates the image in the Welcome Frame
    class ImagePanel extends JPanel {
        private Image welcomeImage;

        public ImagePanel(String imagePath) {
            // Load the image
            welcomeImage = new ImageIcon(imagePath).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Draw the image scaled to the size of the panel
            g.drawImage(welcomeImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    // Custom JPanel to draw the background image
    class BackgroundPanel extends JPanel {
        private BoardStyle style;

        public BackgroundPanel(BoardStyle style) {
            this.style = style;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            style.drawBackground(g2, 0, 0, getWidth(), getHeight());
        }
    }
    
    @Override
    public void stateChanged(ChangeEvent e) {
        revalidate();
        repaint();
    }

    // Button Actions
    private void styleButtonActionPerformed(BoardStyle aStyle) {
        this.model.setBoardStyle(aStyle);
        welcomeFrame.dispose();
        buildComponents();
        setVisible(true);
        initialCountFrame.setVisible(true);
        initialCountFrame.setLocationRelativeTo(this);
    }
    
    private void stonesButtonActionPerformed(int num) {
        this.model.setPitStones(num);
        initialCountFrame.dispose();
    }

    public void showErrorMessage(String errorMsg) {
        JOptionPane.showMessageDialog(
                null,
                errorMsg,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    public void showGameOverMessage(String gameOverMsg) {
        JOptionPane.showMessageDialog(
                null,
                gameOverMsg,
                "Game Over",
                JOptionPane.INFORMATION_MESSAGE
        );
        for (PitComponent component : componentList) {
            component.setEnabled(false);
        }
    }
    public JButton getUndoButton() {
        return undoButton;
    }

    public JButton getEndTurnButton() {
        return endTurnButton;
    }

    public JLabel getPlayerText() {
        return playerText;
    }

    public JPanel getPitPanel() {
        return pitPanel;
    }

    //Getter for PitComponents
    public ArrayList<PitComponent> getPitComponents() {
        return componentList;
    }
}
