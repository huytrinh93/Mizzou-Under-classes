package com.jacl.capstone.helpers.handlers.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.jacl.capstone.world.World;

import java.io.IOException;

/**
 * This class will manage the reading and presenting of any XML data.
 *
 * @author Lee
 *
 */
public class DataHandler
{
	public World world;

	public Element entity_root;
	public Element item_root;
	public Element player_root;
	public Element collectible_root;

	public DataHandler(World world)
	{
		this.world = world;

		//Get all data roots.
		XmlReader reader = new XmlReader();
		try
		{
			entity_root = reader.parse(Gdx.files.internal("data/enemies.xml"));
			item_root = reader.parse(Gdx.files.internal("data/items.xml"));
			player_root = reader.parse(Gdx.files.local("saves/test.xml"));
			collectible_root = reader.parse(Gdx.files.internal("data/collectibles.xml"));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public void handlerInit()
	{

	}
}
