package com.jacl.capstone.world.entities.player.items.weapons;

public class Item {
		//set of all atributes
		private String name;
		private int damage;
		/**
		 * constructor
		 * @param name
		 * @param weight
		 * @param level
		 */
		public Item(String name, int damage) {
			setName(name);
			setDamage(damage);
		}
		/**
		 * mutator for level
		 * @param level
		 */
		private void setDamage(int damage) {
			this.damage=damage;
		}
		/**
		 * mutator for name
		 * @param name
		 */
		private void setName(String name) {
			this.name = name;
		}
		/**
		 * accessor for name
		 * @return
		 */
		public String getName() {
			return this.name;
		}

		/**
		 * accessor for level
		 * @return
		 */
		public int getDamage() {
			return this.damage;
		}
		
		public int getPoints(){
			return 0;
		}
}
