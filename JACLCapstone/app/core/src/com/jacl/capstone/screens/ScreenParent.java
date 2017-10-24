package com.jacl.capstone.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jacl.capstone.CapstoneGame;

/**
 * Handles draw and update order and screen clearing. Extend from this rather than from ScreenAdapter.<br><br>
 * 
 * Call this SpriteBatch or ShapeRenderer during the drawing phase. We don't want to create new renderers because both
 * of these objects are fairly resource-heavy. Because we're working in a desktop environment, this
 * is not as big of a deal as if we were working on a mobile device. Nevertheless, we're doing it out of good practice.
 * 
 * @author Lee
 * @contribute Huy
 */
public abstract class ScreenParent extends ScreenAdapter
{
	public CapstoneGame game;
	public SpriteBatch batch;
	public ShapeRenderer renderer;
	public InputProcessor input;
	
	protected Color color_background;
	
	public ScreenParent(CapstoneGame game)
	{
		this.game = game;
		this.batch = new SpriteBatch();
		this.renderer = new ShapeRenderer();
		
		color_background = setUpBackgroundColor();
		input = setUpInput();
		Gdx.input.setInputProcessor(input);
	}
	
	@Override
	public void show()
	{
		super.show();
		Gdx.input.setInputProcessor(input);
	}
	
	/**
	 * This will be called automatically by the screen. Rather than doing all the drawing here,
	 * just set up the update/draw stack that the extended screens will implement.
	 */
	@Override
	public void render(float delta)
	{
		//Update
		update(delta);
		
		//Draw
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClearColor(color_background.r, color_background.g, color_background.b, color_background.a);
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		draw();
	}
	
	/**
	 * This abstract method changes the background color of the screen. Called once automatically during the constructor,
	 * but it can be called again whenever you want. Can be used to create a day-night type of thing with repeated calls.<br><br>
	 * 
	 * If the background is never seen by the player, then just think of it as the color OpenGL uses to refresh the screen 
	 * after every frame. Use (0, 0, 0, 0).
	 * 
	 * @return The color that will be set as the background.
	 */
	public abstract Color setUpBackgroundColor();
	
	/**
	 * This abstract method assures the creation of the screen's InputProcessor. Put another way, this is here to make
	 * sure the movement keys are set. Called once automatically during the constructor, but it can be called again
	 * whenever you want.
	 * 
	 * @return The InputProcessor we want to use. I strongly recommend we implement the InputProcessor in a new 
	 * input class for each screen.
	 */
	public abstract InputProcessor setUpInput();
	
	/**
	 * This is where we will do all the game logic. All movement, transformations, collision detection, adding/removing
	 * of game entities, flag checking, camera updating, and screen switching should be done here. 
	 * 
	 * @param delta Change in time since last update.
	 */
	public abstract void update(float delta);
	
	/**
	 * This is where we will do all the drawing. No updating should be done here. Remember to do batch.begin() and batch.end()
	 * for sprite drawing and renderer.begin() and renderer.end() during shape rendering.
	 */
	public abstract void draw();
}