package com.jacl.capstone.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.jacl.capstone.CapstoneGame;


public class ScreenOptions extends ScreenAdapter
{
	
	public ScreenOptions(CapstoneGame game)
	{
		this.game = game;
		this.last_screen = game.getScreen();
	}
	
	private Screen last_screen;
	private Stage stage;
	public CapstoneGame game;
	private TextureAtlas atlas;
	private Skin skin;
	private TextureAtlas atlas1;
	private Skin skin1;
	private TextButton buttonBack;
	private Table table;
	private BitmapFont bitmap;
	private Label heading;
	private Label volumeValue;
	private Slider volumeSlider;
	private SpriteBatch batch;
	private Texture background;
	private Music musicbg;
	@Override
	/**
	 * This is called when the screen is created.
	 * show settings in game
	 * putting sliders and checkbox for sound and graphic
	 */
	public void show()
	{
		// adding background music
		musicbg = Gdx.audio.newMusic(Gdx.files.internal("sounds/10 Arpanauts.mp3"));
		if(game.getPreferences().isMusicEffectsEnabled()){
			musicbg.setLooping(true);
			//System.out.println(game.getPreferences().getVolume());
			musicbg.setVolume(game.getPreferences().getVolume());
			musicbg.play();
		}
		
		stage = new Stage(new ScreenViewport());
		
		Gdx.input.setInputProcessor(stage);
		
		atlas = new TextureAtlas("atlas.pack");
		skin = new Skin(atlas);
		
		atlas1 = new TextureAtlas("uiskin.atlas");
		skin1 = new Skin(atlas1);
		
		// Create background
		background = new Texture(Gdx.files.internal("Backgrounds/grassbg1.gif"));
		
		// fonts
		table = new Table(skin1);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		bitmap = new BitmapFont(Gdx.files.internal("hud/fonts/font44.fnt"), false);
		
		// buttons styles
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("button.up");
		textButtonStyle.down = skin.getDrawable("button.down");
		textButtonStyle.pressedOffsetX = 1;
		textButtonStyle.pressedOffsetY = -1;
		textButtonStyle.font = bitmap;
		
		// back button which is back to main menu
		buttonBack = new TextButton("Back", textButtonStyle);
		buttonBack.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				((CapstoneGame) Gdx.app.getApplicationListener()).setScreen(last_screen);
			}
		});
		buttonBack.pad(5);
		
		// heading
		String label = "Options";
		LabelStyle headingStyle = new LabelStyle(bitmap, Color.WHITE);
		heading = new Label(label, headingStyle);
		heading.setFontScale(2);
		
		
		// make checkbox Style
		CheckBoxStyle checkboxStyle = new CheckBoxStyle();
		checkboxStyle.checkboxOff = skin1.getDrawable("check-off");
		checkboxStyle.checkboxOn = skin1.getDrawable("check-on");
		checkboxStyle.font=bitmap;
		
		//create sound checkbox effects
		final CheckBox soundEffectsCheckbox = new CheckBox("Sound Effects", checkboxStyle);
		soundEffectsCheckbox.setChecked(true);
		soundEffectsCheckbox.setChecked(game.getPreferences().isSoundEffectsEnabled() ); 
		soundEffectsCheckbox.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) { 
				boolean enabled = soundEffectsCheckbox.isChecked();
				game.getPreferences().setSoundEffectsEnabled(enabled); 
			} 
		});
		//create music checkbox effects
		final CheckBox musicEffectsCheckbox = new CheckBox("Music Effects", checkboxStyle);
		musicEffectsCheckbox.setChecked(true);
		musicEffectsCheckbox.setChecked(game.getPreferences().isMusicEffectsEnabled() ); 
		musicEffectsCheckbox.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) { 
				boolean enabled = musicEffectsCheckbox.isChecked();
				game.getPreferences().setMusicEffectsEnabled(enabled); 
			} 
		});
		
		SliderStyle style = new SliderStyle();
		style.background = skin1.getDrawable("default-slider");
		style.knob = skin1.getDrawable("default-slider-knob");
		
		volumeSlider = new Slider(0, 100, 1, false, style);
		volumeSlider.setValue(game.getPreferences().getVolume()*100);
		volumeSlider.addCaptureListener(new ChangeListener(){
			public void changed (ChangeEvent event, Actor actor){
				if(volumeSlider.getValue()==0)
				{
					musicEffectsCheckbox.setChecked(false);
					soundEffectsCheckbox.setChecked(false);
				}
				else
				{
					musicEffectsCheckbox.setChecked(true);
				}
				updateVolumeLabel();
			}
		});
		// update title volume
		CharSequence x = Integer.toString(Math.round(game.getPreferences().getVolume()*100));
		volumeValue = new Label(x,headingStyle);
		updateVolumeLabel();
		
		// add together stuffs
		//table.align(Align.left);
		table.add(heading).padBottom(70);
		table.add();
		table.row();
		table.add(volumeSlider);
		table.add(volumeValue).fillX();
		table.row();
		table.add(musicEffectsCheckbox);
		table.row();
		table.add(soundEffectsCheckbox);
		table.row();
		table.add(buttonBack);
		table.row();
		stage.addActor(table);
	}
	
	@Override
	public void render(float delta)
	{
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch = new SpriteBatch();
		batch.begin();
			batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		batch.end();
		
		stage.act(delta);
		stage.draw();
		//stage.setDebugAll(true);
	}
	
	@Override
	public void dispose()
	{
		stage.dispose();
		atlas.dispose();
		skin.dispose();
		bitmap.dispose();
	}
	@Override
	public void hide()
	{
		musicbg.stop();
	}
	
	private void updateVolumeLabel()
	{
		int volume = Math.round(volumeSlider.getValue());
		volumeValue.setText(Integer.toString(volume));
		game.getPreferences().setVolume(volumeSlider.getValue()/100);
		musicbg.setVolume(volumeSlider.getValue()/100);
	}
}
