/*****************************************************************
  * FILE NAME: User.java
  * WHO: Jenny Wang and Lily Xie
  * WHEN: May 18, 2014
  * 
  * WHAT: Recipe represents a recipe that contains a list of 
  * ingredients and a score, which represents how easy or hard it 
  * would be for a user to make that recipe. 
  * This class contains methods to:
  *  - Search for whether a Recipe contains a certain ingredient
  *  - Increment the score based on the availability of certain 
  *    ingredients
  *  - Get Recipe information such as the name, ingredient list, 
  *    score, and size
  ****************************************************************/

import java.util.*;

public class Recipe implements Comparable<Recipe> {
  
  public String name;
  private LinkedList<String> ingredientList;
  private int score;
  
  /****************************************************************
    * Constructor creates the Recipe object. The ingredientList is
    * initialized as empty, and the score is initialized to zero.
    * 
    * @param String name is the name of the Recipe
    **************************************************************/
  public Recipe(String name) {
    this.name = name;
    ingredientList = new LinkedList<String>(); 
    score = 0; 
  }
  
  /****************************************************************
    * addIngredient adds an ingredient i to ingredientList, given
    * that i does not already exist in ingredientList. If the
    * ingredient i already exists, no action is taken.
    * 
    * @param String i is the ingredient being added
    **************************************************************/
  public void addIngredient(String i) {
   if (!ingredientList.contains(i)) ingredientList.add(i);
  }
  
  /****************************************************************
    * getAvailable takes in a LinkedList of given ingredients
    * and returns which of those ingredients are available in the
    * Recipe. This method loops through given and adds the ingredients
    * that are containd in ingredientList to a String result, which is
    * eventually returned.
    * 
    * This method will be used later in our GUI to display
    * which items in the user's fridge can be used in a given
    * recipe. 
    * 
    * @param LinkedList<String> given is the list of given ingredients
    **************************************************************/
  public String getAvailable(LinkedList<String> given) {
    String result = ""; 
    
    for(int i = 0; i < given.size(); i++) {
      if(this.contains(given.get(i))) { //if element is in ingredientList
        result += " " + given.get(i) + "\n"; //add element to string result
      }
    }
    
    return result;
  }
  
  /****************************************************************
    * getNeed takes in a LinkedList of given ingredients and returns 
    * which of those ingredients are not available and needed in the
    * Recipe. This method creates a temporary LinkedList<String>, 
    * created by cloning ingredientList, in order to not destroy
    * ingredientList when traversing through it. getNeed then
    * loops through tempIngredients, popping off each element, and
    * adds an ingredient to the String result if it is not contained
    * in the given list (that is, if the ingredient is in this Recipe's
    * ingredientList and not in the given list).
    * 
    * This method will be used later in our GUI to display
    * which items in the user's fridge are still needed in a given
    * recipe. 
    * 
    * @param LinkedList<String> given is the list of given ingredients
    **************************************************************/
  public String getNeed(LinkedList<String> given) {
    String result = "";
    
    LinkedList<String> tempIngredients = (LinkedList<String>)/*cast*/ingredientList.clone();
    
    while (!tempIngredients.isEmpty()) { 
      String i = tempIngredients.remove();
      if (!given.contains(i)) //if this ingredient is not in the given list
        result += " " + i + "\n"; //add the element to result
    }
    
    return result;
  }
  
  /****************************************************************
    * contains does the same duty as the contains method for 
    * LinkedList. It takes in a search String and returns whether
    * the ingredientList contains that search.
    * 
    * @param String search is the ingredient being searched for
    **************************************************************/
  public boolean contains(String search) {
    return ingredientList.contains(search);
  }
  
   /****************************************************************
    * compareTo implements Comparable and compares two Recipes
    * based on their scores. 
    * 
    * @param Recipe other recipe to be compared
    **************************************************************/
  public int compareTo(Recipe other) {
    if (this.score > other.score) return -1;
    else if (this.score < other.score) return 1;
    else return 0;
  }
  
  /****************************************************************
    * incrementScore increases the score by one. This method
    * will be used in the User class. 
    **************************************************************/
  public void incrementScore() {
   score++; 
  }
  
  /****************************************************************
    * getName returns the Recipe's name
    **************************************************************/
  public String getName() {
    return name; 
  }
  
  /****************************************************************
    * getScore returns the Recipe's score
    **************************************************************/
  public int getScore() {
    return score; 
  }
  
  /****************************************************************
    * getIngredients returns the ingredientList
    **************************************************************/
  public LinkedList<String> getIngredients() {
    return ingredientList; 
  }
  
  /****************************************************************
    * length returns the Recipe's length, represented by the size
    * of its ingredientList.
    **************************************************************/
  public int length() {
    return ingredientList.size();
  }
  
  /****************************************************************
    * toString returns a String representation of the Recipe, 
    * including its name, length, and ingredient list.
    **************************************************************/
  public String toString() {
    String result = name + " has a score of " + getScore() + 
      " and contains " + length() + " ingredients: " 
      + ingredientList.toString();
    return result;
  }
  
  
  public static void main(String[] args) {
    //testing constructor
    System.out.println("Creating: Cake");
    Recipe test = new Recipe("Cake");
    
    //testing addIngredient
    System.out.println("\nTesting addIngredient");
    System.out.println("Adding: flour, egg, milk");
    test.addIngredient("flour");
    test.addIngredient("egg");
    test.addIngredient("milk");
    System.out.println("Adding duplicate");
    test.addIngredient("flour");
    System.out.println("Expected: flour, egg, milk\tActual: " + test);
    
    //testing getAvailable and getNeed
    //creating a random LinkedList for testing
    System.out.println("\nTesting getAvailable and getNeed");
    System.out.println("Creating ingredient list: flour, junk, cats");
    LinkedList<String> x = new LinkedList<String>();
    x.add("flour");
    x.add("junk");
    x.add("cats");
    System.out.println("Expected: flour\tActual: " + test.getAvailable(x));
    System.out.println("Expected: egg, milk\tActual: " + test.getNeed(x));
    
    //testing contains
    System.out.println("\nTesting contains");
    System.out.println("Expected: true\tActual: " + test.contains("flour"));
    System.out.println("Expected: false\tActual: " + test.contains("false"));

    //testing incrementScore
    System.out.println("\nTesting incrementscore");
    System.out.println("Incrementing score twice");
    test.incrementScore();
    test.incrementScore();
    System.out.println("Expected score: 2\tActual: " + test);
    
    //testing compareTo
    System.out.println("\nTesting compareTo");
    System.out.println("Creating a new Recipe of score 1");
    Recipe comp = new Recipe("Cookie");
    comp.incrementScore();
    System.out.println("Expected: -1\tActual: " + test.compareTo(comp));
    System.out.println("Expected: 1\tActual: " + comp.compareTo(test));
  }
  
}