package com.jacl.capstone.world.entities.npc;

import com.badlogic.gdx.utils.XmlReader.Element;
import com.jacl.capstone.data.enums.Alignment;
import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.MovingEntity;
import com.jacl.capstone.world.entities.npc.ai.AI;

/**
 * All enemies and other non-player characters will be a MovingEntity
 * with an added component of artificial intelligence.
 * 
 * @author Lee
 *
 */
public abstract class NPC extends MovingEntity
{
	protected AI ai;
	
	public NPC(World world, float x, float y, Element data, Alignment alignment)
	{
		super(world, x, y, data, alignment);
		
		//Get data.
		health_max = data.getFloat("health");
		health_current = health_max;
		
		ai = initAI();
	}
	
	protected abstract AI initAI();
	
	@Override
	/**
	 * AI will take over in this case.
	 */
	protected void move(float delta)
	{
		ai.updateThinking(delta);
		ai.updatePosition(delta);
		ai.updateAction(delta);
	}
}
