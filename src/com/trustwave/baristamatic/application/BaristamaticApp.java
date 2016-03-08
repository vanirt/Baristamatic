package com.trustwave.baristamatic.application;

import java.util.Scanner;

import com.trustwave.baristamatic.Inventory.InventoryController;
import com.trustwave.baristamatic.drink.DrinkManager;
import com.trustwave.baristamatic.messages.Messages;

/**
 * Main class to launch the Barista-matic application
 * 
 * @author rajput
 *
 */
public class BaristamaticApp {

	private static DrinkManager drinkManager;
	private static InventoryController inventoryController;

	public static void main(String [] args) {

		// initialize the drinkManager and inventoryControllerController
		drinkManager = new DrinkManager();
		inventoryController = InventoryController.getInventoryInstance();

		displayInventoryAndMenu();
		String command = "";
		Scanner scanner = null;
		while (!command.equalsIgnoreCase("q")) {
			scanner = new Scanner(System.in);
			command = scanner.nextLine().trim(); //trim the input because white space should be ignored anyways.
			switch (command) {
			case "":
				break; // ignore white space
			case "1":
			case "2":
			case "3":
			case "4":
			case "5":
			case "6": // handle the drink selected
				int optionNumber = Integer.parseInt(command);
				handleDrinkSelection(optionNumber);
				break;
			case "r":
			case "R": // reset inventory
				inventoryController.resetInventory();
				displayInventoryAndMenu();
				break;
			case "q":
			case "Q": // quit application
				System.out.println(Messages.quitApp);
				break;
			default:
				System.out.println(Messages.invalidInput + command);
			}
		}
		scanner.close();
	}

	public static void print(String message) {
		System.out.println("\n*------------ " + message + " ------------*");
	}
	
	public static void displayStartUpOutput() {
		print(Messages.inventory);
		inventoryController.displayStartupIngredients();;
		displayMenuList();
	}
	
	public static void displayInventoryAndMenu () {
		// Display the ingredient list
		print(Messages.inventory);
		inventoryController.displayIngredients();
		
		// Display the menu
		displayMenuList();

		// Display the options
		print(Messages.selectOption);
		System.out.println(Messages.options);
	}

	// Display the menu
	public static void displayMenuList() {
		print(Messages.menu);
		drinkManager.displayDrink();
	}
	
	public static void handleDrinkSelection (int optionNumber){
		if (!drinkManager.isDrinkAvailable(optionNumber)) {
			System.out.println(Messages.outOfStock + drinkManager.getDrinkName(optionNumber));
		} else {
			drinkManager.handleDrinkSelection(optionNumber);
			displayStartUpOutput(); // include the startup output when dispensing a liquid as per requirements
			printDispensingDrink(optionNumber);
			displayInventoryAndMenu();
		}
	}
	
	public static void printDispensingDrink(int optionNumber) {
		if (drinkManager.getDrinkName(optionNumber) != null)
			System.out.println("\n" + Messages.dispendingDrink + " " + drinkManager.getDrinkName(optionNumber));
	}
	
}
