package com.trustwave.baristamatic.drink;

import java.util.List;

public interface IDrinkManager {
	
	List<Drink> getDrinkMenu();
	
	void handleDrinkSelection(int optionNumber);
	
	String getDrinkName(int optionNumber);
	
	boolean isDrinkAvailable(int optionNumber);
}
