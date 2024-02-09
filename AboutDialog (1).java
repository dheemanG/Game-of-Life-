/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gameoflife;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JDialog;

/**
 *
 * @author Samad
 */
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import javax.swing.JScrollPane;

public class AboutDialog extends JDialog {
    public AboutDialog(JFrame parentFrame) {
    super(parentFrame, "About Game of Life", true);
    initComponents();
}
    private void initComponents() {
        // Set layout manager (you can customize this based on your preference)
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Game of Life - INSTRUCTIONS");
        titleLabel.setFont(titleLabel.getFont().deriveFont(18.0f));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        JPanel innerPanel = new JPanel(new BorderLayout());
        innerPanel.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(10, 10, 10, 10), // Padding for the whole dialog
                BorderFactory.createEtchedBorder() // Border around the text area
        ));
        innerPanel.add(titleLabel, BorderLayout.NORTH);

        JTextArea rulesTextArea = new JTextArea();
        rulesTextArea.setEditable(false);
        rulesTextArea.setLineWrap(true);
        rulesTextArea.setWrapStyleWord(true);
        rulesTextArea.setText("""
                              The Game of Life is a cellular automaton devised by the British mathematician John Horton Conway in 1970. It is a zero-player game, meaning that its evolution is determined by its initial state, with no further input from humans. The universe of the Game of Life is an infinite, two-dimensional orthogonal grid of square cells, each of which is in one of two possible states, alive or dead. The game follows a set of rules that dictate how the cells evolve over time.\n
                              There are various features implemented in this game that allow you to visualize the rules. The shape selector on the bottom left of the screen can be used to select your starting shape.\n
                              The start button can be used to start the simulation of the game or you can manually simulate the game step by step using the next button.
                              You also have the freedom to change the simulation speed using the slider as well as zoom into the grid to visualize more closely.\n
                              We have provided a dark and light theme to make it more appealing.\n
                              You can edit the life/death of cells using the edit checkbox which will allow you to edit.\n
                              The generate Random allows you to randomly bring cells to life or make some cells to die.
                              You can save your Grid configuration by right clicking the mouse and saving your grid or also open the grid once you start your application.\n
                              
                              Keyboard Shortcuts:\n
                              - Start Simulation: E\n
                              - Stop Simulation: E\n
                              - Next Generation: N\n
                              - Increase Speed: Right Arrow\n
                              - Decrease Speed: left Arrow\n
                     
                              """);

        // Add the text area to a JScrollPane
        JScrollPane scrollPane = new JScrollPane(rulesTextArea);
        innerPanel.add(scrollPane, BorderLayout.CENTER);

        // Add the inner panel to the main layout
        add(innerPanel, BorderLayout.CENTER);

        // Set dialog properties
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
