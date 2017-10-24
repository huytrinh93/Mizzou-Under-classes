package com.jacl.capstone.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * This class should be used to load our assets.<br><br>
 *
 * To load assets:<br>
 * load(<file_name>, <asset_type>.class);<br><br>
 *
 * To retrieve them:<br>
 * get(<file_name>, <asset_type>.class);<br><br>
 *
 * Various String constants will be added to allow
 * for calling of file names in a cleaner fashion.
 * @author Lee
 */
public class Assets extends AssetManager
{
	public static final String SPRITE_BASE = "sprites/";
	public static final String TEXTURE_BASE = "textures/";

	public static final String HEALTHBAR_BACKGROUND = "hud/healthbar/health-red.png";
	public static final String HEALTHBAR_FOREGROUND = "hud/healthbar/health-blue.png";

	public static final String FONT_DIALOGUE = "hud/fonts/dialogue.fnt";
	public static final String FONT12 = "hud/fonts/font12.fnt";
	public static final String FONT16 = "hud/fonts/font16.fnt";
	public static final String FONT20 = "hud/fonts/font20.fnt";
	public static final String FONT24 = "hud/fonts/font24.fnt";
	public static final String FONT28 = "hud/fonts/font28.fnt";
	public static final String FONT32 = "hud/fonts/font32.fnt";
	public static final String FONT36 = "hud/fonts/font36.fnt";
	public static final String FONT40 = "hud/fonts/font40.fnt";
	public static final String FONT44 = "hud/fonts/font44.fnt";
	public static final String FONT48 = "hud/fonts/font48.fnt";
	public static final String FONT52 = "hud/fonts/font52.fnt";
	public static final String FONT56 = "hud/fonts/font56.fnt";
	public static final String FONT60 = "hud/fonts/font60.fnt";

	public Assets()
	{
		/* Note: These will all be loaded asynchronously.
		 * Trying to grab these assets before they are
		 * fully loaded will result in an error and
		 * a crash.
		 *
		 * To fix this, we will want to either
		 * finishLoading() at the end of this constructor
		 * or create a loading screen screen that shows
		 * the progress of our assets being loaded.
		 *
		 * For this project, the second option will be
		 * utilized.
		 */
		//Load different assets.
		//Audio


		//Fonts
		loadFontsFromFolder("hud/fonts/");

		//Textures
		loadTexturesFromFolder(SPRITE_BASE + "characters/dia/");
		loadTexturesFromFolder(SPRITE_BASE + "enemies/demon/");
		loadTexturesFromFolder(SPRITE_BASE + "enemies/spider/");
		loadTexturesFromFolder(SPRITE_BASE + "enemies/skeleton/");
		loadTexturesFromFolder(SPRITE_BASE + "items/");
		loadTexturesFromFolder(TEXTURE_BASE + "weapons/");

		loadTexturesFromFolder("hud/healthbar/");
		
		load("sprites/dark.png", Texture.class);

		finishLoading();
	}

	private void loadFontsFromFolder(String folder)
	{
		for(FileHandle file : Gdx.files.internal("../core/assets/" + folder).list())
		{
			if(file.extension().equals("fnt"))
			{
				load(folder + file.name(), BitmapFont.class);
			}
		}
	}

	private void loadTexturesFromFolder(String folder)
	{
		for(FileHandle file : Gdx.files.internal("../core/assets/" + folder).list())
		{
			load(folder + file.name(), Texture.class);
		}
	}
}
