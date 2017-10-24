package com.jacl.capstone.helpers.handlers.world;

import java.util.ArrayList;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.jacl.capstone.world.World;

/**
 * Helper class that manages the tiled map and its properties.
 * 
 * @author Lee
 *
 */
public class MapHandler
{
	public World world;
	
	//Map naming.
	private final String MAP_DIRECTORY = "maps/";
	public String map_name;
	
	//Map and layers.
	public TiledMap map;
	public TiledMapRenderer tiled_map_renderer;
	public int[] layers_under_player, layers_over_player;
	
	//Each tile will be a certain width/height. This is defined in the map file. The map will also define the width and height of the map in tiles.
	public float tile_size;
	public int tiles_total_horizontal, tiles_total_vertical;
	
	//Other map qualities.
	public boolean is_outside;
	public int chapter;
	
	/*
	 * Note for future Lee:
	 * ***********************************http://stackoverflow.com/questions/23144367/why-do-i-have-lines-going-across-my-libgdx-game-using-tiled**********************************************
	 */
	
	public MapHandler(World world)
	{
		this.world = world;
	}

	/**
	 * Initialize portions of the map.
	 */
	public void handlerInit(String map_name, int map_chapter)
	{
		//Get map file path.
		this.chapter = map_chapter;
		String chapter_directory = "Chapter" + map_chapter + "/";
		this.map_name = map_name;
		map = new TmxMapLoader().load(MAP_DIRECTORY + chapter_directory + map_name);
		tiled_map_renderer = new OrthogonalTiledMapRenderer(map);
		
		//Read bounds and sizes of map.
		tile_size = map.getProperties().get("tilewidth", Integer.class);
		tiles_total_horizontal = map.getProperties().get("width", Integer.class);
		tiles_total_vertical = map.getProperties().get("height", Integer.class);
		
		//Read other map qualties
		is_outside = Boolean.parseBoolean(map.getProperties().get("outside", String.class));
		
		//Separate the map layers.
		separateLayers();
	}
	
	/**
	 * To give a little more flexibility when it comes to rendering items, we want to divide the map
	 * into layers. The layers will be those that are above the player and those that are below the player.
	 * Player's level is considered level 1. He/She is standing on level 1 and collides with level 1 objects.
	 * Level 2+ objects/collisions should be seen as being above his/her head and will be rendered after the
	 * player. Level 0- objects are below the player and the main level ground.
	 */
	private void separateLayers()
	{		
		ArrayList<Integer> under = new ArrayList<Integer>();
		ArrayList<Integer> over = new ArrayList<Integer>();
		
		//Loop through all the map's layers and put them into one of these two categories.
		for(int i = 0; i < map.getLayers().getCount(); i++)
		{
			if(i < 2)
			{
				under.add(i);
			}
			else
			{
				over.add(i);
			}
		}
		
		//Make these the arrays.
		int array_counter = 0;
		layers_under_player = new int[under.size()];
		for(Integer i : under)
		{
			layers_under_player[array_counter++] = i;
		}
		
		array_counter = 0;
		layers_over_player = new int[over.size()];
		for(Integer i : over)
		{
			layers_over_player[array_counter++] = i;
		}
	}
}
