/** 
 * FILE NAME: UserGUI.java
 * WHO: Jenny Wang & Lily Xie
 * WHAT: Sets up the GUI for user interaction.
 * This class creates one JFrame with a JTabbedPane that houses
 * the About tab and the User tab. The About tab includes a short introduction/instructions
 * and the User tab includes all of the user interaction.
 * WHEN: May 18 2014
 */

import javax.swing.JFrame;
import javax.swing.*;
import java.io.*;

public class UserGUI {
  
  public static void main (String[] args) throws IOException {
    User driver = new User("Takis", "recipes0516.txt");
    
    JTabbedPane tp = new JTabbedPane();
    tp.addTab("About", new AboutPanel());
    tp.addTab("User", new UserPanel(driver));
    
    JFrame f = new JFrame("What's For Dinner?");
    UserPanel upanel = new UserPanel(driver);
    f.getContentPane().add(tp);
    f.setSize(900, 700);
    f.setVisible(true);
  }
}
