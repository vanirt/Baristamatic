package com.trustwave.baristamatic.Inventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.trustwave.baristamatic.ingredient.Ingredient;
import com.trustwave.baristamatic.messages.Messages;

/**
 * InventoryController class maintains the inventory of ingredient's for
 * Barista-matic. This is a singleton class because same instance of the class
 * must be shared among all the clients. This will ensure that the inventory
 * state is consistent throughout the application and updating and resetting
 * inventory will not introduce any inconsistencies.
 * 
 * @author rajput
 *
 */
public class InventoryController implements IInventoryController {

	private List<Ingredient> ingredientsInventory;

	private InventoryController() {
		initializeInventory();
	}

	private void initializeInventory() {
		ingredientsInventory = new ArrayList<Ingredient>();
		ingredientsInventory.add(new Ingredient(Messages.INGREDIENT_COCOA, totalIngredientUnits));
		ingredientsInventory.add(new Ingredient(Messages.COFFEE, totalIngredientUnits));
		ingredientsInventory.add(new Ingredient(Messages.INGREDIENT_CREAM, totalIngredientUnits));
		ingredientsInventory.add(new Ingredient(Messages.DECAF_COFFEE, totalIngredientUnits));
		ingredientsInventory.add(new Ingredient(Messages.INGREDIENT_ESPRESSO, totalIngredientUnits));
		ingredientsInventory.add(new Ingredient(Messages.INGREDIENT_FOAMED_MILK, totalIngredientUnits));
		ingredientsInventory.add(new Ingredient(Messages.INGREDIENT_STEAMED_MILK, totalIngredientUnits));
		ingredientsInventory.add(new Ingredient(Messages.INGREDIENT_SUGAR, totalIngredientUnits));
		ingredientsInventory.add(new Ingredient(Messages.INGREDIENT_WHIPPED_CREAM, totalIngredientUnits));

		// sort the inventory list based on the ingredient name
		Collections.sort(ingredientsInventory, new Comparator<Ingredient>() {
			@Override
			public int compare(Ingredient i1, Ingredient i2) {
				return i1.getIngredientName().compareToIgnoreCase(i2.getIngredientName());
			}
		});
	}

	// singleton with concept of Initialization-on-demand holder
	private static class InventoryInstanceHolder {
		private static InventoryController inventoryInstance = new InventoryController();

		public static InventoryController getInstance() {
			return InventoryInstanceHolder.inventoryInstance;
		}
	}

	public static InventoryController getInventoryInstance() {
		return InventoryInstanceHolder.getInstance();
	}

	public List<Ingredient> getIngredients() {
		if (ingredientsInventory != null)
			return ingredientsInventory;

		return null;
	}

	/**
	 * re initialize the inventory
	 */
	public void resetInventory() {
		initializeInventory();
	}

	/**
	 * return ingredient's units available depending on the ingredient Name
	 * 
	 * @param ingredientName
	 * @return units: no. of units available
	 */
	public int getIngredientUnits(String ingredientName) {
		for (Ingredient i : getIngredients()) {
			if (i.getIngredientName().equals(ingredientName)) {
				return i.getUnits();
			}
		}
		return -1;
	}
	
	// display ingredients with current units in stock
	public void displayIngredients(){
		for (Ingredient i : getIngredients())
			System.out.println(i.getIngredientName() + ", " + i.getUnits());
	}
	
	// display the ingredients with total initial units. 
	public void displayStartupIngredients(){
		for (Ingredient i : getIngredients())
			System.out.println(i.getIngredientName() + ", " + totalIngredientUnits);
	}
}
