package com.jacl.capstone.world.entities.events;

import com.jacl.capstone.data.enums.EventTypes;
import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.events.navigation.GoToEventEntity;
import com.jacl.capstone.world.entities.events.spawning.SpawnEnemy;

/**
 * Get the correct EventEntity from the given properties.
 * 
 * @author Lee
 *
 */
public class EventEntityFactory
{
	public static EventEntity get(World world, float x, float y, String command)
	{
		//This event will be determined by the command variable.
		String[] tokens = command.split(" ");
		
		//First token is the command. Any additional tokens will be arguments the event will use.
		switch(EventTypes.valueOf(tokens[0]))
		{
			case GOTO:
				return new GoToEventEntity(world, x, y, tokens);
			case SPAWN_ENEMY:
				return new SpawnEnemy(world, x, y, tokens);
			default:
				return null;
		}
	}
}
