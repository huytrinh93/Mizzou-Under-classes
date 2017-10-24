package com.jacl.capstone.helpers.handlers.world;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.Entity;
import com.jacl.capstone.world.entities.player.Player;

public class EntityHandler
{
	public World world;
	
	//We will have an all entities array that will be used for drawing. We can sort this array by the Y-values to give an effect of being in front of/behind each other.
	public ArrayList<Entity> to_add;
	public ArrayList<Entity> all_entities;
	public Iterator<Entity> entity_iterator;
	public float array_sort_counter_current;
	public final float ARRAY_SORT_COUNTER_TICK = 0.75f;
	
	public Player player;
	
	public EntityHandler(World world)
	{
		this.world = world;
	}
	
	/**
	 * Initialize the handler after entering a new screen.
	 * @param start_x Player's starting X location (in blocks).
	 * @param start_y Player's starting Y location (in blocks). 
	 */
	public void handlerInit(String map_name, int start_x, int start_y, float health_max, float health_current, float health_regen)
	{
		//We will eventually have an EntityManager here to complete the set.
		player = new Player(world, start_x, start_y, world.data_handler.player_root, health_max, health_current, health_regen);
		
		//Because this is when we are recreating the EnitityHandler, there should be no items in the all_entities array. Let's make it and add the player.
		//Note: We would normally sort the array after adding an entity, but because this is the only entity, there's no need.
		to_add = new ArrayList<Entity>();
		all_entities = new ArrayList<Entity>();
		all_entities.add(player);
	}
	
	/**
	 * Populate the world by adding the passed entity.
	 * @param e Entity to add.
	 */
	public void add(Entity e)
	{
		//Add and sort by Y values. Highest to lowest.
		all_entities.add(e);
		Collections.sort(all_entities, new EntityComparator());
	}
	
	public void update(float delta)
	{
		for(Entity e : all_entities)
		{
			e.update(delta);
		}
		
		//Determine if it's time to sort the entities. This is done slower than our update rate because sorting takes a long time relative to the other logic in the update step.
		array_sort_counter_current += delta;
		if(array_sort_counter_current >= ARRAY_SORT_COUNTER_TICK)
		{
			array_sort_counter_current -= ARRAY_SORT_COUNTER_TICK;
			Collections.sort(all_entities, new EntityComparator());
		}
		
		//Added buffered entities to list.
		for(Entity e : to_add){
			add(e);
		}
		to_add.clear();
		
		//Remove old entities.
		remove();
	}
	
	public void draw()
	{
		world.screen.batch.setProjectionMatrix(world.camera_handler.combined);
		world.screen.batch.begin();
			//While drawing the entities, draw the highest Y values first. 
			//These are the sprites that are farthest from the camera. 
			//They are behind the lower Y values. 
			//Because we have previously sorted the array, it is not
			//necessary to worry about anything other than drawing
			//in order.
			for(Entity e : all_entities)
			{
				e.draw(world.screen.batch);
			}
		world.screen.batch.end();
	}
	
	/**
	 * Clear out old entities.
	 */
	public void remove()
	{
		entity_iterator = all_entities.iterator();
		while(entity_iterator.hasNext())
		{
			if(entity_iterator.next().remove)
			{
				entity_iterator.remove();
			}
		}
	}
	
	/**
	 * Used in sorting the items by Y values. Sort by the top value of the sprite
	 * @author Lee
	 */
	private class EntityComparator implements Comparator<Entity>
	{
		@Override
		public int compare(Entity a, Entity b) 
		{
			return a.getCenterY() > b.getCenterY() ? -1 : a.getCenterY() == b.getCenterY() ? 0 : 1;
		}
	}
}
