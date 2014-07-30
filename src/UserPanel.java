/** 
 * FILE NAME: UserPanel.java
 * WHO: Jenny Wang & Lily Xie
 * WHAT: Sets up the panel that allows for user interaction with our program. Within the panel,
 * there are separate panels for user input and separate panels for output of information
 * from the recipes database.
 * WHEN: May 18 2014
 */

import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/** 
 * Since this class uses BufferedImage, an IOException 
 * is thrown if file for image cannot be found.
 * Various panels are placed within the UserPanel for 
 * smoother layout design and for the checkboxes, each
 * panel contains about seven checkboxes.
 */
public class UserPanel extends JPanel {
  private User driver;
  private JPanel intro, buttons, p, p1, p2, p3, p4, p5, p6;
  private JCheckBox[] foods = new JCheckBox[50];
  private JButton calculate;
  private JLabel title, text1, text2;
  private JTextArea available, need;
  private JComboBox options, topThree;
  
  public UserPanel(User u) throws IOException {
    driver = u;
    setBackground(Color.WHITE);
    setLayout (new BoxLayout(this, BoxLayout.Y_AXIS));
    
    p = new JPanel();
    add(p);
    p.setBackground(Color.WHITE);
    BufferedImage icon = ImageIO.read(new File("icon.jpg"));
    JLabel picLabel = new JLabel(new ImageIcon(icon));
    p.add(picLabel);
    
    JLabel text = new JLabel("What's in your fridge?");
    text.setHorizontalAlignment( SwingConstants.CENTER );
    text.setForeground(Color.RED.darker());
    text.setFont (new Font ("Trebuchet MS", Font.BOLD, 30));
    p.add(text);
    add (Box.createRigidArea (new Dimension (0, 20)));
    
    // These next few panels house the check boxes of ingredients
    p1 = new JPanel(new FlowLayout());
    p1.setBackground(Color.WHITE);
    add(p1);
    
    p1.add(foods[0] = new JCheckBox("Bacon"));
    p1.add(foods[1] = new JCheckBox("Bananas"));
    p1.add(foods[2] = new JCheckBox("Blueberries"));
    p1.add(foods[3] = new JCheckBox("Bread"));
    p1.add(foods[4] = new JCheckBox("Broth"));
    p1.add(foods[5] = new JCheckBox("Butter"));
    p1.add(foods[6] = new JCheckBox("Carrots"));
    
    p2 = new JPanel(new FlowLayout());
    add(p2);
    p2.setBackground(Color.WHITE);
    p2.add(foods[7] = new JCheckBox("Celery"));
    p2.add(foods[8] = new JCheckBox("Chicken"));
    p2.add(foods[9] = new JCheckBox("Chips"));
    p2.add(foods[10] = new JCheckBox("Cheese"));
    p2.add(foods[11] = new JCheckBox("Dressing"));
    p2.add(foods[12] = new JCheckBox("Eggs"));
    p2.add(foods[13] = new JCheckBox("Flour"));
    p2.add(foods[14] = new JCheckBox("Ham"));
    
    p3 = new JPanel(new FlowLayout());
    add(p3);
    p3.setBackground(Color.WHITE);
    p3.add(foods[15] = new JCheckBox("Ice Cream"));
    p3.add(foods[16] = new JCheckBox("Jelly"));
    p3.add(foods[17] = new JCheckBox("Lettuce"));
    p3.add(foods[18] = new JCheckBox("Maple Syrup"));
    p3.add(foods[19] = new JCheckBox("Mayo"));
    p3.add(foods[20] = new JCheckBox("Milk"));
    p3.add(foods[21] = new JCheckBox("Mushrooms"));
    
    p4 = new JPanel(new FlowLayout());
    add(p4);
    p4.setBackground(Color.WHITE);
    p4.add(foods[22] = new JCheckBox("Pasta"));
    p4.add(foods[23] = new JCheckBox("PB"));
    p4.add(foods[24] = new JCheckBox("Peas"));
    p4.add(foods[25] = new JCheckBox("Peppers"));
    p4.add(foods[26] = new JCheckBox("Strawberries"));
    p4.add(foods[27] = new JCheckBox("Spinach"));
    p4.add(foods[28] = new JCheckBox("Tomato"));
    
    for(int i = 0; i < 29; i++) {
      foods[i].setFont (new Font ("Trebuchet MS", Font.PLAIN, 12));
      foods[i].setBackground(Color.WHITE);
    }
    
    add (Box.createRigidArea (new Dimension (0, 20)));
    
    calculate = new JButton();
    calculate.setBackground(Color.RED.darker());
    calculate.setForeground(Color.WHITE);
    calculate.setOpaque(true);
    calculate.setBorderPainted(false);
    calculate.setText("Calculate");
    add(calculate);
    calculate.addActionListener (new ButtonListener());
    
    add (Box.createRigidArea (new Dimension (0, 20)));
    
    // the items in this panel are added later on in the ButtonListener
    p5 = new JPanel();
    add(p5);
    p5.setBackground(Color.WHITE);
    text1 = new JLabel("Choose what you want to make: ");
    text1.setForeground(Color.RED.darker());
    text1.setFont (new Font ("Trebuchet MS", Font.BOLD, 12));
    
    String[] three = {"Select from your top three choices"};
    topThree = new JComboBox(three);
    topThree.addActionListener (new ComboListener());
    
    text2 = new JLabel("Or select from all the options: ");
    text2.setForeground(Color.RED.darker());
    text2.setFont (new Font ("Trebuchet MS", Font.BOLD, 12));
    
    String[] choices = driver.getRecipeNames();
    options = new JComboBox(choices);
    options.addActionListener (new ComboListener());
    
    add (Box.createRigidArea (new Dimension (0, 20)));
    
    // these texts are calculated later in the ButtonListener because it uses
    // information that the user provides
    JPanel p6 = new JPanel();
    add(p6);
    p6.setBackground(Color.WHITE);
    title = new JLabel("");
    p6.add(title);
    title.setFont (new Font ("Trebuchet MS", Font.BOLD, 18));
    
    available = new JTextArea("");
    p6.add(available);
    available.setFont (new Font ("Trebuchet MS", Font.BOLD, 15));
    
    need = new JTextArea("");
    p6.add(need);
    need.setFont (new Font ("Trebuchet MS", Font.BOLD, 15));
  }
  
