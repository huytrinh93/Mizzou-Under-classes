package com.jacl.capstone.helpers;

import com.jacl.capstone.AudioPlayer;
import com.jacl.capstone.data.enums.ItemSelection;
import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.MovingEntity;
import com.jacl.capstone.world.entities.player.items.weapons.ItemFactory;
import com.jacl.capstone.world.entities.player.items.weapons.Weapon;

/**
 * Manages attacking.
 * 
 * @author Lee
 */
public class AttackHelper
{
	private World world;
	
	public Weapon weapon;
	public boolean mid_attack;
	public float attack_time_current;
	public float attack_time_max;
	private AudioPlayer sfx;
	
	public AttackHelper(MovingEntity entity)
	{
		this.world = entity.world;
	}
	
	/**
	 * Attack was requested. Send in the item we attacked with.
	 */
	public void doAttack(ItemSelection item_selection)
	{
		// Only start an attack if we aren't already mid attack
		if(!mid_attack){
			//start audio effects when sword swing
			sfx = new AudioPlayer("sounds/scratch.mp3");
			sfx.play();
			// Get the selected item if a copy of the item does not exist.
			weapon = ItemFactory.spawn(item_selection, world);
			world.entity_handler.add(weapon);
			
			// We don't want to stop mid attack. Commit to the attack until the end
			// by setting a mid-attack flag.
			mid_attack = true;
			attack_time_current = 0f;
			attack_time_max = weapon.use_time;
			
			
		}
	}
	
	/**
	 * End attack. This is called when the attack animation is over or if the
	 * attacker is hit.
	 */
	public void stopAttack()
	{
		mid_attack = false;
		if(weapon != null){
			weapon.remove = true;
		}
	}
	
	public void update(float delta)
	{
		if(mid_attack){
			attack_time_current += delta;
			if(attack_time_current > attack_time_max){
				stopAttack();
			}
		}
	}
}