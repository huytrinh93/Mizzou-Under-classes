package com.jacl.capstone.world.entities.npc.ai;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.jacl.capstone.data.enums.Direction;
import com.jacl.capstone.world.entities.npc.NPC;

/**
 * Think of this AI as having a movement pattern that is the same as what you'd see from NPCs in Pokemon.
 * Actions include either move or rest for movement and attack for special cases.
 * @author Lee
 */
public class RandomAI extends AI
{
	//There will be a radius around the AI unit where the unit is close enough
	//to attack the player.
	private final float ATTACK_RADIUS;
	private Vector2 player_position;
	
	//Two movement actions are either move or wait. Waiting should be the more common action of the two.
	private Random random;
	private final float CHANCE_WAIT = 0.1f;
	
	private boolean moving;
	private float move_current;
	private float move_time_random;
	private Direction direction;
	
	private boolean waiting;
	private float wait_current;
	private float wait_time_random;
	
	public RandomAI(NPC npc)
	{
		super(npc);
		
		ATTACK_RADIUS = (float) Math.pow(1.5f * npc.world.map_handler.tile_size, 2);
		player_position = new Vector2();
		random = new Random();
	}
	
	@Override
	public void updateThinking(float delta)
	{
		//If we are neither moving nor waiting, we can make a decision based upon random chance.
		if(!moving && !waiting)
		{
			//random < CHANCE_WAIT = Wait. Move otherwise.
			if(random.nextFloat() < CHANCE_WAIT)
			{
				waiting = true;
				wait_current = 0f;
				wait_time_random = random.nextFloat();
			}
			else
			{
				moving = true;
				move_current = 0f;
				move_time_random = random.nextFloat();
				direction = Direction.values()[random.nextInt(4)];
			}
		}
	}
	
	@Override
	public void updatePosition(float delta)
	{
		//Update timing and position if necessary.
		if(moving)
		{
			if(move_current < move_time_random)
			{
				move_current += delta;
				switch(direction)
				{
					case UP:
						npc.sprite.translateY(npc.move_speed * delta);
						break;
					case DOWN:
						npc.sprite.translateY(-npc.move_speed * delta);
						break;
					case LEFT:
						npc.sprite.translateX(-npc.move_speed * delta);
						break;
					case RIGHT:
						npc.sprite.translateX(npc.move_speed * delta);
						break;
				}
			}
			else
			{
				moving = false;
			}
		}
		else if(waiting)
		{
			if(wait_current < wait_time_random)
			{
				wait_current += delta;
			}
			else
			{
				waiting = false;
			}
		}
	}
	
	@Override
	public void updateAction(float delta)
	{
		//Despite what our move action (move or wait) is, if the enemy (that is, the player) gets
		//too close, we can stop the move action and attack.
	}
}
