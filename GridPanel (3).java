package com.mycompany.gameoflife;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class GridPanel extends JPanel implements MouseListener, MouseMotionListener {
    private GameofLifeModel model;
    private ControlsPanel controlPanel;
    private PreferencesDialog preferencesDialog;
    private int gridSquareSize = 5;
    private int initial = -1;
    private JPopupMenu popupMenu;
    Timer time;
    private Color liveCellColor = Color.GREEN;
    private Color starvingCellColor = Color.RED;
    private Color overpopulatedCellColor = Color.BLUE;
    private Color stablePatternColor = Color.YELLOW;
    private Color oscillatorColor = Color.CYAN;

    public GridPanel(GameofLifeModel model,ControlsPanel controlPanel,PreferencesDialog pref) {
        this.preferencesDialog = pref;
        // Create the pop-up menu
        popupMenu = new JPopupMenu();
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem openMenuItem = new JMenuItem("Open");
        JMenuItem preferenceItem = new JMenuItem("Preference");
        // Add action listeners to the menu items
        saveMenuItem.addActionListener(e -> saveToFile());
        openMenuItem.addActionListener(e -> openFromFile());
        preferenceItem.addActionListener(e -> Openpref());
        // Add menu items to the pop-up menu
        popupMenu.add(saveMenuItem);
        popupMenu.add(openMenuItem);
        popupMenu.add(preferenceItem);
        // Add mouse listener to show the pop-up menu on right-click
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    popupMenu.show(GridPanel.this, e.getX(), e.getY());
                }
            }
        });
        this.controlPanel = controlPanel;
        this.model = model;
        setLayout(null);
        addMouseMotionListener(this);
        addMouseListener(this);
        setBackground(Color.GRAY);
        controlPanel.setModel(model);
        controlPanel.setGrid(this);
        time = new Timer(controlPanel.gettimeDelay(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add logic for updating the game state
                if(!controlPanel.isEditCheckBoxSelected()){
                    model.updateGrid();
                    controlPanel.updateGenerationLabel();
                    repaint();
                }
            }
        });
        controlPanel.setTimer(time);
        
    }
    private void saveToFile() {
        JFileChooser fileChooser = new JFileChooser();
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileToSave))) {
                // Save the necessary data to the file
                oos.writeObject(model.getLife());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    private void Openpref(){
        preferencesDialog.setVisible(true);
    }
    private void openFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        int userSelection = fileChooser.showOpenDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToOpen = fileChooser.getSelectedFile();
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileToOpen))) {
                // Read the saved data from the file and set it in the model and controls
                int[][] life = (int[][]) ois.readObject();
                model.setLife(life);
                
                repaint();
             
                // Add more data to load if needed
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        

        grid(g2);
        display(g2);
        
    }

    private void grid(Graphics2D g) {
        int[][] life = model.getLife();
        if(controlPanel.getTheme()){
            g.setColor(Color.BLUE);
        }
        else{
            g.setColor(Color.BLACK);
        }
        for (int x = 0; x < life.length; x++) {
            for (int y = 0; y < life[x].length; y++) {
                    g.drawRect(x * gridSquareSize, y * gridSquareSize, gridSquareSize, gridSquareSize);
                
            }
        }
    }
    private void display(Graphics2D g){
        int[][] life = model.getLife();
        if(controlPanel.getTheme()){
            g.setColor(Color.BLUE);
        }
        else{
            g.setColor(Color.GREEN);
        }
        for (int x = 0; x < life.length; x++) {
            for (int y = 0; y < life[x].length; y++) {
                if(life[x][y] == 1){
                    g.fillRect(x * gridSquareSize, y * gridSquareSize, gridSquareSize, gridSquareSize);
                }
                    
                
            }
        }
    }
    public void setGridSquareSize(int size) {
    this.gridSquareSize = size;
}
    

    

    

    

    

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getX() / gridSquareSize;
        int y = e.getY() / gridSquareSize;
        if(controlPanel.isEditCheckBoxSelected()){
            if (model.getLife()[x][y] == 0 && initial == 0) {
            model.setCell(x, y, 1);
        } else if (model.getLife()[x][y] == 1 && initial == 1) {
            model.setCell(x, y, 0);
        }
        model.copyArray();
        repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Implement if needed
    }

    @Override
    public void mouseClicked(MouseEvent e) {
       int x = e.getX() / gridSquareSize;
        int y = e.getY() / gridSquareSize;

        // Check if the "Edit" checkbox isselected
        if (controlPanel.isEditCheckBoxSelected()) {
            // Toggle the state of the cell
            model.setCell(x, y, model.getLife()[x][y] == 0 ? 1 : 0);
            model.copyArray();
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX() / gridSquareSize;
        int y = e.getY() / gridSquareSize;

        if(controlPanel.isEditCheckBoxSelected()){
            if (model.getLife()[x][y] == 0) {
            initial = 0;
        } else {
            initial = 1;
        }
        repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
        initial = -1;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Implement if needed
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Implement if needed
    }
}


