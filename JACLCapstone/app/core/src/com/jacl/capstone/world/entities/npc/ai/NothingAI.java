package com.jacl.capstone.world.entities.npc.ai;

import com.jacl.capstone.world.entities.npc.NPC;

/**
 * Nothing will happen in this AI. It is here mainly as a test 
 * to see that everything is working.
 * 
 * @author Lee
 *
 */
public class NothingAI extends AI
{
	public NothingAI(NPC npc)
	{
		super(npc);
	}

	@Override
	public void updateThinking(float delta)
	{
		System.out.println("Thinking step.");
	}

	@Override
	public void updatePosition(float delta)
	{
		System.out.println("Moving step.");
	}

	@Override
	public void updateAction(float delta)
	{
		System.out.println("Action step.");
	}
}
