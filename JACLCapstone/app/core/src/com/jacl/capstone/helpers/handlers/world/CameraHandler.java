package com.jacl.capstone.helpers.handlers.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.jacl.capstone.world.World;

/**
 * Idea behind the camera:
 * The world map should be divided into sectors to avoid rendering the whole map at once.
 * The camera will follow the player while still staying within the bounds of the sector.
 * Thus, the sector's bounds take precedence.
 * 
 * The camera will always show the same size of screen. This is regardless of screen resolution.
 * To do this, we must determine how many tiles we want the screen to show and stretch this into
 * the screen's resolution.
 * 
 * Because most modern screens are 16:9, we should go for as close as as we can with our
 * defined width:height ratio.
 * 
 * @author Lee
 */
public class CameraHandler extends OrthographicCamera
{
	public World world;
	
	//Define how many tiles we want to see. Define one in terms of the other so that we only need to change the independent one in the future.
	private final float TILES_HORIZONTAL = 20f;
	private final float TILES_VERTICAL = TILES_HORIZONTAL * 9f / 16f;
	
	//Each tile will be a certain width/height. This is defined in the map file.
	private float TILE_SIZE;

	//The map will also define the width and height of the map in tiles.
	private int TILES_TOTAL_HORIZONTAL, TILES_TOTAL_VERTICAL;
	
	/*
	 * This value can be used to have the camera follow the player in an imperfect
	 * fashion.
	 * 
	 * Lower values = Less responsive camera, and 0 = No movement.
	 * Higher values = More responsive camera. Infinity = Perfect response, but
	 * there should never be a time where this is necessary (or possible).
	 * 
	 * Any value between 2 and 5 should be fine for everyday camera activity. Might 
	 * be able to add a drunk/drugged/dizzy effect by lowering the value to less
	 * than 1.
	 */
	private final float LERP = 4f;
	
	public CameraHandler(World world)
	{
		super();
		
		this.world = world;
	}
	
	public void handlerInit()
	{
		//Read bounds and sizes of map.
		TILE_SIZE = world.map_handler.map.getProperties().get("tilewidth", Integer.class);
		TILES_TOTAL_HORIZONTAL = world.map_handler.map.getProperties().get("width", Integer.class);
		TILES_TOTAL_VERTICAL = world.map_handler.map.getProperties().get("height", Integer.class);
		
		//Define camera width and height in terms of tiles. 
		//This is done by multiplying how many tiles we want to see in each direction by the size of each tile. 
		setToOrtho(false, TILE_SIZE * TILES_HORIZONTAL, TILE_SIZE * TILES_VERTICAL);
		
		//Initialize camera to player's position.
		position.x = world.entity_handler.player.getCenterX();
		position.y = world.entity_handler.player.getCenterY();
		updateCamera();
	}
	
	/**
	 * Update the camera each tick of the game. This involves changing the camera's position based upon 
	 * both the player's position and the bounds of the world.<br><br>
	 * 
	 * Main idea here is that the world's bounds have a greater priority than the player's position.
	 * To represent this, move to the player's position first. Afterward, readjust the camera to 
	 * fit with the world's bounds.
	 */
	public void updateCamera()
	{
		//Look at player's position, but add a delay if just recently moved to make the camera smoother.
		position.x += (world.entity_handler.player.getCenterX() - position.x) * Gdx.graphics.getDeltaTime() * LERP;
		position.y += (world.entity_handler.player.getCenterY() - position.y) * Gdx.graphics.getDeltaTime() * LERP;
		
		//Adjust bounds in accordance with the world's bounds.
		//Left side
		if(position.x < viewportWidth / 2f)
		{
			position.x = viewportWidth / 2f;
		}
		
		//Right side
		else if(position.x > TILES_TOTAL_HORIZONTAL * TILE_SIZE - viewportWidth / 2f)
		{
			position.x = TILES_TOTAL_HORIZONTAL * TILE_SIZE - viewportWidth / 2f;
		}
		
		//Bottom side
		if(position.y < viewportHeight / 2f)
		{
			position.y = viewportHeight / 2f;
		}
		
		//Top side
		else if(position.y > TILES_TOTAL_VERTICAL * TILE_SIZE - viewportHeight / 2f)
		{
			position.y = TILES_TOTAL_VERTICAL * TILE_SIZE - viewportHeight / 2f;
		}
		
		//Finish updating and end.
		update();
	}
}
