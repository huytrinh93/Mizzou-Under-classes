package com.jacl.capstone.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.jacl.capstone.helpers.handlers.world.CameraHandler;
import com.jacl.capstone.helpers.handlers.world.CollisionHandler;
import com.jacl.capstone.helpers.handlers.world.DataHandler;
import com.jacl.capstone.helpers.handlers.world.EntityHandler;
import com.jacl.capstone.helpers.handlers.world.EventEntityHandler;
import com.jacl.capstone.helpers.handlers.world.MapHandler;
import com.jacl.capstone.screens.ScreenGame;
import com.jacl.capstone.world.atmosphere.TimeColorer;

/**
 * Handles the updating and rendering of game objects. Create managers to keep this class general.
 * @author Lee
 */
public class World
{
	public ScreenGame screen;	
	
	//Handlers.
	public CameraHandler camera_handler;
	public MapHandler map_handler;
	public EntityHandler entity_handler;
	public CollisionHandler collision_handler;	
	public EventEntityHandler event_handler;
	public DataHandler data_handler;
	
	//Atmosphere.
	public Color time_color;	
	
	public World(ScreenGame screen)
	{
		this.screen = screen;
		
		//Initialize helpers.
		map_handler = new MapHandler(this);
		entity_handler = new EntityHandler(this);
		collision_handler = new CollisionHandler(this);
		event_handler = new EventEntityHandler(this);
		camera_handler = new CameraHandler(this);
		data_handler = new DataHandler(this);
	}
	
	/**
	 * Call this to initialize the world to the passed map. This is called after loading from a save.
	 * @param map_name The map to load. Note: Map directory should not be included. Simply pass the name of the map found in the map directory of the assets folder.
	 * @param start_x Player's starting X location (in blocks).
	 * @param start_y Player's starting Y location (in blocks). 
	 */
	public void init(String map_name, int start_x, int start_y, float health_max, float health_current, float health_regen, int map_chapter)
	{
		map_handler.handlerInit(map_name, map_chapter);
		entity_handler.handlerInit(map_name, start_x, start_y, health_max, health_current, health_regen);
		camera_handler.handlerInit();
		collision_handler.handlerInit();
		event_handler.handlerInit();
		data_handler.handlerInit();
	}

	public void update(float delta)
	{
		if(screen.hud.dialogue_handler.showing_dialogue)		//If dialogue is showing, don't do anything here.
		{
			
		}
		else if(event_handler.event != null)						//If there is an active event, play it. Otherwise, update normally.
		{
			event_handler.event.update(delta);
		}
		else
		{
			worldUpdate(delta);
		}
	}
	
	public void draw()
	{
		//If there is an active event, draw it. Otherwise, draw normally.
		if(event_handler.event != null)
		{
			event_handler.event.draw(screen.batch);
		}
		else
		{
			worldDraw();
		}
	}
	
	/**
	 * The function in which the actual updating is done. Separated out so that 
	 * events/dialogue may use the normal game logic without getting held-up in the
	 * update() method.
	 */
	public void worldUpdate(float delta)
	{
		//Update entities.
		entity_handler.update(delta);
		
		//Update camera onto player.
		camera_handler.updateCamera();
		
		time_color = TimeColorer.getColor(screen.hud.time);
	}
	
	/**
	 * The function in which the actual drawing is done. Separated out so that 
	 * events may use this as a frame buffer of sorts without getting held-up
	 * in the draw() method.
	 */
	public void worldDraw()
	{
		//Render layers/objects under player.
		map_handler.tiled_map_renderer.setView(camera_handler);
		map_handler.tiled_map_renderer.render(map_handler.layers_under_player);
		
		//Render player and other entities.
		entity_handler.draw();
		
		//Render layers/objects over player.
		map_handler.tiled_map_renderer.render(map_handler.layers_over_player);
		
		//Draw the day/night overlay if we are outside.
		if(map_handler.is_outside){
			Gdx.gl.glEnable(GL20.GL_BLEND);
			screen.renderer.setColor(time_color);
			screen.renderer.begin(ShapeType.Filled);	
				screen.renderer.rect(0f, 0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			screen.renderer.end();
			Gdx.gl.glDisable(GL20.GL_BLEND);
		}
		//If we are inside, we can use the shorter vision lens
		//****NOTE: The current way this code works will use the vision lens in houses as well. This should not happen in a fully produced game. For capstone, however, it's fine.
		else{
			/*Sprite dark = new Sprite(screen.game.assets.get("sprites/dark.png", Texture.class));
			dark.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			//screen.batch.setTransformMatrix(screen.hud.camera.combined);
			screen.batch.begin();
				dark.draw(screen.batch);
			screen.batch.end();*/
		}
	}
}
