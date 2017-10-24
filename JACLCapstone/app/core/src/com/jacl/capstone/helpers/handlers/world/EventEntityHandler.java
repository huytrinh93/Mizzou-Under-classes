package com.jacl.capstone.helpers.handlers.world;

import java.util.HashMap;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.events.EventEntity;
import com.jacl.capstone.world.entities.events.EventEntityFactory;

/**
 * Event entities will be scattered throughout the land. If one is stepped upon, we must activate it.<br><br>
 * 
 * This is the class that handles the activation. It will read from whatever cell the player is
 * currently standing on and determine if there is an event entity here. If there is one, get the
 * event and activate its logic.<br><br>
 * 
 * If an event is active, the world's logic will transform into the logic of the event. That is to 
 * say the event will override any movement/attack commands until the event is completed. 
 * 
 * @author Lee
 *
 */
public class EventEntityHandler
{
	public World world;
	
	//Map-related items.
	public final String COLLISION_LAYER = "events";
	public TiledMapTileLayer event_layer;
	public HashMap<String, EventEntity> event_map;
	
	//Currently selected event.
	public EventEntity event;
	
	public EventEntityHandler(World world)
	{
		this.world = world;
	}
	
	public void handlerInit()
	{
		event_layer = (TiledMapTileLayer) world.map_handler.map.getLayers().get(COLLISION_LAYER);
		
		//Get cells.
		if(event_layer != null)
		{
			event_map = new HashMap<String, EventEntity>();
			for(int y = 0; y < event_layer.getHeight(); y++)
			{	
				for(int x = 0; x < event_layer.getWidth(); x++)
				{
					//If the tile is not null, we can put the event into the event map.
					if(event_layer.getCell(x, y) != null)
					{
						event_map.put(x + "," + y, EventEntityFactory.get(world, x * world.map_handler.tile_size, y * world.map_handler.tile_size, (String) event_layer.getProperties().get(x + "," + y)));
					}
				}
			}
		}
	}
	
	/**
	 * If there is an event in this location, activate it.
	 * @param x Center of player's horizontal size.
	 * @param y Center of player's vertical size.
	 */
	public void doEventEntity(float x, float y)
	{
		//First, determine if there is an event in this location.
		int new_x = (int) (x / event_layer.getTileWidth());
		int new_y = (int) (y / event_layer.getTileHeight());
		if(event_map.containsKey(new_x + "," + new_y))
		{
			//Now we need to determine if we have collided with the event.
			EventEntity event = event_map.get(new_x + "," + new_y);
			if(event.canCollide() && event.eventCollision(x, y))
			{
				world.event_handler.event = event;
				
				//Reset the event at that HashMap spot.
				event_map.put(new_x + "," + new_y, EventEntityFactory.get(world, new_x * world.map_handler.tile_size, new_y * world.map_handler.tile_size, (String) event_layer.getProperties().get(new_x + "," + new_y)));
			}
		}
	}
}
