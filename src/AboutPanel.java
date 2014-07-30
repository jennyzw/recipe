/** 
* FILE NAME: AboutPanel.java
* WHO: Jenny Wang & Lily Xie
* WHAT: The About tab includes some information about our program. Note that the IOException 
* is thrown if file for image cannot be found.
* WHEN: May 18 2014
*/

import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;

public class AboutPanel extends JPanel {

  public AboutPanel() throws IOException {
    setBackground(Color.WHITE);
    setLayout (new BoxLayout (this, BoxLayout.Y_AXIS));
    
    BufferedImage fridge = ImageIO.read(new File("coverimage.jpg"));
    JLabel picLabel = new JLabel(new ImageIcon(fridge));
    add(picLabel);
  } 
}