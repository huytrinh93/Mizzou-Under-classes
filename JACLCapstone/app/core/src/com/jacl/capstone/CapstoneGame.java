package com.jacl.capstone;

import com.badlogic.gdx.Game;
import com.jacl.capstone.data.Assets;
import com.jacl.capstone.screens.ScreenMenu;

public class CapstoneGame extends Game
{	
	public String GAME_NAME;
	public String GAME_VERSION;
	
	public Assets assets;
	
	private final GamePreferences preferences;
	
	public CapstoneGame(String gameName, String gameVersion)
	{
		this.GAME_NAME = gameName;
		this.GAME_VERSION = gameVersion;
		preferences = new GamePreferences(this);
	}
	
	//Collaborators
	public GamePreferences getPreferences()
	{
		return preferences;
	}

	/**
	 * This is basically the constructor. Use it to initialize any necessary objects and to set the screen.
	 */
	@Override
	public void create()
	{
		//Initialize assets here through an extension of the AssetManager class.
		assets = new Assets();
		
		//Setting the screen changes the display. To change the screen from another class: game.setScreen(...); That's why we pass in a reference to this class.
		setScreen(new ScreenMenu(this));
	}	
	
	/**
	 * Don't ever call this. Let the game handle this method. Calling this method before the game is 
	 * ending will lead to either invisible textures or NullPointerExceptions.<br><br>
	 * 
	 * This method destroys any allocated audio or graphical memory. Java has a garbage collector for
	 * most other objects in the game, but libGDX puts the decision on the user when the game's assets
	 * need to be destroyed.<br><br>
	 * 
	 * In a mobile environment, we would want to dynamically load and remove assets due to the lack of 
	 * graphical memory. Child screens to the game would want to call their dispose() methods upon ending
	 * to assure graphical/audio memory is not bloated with unused assets. Because this is a desktop 
	 * environment, however, this action is not necessary. We have more than enough space to load all the
	 * game's assets when the game first loads and keep them in memory until we're ready to end. Upon ending,
	 * this method is called and all the assets are released.
	 */
	@Override
	public void dispose()
	{
		super.dispose();
		assets.dispose();
	}
}
