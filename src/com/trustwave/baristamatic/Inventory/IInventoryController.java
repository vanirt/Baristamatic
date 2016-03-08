package com.trustwave.baristamatic.Inventory;

import java.util.List;

import com.trustwave.baristamatic.ingredient.Ingredient;

public interface IInventoryController {
	
	int totalIngredientUnits = 10;
	
	List<Ingredient> getIngredients();
	
	void resetInventory();
	
	int getIngredientUnits(String ingredientName);
}
