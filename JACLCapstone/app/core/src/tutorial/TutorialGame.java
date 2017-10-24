package tutorial;

import com.badlogic.gdx.Game;

public class TutorialGame extends Game
{	
	public AssetsTutorial assets;
	
	/**
	 * This is basically the constructor. Use it to initialize any necessary objects and to set the screen.
	 */
	@Override
	public void create()
	{
		//Initialize assets here through an extension of the AssetManager class.
		assets = new AssetsTutorial();
		assets.finishLoading();
		
		//Setting the screen changes the display. To change the screen from another class: game.setScreen(...); That's why we pass in a reference to this class.
		setScreen(new ScreenTutorialGame(this));
	}	
}
