package com.jacl.capstone.hud.world;

import com.jacl.capstone.hud.HUD;

public class MoneyCount
{
	public HUD hud;
	public int amount;
	
	public MoneyCount(HUD hud){
		this.hud = hud;
		amount = 0;
	}
	
	/**
	 * Change money amount by this value.
	 * @param change_by Amount to change by. Positive adds and negative subtracts.
	 */
	public void change(int change_by){
		amount += change_by;
	}
	
	@Override
	public String toString()
	{
		return "Gold: " + amount;
	}
}
