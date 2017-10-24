package com.jacl.capstone.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.jacl.capstone.CapstoneGame;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * 
 * @author huy_trinh
 * Also need game state such as menu, in-game, gameover
 * States are in int from 1-3.
 *  
 */
public class ScreenMenu extends ScreenAdapter{
	private String caption;
	private Stage stage;
	public CapstoneGame game;
	private TextureAtlas atlas;
	private Skin skin;
	private Table table;
	private BitmapFont bitmap;
	private Label heading;
	private TextButton buttonExit,buttonLoad,buttonNew,buttonCredit,buttonOptions;
	public static Texture backgroundTexture;
	private SpriteBatch spriteBatch;
	private Music musicbg;
	public ScreenMenu(CapstoneGame game) {
		this.game = game;
	}
	/**
	 * create Screen menu with button of Exit, Load, New, Credit, and Options
	 * @param game pass parameter of game
	 * @param paused pass string for screen label
	 */
	public ScreenMenu(CapstoneGame game, String paused){
		this.game = game;
		this.caption = paused;
	}

	@Override
	/**
	 * This is called when the screen is created.
	 * New Game: start a new game
	 * Load: Gather the player's location and time from the save file.
	 * Options: show volume, graphic and such
	 * Credits: mostly name
	 */
	public void show() {
		stage = new Stage();
		spriteBatch = new SpriteBatch();
		
		Gdx.input.setInputProcessor(stage);
		
		atlas = new TextureAtlas("atlas.pack");
		skin = new Skin(atlas);
		spriteBatch = new SpriteBatch();
		//setting up backgrounds
		backgroundTexture = new Texture("Backgrounds/menubg.gif");// load the image
		
		// adding background music
		musicbg = Gdx.audio.newMusic(Gdx.files.internal("sounds/menu.mp3"));
		if(game.getPreferences().isMusicEffectsEnabled()){
			musicbg.setLooping(true);
			//System.out.println(game.getPreferences().getVolume());
			musicbg.setVolume(game.getPreferences().getVolume());
			musicbg.play();
		}
		
		//fonts
		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		bitmap = new BitmapFont(Gdx.files.internal("hud/fonts/font44.fnt"),false);
		
		//buttons styles
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("button.up");
		textButtonStyle.down = skin.getDrawable("button.down");
		textButtonStyle.pressedOffsetX = 1;
		textButtonStyle.pressedOffsetY = -1;
		textButtonStyle.font = bitmap;
		
		//exit button which is exit console
		buttonExit = new TextButton("EXIT", textButtonStyle);
		buttonExit.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				Gdx.app.exit();
			}
		});
		buttonExit.pad(5);
		
		// new game button which is start a new game
		buttonNew = new TextButton("New Game",textButtonStyle);
		buttonNew.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				
				((CapstoneGame) Gdx.app.getApplicationListener()).setScreen(new ScreenGame(game, "new.xml"));
			}
		});
		buttonNew.pad(5);
		
		//button load from a save point
		buttonLoad = new TextButton("Load Game",textButtonStyle);
		buttonLoad.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				//musicbg.stop();
				((CapstoneGame) Gdx.app.getApplicationListener()).setScreen(new ScreenGame(game, "test.xml"));
			}
		});
		buttonLoad.pad(5);
		
		//button credit which show contributors
		buttonCredit = new TextButton("Credits",textButtonStyle);
		buttonCredit.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				((CapstoneGame) Gdx.app.getApplicationListener()).setScreen(new ScreenCredit(game));
			}
		});
		buttonCredit.pad(5);
		
		// Options button which show volume, graphic etc.
		buttonOptions = new TextButton("Options",textButtonStyle);
		buttonOptions.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				((CapstoneGame) Gdx.app.getApplicationListener()).setScreen(new ScreenOptions(game));
				//musicbg.pause();
			}
		});
		buttonOptions.pad(5);
		
		//heading
		if(this.caption == null)
			this.caption = game.GAME_NAME+" "+game.GAME_VERSION;
		LabelStyle headingStyle = new LabelStyle(bitmap, Color.WHITE);
		heading = new Label(this.caption,headingStyle);
		heading.setFontScale(2);
		
		//add together stuffs
		table.add(heading);
		table.row();
		table.getCell(heading).spaceBottom(30);
		table.add(buttonNew);
		table.row();
		table.add(buttonLoad);
		table.row();
		table.add(buttonOptions);
		table.row();
		table.add(buttonCredit);
		table.row();
		table.add(buttonExit);
		//table.debug();
		stage.addActor(table);
	}
	
	@Override
	public void render(float delta) {
		//Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		spriteBatch = new SpriteBatch();
		spriteBatch.begin();
			spriteBatch.draw(backgroundTexture,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		spriteBatch.end();
		stage.act(delta);
		stage.draw();
	}
	
	@Override
	public void dispose() {
		stage.dispose();
		atlas.dispose();
		skin.dispose();
		bitmap.dispose();
		musicbg.dispose();
	}
	@Override
	public void hide(){
		musicbg.pause();
	}
}
