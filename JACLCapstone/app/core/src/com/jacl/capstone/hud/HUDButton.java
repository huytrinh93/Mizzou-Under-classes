package com.jacl.capstone.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.jacl.capstone.screens.ScreenParent;

/**
 * The way this should work is like so:
 * Create a new child class of this class for each button that will be available for clicking.
 * Each of these buttons will have an upColor that is used when inactive, a down color that is
 * used while being held down but not being clicked, and a click() method that is activated
 * upon a full click.
 * @author Lee
 */
public abstract class HUDButton
{
	public ScreenParent screen;
	public Sprite sprite;
	
	public boolean is_down;
	
	public HUDButton(ScreenParent screen, float x, float y, float width, float height, Texture texture)
	{
		this.screen = screen;
		
		sprite = new Sprite(texture);
		sprite.setBounds(x, y, width, height);
	}
	
	public void update(float delta)
	{//I can't imagine we'll need any animations here.
	}
	
	public void draw()
	{
		sprite.draw(screen.batch);
	}
	
	public abstract void upColor();
	public abstract void downColor();
	public abstract void click();
}
