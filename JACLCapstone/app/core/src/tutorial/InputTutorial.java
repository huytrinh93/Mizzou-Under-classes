package tutorial;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;

/**
 * This is how we should implement the input classes.
 * InputProcessor is the interface LibGDX uses to manipulate the controls of the game.
 * The different method names within this class represent the corresponding input action.
 * 
 * @author Lee
 *
 */
public class InputTutorial implements InputProcessor
{	
	public ScreenTutorialGame screen;
	
	public InputTutorial(ScreenTutorialGame screenTutorialGame)
	{
		//We want to save an instance of the screen that relates to this InputProcessor to be able to manipulate it later.
		this.screen = screenTutorialGame;
	}
	
	@Override
	public boolean keyDown(int keycode)
	{
		//keycode is an integer representation of the key being used. This is the key in the down position in this case.
		switch(keycode)
		{
			//The Keys class has static references to all keys on the keyboard. We can use these to decode the button click.
			case Keys.UP:
				screen.up = true;
				break;
			case Keys.DOWN:
				screen.down = true;
				break;
			case Keys.LEFT:
				screen.left = true;
				break;
			case Keys.RIGHT:
				screen.right = true;
				break;
		}
		
		//Always return true on input methods you use. This tells LibGDX that it is in use and should be reading for it.
		return true;
	}
	
	@Override
	public boolean keyUp(int keycode)
	{
		//keycode is an integer representation of the key being used. This is the key in the up position in this case. Note: Keys that have never been pressed are also in this position. It is not solely a key being up after being down.
		switch(keycode)
		{
			//The Keys class has static references to all keys on the keyboard. We can use these to decode the button click.
			case Keys.UP:
				screen.up = false;
				break;
			case Keys.DOWN:
				screen.down = false;
				break;
			case Keys.LEFT:
				screen.left = false;
				break;
			case Keys.RIGHT:
				screen.right = false;
				break;
		}
		
		//Always return true on input methods you use. This tells LibGDX that it is in use and should be reading for it.
		return true;
	}
	
	@Override
	public boolean keyTyped(char character)
	{//Don't use. Would be used if a key is typed -- like if we wanted to implement a keyboard that reads key presses. Not useful in our game.
		return false;
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{//This would be used for mouse clicks.
		return false;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{//This would be used for mouse clicks.
		return false;
	}
	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{//This would be used for mouse being clicks and then dragged. Think swipe commands on a touchscreen.
		return false;
	}
	
	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{//This would be used for the mouse being moved.
		return false;
	}
	
	@Override
	public boolean scrolled(int amount)
	{//This would be used for the mouse scroll wheel being used.
		return false;
	}
	
}
