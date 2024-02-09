/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gameoflife;

/**
 *
 * @author Samad
 */
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.prefs.Preferences;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
public class ControlsPanel extends JPanel {
    GameofLifeModel model;
    private JButton nextButton;
    private JButton startButton;
    private JButton stopButton;
    private JButton randomButton;
    private JComboBox<String> shapeMenu;
    private int  prefSpeed;
    private String prefshape;
    private int prefZoom;
    private JSlider speedSlider;
    private JSlider scaleSlider;
     private JLabel slowLabel;
    private JLabel mediumLabel;
    private JLabel fastLabel;
    private static Hashtable<Integer, JLabel> createLabelTable() {
            Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
            labelTable.put(10, new JLabel("Fast"));
            labelTable.put(5, new JLabel("Medium"));
            labelTable.put(1, new JLabel("Slow"));
            return labelTable;
        }   
    
    private  JLabel smallLabel;
    private  JLabel medium2Label;
    private JLabel largeLabel;
     private static Hashtable<Integer, JLabel> createLabelTableSize() {
            Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
            labelTable.put(5, new JLabel("Small"));
            labelTable.put(15, new JLabel("Medium"));
            labelTable.put(25, new JLabel("large"));
            return labelTable;
        }   

    private JCheckBox editCheckbox;
    private JCheckBox darkTheme;
    private JLabel generationLabel;
    private int generationCount = 0;
    private GridPanel grid;
    private int timeDelay = 1000; 
    public String selectedShape = "Clear";
    Timer time2;
    boolean started = false;
    public ControlsPanel() {
       
        initComponents();
    }

