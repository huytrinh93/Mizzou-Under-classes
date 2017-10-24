package com.jacl.capstone.world.entities.player.items.weapons;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.jacl.capstone.data.enums.Direction;
import com.jacl.capstone.world.World;

/**
 * Player's main weapon.
 * @author Lee
 */
public class Sword extends Weapon
{
	/**
	 * This is how many degrees off the four cardinal compass directions the sword spawns.
	 */
	private final float OFF_90 = -15f;
	private final float ROTATE_DEGREE = 115f;
	
	public Sword(World world, Element data, Direction direction)
	{
		/* Note: The location of the sword will not be passed to this class.
		 * We will be gathering the location from the direction instead. 
		 * Therefore, we don't have to pass useful coordinates to the
		 * superclass.
		 */
		super(world, 0, 0, data);
		
		//All weapons will start from the player's center.
		//The rotation of the sword will depend upon the player's direction.
		sprite.setOrigin(0f, 0f);
		sprite.setX(world.entity_handler.player.getCenterX());
		sprite.setY(world.entity_handler.player.getCenterY());
		if(direction == Direction.UP)
		{
			sprite.setRotation(0f + OFF_90);
		}
		else if(direction == Direction.LEFT)
		{
			sprite.setRotation(90f + OFF_90);
		}
		else if(direction == Direction.DOWN)
		{
			sprite.setRotation(180f + OFF_90);
		} 
		else if(direction == Direction.RIGHT)
		{
			sprite.setRotation(270f + OFF_90);
		}
	}

	@Override
	public void update(float delta)
	{
		if(!remove)
		{
			sprite.rotate(delta * ROTATE_DEGREE / use_time);
		}
	}

	@Override
	public void draw(SpriteBatch batch)
	{
		if(!remove)
		{
			sprite.draw(batch);
		}
	}
	
	@Override
	protected void die()
	{//Swords can't die.
	}
}
