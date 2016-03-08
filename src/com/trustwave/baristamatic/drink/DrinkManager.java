package com.trustwave.baristamatic.drink;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.trustwave.baristamatic.Inventory.IInventoryController;
import com.trustwave.baristamatic.Inventory.InventoryController;
import com.trustwave.baristamatic.ingredient.Ingredient;
import com.trustwave.baristamatic.messages.Messages;

/**
 * Drink Manager maintains the list of drinks available in the application. This
 * class updates the drink availability depending on ingredients available in
 * the inventory, and also updates the inventory depending on the drink selected
 * by the user. It also has a utility methods to return the drink name and
 * availability based on option number and drink name .
 * 
 * @author rajput
 *
 */
public class DrinkManager implements IDrinkManager {

	private List<Drink> drinksMenu;
	private IInventoryController inventoryInstance = InventoryController.getInventoryInstance();
	
	public DrinkManager() {
		drinksMenu = new ArrayList<Drink>();
		drinksMenu.add(getCafeLatte());
		drinksMenu.add(getCaffeMocha());
		drinksMenu.add(getCaffeAmericano());
		drinksMenu.add(getCoffee());
		drinksMenu.add(getDecafCoffee());
		drinksMenu.add(getCappuccino());
		// sort the list
		Collections.sort(drinksMenu, new Comparator<Drink>() {
			@Override
			public int compare(Drink d1, Drink d2) {
				return d1.getDrinkName().compareToIgnoreCase(d2.getDrinkName());
			}
		});

		// assign optionNumbers to the drinks according their index in the
		// sorted list
		int i = 1;
		for (Drink d : drinksMenu) {
			d.setOptionNumber(i);
			i++;
		}
	}

	public List<Drink> getDrinkMenu() {
		if (drinksMenu != null)
			return drinksMenu;

		return null;
	}

	private Drink getCaffeAmericano() {
		// prepare caffe americano
		List<Ingredient> coffeeIng = new ArrayList<Ingredient>();
		coffeeIng.add(new Ingredient(Messages.INGREDIENT_ESPRESSO, 3));
		return new Drink(Messages.DRINK_CAFFE_AMERICANO, coffeeIng);
	}

	private Drink getCafeLatte() {
		// prepare caffe latte
		List<Ingredient> coffeeIng = new ArrayList<Ingredient>();
		coffeeIng.add(new Ingredient(Messages.INGREDIENT_ESPRESSO, 2));
		coffeeIng.add(new Ingredient(Messages.INGREDIENT_STEAMED_MILK, 1));
		return new Drink(Messages.DRINK_CAFFE_LATTE, coffeeIng);
	}

	private Drink getCaffeMocha() {
		// prepare caffe mocha
		List<Ingredient> coffeeIng = new ArrayList<Ingredient>();
		coffeeIng.add(new Ingredient(Messages.INGREDIENT_ESPRESSO, 1));
		coffeeIng.add(new Ingredient(Messages.INGREDIENT_COCOA, 1));
		coffeeIng.add(new Ingredient(Messages.INGREDIENT_STEAMED_MILK, 1));
		coffeeIng.add(new Ingredient(Messages.INGREDIENT_WHIPPED_CREAM, 1));
		return new Drink(Messages.DRINK_CAFFE_MOCHA, coffeeIng);
	}

	private Drink getCappuccino() {
		// prepare cappuccino
		List<Ingredient> coffeeIng = new ArrayList<Ingredient>();
		coffeeIng.add(new Ingredient(Messages.INGREDIENT_ESPRESSO, 2));
		coffeeIng.add(new Ingredient(Messages.INGREDIENT_FOAMED_MILK, 1));
		coffeeIng.add(new Ingredient(Messages.INGREDIENT_STEAMED_MILK, 1));
		return new Drink(Messages.DRINK_CAPPUCCINO, coffeeIng);
	}

