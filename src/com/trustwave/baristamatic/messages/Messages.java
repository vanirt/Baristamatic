package com.trustwave.baristamatic.messages;

/**
 * Messages class store all the messages displayed to the user and constants used in the application 
 * 
 * @author rajput
 *
 */
public class Messages {

	// common to both drink options and ingredients
	public static final String COFFEE = "Coffee";
	public static final String DECAF_COFFEE = "Decaf Coffee";

	// Drink options
	public static final String DRINK_CAFFE_LATTE = "Caffe Latte";
	public static final String DRINK_CAFFE_AMERICANO = "Caffe Americano";
	public static final String DRINK_CAFFE_MOCHA = "Caffe Mocha";
	public static final String DRINK_CAPPUCCINO = "Cappuccino";

	// Ingredients
	public static final String INGREDIENT_SUGAR = "Sugar";
	public static final String INGREDIENT_CREAM = "Cream";
	public static final String INGREDIENT_STEAMED_MILK = "Steamed Milk";
	public static final String INGREDIENT_FOAMED_MILK = "Foamed Milk";
	public static final String INGREDIENT_ESPRESSO = "Espresso";
	public static final String INGREDIENT_COCOA = "Cocoa";
	public static final String INGREDIENT_WHIPPED_CREAM = "Whipped Cream";

	// Display message
	public static String menu = "Drinks Menu";
	public static String inventory = "Inventory";
	public static String selectOption = "Options";
	public static String options = "1-6: Drinks \nr/R: Reset Inventory \nq/Q: Quit Application";
	public static String dispendingDrink = "Dispensing: ";
	public static String invalidInput = "Invalid Selection: ";
	public static String outOfStock = "Out of stock: ";
	public static String quitApp = "Quitting Application";

}
