package com.jacl.capstone.hud.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.jacl.capstone.data.Assets;
import com.jacl.capstone.hud.HUD;

/**
 * Represents the player's current health in a visual sense. Note: Don't use this
 * visual representation when checking health-related functions (like dying or healing).
 * Instead, we want to have a changeHealth() method in the Player class. This method
 * will call the changeValue() method in this class.
 * @author Lee
 */
public class HealthBar
{
	public HUD hud;
	
	private NinePatch health_bar_background;
	private NinePatch health_bar_foreground;
	private float width;
	
	//hub.com/
	
	private final float X = 9f;
	private final float Y = 9f;
	private final float CONSTANT_WIDTH = 150f;
	private final float CONSTANT_HEIGHT = 8f;
	
	public HealthBar(HUD hud)
	{
		this.hud = hud;
		
		health_bar_background = new NinePatch(hud.screen.game.assets.get(Assets.HEALTHBAR_BACKGROUND, Texture.class), 5, 5, 2, 2);
		health_bar_foreground = new NinePatch(hud.screen.game.assets.get(Assets.HEALTHBAR_FOREGROUND, Texture.class), 5, 5, 2, 2);
	}
	
	/**
	 * Called after loading from a save.
	 */
	public void init()
	{
	}
	
	public void update(float delta)
	{//Use this for animation setting the width of the bar.
		//Animation.
		
		//Bar width.
		width = CONSTANT_WIDTH * hud.screen.world.entity_handler.player.health_current / hud.screen.world.entity_handler.player.health_max;
	}
	
	public void draw()
	{
		health_bar_background.draw(hud.screen.batch, X, Y, CONSTANT_WIDTH, CONSTANT_HEIGHT);
		health_bar_foreground.draw(hud.screen.batch, X, Y, width, CONSTANT_HEIGHT);
	}
}