	private Drink getCoffee() {
		// prepare coffee
		List<Ingredient> coffeeIng = new ArrayList<Ingredient>();
		coffeeIng.add(new Ingredient(Messages.COFFEE, 3));
		coffeeIng.add(new Ingredient(Messages.INGREDIENT_SUGAR, 1));
		coffeeIng.add(new Ingredient(Messages.INGREDIENT_CREAM, 1));
		return new Drink(Messages.COFFEE, coffeeIng);
	}

	private Drink getDecafCoffee() {
		// prepare decaf coffee
		List<Ingredient> coffeeIng = new ArrayList<Ingredient>();
		coffeeIng.add(new Ingredient(Messages.DECAF_COFFEE, 3));
		coffeeIng.add(new Ingredient(Messages.INGREDIENT_SUGAR, 1));
		coffeeIng.add(new Ingredient(Messages.INGREDIENT_CREAM, 1));
		return new Drink(Messages.DECAF_COFFEE, coffeeIng);
	}

	/**
	 * update the inventory depending on the drink selected. i.e. reduce the
	 * inventory ingredients by the number used by the drink
	 * 
	 * @param optionNumber
	 */
	public void handleDrinkSelection(int optionNumber) {
		Drink drink = getDrink(optionNumber);
		if (drink != null) {
			for (Ingredient drinkIng : drink.getIngredients()) {
				for (Ingredient invIng : inventoryInstance.getIngredients()) {
					if (drinkIng.getIngredientName().equals(invIng.getIngredientName())) {
						invIng.setUnits(invIng.getUnits() - drinkIng.getUnits());
					}
				}
			}
		}

		// update the drink availability after updating the inventory
//		updateDrinkAvailibility();
	}
	
	/**
	 * returns the drink name for the option selected
	 * 
	 * @param optionNumber
	 * @return Drink Name if option was found in the list, null otherwise
	 */
	public String getDrinkName(int optionNumber) {
		for (Drink d : getDrinkMenu()) {
			if (d.getOptionNumber() == optionNumber)
				return d.getDrinkName();
		}
		return null;
	}
	
	/**
	 * get the Drink object depending on the menu option number
	 * 
	 * @param optionNumber
	 * @return Drink if its available in the list, null otherwise
	 */
	public Drink getDrink(int optionNumber) {
		for (Drink drink : getDrinkMenu()) {
			if (drink.getOptionNumber() == optionNumber)
				return drink;
		}
		return null;
	}
	

	/**
	 * get the Drink object based on the drink name
	 * 
	 * @param optionNumber
	 * @return Drink if its available in the list, null otherwise
	 */
	public Drink getDrink(String drinkName) {
		for (Drink drink : getDrinkMenu()) {
			if (drink.getDrinkName().equals(drinkName))
				return drink;
		}
		return null;
	}
	
	/**
	 * returns if the drink represented by the given option number is available
	 * 
	 * @param optionNumber
	 * @return true if drink is available, false otherwise
	 */
	public boolean isDrinkAvailable(int optionNumber) {
		return isDrinkAvailable(getDrink(optionNumber));
	}

	/**
	 * returns if the drink represented by given name is available
	 * 
	 * @param drinkName
	 * @return true if drink is available, false otherwise
	 */
	public boolean isDrinkAvailable(String drinkName) {
		return isDrinkAvailable(getDrink(drinkName));
	}
	
	public boolean isDrinkAvailable(Drink drinkSelected) {
		boolean isDrinkAvailable = true;
		if (drinkSelected != null) {
			for (Ingredient i : drinkSelected.getIngredients()) {
				if (inventoryInstance.getIngredientUnits(i.getIngredientName()) < i.getUnits()) {
					isDrinkAvailable = false;
					break;
				}
			}
		}
		return isDrinkAvailable;
	}
	
	public void displayDrink() {
		for (Drink d : getDrinkMenu()) {
			System.out.print(d.getOptionNumber() + ": " + d.getDrinkName() + ", " + "$");
			System.out.printf("%.2f", d.getCost());
			System.out.println(", " + isDrinkAvailable(d.getOptionNumber()));
		}
	}
}
