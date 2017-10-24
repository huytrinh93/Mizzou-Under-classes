package com.jacl.capstone.world.entities.npc.ai;

import com.jacl.capstone.helpers.handlers.world.EntityHandler;
import com.jacl.capstone.world.entities.npc.NPC;

/**
 * All AI classes will utilize this parent class.
 * 
 * @author Lee
 *
 */
public abstract class AI
{
	public NPC npc;
	public EntityHandler handler; 
	
	public AI(NPC npc)
	{
		this.npc = npc;
		handler = npc.world.entity_handler;
	}
	
	/**
	 * From the current position, determine
	 * what the next best move will be.
	 * @param delta Change in time.
	 */
	public abstract void updateThinking(float delta);
	
	/**
	 * Update position after thinking.
	 * @param delta Change in time.
	 */
	public abstract void updatePosition(float delta);
	
	/**
	 * After moving, the AI can decide an action.
	 * @param delta Change in time.
	 */
	public abstract void updateAction(float delta);
}
