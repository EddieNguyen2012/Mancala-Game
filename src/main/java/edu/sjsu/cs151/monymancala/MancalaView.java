package edu.sjsu.cs151.monymancala;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;

public class MancalaView extends JFrame implements ChangeListener {
    private MancalaModel model;
    private BoardStyle style;
    private JPanel selectedPitPanel = null; // To keep track of the selected panel
    private JPanel centerPanel;
    private JPanel westPanel;
    private JPanel eastPanel;
    private JPanel topLabelPanel;
    private JPanel bottomLabelPanel;
    private JPanel pitPanel;
    private ArrayList<JComponent> componentList;

    public MancalaView(MancalaModel model) {
        this.model = model;
        this.style = model.getStyle();

        componentList = new ArrayList<>();

        //Frame
        setTitle("Mancala");
        setLayout(new BorderLayout());

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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
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
        pitPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectPanel(pitPanel);
            }
        });

        //Player B's row of pits
        for(int i = 12; i >= 7; i--){
            Pit pit = model.getPit(i);
            PitComponent pitComponent = new PitComponent(pit, style);
            componentList.add(pitComponent);
            pitPanel.add(pitComponent);
        }

        //Player A's row of pits
        for(int i = 0; i <= 5; i++){
            Pit pit = model.getPit(i);
            PitComponent pitComponent = new PitComponent(pit, style);
            componentList.add(pitComponent);
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

        eastPanel.add(mancalaAComponent, BorderLayout.CENTER);
        westPanel.add(mancalaBComponent, BorderLayout.CENTER);
        eastPanel.setBorder(BorderFactory.createEmptyBorder(20,10,20,20));
        westPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,10));
    }
    
    private void selectPanel(JPanel panel) {
        // Deselect the previously selected panel
        if (selectedPitPanel != null) {
            selectedPitPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));       // Reset border
            selectedPitPanel.setBackground(Color.LIGHT_GRAY);                              // Reset background color
        }

        // Select the current panel
        selectedPitPanel = panel;
        selectedPitPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));        // Highlight selected panel
        pitPanel.repaint();
    }
    
    @Override
    public void stateChanged(ChangeEvent e) {
        updateView();
    }

    private void updateView() {
        for(JComponent component : componentList) {
            component.repaint();
        }
    }
}
