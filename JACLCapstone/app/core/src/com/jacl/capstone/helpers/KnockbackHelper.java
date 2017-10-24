package com.jacl.capstone.helpers;

import com.jacl.capstone.data.enums.Direction;
import com.jacl.capstone.world.entities.MovingEntity;
import com.jacl.capstone.world.entities.player.Player;

/**
 * Manages knockback from hits.
 * @author Lee
 */
public class KnockbackHelper
{
	private MovingEntity entity;
	
	//Knockback should always be the same amount for continuity throughout the game regardless of entity.
	private final float KNOCKBACK_BLOCKS = 1.5f;
	private final float KNOCKBACK_SPEED = 20f;
	private final float KNOCKBACK_SPEED_TICK;
	private final float KNOCKBACK_DISTANCE;
	
	//Knockback itself will have a flag set if happening. It also has distance and direction.
	public float current_knockback;
	public Direction knockback_direction;
	public boolean is_being_knocked_back;
	
	public KnockbackHelper(MovingEntity entity)
	{
		this.entity = entity;
		
		//Transform to correct units (pixels to blocks).
		KNOCKBACK_SPEED_TICK = KNOCKBACK_SPEED * entity.world.map_handler.tile_size;
		KNOCKBACK_DISTANCE = KNOCKBACK_BLOCKS * entity.world.map_handler.tile_size;
	}
	
	/**
	 * The type of knockback that being hit causes. The direction is set from an earlier collision calculation.
	 */
	public void doKnockback()
	{
		if(!entity.invincible.is_invincible)
		{
			//Start Knockback.
			is_being_knocked_back = true;
			current_knockback = 0f;
			
			//Stop all attacking and motion.
			entity.attack.stopAttack();
			
			//Only the player can be invincible.
			if(entity instanceof Player)
			{
				entity.invincible.goInvincible();
			}
		}
	}
	
	/**
	 * A knockback that can be used for testing or event-necessary purposes.
	 * @param knockback_direction The direction to move toward.
	 */
	public void doKnockback(Direction knockback_direction)
	{
		this.knockback_direction = knockback_direction;
		doKnockback();
	}
	
	public void update(float delta)
	{
		if(is_being_knocked_back)
		{
			//Calculate the knockback.
			if(knockback_direction == Direction.LEFT)
			{
				entity.sprite.translateX(delta * KNOCKBACK_SPEED_TICK);
			}
			else if(knockback_direction == Direction.RIGHT)
			{
				entity.sprite.translateX(-delta * KNOCKBACK_SPEED_TICK);
			}
			else if(knockback_direction == Direction.UP)
			{
				entity.sprite.translateY(-delta * KNOCKBACK_SPEED_TICK);
			}
			else
			{
				entity.sprite.translateY(delta * KNOCKBACK_SPEED_TICK);
			}
			
			//If knockback is over, end.
			current_knockback += delta * KNOCKBACK_SPEED_TICK;
			if(current_knockback >= KNOCKBACK_DISTANCE)
			{
				is_being_knocked_back = false;
			}
		}
	}
}