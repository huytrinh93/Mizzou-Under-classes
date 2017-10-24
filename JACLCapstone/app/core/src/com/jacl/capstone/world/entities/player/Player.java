package com.jacl.capstone.world.entities.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.jacl.capstone.data.enums.Alignment;
import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.MovingEntity;

/**
 * This is the player that is controlled by input.There should 
 * be a direct link between the inputs that are executed and 
 * the actions this class takes.<br><br>
 * 
 * When an attack is requested, spawn the item from a reference
 * point of this player.<br><br>
 * 
 * When the enemy wishes to attack or move toward the player,
 * do so by aiming at the player's center.<br><br>
 * 
 * The player will be the only entity that communicates with 
 * EventEntities. During the block collision step, check for
 * collisions with event blocks.
 * @author Lee
 */
public class Player extends MovingEntity
{
	//This number will be used in diagonal movement calculations.
	private final float FOURTH_ROOT_FOUR = 1.189207115f;
	
	//Rather than AI, we will use signals to define the correct time to move/attack.
	public boolean up, down, left, right;

	public Player(World world, float x, float y, Element data, float health_max, float health_current, float health_regen)
	{
		super(world, x, y, data, Alignment.PLAYER);
		
		changeMaxHealthValueTo(health_max);
		changeCurrentHealthValueTo(health_current);
		this.health_regen = health_regen;
	}

	@Override
	public void draw(SpriteBatch batch)
	{
		sprite.draw(batch);
	}
	
	/**
	 * Read the signals. Translate appropriately.<br><br>
	 * 
	 * Keep in mind that simply translating in whatever direction the player is pressing
	 * works for the 4 main directions, but if this method is used for the 4 corners,
	 * the player will be moving at (player_speed) * (root(2)). To correct this, we 
	 * will translate the player in both directions by the sprite's speed divided by 
	 * the 2^(1/4), or (root(root(2))). Doing this will get a final speed magnitude 
	 * of x * root(2) / root(2), or x.<br><br>
	 * 
	 * Rather than calculating the fourth root of two every time, let's just store it here as 
	 * an approximation. Move the sprite's speed down by the fourth root of two, do the 
	 * translation, and correct it.
	 */
	@Override
	protected void move(float delta)
	{
		//We can't move while attacking
		if(!attack.mid_attack)
		{
			//Correct if diagonal.
			if(up && left || up && right || down && left || down && right)
			{
				move_speed /= FOURTH_ROOT_FOUR;
			}

			//Do the translation.
			if(up)
			{
				sprite.translateY(move_speed * delta);
			}
			else if(down)
			{
				sprite.translateY(-move_speed * delta);
			}
			
			if(left)
			{
				sprite.translateX(-move_speed * delta);
			}
			else if(right)
			{
				sprite.translateX(move_speed * delta);
			}
			
			//Undo correction if diagonal.
			if(up && left || up && right || down && left || down && right)
			{
				move_speed *= FOURTH_ROOT_FOUR;
			}
		}
	}
	
	@Override
	public void entityCollision()
	{
		super.entityCollision();
		
		//The player is also looking for collision with collectibles.
		world.collision_handler.collectibleCollision(this);
	}
	
	@Override
	public void update(float delta)
	{//On top of normal updating, check for events we may have started.
		super.update(delta);
		world.event_handler.doEventEntity(getCenterX(), getCenterY());
	}
	
	/**
	 * We're trying to save the game. Package the saveable items here.
	 * @return In order: max, current, regen.
	 */
	public float[] packageForSave()
	{
		return new float[]{health_max, health_current, health_regen};
	}
	
	@Override
	protected void die(){
		
	}
}
