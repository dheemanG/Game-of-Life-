/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gameoflife;

/**
 *
 * @author Samad
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.prefs.Preferences;
public class PreferencesDialog extends JDialog {
    public static final String PREF_STARTING_PATTERN = "starting_pattern";
    public static final String PREF_SIMULATION_SPEED = "simulation_speed";
    public static final String PREF_ZOOM_FACTOR = "zoom_factor";
    public static final String PREF_REMEMBER_GRID = "remember_grid";
    public static final String PREF_GRID_STATE = "grid_state";
    private JComboBox<String> startingPatternComboBox;
    private JSlider simulationSpeedSlider;
    private JSlider zoomFactorSlider;
    private JCheckBox rememberGridCheckBox;
    GameofLifeModel model;
    public PreferencesDialog(Frame parent,GameofLifeModel model) {
       
        super(parent, "Preferences", true);
        initializeComponents();
        pack();
        setLocationRelativeTo(parent);
        this.model = model;
    }

    private void initializeComponents() {
        setLayout(new GridLayout(0, 2));

        // Add components for starting pattern
        add(new JLabel("Starting Pattern:"));
        startingPatternComboBox = new JComboBox<>(new String[]{"Clear", "Glider", "Beacon", "Beehive", "Boat", "Blinker", "Pond"});
        add(startingPatternComboBox);

        // Add components for simulation speed
        add(new JLabel("Simulation Speed:"));
        simulationSpeedSlider = new JSlider(JSlider.HORIZONTAL, 1, 10, 1);
        simulationSpeedSlider.setPaintLabels(true);
        simulationSpeedSlider.setMajorTickSpacing(1);
        simulationSpeedSlider.setPaintTicks(true);
        add(simulationSpeedSlider);

        // Add components for zoom factor
        add(new JLabel("Zoom Factor:"));
        zoomFactorSlider = new JSlider(JSlider.HORIZONTAL, 1, 10, 1);
        zoomFactorSlider.setPaintLabels(true);
        zoomFactorSlider.setMajorTickSpacing(1);
        zoomFactorSlider.setPaintTicks(true);
        add(zoomFactorSlider);

        // Add component for remembering grid
        add(new JLabel("Remember Grid Content:"));
        rememberGridCheckBox = new JCheckBox();
        add(rememberGridCheckBox);

        // Add OK button to close the dialog
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve selected preferences and update the application state
                String selectedPattern = (String) startingPatternComboBox.getSelectedItem();
                int speed = simulationSpeedSlider.getValue();
                int zoomFactor = zoomFactorSlider.getValue();
                boolean rememberGrid = rememberGridCheckBox.isSelected();
                int [][] gridState = model.getLife();
                // TODO: Update the application state with the selected preferences
                savePreferences(selectedPattern, speed, zoomFactor, rememberGrid,gridState);

                // Close the dialog
                dispose();
            }
        });
        add(okButton);
    }

    
    private void savePreferences(String startingPattern, int simulationSpeed, int zoomFactor, boolean rememberGrid,int[][]gridState) {
        Preferences prefs = Preferences.userNodeForPackage(getClass());

        prefs.put(PREF_STARTING_PATTERN, startingPattern);
        prefs.putInt(PREF_SIMULATION_SPEED, simulationSpeed);
        prefs.putInt(PREF_ZOOM_FACTOR, zoomFactor);
        prefs.putBoolean(PREF_REMEMBER_GRID, rememberGrid);
        if (rememberGrid) {
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ObjectOutput out = new ObjectOutputStream(bos)) {
                out.writeObject(gridState);
                prefs.putByteArray(PREF_GRID_STATE, bos.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

          
            frame.setSize(400, 300);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

