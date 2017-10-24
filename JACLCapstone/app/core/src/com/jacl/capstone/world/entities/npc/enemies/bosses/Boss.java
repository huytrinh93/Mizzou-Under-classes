package com.jacl.capstone.world.entities.npc.enemies.bosses;

import com.badlogic.gdx.utils.XmlReader.Element;
import com.jacl.capstone.data.enums.Alignment;
import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.npc.enemies.Enemy;

/**
 * Bosses are more interesting than their standard enemy counterparts.
 * They will usually have phases. When a condition is met, it will 
 * transition between the phases. After the final phase is completed,
 * the boss is dead.
 * 
 * @author Lee
 * 
 */
public abstract class Boss extends Enemy
{
	protected int boss_phases;				//Number of phases for this boss.
	protected int current_phase;			//Current phase number.
	
	public Boss(World world, float x, float y, Element data, Alignment alignment)
	{
		super(world, x, y, data, Alignment.ENEMY);
	}
	
	/**
	 * The phase will change after a certain condition is met. 
	 * @return True if ready to change. False otherwise.
	 */
	protected abstract boolean phaseCheck();
}
