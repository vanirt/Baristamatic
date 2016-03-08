package com.trustwave.baristamatic.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.trustwave.baristamatic.Inventory.InventoryController;
import com.trustwave.baristamatic.drink.Drink;
import com.trustwave.baristamatic.drink.DrinkManager;
import com.trustwave.baristamatic.ingredient.Ingredient;
import com.trustwave.baristamatic.messages.Messages;

import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Test Class for Barista-matic Application
 * @author rajput
 *
 */
public class BaristamaticAppTest extends TestCase {

	private static DrinkManager drinkManager;
	private static InventoryController inventoryController;
	private static Map<Integer, String> expectedDrinkMenu;
	
	public static TestSuite suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(BaristamaticAppTest.getOrderedTests());
        suite.setName(BaristamaticAppTest.class.getSimpleName());
        return suite;
    }

    public static TestSuite getOrderedTests() {
        TestSuite testSuite = new TestSuite();

        testSuite.addTest(TestSuite.createTest(BaristamaticAppTest.class, "initalize"));
        testSuite.addTest(TestSuite.createTest(BaristamaticAppTest.class, "testDrinkMenu"));
        testSuite.addTest(TestSuite.createTest(BaristamaticAppTest.class, "testDrinkOptionNumber"));
        testSuite.addTest(TestSuite.createTest(BaristamaticAppTest.class, "testDrinkIngredients"));
        testSuite.addTest(TestSuite.createTest(BaristamaticAppTest.class, "testInventory"));
        testSuite.addTest(TestSuite.createTest(BaristamaticAppTest.class, "testDrinkAvailability"));
        testSuite.addTest(TestSuite.createTest(BaristamaticAppTest.class, "testIsInventoryUpdated"));
        testSuite.addTest(TestSuite.createTest(BaristamaticAppTest.class, "testResetInventory"));

        return testSuite;
    }
    
    public void initalize() {
		drinkManager = new DrinkManager();
		inventoryController = InventoryController.getInventoryInstance();
		expectedDrinkMenu = new HashMap<>();
		expectedDrinkMenu.put(1, Messages.DRINK_CAFFE_AMERICANO);
		expectedDrinkMenu.put(2, Messages.DRINK_CAFFE_LATTE);
		expectedDrinkMenu.put(3, Messages.DRINK_CAFFE_MOCHA);
		expectedDrinkMenu.put(4, Messages.DRINK_CAPPUCCINO);
		expectedDrinkMenu.put(5, Messages.COFFEE);
		expectedDrinkMenu.put(6, Messages.DECAF_COFFEE);
    }
    
    @Test //tests if all the drinks are present in the menu
    public void testDrinkMenu () {
    	int i =1;
    	for(Drink d: drinkManager.getDrinkMenu()) {
    		assertTrue("Drink "+ d.getDrinkName() + " was not found in the menu", d.getDrinkName().equals(expectedDrinkMenu.get(i)));
    		assertFalse("Drink Dark Roast was found in the menu", d.getDrinkName().equals("Dark Roast"));
    		i++;
    	}
    }
    
    @Test //tests if the drinks are assigned right option number
    public void testDrinkOptionNumber () {
    	System.out.println("Drinks are assigned following option numbers");
    	int currentKey = 0;
    	String currentDrinkName = null;
    	for(Drink d: drinkManager.getDrinkMenu()) {
    		currentKey = d.getOptionNumber();
    		if(expectedDrinkMenu.containsKey(currentKey)) {
    			currentDrinkName = d.getDrinkName();
    			assertTrue("The drink " + currentDrinkName + "is assigned wrong option number: " + currentKey, currentDrinkName.equals(expectedDrinkMenu.get(currentKey)));
    			System.out.println(currentKey + ": " + currentDrinkName);
    		}
    	}
    }
    
    @Test //tests the ingredients for each drink type
    public void testDrinkIngredients() {
    	System.out.println("\nIngredient name and number of units consumed by drinks.");
    	for(Drink d: drinkManager.getDrinkMenu()) {
    		switch(d.getDrinkName()) { 
    		case Messages.DRINK_CAFFE_AMERICANO:
    			System.out.println(d.getDrinkName() + ":");
    			for(Ingredient ingredient: d.getIngredients()) {
    				if(ingredient.getIngredientName().equals(Messages.INGREDIENT_ESPRESSO)) {
    					assertTrue(ingredient.getUnits() == 3);
    				}
					System.out.println(ingredient.getIngredientName() + ": " + ingredient.getUnits() + " units");
    			}
    			break;
    		case Messages.DRINK_CAFFE_LATTE:
    			System.out.println(d.getDrinkName() + ":");
    			for(Ingredient ingredient: d.getIngredients()) {
    				if(ingredient.getIngredientName().equals(Messages.INGREDIENT_ESPRESSO))
    					assertTrue(ingredient.getUnits() == 2);
    				if(ingredient.getIngredientName().equals(Messages.INGREDIENT_STEAMED_MILK))
    					assertTrue(ingredient.getUnits() == 1);
					System.out.println(ingredient.getIngredientName() + ": " + ingredient.getUnits() + " units");
    			}
    		//TODO: do similar check for all the drinks
    		}
    	}
    }
    
    @Test //tests if each ingredient in the inventory is set to 10 units initially 
    public void testInventory() {
    	for(Ingredient i : inventoryController.getIngredients())
    		assertTrue("Total initial units for ingredient " + i.getIngredientName() + " is not set to " + i.getUnits(), i.getUnits() == 10);
    }
    
    @Test  //tests if the drink is available if the ingredients drink requires are not in stock
    public void testDrinkAvailability() {
    	//Select drink Caffe Americano 4 times to set the esspresso level to 0 unit. Then check if drinks using espresso ingredient are available or not
    	int i = 4;
    	while(i > 0) {
    		drinkManager.handleDrinkSelection(1);
    		i--;
    	}
    	
    	assertFalse("Drink " + Messages.DRINK_CAFFE_AMERICANO + " should be marked not available but is available.", drinkManager.isDrinkAvailable(Messages.DRINK_CAFFE_AMERICANO));
    	assertFalse("Drink " + Messages.DRINK_CAFFE_LATTE + " should be marked not available but is available.",drinkManager.isDrinkAvailable(Messages.DRINK_CAFFE_LATTE));
    	assertFalse("Drink " + Messages.DRINK_CAFFE_MOCHA + " should be marked not available but is available.",drinkManager.isDrinkAvailable(Messages.DRINK_CAFFE_MOCHA));
    	assertFalse("Drink " + Messages.DRINK_CAPPUCCINO + " should be marked not available but is available.",drinkManager.isDrinkAvailable(Messages.DRINK_CAPPUCCINO));
    	
    	//reset the inventory
    	inventoryController.resetInventory();
    	
    	assertTrue("Drink " + Messages.DRINK_CAFFE_AMERICANO + " is not available after reset.", drinkManager.isDrinkAvailable(Messages.DRINK_CAFFE_AMERICANO));
    	assertTrue("Drink " + Messages.DRINK_CAFFE_LATTE + " is not available after reset.",drinkManager.isDrinkAvailable(Messages.DRINK_CAFFE_LATTE));
    	assertTrue("Drink " + Messages.DRINK_CAFFE_MOCHA + " is not available after reset.",drinkManager.isDrinkAvailable(Messages.DRINK_CAFFE_MOCHA));
    	assertTrue("Drink " + Messages.DRINK_CAPPUCCINO + " is not available after reset.",drinkManager.isDrinkAvailable(Messages.DRINK_CAPPUCCINO));
    	
    	//check availability of coffee
    	i= 4;
    	while(i > 0) {
    		drinkManager.handleDrinkSelection(5);
    		i--;
    	}
    	assertFalse("Drink " + Messages.COFFEE + " should be marked not available but is available.", drinkManager.isDrinkAvailable(Messages.COFFEE));

    	//reset the inventory
    	inventoryController.resetInventory();
    	
    	//TODO: Do similar tests for all the possible combinations
    }
    
    @Test //tests if inventory is updated after a drink is selected
    public void testIsInventoryUpdated() {
    	//check availability of coffee
    	int j= 4;
    	while(j > 0) {
    		drinkManager.handleDrinkSelection(6);
    		j--;
    	}
    	assertFalse("Drink " + Messages.DECAF_COFFEE + " should be marked not available but is available.", drinkManager.isDrinkAvailable(Messages.DECAF_COFFEE));
    	
    	//after selecting decaf coffee 4 times, decaf coffee ingredient should be set to 0 and sugar and cream to 6
    	for(Ingredient i : inventoryController.getIngredients()) {
    		if(i.getIngredientName().equals(Messages.DECAF_COFFEE))
    			assertTrue("Inventory units for " + i.getIngredientName() + " should be set to 0 but is " + i.getUnits(), i.getUnits() == 0);
    		if(i.getIngredientName().equals(Messages.INGREDIENT_SUGAR))
    			assertTrue("Inventory units for " + i.getIngredientName() + " should be set to 6 but is " + i.getUnits(), i.getUnits() == 6);
    		if(i.getIngredientName().equals(Messages.INGREDIENT_CREAM))
    			assertTrue("Inventory units for " + i.getIngredientName() + " should be set to 6 but is " + i.getUnits(), i.getUnits() == 6);
    	}
    	
    	inventoryController.resetInventory();
    	
    	j = 3;
    	while(j > 0) {
    		drinkManager.handleDrinkSelection(3);
    		j--;
    	}
    	
    	for(Ingredient i : inventoryController.getIngredients()) {
    		if(i.getIngredientName().equals(Messages.INGREDIENT_ESPRESSO))
    			assertTrue("Inventory units for " + i.getIngredientName() + " should be set to 7 but is " + i.getUnits(), i.getUnits() == 7);
    		if(i.getIngredientName().equals(Messages.INGREDIENT_COCOA))
    			assertTrue("Inventory units for " + i.getIngredientName() + " should be set to 7 but is " + i.getUnits(), i.getUnits() == 7);
    		if(i.getIngredientName().equals(Messages.INGREDIENT_STEAMED_MILK))
    			assertTrue("Inventory units for " + i.getIngredientName() + " should be set to 7 but is " + i.getUnits(), i.getUnits() == 7);
    		if(i.getIngredientName().equals(Messages.INGREDIENT_WHIPPED_CREAM))
    			assertTrue("Inventory units for " + i.getIngredientName() + " should be set to 7 but is " + i.getUnits(), i.getUnits() == 7);
    	}
    	
    	inventoryController.resetInventory();
    	//TODO: add similar test cases for all possible drink selections
    }
    
    @Test //tests if inventory is reset after reset inventory option is selected by the user
    public void testResetInventory() {
    	int j = 0;
    	while(j < 4) {
    		drinkManager.handleDrinkSelection(1); //select americano thrice
    		j++;
    	}
    	
		drinkManager.handleDrinkSelection(3); //select mocha once. This will cause espresso ingredient to go out of stock
		
		j=0;
		while(j < 4) {
    		drinkManager.handleDrinkSelection(5); //select coffee thrice
    		j++;
    	}
		
		j=0;
		while(j < 4) {
    		drinkManager.handleDrinkSelection(6); //select decaf coffee thrice
    		j++;
    	}
		
    	//ingredients consumed by the above drinks selected should not be set to 10 units in the inventory
    	for(Ingredient i : inventoryController.getIngredients()) {
    		if(i.getIngredientName().equals(Messages.INGREDIENT_ESPRESSO))
    			assertTrue("Inventory units for " + i.getIngredientName() + " should not be set to 10", i.getUnits() != 10);
    		if(i.getIngredientName().equals(Messages.INGREDIENT_STEAMED_MILK))
    			assertTrue("Inventory units for " + i.getIngredientName() + " should not be set to 10", i.getUnits() != 10);
    		if(i.getIngredientName().equals(Messages.INGREDIENT_COCOA))
    			assertTrue("Inventory units for " + i.getIngredientName() + " should not be set to 10", i.getUnits() != 10);
    		if(i.getIngredientName().equals(Messages.COFFEE))
    			assertTrue("Inventory units for " + i.getIngredientName() + " should not be set to 10", i.getUnits() != 10);
    		if(i.getIngredientName().equals(Messages.DECAF_COFFEE))
    			assertTrue("Inventory units for " + i.getIngredientName() + " should not be set to 10", i.getUnits() != 10);
    	}
    	    	
    	//none of the drinks should be available now
    	for(Drink d: drinkManager.getDrinkMenu()) 
    		assertFalse(d.getDrinkName() + " should not be available", drinkManager.isDrinkAvailable(d.getOptionNumber()));
    	
    	inventoryController.resetInventory();
    	
    	//after inventory is reset, all the ingredient's units should be set to 10
    	for(Ingredient i : inventoryController.getIngredients()) {
    		assertTrue("After reset, ingredient " + i.getIngredientName() + "is set to " + i.getUnits() + " units instead it should be set to 10 units", i.getUnits() == 10);
    	}
    	
    	//all the drinks must be available now
    	for(Drink d: drinkManager.getDrinkMenu()) 
    		assertTrue(d.getDrinkName() + " is not available after resetting inventory", drinkManager.isDrinkAvailable(d.getOptionNumber()));
    }
    
}
