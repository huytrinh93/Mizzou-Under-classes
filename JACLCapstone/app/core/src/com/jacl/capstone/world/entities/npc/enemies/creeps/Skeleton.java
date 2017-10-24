package com.jacl.capstone.world.entities.npc.enemies.creeps;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.jacl.capstone.data.enums.Alignment;
import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.npc.ai.AI;
import com.jacl.capstone.world.entities.npc.ai.RandomAI;
import com.jacl.capstone.world.entities.npc.enemies.Enemy;

/**
 * This enemy will not be in the final game. Purely for testing.
 */
public class Skeleton extends Enemy
{
	public Skeleton(World world, float x, float y, Element data)
	{
		super(world, x, y, data.getChildByName("sample_creep"), Alignment.ENEMY);
	}
	
	@Override
	protected AI initAI()
	{
		return new RandomAI(this);
	}

	@Override
	public void draw(SpriteBatch batch)
	{
		sprite.draw(batch);
	}
}
