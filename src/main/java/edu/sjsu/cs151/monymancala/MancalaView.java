package edu.sjsu.cs151.monymancala;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MancalaView extends JFrame implements ChangeListener {
    private MancalaModel model;
    private BoardStyle style;
    private PitComponent selectedPit = null; // To keep track of the selected pit component
    private JFrame welcomeFrame;
    private JPanel centerPanel;
    private JPanel westPanel;
    private JPanel eastPanel;
    private JPanel topLabelPanel;
    private JPanel bottomLabelPanel;
    private JPanel pitPanel;
    private ArrayList<JComponent> componentList;

    public MancalaView(MancalaModel model) {
        this.model = model;
        // Welcome Window
        welcomeWindow();
        componentList = new ArrayList<>();

        //Frame
        setTitle("Mancala");
        setLayout(new BorderLayout());
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

        //Panels added to main frame
        add(centerPanel, BorderLayout.CENTER);
        add(westPanel, BorderLayout.WEST);
        add(eastPanel, BorderLayout.EAST);
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
        ImagePanel imagePanel = new ImagePanel("MancalaBackground.jpg");
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

    private void buildCenterPanel() {

        //Top labels for player B pits
        topLabelPanel = new JPanel(new GridLayout(1, 6));
        String[] topLabels = new String[]{"B6", "B5", "B4", "B3", "B2", "B1"};
        for (String label : topLabels) {
            topLabelPanel.add(new JLabel(label, SwingConstants.CENTER));
        }

        //Bottom labels for player A pits
        bottomLabelPanel = new JPanel(new GridLayout(1, 6));
        String[] bottomLabels = {"A1", "A2", "A3", "A4", "A5", "A6"};
        for (String label : bottomLabels) {
            bottomLabelPanel.add(new JLabel(label, SwingConstants.CENTER));
        }

        //Pit panels between the labels
        pitPanel = new JPanel();
        pitPanel.setLayout(new GridLayout(2, 6, 10, 10));

        //Player B's row of pits
        for(int i = 12; i >= 7; i--){
            Pit pit = model.getPit(i);
            PitComponent pitComponent = new PitComponent(pit, style);
            componentList.add(pitComponent);
            pitComponent.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    selectPit(pitComponent);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    pitComponent.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    pitComponent.setBorder(null);
                }
            });
            pitPanel.add(pitComponent);
        }

        //Player A's row of pits
        for(int i = 0; i <= 5; i++){
            Pit pit = model.getPit(i);
            PitComponent pitComponent = new PitComponent(pit, style);
            componentList.add(pitComponent);
            pitComponent.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    selectPit(pitComponent);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    pitComponent.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    pitComponent.setBorder(null);
                }
            });
            pitPanel.add(pitComponent);
        }

        centerPanel.add(topLabelPanel, BorderLayout.NORTH);
        centerPanel.add(pitPanel, BorderLayout.CENTER);
        centerPanel.add(bottomLabelPanel, BorderLayout.SOUTH);

    }

    private void buildSidePanels() {
        /*
            Putting the mancalas for each player (index 13 for mancala B and index 6 for mancala A)
            into east and west panel
         */
        Pit mancalaA = model.getPit(6);
        Pit mancalaB = model.getPit(13);

        MancalaComponent mancalaAComponent = new MancalaComponent(mancalaA,style);
        MancalaComponent mancalaBComponent = new MancalaComponent(mancalaB, style);

        componentList.add(mancalaAComponent);
        componentList.add(mancalaBComponent);

        eastPanel.add(mancalaAComponent, BorderLayout.CENTER);
        westPanel.add(mancalaBComponent, BorderLayout.CENTER);
        eastPanel.setBorder(BorderFactory.createEmptyBorder(20,10,20,20));
        westPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,10));
    }
    
    private void selectPit(PitComponent aPit) {
        // Deselect the previously selected panel
        if (selectedPit != null) {
            selectedPit.setBorder(BorderFactory.createEmptyBorder());                   // Reset border
        }

        // Select the current panel
        selectedPit = aPit;
        selectedPit.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));// Highlight selected panel

        int pitIndex = aPit.getCorrespondingPit().getIndex();
        model.makeMove(pitIndex);

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
    
    @Override
    public void stateChanged(ChangeEvent e) {
        updateView();
        revalidate();
        repaint();
    }

    private void updateView() {
        for(JComponent component : componentList) {
            component.repaint();
        }
    }
    // Button Actions
    private void styleButtonActionPerformed(BoardStyle aStyle) {
        this.model.setBoardStyle(aStyle);
        welcomeFrame.dispose();
        buildComponents();
        setVisible(true);
    }

    public void showErrorMessage(String errorMsg) {
        JOptionPane.showMessageDialog(
                null,
                errorMsg,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    // Getter for buttons
}
