package tutorial;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class AssetsTutorial extends AssetManager
{
	public AssetsTutorial()
	{
		/*
		 * The passed path starts from the assets/ folder. Because we used the UI to build our libGDX project,
		 * the assets/ files are linked together between the -core and -desktop (this would also include -ios, 
		 * -html, and -android if we were using those) folders.
		 * 
		 * The AssetManager can load virtually any asset. We can even make our own AssetLoaders (I'll handle that if necessary).
		 * We must pass in the second value (Texture.class in this instance) to tell it the type of asset we want
		 * this file to be. If we were loading a sound file, we'd use Sound.class instead.
		 * 
		 * As far as file types go, try to follow these guidelines for all types unless impossible:
		 * Texture: .png
		 * Sound: .mp3
		 * Music: .mp3
		 * BitmapFont: .fnt
		 */
		load("texture.jpg", Texture.class);
	}
}
