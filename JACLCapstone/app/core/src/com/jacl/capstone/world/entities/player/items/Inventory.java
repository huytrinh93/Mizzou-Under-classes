package com.jacl.capstone.world.entities.player.items;
import java.util.ArrayList;

import com.jacl.capstone.world.entities.player.items.weapons.Item;

public class Inventory {
	//set of attributes
	private ArrayList<Item> items;
	/**
	 * 2 overloading constructor for this class
	 */
	public Inventory () {
		initBag();
	}

	private void initBag() {
		this.items = new ArrayList<Item>();
	}
	/**
	 * add Item to the bag
	 * @param item
	 * @return true/false
	 */
	public boolean addItem(Item item) {
		if (this.items.size() <= 20 ){
			this.items.add(item);
			return true;
		}
		return false;
	}
	/**
	 * drop item from the bag
	 * @param item
	 * @return true/false
	 */
	public boolean dropItem(Item item) {
		if (this.items.remove(item)) {
			return true;
		}
		return false;
	}
	/**
	 * get all item in the bag
	 * @return
	 */
	public ArrayList<Item> getItems() {
		return this.items;
	}
	/**
	 * @return the size of bag
	 */
	public int size() {
		return this.items.size();
	}
	/**
	 * get item base on the name
	 * @param name
	 * @return item
	 */
	public Item getItem(String name) {
		for (int i = 0; i < this.items.size(); ++i) {
			if (this.items.get(i).getName().equalsIgnoreCase(name)) {
				return this.items.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Returns an Item object at that itemIdx in the items ArrayList.
	 * @param itemIdx
	 * @return
	 */
	public Item getItem(int itemIdx){
		return items.get(itemIdx);
	}
	
	/**
	 * Removes all objects from the items ArrayList as well as reset the weight.
	 */
	public void dropItems(){
		this.items.clear();
	}
}
