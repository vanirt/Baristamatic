package com.trustwave.baristamatic.drink;

import java.util.ArrayList;
import java.util.List;

import com.trustwave.baristamatic.ingredient.Ingredient;

/**
 * Drink class represents the drink object and its associated properties 
 * like drink name, ingredients, cost, option number in the menu and drink availability
 * @author rajput
 *
 */
public class Drink {

	private String drinkName;
	private List<Ingredient> ingredients = new ArrayList<Ingredient>();
	private float cost;
	private int optionNumber;

	public Drink(String drinkName, List<Ingredient> ingredients) {
		this.ingredients = ingredients;
		this.drinkName = drinkName;
		this.cost = setCost(); // calculate the cost based on the ingredient's cost
	}

	public String getDrinkName() {
		return drinkName;
	}

	public void setDrinkName(String drinkName) {
		this.drinkName = drinkName;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}
	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public float getCost() {
		return cost;
	}

	// calculate the cost based on the ingredient units used in the drink
	public float setCost() {
		float cost = 0.0f;
		for (Ingredient i : getIngredients())
			cost += i.getUnits() * i.getCost();

		cost = Math.round(cost * 100.0f) / 100.0f;
		return cost;
	}

	public int getOptionNumber() {
		return optionNumber;
	}

	public void setOptionNumber(int optionNumber) {
		this.optionNumber = optionNumber;
	}

}
