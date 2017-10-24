package com.jacl.capstone.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.jacl.capstone.data.Assets;
import com.jacl.capstone.helpers.handlers.hud.DialogueHandler;
import com.jacl.capstone.hud.world.HealthBar;
import com.jacl.capstone.hud.world.MoneyCount;
import com.jacl.capstone.screens.ScreenGame;
import com.jacl.capstone.world.atmosphere.GameTime;
import com.jacl.capstone.world.atmosphere.TimeColorer;

/**
 * Updates and renders the HUD of the world.
 * @author Lee
 */
public class HUD
{
	public ScreenGame screen;
	
	public DialogueHandler dialogue_handler;
	public OrthographicCamera camera;
	public BitmapFont font;
	
	public HealthBar health_bar;
	public GameTime time;
	public MoneyCount money;
	
	public HUD(ScreenGame screen)
	{
		this.screen = screen;
		
		//To make the HUD easier to use, we should use a second camera.
		//This new camera will change how we draw items in the sprite batch.
		//Set the dimensions of the camera equal to the size of the screen and center the camera on the middle.
		//Draw items as a percentage of the screen.
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0f);
		camera.update();
		
		font = screen.game.assets.get(Assets.FONT24, BitmapFont.class);
		
		dialogue_handler = new DialogueHandler(this);
		health_bar = new HealthBar(this);
		money = new MoneyCount(this);
	}
	
	/**
	 * Call this to initialize the HUD. This is called after loading from a save.
	 */
	public void init(String time_set)
	{
		health_bar.init();
		time = new GameTime(time_set);
	}
	
	public void update(float delta)
	{
		if(dialogue_handler.showing_dialogue)						//If dialogue is showing, don't do anything here.
		{
			
		}
		else if(screen.world.event_handler.event != null)		//If there is an active event, play it. Otherwise, update normally.
		{
			screen.world.event_handler.event.update(delta);
		}
		else
		{
			HUDUpdate(delta);
		}
	}
	
	public void draw()
	{
		//If there is an active event, draw it. Otherwise, draw normally.
		if(screen.world.event_handler.event != null)
		{
			screen.world.event_handler.event.draw(screen.batch);
		}
		else
		{
			HUDDraw();
		}
	}
	
	/**
	 * The function in which the actual updating is done. Separated out so that 
	 * events/dialogue may use the normal game logic without getting held-up in the
	 * update() method.
	 */
	public void HUDUpdate(float delta)
	{
		time.update(delta);
		if(time.recently_updated_minute)
		{
			screen.world.time_color = TimeColorer.getColor(time);
		}
		
		health_bar.update(delta);
		dialogue_handler.update(delta);
	}
	
	/**
	 * The function in which the actual drawing is done. Separated out so that 
	 * events/dialogue may use this as a frame buffer of sorts without getting held-up
	 * in the draw() method.
	 */
	public void HUDDraw()
	{
		//Set the projection matrix of the sprite to our new camera. This keeps the two layers from affecting the coordinates of the other.
		screen.batch.setProjectionMatrix(camera.combined);
		screen.batch.begin();
			font.draw(screen.batch, time.toString(), 0f, Gdx.graphics.getHeight());											//Time.
			
			font.setColor(Color.YELLOW);
			font.draw(screen.batch, money.toString(), 0, Gdx.graphics.getHeight() - font.getLineHeight());			//Money.
			font.setColor(Color.WHITE);
			
			health_bar.draw();																												//Health.
		screen.batch.end();
		
		dialogue_handler.draw();																											//Dialogue.
	}
}