    private void initComponents() {
        setLayout(new FlowLayout(FlowLayout.CENTER));

        nextButton = new JButton("Next");
        startButton = new JButton("Start");
        shapeMenu = new JComboBox<>(new String[]{"Clear","Glider", "Beacon","Beehive","Boat","Blinker","Toad"}); // Add your shape options
        speedSlider= new JSlider(JSlider.HORIZONTAL,1, 10, 1);
        scaleSlider=new JSlider(JSlider.HORIZONTAL, 5, 25, 5);
        slowLabel = new JLabel("Slow");
        mediumLabel = new JLabel("Medium");
        fastLabel = new JLabel("Fast");
        
        smallLabel=new JLabel("small");
        medium2Label=new JLabel("medium");
        largeLabel=new JLabel("Large");

        editCheckbox = new JCheckBox("Edit");
        darkTheme = new JCheckBox("Dark Theme");
        generationLabel = new JLabel("Generation: 0");
        randomButton = new JButton("Generate Random");
          speedSlider.setPaintLabels(true);
        speedSlider.setMajorTickSpacing(1);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintTrack(true);
       
            // Set labels for specific divisions
        speedSlider.setLabelTable(createLabelTable());

            // Add an ActionListener to handle slider changes
        speedSlider.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    int speed = 1020-(100*speedSlider.getValue());
                    timeDelay = speed;
                    time2.setDelay(timeDelay);
                }
            });

        darkTheme.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(darkTheme.isSelected()){
                    setBackground(Color.BLACK);
                    setComponentTheme(Color.BLUE, Color.BLACK);
                }
                else{
                    setBackground(null);
                    setComponentTheme(null,null);
                }
                grid.repaint();
            }
        });
        scaleSlider.setPaintLabels(true);
        scaleSlider.setMajorTickSpacing(1);
        scaleSlider.setPaintTicks(true);
        scaleSlider.setPaintTrack(true);
       
            // Set labels for specific divisions
        scaleSlider.setLabelTable(createLabelTableSize());
        
        scaleSlider.addChangeListener(new ChangeListener(){
            
            @Override
                public void stateChanged(ChangeEvent e) {
                    
                    grid.setGridSquareSize(scaleSlider.getValue());
                    grid.repaint();
                }
            
        });

        
        shapeMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 selectedShape = shapeMenu.getItemAt(shapeMenu.getSelectedIndex());
                if (selectedShape.equals("Clear") ) {
                    model.initializeClear();
                } else {
                    model.initializeShape(selectedShape);
                }
                model.copyArray();
                grid.repaint();
            }
        });
        nextButton.addActionListener(e -> handleNextButtonClick());

        startButton.addActionListener(e -> handleStartButtonClick());
        randomButton.addActionListener(e -> handleRandomButton());
        add(shapeMenu);
        add(nextButton);
        add(startButton);
        add(speedSlider);
        add(scaleSlider);
        add(editCheckbox);
        add(randomButton);
        add(generationLabel);
        add(darkTheme);
        Preferences prefs = Preferences.userNodeForPackage(PreferencesDialog.class);
      
        registerKeyboardShortcuts();
    }
    private void setComponentTheme(Color foreground, Color background) {
        nextButton.setForeground(foreground);
        startButton.setForeground(foreground);
        shapeMenu.setForeground(foreground);
        speedSlider.setForeground(foreground);
        scaleSlider.setForeground(foreground);
        editCheckbox.setForeground(foreground);
        randomButton.setForeground(foreground);
        generationLabel.setForeground(foreground);
        darkTheme.setForeground(foreground);

        nextButton.setBackground(background);
        startButton.setBackground(background);
        shapeMenu.setBackground(background);
        speedSlider.setBackground(background);
        scaleSlider.setBackground(background);
        editCheckbox.setBackground(background);
        randomButton.setBackground(background);
        generationLabel.setBackground(background);
        darkTheme.setBackground(background);
    }
    
    private void registerKeyboardShortcuts() {
        // Register shortcut for starting/stopping the simulation
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke("E"), "toggleSimulation");
        getActionMap().put("toggleSimulation", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleStartButtonClick();
            }
        });

        // Register shortcut for incrementing the simulation
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke("N"), "nextGeneration");
        getActionMap().put("nextGeneration", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleNextButtonClick();
            }
        });
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke("RIGHT"), "increaseSpeed");
        getActionMap().put("increaseSpeed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                increaseSpeed();
            }
        });

        // Register shortcut for decreasing speed
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke("LEFT"), "decreaseSpeed");
        getActionMap().put("decreaseSpeed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                decreaseSpeed();
            }
        });
    }
    private void increaseSpeed() {
        // Add logic for increasing speed
        int currentSpeed = speedSlider.getValue();
        if (currentSpeed < speedSlider.getMaximum()) {
            speedSlider.setValue(currentSpeed + 1);
        }
    }

    private void decreaseSpeed() {
        // Add logic for decreasing speed
        int currentSpeed = speedSlider.getValue();
        if (currentSpeed > speedSlider.getMinimum()) {
            speedSlider.setValue(currentSpeed - 1);
        }
    }
    private void handleRandomButton(){
        model.initializeGame();
        model.copyArray();
        grid.repaint();
    }
    private void handleNextButtonClick() {
    if (!started && !isEditCheckBoxSelected()) {
        // If the simulation is not running, update the grid once
        model.updateGrid();
        grid.repaint();
        updateGenerationLabel();
    }
}

    private void setPreferences(String shape,int speed,int zoom){
        this.prefshape = shape;
        this.prefSpeed = speed;
        this.prefZoom = zoom;
        
    }
    private void setShape(String shape){
        selectedShape = shape;
    }
    public String getShape(){
        return selectedShape;
    }
    private void handleStartButtonClick() {
        // Add logic for handling "Start" button click
        if(!started){
            time2.start();
        startButton.setText("Stop");
        started = true;
        nextButton.setEnabled(false);
        }
        else{
            time2.stop();
            startButton.setText("Start");
            started = false;
            nextButton.setEnabled(true);
    }
        
    }

    public void updateGenerationLabel() {
        generationCount+=1;
        generationLabel.setText("Generation: " + generationCount);
    }
    public boolean isEditCheckBoxSelected(){
        return editCheckbox.isSelected();
    }
    public void setTimer(Timer time){
        time2 = time;
    }
    public void setModel(GameofLifeModel model){
        this.model = model;
    }
    public void setGrid(GridPanel grid){
        this.grid = grid;
    }
    public int gettimeDelay(){
        return timeDelay;
    }
    public boolean getTheme(){
        if(darkTheme.isSelected()){
            return true;
        }
        return false;
    }
    void setEditCheckBoxSelected(boolean b) {
        editCheckbox.setSelected(b);
    }
}

