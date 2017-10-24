package com.jacl.capstone.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.jacl.capstone.CapstoneGame;
import com.jacl.capstone.data.enums.ItemSelection;
import com.jacl.capstone.screens.ScreenGame;
import com.jacl.capstone.screens.ScreenInventory;
import com.jacl.capstone.screens.ScreenMenu;

public class InputGame implements InputProcessor
{
	public ScreenGame screen;
	public CapstoneGame game;
	private TextureRegion textureRegion;
	private ScreenMenu mainMenu;
	private ScreenInventory inventory;
	public InputGame(ScreenGame screen)
	{
		this.game = screen.game;
		this.mainMenu = new ScreenMenu(game,"Paused");
		this.screen = screen;
	}

	@Override
	public boolean keyDown(int keycode)
	{
		if(screen.hud.dialogue_handler.showing_dialogue)
		{
			
		}
		else
		{
			//keycode is an integer representation of the key being used. This is the key in the down position in this case.
			switch(keycode)
			{
				//The Keys class has static references to all keys on the keyboard. We can use these to decode the button click.
				case Keys.UP:
				case Keys.W:
					screen.world.entity_handler.player.up = true;
					break;
				case Keys.DOWN:
				case Keys.S:
					screen.world.entity_handler.player.down = true;
					break;
				case Keys.LEFT:
				case Keys.A:
					screen.world.entity_handler.player.left = true;
					break;
				case Keys.RIGHT:
				case Keys.D:
					screen.world.entity_handler.player.right = true;
					break;
			}
		}
		
		//Always return true on input methods you use. This tells LibGDX that it is in use and should be reading for it.
		return true;
	}
	
	@Override
	public boolean keyUp(int keycode)
	{
		//keycode is an integer representation of the key being used. This is the key in the up position in this case. Note: Keys that have never been pressed are also in this position. It is not solely a key being up after being down.
		if(screen.hud.dialogue_handler.showing_dialogue)
		{
			switch(keycode)
			{
				//The Keys class has static references to all keys on the keyboard. We can use these to decode the button click.
				case Keys.UP:
				case Keys.W:
					screen.hud.dialogue_handler.reverseDialogue();
					break;
				case Keys.DOWN:
				case Keys.S:
					screen.hud.dialogue_handler.forwardDialogue();
					break;
				case Keys.SPACE:
					screen.hud.dialogue_handler.forwardDialogue();
					break;
			}
		}
		else
		{
			switch(keycode)
			{
				//The Keys class has static references to all keys on the keyboard. We can use these to decode the button click.
				case Keys.UP:
				case Keys.W:
					screen.world.entity_handler.player.up = false;
					break;
				case Keys.DOWN:
				case Keys.S:
					screen.world.entity_handler.player.down = false;
					break;
				case Keys.LEFT:
				case Keys.A:
					screen.world.entity_handler.player.left = false;
					break;
				case Keys.RIGHT:
				case Keys.D:
					screen.world.entity_handler.player.right = false;
					break;
				case Keys.SPACE:
					screen.world.entity_handler.player.attack.doAttack(ItemSelection.SWORD);;
					break;
				case Keys.E:
					//screen.world.entity_handler.add(EnemyFactory.spawn(EnemyType.SAMPLE_CREEP, screen.world, 2, 4.5f, screen.world.data_handler.entity_root));
					break;
				case Keys.R:
					clearInput();							//InputGame.class.cast(Gdx.input.getInputProcessor()).clearInput();
					//screen.hud.dialogue_handler.startDialogue("Hello. This is a long string.\n TIEUEUEUEJFKJDKJJ<s>KJKAKFJAKJFJKSAFKJSFJK");
					screen.hud.dialogue_handler.startDialogue(Gdx.files.internal("dialogue/chapter1-dialog.txt").readString());
					break;
				case Keys.I:
					if (game.getScreen() == inventory)
					{
						//mainMenu.pause();
						System.out.println("abc");
						game.setScreen(screen);
					}
					else if(game.getScreen() == screen)
					{
						screen.pause();
						textureRegion = ScreenUtils.getFrameBufferTexture(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
						this.inventory = new ScreenInventory(game,textureRegion);
						game.setScreen(inventory);
					}
					break;
				case Keys.ESCAPE:
					if (game.getScreen() == mainMenu)
					{
						mainMenu.pause();
						game.setScreen(screen);
					}
					else if(game.getScreen() == screen)
					{
						screen.pause();
						game.setScreen(mainMenu);
					}	
					break;
				case Keys.BACKSPACE:
					screen.hud.time.hours++;
					break;
			}
		}
		
		//Always return true on input methods you use. This tells LibGDX that it is in use and should be reading for it.
		return true;
	}
	
	@Override
	public boolean keyTyped(char character)
	{
		return false;
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		return false;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		return false;
	}
	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		return false;
	}
	
	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		return false;
	}
	
	@Override
	public boolean scrolled(int amount)
	{
		return false;
	}
	
	/**
	 * Clear any input-related variables that are being held.
	 */
	public void clearInput()
	{
		//Directions
		screen.world.entity_handler.player.up = false;
		screen.world.entity_handler.player.down = false;
		screen.world.entity_handler.player.left = false;
		screen.world.entity_handler.player.right = false;
		
		//Other actions.
	}
}
