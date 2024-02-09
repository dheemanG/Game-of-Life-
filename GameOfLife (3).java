/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.gameoflife;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.SplashScreen;
/**
 *
 * @author Samad
 */

public class GameOfLife extends JFrame {
     
    public static void main(String[] args) {
        
        EventQueue.invokeLater(new Runnable(){
            @Override public void run(){
                JFrame frame = new JFrame();
                JMenuBar menubar = new JMenuBar();
                frame.setJMenuBar(menubar);
                JMenu fileMenu = new JMenu("File");
                menubar.add(fileMenu);
                JMenuItem aboutItem = new JMenuItem("About");
                aboutItem.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent event){
                        showAboutDialog(frame);
                    }
                });
                fileMenu.add(aboutItem);
                frame.setSize(1100,600);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.setTitle("Game of Life");
                frame.setResizable(true);
               
                ControlsPanel controlsPanel = new ControlsPanel();
               
                GameofLifeModel model = new GameofLifeModel(frame.getHeight() - controlsPanel.getHeight()/5, frame.getWidth()/5,controlsPanel);
                PreferencesDialog preference = new PreferencesDialog(frame,model);
                GridPanel view = new GridPanel(model,controlsPanel,preference);
                frame.setLayout(new BorderLayout());
                
                frame.add(view,BorderLayout.CENTER);
                
                frame.add(controlsPanel,BorderLayout.SOUTH);
            }
        });
    }
    private static void showAboutDialog(JFrame parent) {
        AboutDialog aboutDialog = new AboutDialog(parent);
       
    }
}



