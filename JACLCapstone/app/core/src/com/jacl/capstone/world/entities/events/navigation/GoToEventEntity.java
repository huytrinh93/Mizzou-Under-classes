package com.jacl.capstone.world.entities.events.navigation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.events.EventEntity;

/**
 * Player requested to go from one map to another via doorway/portal/vortex/etc.
 * To show this, the screen will fade the previous screen to black and fade from
 * black to the new map.
 * 
 * @author Lee
 *
 */
public class GoToEventEntity extends EventEntity
{
	//Fade-related items.
	private final float FADE_TIME = 1f;				//Time to fade (in seconds). Double this to see how long the full transition is. 
	private float current_fade = 0f;					//Current amount of time since fade started.
	private boolean fade_out = true;					//Are we fading in or out?
	
	//These are the tokens from the arguments.
	private String map_to_load;						//Name of the map file to load. Note: Do not include the map directory. It is done automatically in the World class.
	private int start_x, start_y;						//The X and Y tile locations of the block we will end up on after the new map loads.
	
	public GoToEventEntity(World world, float x, float y, String... arguments)
	{
		super(world, x, y, arguments);	
		
		//First token is the command. We already know that one. Let's look at the others (starting from 1 rather than 0).
		//First is the map we are going to load.
		map_to_load = arguments[1];
		
		//Second is this location we will spawn. Split this into x and y location.
		String[] location = arguments[2].split(",");
		start_x = Integer.parseInt(location[0]);
		start_y = Integer.parseInt(location[1]);
	}

	@Override
	public void update(float delta)
	{
		/*
		 * The logic here states that we will fade to black while we leave the current map.
		 * We will fade in from black as we arrive at the new location.
		 * 
		 * Do this in three parts:
		 * 1) Fading out. Initialize new location once this is over.
		 * 2) Fading in. Draw the world from this point on to give a
		 * 	preview of the map we have entered.
		 * 3) Ending the event. Set any necessary ending flags and clear.
		 */
		if(fade_out)
		{
			current_fade += delta;
			
			//Are we ready for Phase 2?
			if(current_fade >= FADE_TIME)
			{
				//Switch to the opposite direction.
				fade_out = false;
				current_fade = FADE_TIME;
				
				//Initialize the new map. Must happen at this exact moment so that no crazy drawing happens while we switch over to the new map.
				world.init(map_to_load, start_x, start_y, world.entity_handler.player.health_max, world.entity_handler.player.health_current, world.entity_handler.player.health_regen, world.map_handler.chapter);
			}
		}
		else
		{
			current_fade -= delta;
			
			//Are we ready for Phase 3?
			if(current_fade <= 0f)
			{
				world.event_handler.event = null;
			}			
		}
	}

	@Override
	public void draw(SpriteBatch batch)
	{
		world.worldDraw();
		
		//Draw the fading the same way we draw time coloring in World.
		Gdx.gl.glEnable(GL20.GL_BLEND);
		world.screen.renderer.setColor(new Color(Color.rgba8888(0f, 0f, 0f, current_fade * 1f / FADE_TIME)));
		world.screen.renderer.begin(ShapeType.Filled);
			world.screen.renderer.rect(0f, 0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		world.screen.renderer.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);
	}

	@Override
	public boolean canCollide()
	{
		return true;
	}
}
