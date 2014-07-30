/****************************************************************
  * FILE NAME: User.java
  * WHO: Jenny Wang and Lily Xie
  * WHEN: May 18, 2014
  * 
  * WHAT: This class initializes a User, which contains 
  * a list of items the user has in their fridge and a
  * recipe box. The recipes in the recipe box are read in
  * from a .txt file, read either from a user inputted file,
  * if they have their own collection of recipes, or a 
  * default file that we have written. 
  * 
  * This class contains methods to:
  *  - Add or remove items the user has in their fridge
  *  - Score all recipes and determine the ones best suited 
  *    for the user based on the items available in 
  *    their fridge
  *  - Getters
  ***************************************************************/

import java.util.*;
import java.io.*;

public class User {
  
  private String name; //user's name
  public PriorityQueue<Recipe> recipeBox; 
  public LinkedList<String> contents;
  
  /****************************************************************
   * Constructor creates the User and initializes a recipeBox 
   * by reading from a given file. It also initalizes the contents
   * of the user's fridge as empty (to be filled in later with
   * addContent or removeContent).
   * 
   * @param String name is the user's name
   * @param String recipeFileName is the filename of the .txt
   * document containing the recipes
   **************************************************************/
  public User(String name, String recipeFileName) {
    this.name = name;
    
    recipeBox = new PriorityQueue<Recipe>(); //initialize as empty
    readRecipes(recipeFileName); //read recipe
    
    contents = new LinkedList<String>(); //initializes as empty
  }
  
  /****************************************************************
   * Second constructor creates a User reading from the default 
   * recipe file. This can be updated in future versions to
   * read from a more updated file. This constructor calls the
   * previous constructor.
   * 
   * @param String name is the user's name
   **************************************************************/
  public User (String name) {
   this (name, "recipes0516.txt");
  }

  /****************************************************************
   * readRecipes is a private method used in the constructor to
   * populate recipeBox. It performs a try/catch of reading a 
   * .txt file, with the scanner stopping for delimiters.
   * The format of the .txt file is:
   * 
   * Recipe name
   * . //delimiter for ingredients
   * Ingredient
   * . 
   * //etc...
   * Last ingredient
   * # //delimiter for recipes
   * 
   * This method catches a filenotfound exception and prints out
   * an error message. 
   * 
   * @param String fileName is the name of the file being read
   **************************************************************/
  private void readRecipes(String fileName) {
    try {
      Scanner reader = new Scanner(new File(fileName));
      
      while (reader.hasNextLine()) {
        
        //creates a recipe
        String recipeName = reader.nextLine();
        Recipe r = new Recipe(recipeName);
        
        //reads in recipe's ingredients
        while (!reader.nextLine().equals("#")) {
          String ingredient = reader.nextLine();
          r.addIngredient(ingredient);
        }
        
        //adds recipe to recipeBox
        recipeBox.add(r);
      }
      
      reader.close();
      
    }catch (FileNotFoundException e) {
      System.out.println("Inputed recipe file not found"); 
    }
  }
  
  
  /****************************************************************
   * addContent adds an item to the contents of the user's fridge
   * if the item does not already exist.
   * 
   * @param String ingredient is the item being added to contents
   **************************************************************/
  public void addContent(String ingredient) {
    if (!contents.contains(ingredient)) contents.add(ingredient);
  }
  
  /****************************************************************
   * removeContent removes an item from the contents of the user's 
   * fridge if the item is contained in contents
   * 
   * @param String ingredient is the item being removed
   **************************************************************/
  public void removeContent(String ingredient) {
    if (contents.contains(ingredient)) contents.remove(ingredient);
  }

  /****************************************************************
   * calculateScores calculates the scores of all recipes in 
   * recipeBox based on the user's fridge's contents. It uses
   * two nested for loops to:
   * 1) visit every recipe in recipeBox
   * 2) loop through the contents of the recipe and increment
   * the score of the recipe if a particular item is available
   * 
   * It creates a temporary PriorityQueue<Recipe> in order to
   * nondestructively traverse recipeBox, and eventually reassigns
   * recipeBox to the temporary queue.
   **************************************************************/
  public void calculateScores() {
    PriorityQueue<Recipe> newRecipes = new PriorityQueue<Recipe>();
    
    //outer for loop visits each element in recipeBox
    while (!recipeBox.isEmpty()) {
      Recipe r = recipeBox.remove(); //pops each element in recipeBox
      
      //inner for loop tests recipe for each ingredient
      for (int j = 0; j < contents.size(); j++) { 
        String ingredient = contents.get(j); 
        if (r.contains(ingredient)) r.incrementScore(); 
      }
      
      newRecipes.add(r); //reconstructs recipeBox
    }
    
    recipeBox = newRecipes;
  }
  
  /****************************************************************
   * topRecipes returns a String[] of the top three most appropriate
   * recipes. In the future, this method could be revised to 
   * return @param n most appropriate recipes. This method 
   * creates a temporary PriorityQueue<Recipe> in order to 
   * non-destructively traverse recipeBox. It adds the first three
   * recipes in recipeBox (sorted because it is a PriorityQueue)
   * to the String[] and then reconstructs the PriorityQueue.
   * 
   * KNOWN BUG: if there are fewer than three recipes, this method
   * will run into a NoSuchElement exception. However, we only 
   * plan to read from the default recipe list, which contains
   * 10 recipes, in this application. In the future, if 
   * we were to revise it to return n recipes, we would have to
   * make sure n > 0 && n < the number of recipes. 
   **************************************************************/
  public String[] topRecipes() {
    String[] s = new String[3];  
    
    PriorityQueue<Recipe> temp = new PriorityQueue<Recipe>(); 
    
    //add top 3 recipes
    for (int i = 0; i < 3; i++) {
      Recipe r = recipeBox.remove();
      s[i] = r.getName();
      temp.add(r);
    }
    
    //reconstruct recipeBox
    while (!recipeBox.isEmpty()) {
      temp.add(recipeBox.remove());
    }
    
    recipeBox = temp;
    return s;
  }

