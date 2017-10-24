package com.jacl.capstone.world.entities.npc.enemies;

import com.badlogic.gdx.utils.XmlReader.Element;
import com.jacl.capstone.data.enums.EnemyType;
import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.npc.enemies.creeps.Skeleton;

/**
 * Handles spawning of enemies.
 * @author Lee
 */
public class EnemyFactory
{
	/**
	 * Get a requested enemy.
	 * @param type Type of enemy to spawn.
	 * @param x X location (in blocks).
	 * @param y Y location (in blocks).
	 * @param data Root of the "enemies" data node for initialization.
	 * @return A new instance of the requested enemy.
	 */
	public static Enemy spawn(EnemyType type, World world, float x, float y, Element data)
	{
		switch(type)
		{
			case SKELETON:
				return new Skeleton(world, x, y, data);
			default:
				return null;
		}
	}
}
