package com.jacl.capstone.world.entities.events;

import com.jacl.capstone.data.enums.Alignment;
import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.Entity;

/**
 * Event entities will be scattered throughout the land. If one is stepped upon, we must activate it.<br><br>
 * 
 * This is the actual EventEntity class. Extend from this for entities 
 * 
 * If an event is active, the world's logic will transform into the logic of the event. That is to 
 * say the event will override any movement/attack commands until the event is completed. 
 * 
 * @author Lee
 *
 */
public abstract class EventEntity extends Entity
{
	//Normal collision would be bad here because barely clipping the corner of the event
	//entity tile would activate the event. Thinking of a doorway, simply walking up to
	//the door would be enough to activate it. This isn't good.
	//Instead, we want to be within the event entity tile before we start it.
	//To cause this effect, create a smaller rectangle from the event entity cell's
	//collision rectangle.
	protected final float SHRINK_SIZE_BY = 0.15f;
	
	
	public EventEntity(World world, float x, float y, String... arguments)
	{
		super(world, x, y, null, Alignment.NEUTRAL);
		
		//We want to correctly shape the collision sprite for events to avoid the example from above.
		float i, j, width, height;
		int new_x = (int) (x / world.event_handler.event_layer.getTileWidth());
		int new_y = (int) (y / world.event_handler.event_layer.getTileHeight());
		
		//Right
		new_x++;
		if(eventExists(new_x, new_y) && eventExists(new_x - 2, new_y))
			width = world.map_handler.tile_size;
		else if(eventExists(new_x, new_y))
			width = world.map_handler.tile_size / 2f;
		else
			width = SHRINK_SIZE_BY * world.map_handler.tile_size;	
			
		//Left
		new_x -= 2;
		if(eventExists(new_x, new_y))
			i = x;
		else
			i = x + world.map_handler.tile_size / 2f - width / 2f;	
			
		//Top
		new_y++;
		if(eventExists(new_x, new_y) && eventExists(new_x, new_y - 2))
			height = world.map_handler.tile_size;
		else if(eventExists(new_x, new_y))
			height = world.map_handler.tile_size / 2f;
		else
			height = SHRINK_SIZE_BY * world.map_handler.tile_size;	
			
		//Bottom
		new_y -= 2;
		if(eventExists(new_x, new_y))
			j = y;
		else
			j = y + world.map_handler.tile_size / 2f - height / 2f;	
		
		sprite.setBounds(i, j, width, height);
	}
	
	/**
	 * Does an event exist at the passed point? Used in creating the collision
	 * box of the event.
	 * @param x X coordinate (in tiles).
	 * @param y Y coordinate (in tiles).
	 * @return True if an event exists at that tile. False otherwise.
	 */
	private boolean eventExists(int x, int y)
	{
		return world.event_handler.event_map.containsKey(x + "," + y);// && world.event_handler.event_map.get(x + "," + y) instanceof GoToEventEntity;
	}
	
	/**
	 * Did we collide with the event?
	 * @return True if collision. False otherwise.
	 */
	public boolean eventCollision(float x, float y)
	{//You could make this more complex if you want. No reason for this project, however.
		return true;
	}

	public abstract boolean canCollide(); 
}