  /****************************************************************
   * getRecipeNames returns a String[] of all recipe names in 
   * recipeBox. This method creates a temporary PriorityQueue<Recipe> 
   * in order to non-destructively traverse recipeBox. It calls
   * the getName() method for each Recipe and saves that String to
   * the String[] names, to be returned at the end of the method.
   * Finally, it reassigns recipeBox as the temporary queue.
   **************************************************************/
  public String[] getRecipeNames() {
    String[] names = new String[recipeBox.size()];
    PriorityQueue<Recipe> newRecipes = new PriorityQueue<Recipe>();

    //traverse recipeBox
    int i = 0;
    while (!recipeBox.isEmpty()) {
      Recipe r = recipeBox.remove(); //pop Recipe
      names[i] = r.getName();
      newRecipes.add(r); //add name
      i++; //increment
    }
    
    recipeBox = newRecipes;
    return names;
  }
  
  /****************************************************************
   * findInList takes in a String name and returns the Recipe 
   * associated with it. It non-destructively traverses recipeBox
   * and returns the Recipe whose name matches @param String 
   * name being searched for, and an error message if the recipe
   * does not exist. 
   **************************************************************/
  public Recipe findInList(String name) {
    PriorityQueue<Recipe> newRecipes = new PriorityQueue<Recipe>();
    
    Recipe returnRecipe = new Recipe("Recipe does not exist");
    
    while (!recipeBox.isEmpty()) { 
      Recipe r = recipeBox.remove(); //pops each element in recipeBox
      if (r.getName().equals(name)) //if Recipe with given name is found
        returnRecipe = r; //set returnRecipe
      newRecipes.add(r); //reconstructs recipeBox
    }
    
    recipeBox = newRecipes;
    return returnRecipe;
  }
  
  /****************************************************************
   * getContents non-destructively traverses the contents 
   * LinkedList and returns a String representation of all contents
   * separated by a new line.
   **************************************************************/
  public String getContents() {
    String s = "";
    LinkedList<String> newContents = new LinkedList<String>();
    
    //traverse contents
    while (!contents.isEmpty()) { 
      String c = contents.remove(); 
      s += c.toString() + "\n";
      newContents.add(c); //reconstructs recipeBox
    }
    
    contents = newContents;
    return s; 
  }
  
  /****************************************************************
   * getRecipes non-destructively traverses the recipeBox 
   * PriorityQueue and returns a String representation of all recipes
   * separated by a new line.
   **************************************************************/
  public String getRecipes() {
    String s = "";
    PriorityQueue<Recipe> newRecipes = new PriorityQueue<Recipe>();
    
    //traverse recipeBox
    while (!recipeBox.isEmpty()) { 
      Recipe r = recipeBox.remove(); 
      s += r.toString() + "\n";
      newRecipes.add(r); //reconstructs recipeBox
    }
    
    recipeBox = newRecipes;
    return s; 
  }
  
  /****************************************************************
   * toString returns a String representation of the user.
   **************************************************************/
  public String toString() {
   String r = name + " has " + contents.size() + " items in the fridge:\n" 
     + getContents() + "\n" + name + " has " + recipeBox.size() 
     + " recipes in the recipe box:\n" + getRecipes();
   return r;
  }
  
  
  public static void main(String[] args) {
    //testing constructor
    System.out.println("Testing constructor");
    System.out.print("Expected: error\tActual: ");
    User errorTest = new User("ERRORTEST", "nonexistent.txt");
    User test = new User("TEST", "recipes0516.txt");
    User emptyTest = new User("TEST", "EmptyRecipes.txt");
    
    //testing addContent and removeContent
    System.out.println("\nTesting addContent and removeContent");
    System.out.println("Adding Peppers, Bacon, Bacon, Mayo, Chips, w@Cky, remove");
    test.addContent("Peppers");
    test.addContent("Bacon");
    test.addContent("Bacon"); //testing duplicates
    test.addContent("Mayo");
    test.addContent("Chips");
    test.addContent("w@Cky"); //testing weird shit
    test.addContent("remove");
    System.out.println("Removing remove");
    test.removeContent("remove");
    System.out.println("Removing nonexistent");
    test.removeContent("nonexistent");
    System.out.println("Expected: [Peppers, Bacon, Mayo, Chips, w@Cky]\tActual: " + test.contents);
    //do we need a printContents method??? i don't think so
    
    //testing calculateScores
    System.out.println("\nTesting calculateScores");
    test.calculateScores();
    emptyTest.calculateScores();
    System.out.println("Expected: Chicken Salad Sandwich, BLT, Ham Sandwich,...\tActual: " 
                         + test.recipeBox);
    
    //testing topRecipes
    System.out.println("\nTesting topRecipes");
    System.out.println("Expected: Chicken Salad Sandwich, BLT, Ham Sandwich \tActual: " + Arrays.toString(test.topRecipes()));
   
    //testing getRecipeNames
    System.out.println("\nTesting getRecipeNames");
    System.out.println("Expected: empty\tActual: " + Arrays.toString(emptyTest.getRecipeNames()));
    System.out.println("Expected: Chicken Salad Sandwich, BLT,...\tActual: " + Arrays.toString(test.getRecipeNames()));

    //testing findInList
    System.out.println("\nTesting findInList");
    System.out.println("Expected: error\tActual: " + test.findInList("error"));
    System.out.println("Expected: PBnJ\tActual: " + test.findInList("PBnJ"));

  }
  
}