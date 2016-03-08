package com.trustwave.baristamatic.ingredient;

import com.trustwave.baristamatic.messages.Messages;

/**
 * Ingredient class represents the ingredient object and its associated properties like 
 * name, units and cost.
 * 
 * @author rajput
 */
public class Ingredient {

	private String ingredientName;
	private int units;
	private float cost;
	
	public Ingredient(String ingredientName, int units) {
		this.ingredientName = ingredientName;
		this.units = units;
		
		// assign a cost to the ingredient based on its name
		switch (ingredientName) {
		case Messages.COFFEE:
		case Messages.DECAF_COFFEE:
			this.cost = 0.75f;
			break;
		case Messages.INGREDIENT_SUGAR:
		case Messages.INGREDIENT_CREAM:
			this.cost = 0.25f;
			break;
		case Messages.INGREDIENT_STEAMED_MILK:
		case Messages.INGREDIENT_FOAMED_MILK:
			this.cost = 0.35f;
			break;
		case Messages.INGREDIENT_ESPRESSO:
			this.cost = 1.10f;
			break;
		case Messages.INGREDIENT_COCOA:
			this.cost= 0.90f;
			break;
		case Messages.INGREDIENT_WHIPPED_CREAM:
			this.cost= 1.00f;
			break;
		}
	}
	
	public String getIngredientName() {
		return ingredientName;
	}
	
	public void setIngredientName(String ingredientName) {
		this.ingredientName = ingredientName;
	}
	
	public int getUnits() {
		return units;
	}
	
	public void setUnits(int units) {
		this.units = units <= 0 ? 0 : units;
	}
	
	public float getCost() {
		return cost;
	}
	
}