  /** 
   * The ButtonListener is attached to the "calculate" button. It populates the "contents"
   * LinkedList with the ingredients that the user selects and calculates the scores of
   * each recipe based on the items that the user has. It also adds the combo
   * boxes after the user clicks "calculate".
   */
  private class ButtonListener implements ActionListener {
    
    public void actionPerformed (ActionEvent event) {
      
      if (event.getSource() == calculate) {
        for(int i = 0; i < 29; i++) {
          if(foods[i].isSelected()) {            
            String foodname = foods[i].getText();
            driver.addContent(foodname);  
          }   
        }
        p5.add(text1);
        p5.add(topThree);
        p5.add(text2);
        p5.add(options);
        driver.calculateScores();
        String[] newTopChoices = driver.topRecipes();
        for(int i = 0; i < newTopChoices.length; i++) {
          topThree.addItem(newTopChoices[i]);
        }
      }  
    }    
  }
  
  /** 
   * The ComboListener is attached to the combo boxes. It retrieves the 
   * selected recipe that the user wants to make and returns specific information
   * about the ingredients that the user already has and the ingredients that
   * the user needs in order to make that recipe.
   */
  private class ComboListener implements ActionListener {
    
    private String selectedFood;
    private Recipe selectedRecipe;
    private String a, n;
    
    public void actionPerformed (ActionEvent event) {
      
      // gets the "selectedFood" item based on which menu the user chooses from
      if(event.getSource() == topThree) {
        selectedFood = (String)topThree.getSelectedItem();
      }
      else if(event.getSource() == options) {
        selectedFood = (String)options.getSelectedItem();
      }
      selectedRecipe = driver.findInList(selectedFood);
      
      a = selectedRecipe.getAvailable(driver.contents);
      n = selectedRecipe.getNeed(driver.contents);
      available.setText("You have: \n" + a);
      need.setText("You still need: \n" + n);
    }
  }
}