package tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScreenTutorialGame extends ScreenAdapter
{
	public TutorialGame game;					//Allows access of game data objects.
	
	public SpriteBatch batch;					//Manages drawing.
	
	public Sprite sprite;						//Almost everything on the screen will be a sprite.
	
	public float sprite_speed = 300f;		//Try to add the 'f' after every float value to assure the most accurate value.
														//Also keep in mind that, because we're using delta * speed to set our translations,
														//this speed is in terms of pixels-per-second.
	
	public boolean up, down, left, right;	//Movement signals. If being pressed, set the signal. The updating/rendering will read
														//this signal and move appropriately.
	
	public ScreenTutorialGame(TutorialGame game)
	{
		//Pass in the game reference to access the AssetManager and the game-related methods like setScreen(...).
		this.game = game;
		
		//Initialize SpriteBatch. This object manages the background OpenGL that we don't care about.
		batch = new SpriteBatch();
		
		//Initialize sprites with a texture from the AssetManager extension class found in TutorialGame.
		sprite = new Sprite(game.assets.get("texture.jpg", Texture.class));
		sprite.setBounds(0, 30, 300, 300);
		
		//Set the input processor to our personal input class. For neatness, we will want to make a new input tutorial class for every part of the game. (Map, Main Menu, Pause Menu)
		Gdx.input.setInputProcessor(new InputTutorial(this));
	}
	
	/**
	 * The render() method is called every frame to update and draw the screen.<br><br>
	 * 
	 * Best way I have found to use it is as follows:<br>
	 * Update all items first. This includes movement, spells, attacks, etc.<br>
	 * Collision detection next. This allows us to destroy any unneeded sprites or set knockback on being hit. Set visibility of objects at this point.<br>
	 * Draw afterward so that the update is the most accurate representation of the current state of the game.
	 */
	@Override
	public void render(float delta)
	{
		//Update. If multiple updates need to be made across multiple managers (like the UI, the world, etc), make a new method for neatness.
		if(sprite != null)
		{
			//Read the signals. Translate appropriately.
			if(up)
				sprite.translateY(sprite_speed * delta);
			if(down)
				sprite.translateY(-sprite_speed * delta);
			if(left)
				sprite.translateX(-sprite_speed * delta);
			if(right)
				sprite.translateX(sprite_speed * delta);
			
			//If sprite's left edge goes off the right side of the screen, move to other side of the screen. This allows us to test bounds and collision detection.
			if(sprite.getX() > Gdx.graphics.getWidth())
				sprite.setX(-sprite.getWidth());
			
			/* Drawing comes next.
			 * We want to clear the last rendering of the screen by simply painting over it.
			 * We do this through two OpenGL functions.
			 * The first function sets the color to use as our reset color. Black or white are common here.
			 * The second function does the actual clearing. Everything is painted over so that we have
			 * a blank slate. The drawing that happens after this will draw over this painted color, so
			 * you can think of it as a way to set the background color.*/
			Gdx.gl.glClearColor(0f, 0f, 0f, 0f);		//Reset color is (0,0,0). This is black.
																	//Note: The passed numbers are between 0 and 1.
																	//If you have a hex value, simply divide each channel by 255.
			
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);	//Clearing done here.
			
			//Must start drawing with SpriteBatch.begin(). I like to tab-over all items being drawn to make it look like a block of code for simple finding.
			batch.begin();
			
				//Draw if sprite is not null.
				if(sprite != null)
					sprite.draw(batch);
			
			//Batch must end drawing with SpriteBatch.end(). End tabbing-over here.
			batch.end();
		}
	}
}
