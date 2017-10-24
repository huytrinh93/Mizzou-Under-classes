package com.jacl.capstone.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jacl.capstone.CapstoneGame;

public class ScreenCredit extends ScreenAdapter{

	public ScreenCredit(CapstoneGame game) {
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
	private Label heading;
	private BitmapFont bitmap44;
	private BitmapFont bitmap24;
	private BitmapFont bitmap28;

	private static final String shortStory="This is the beginning of story in Jewel\n"
			+ "In a vast kingdom named Jewel,\n "
			+ "there once lived a family secluded in the woods.\n"
			+ "In this family there was a mother, a father, \n"
			+ "an older brother, and a younger sister.\n"
			+ "The mother and father loved their children\n"
			+ "very much, teaching them the ways of life as\n"
			+ "the children grew.\n"
			+ "The oldest brother was named Malc,\n"
			+ "short for Malachite, and he was always curious\n"
			+ "for knowledge.\n"
			+ "He would question his mother and father about\n "
			+ "everything in life and why the things\n"
			+ "were the way they are.\n"
			+ "The younger sister was named Dia, short for\n"
			+ "Diamond, and she was very loyal and followed\n"
			+ "her parents orders to a tee.";
	
	@Override
	/**
	 * This is called when the credits button on main menu is selected.
	 * Show story or contributors.
	 */
	public void show() {
		stage = new Stage();
		
		Gdx.input.setInputProcessor(stage);
		
		atlas = new TextureAtlas("atlas.pack");
		skin = new Skin(atlas);
		
		atlas1 = new TextureAtlas("uiskin.atlas");
		skin1 = new Skin(atlas1);
		
		bitmap44 = new BitmapFont(Gdx.files.internal("hud/fonts/font44.fnt"),false);
		bitmap24 = new BitmapFont(Gdx.files.internal("hud/fonts/font24.fnt"),false);
		bitmap28 = new BitmapFont(Gdx.files.internal("hud/fonts/font28.fnt"),false);
		//heading
		String label = "About";
		LabelStyle headingStyle = new LabelStyle(bitmap44, Color.WHITE);
		heading = new Label(label,headingStyle);
		heading.setFontScale(2);
		
		//create scrollPane content
		LabelStyle mainStyle = new LabelStyle(bitmap28, Color.WHITE);
		LabelStyle contentStyle = new LabelStyle(bitmap24, Color.WHITE);
		final Label text = new Label(shortStory,mainStyle);
		text.setAlignment(Align.center);
		text.setWrap(true);
		//add label for contributors
		final Label contributor = new Label("Contributor: ",mainStyle);
		contributor.setAlignment(Align.center);
		contributor.setWrap(true);
		final Label lee = new Label(" - Lee Presswood",contentStyle);
		lee.setAlignment(Align.center);
		lee.setWrap(true);
		final Label cj = new Label(" - Charles Voege",contentStyle);
		cj.setAlignment(Align.center);
		cj.setWrap(true);
		final Label joe = new Label(" - Joseph Trammel",contentStyle);
		joe.setAlignment(Align.center);
		joe.setWrap(true);
		final Label amy = new Label(" - Amy Schmidt",contentStyle);
		amy.setAlignment(Align.center);
		amy.setWrap(true);
		final Label huy = new Label(" - Huy Trinh",contentStyle);
		huy.setAlignment(Align.center);
		huy.setWrap(true);
		
		// scroll table and fonts
		final Table scrollTable = new Table();
		scrollTable.add(text).padBottom(80);
		scrollTable.row();
		scrollTable.add(contributor);
		scrollTable.row();
		scrollTable.add(lee);
		scrollTable.row();
		scrollTable.add(cj);
		scrollTable.row();
		scrollTable.add(joe);
		scrollTable.row();
		scrollTable.add(amy);
		scrollTable.row();
		scrollTable.add(huy);
		scrollTable.row();
		ScrollPaneStyle scrollStyle = new ScrollPaneStyle();
		//scrollStyle.vScroll = skin1.getDrawable("default-slider");  
		scrollStyle.background = skin1.getDrawable("default-rect"); 
		scrollStyle.vScrollKnob = skin1.getDrawable("default-round-large");
		final ScrollPane scroller = new ScrollPane(scrollTable,scrollStyle);
		//scroller.fling(0.5f, 0.1f, 0.1f);
		//scroller.setFlingTime(2);
		
		
		final Table table = new Table();
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		//buttons styles
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("button.up");
		textButtonStyle.down = skin.getDrawable("button.down");
		textButtonStyle.pressedOffsetX = 1;
		textButtonStyle.pressedOffsetY = -1;
		textButtonStyle.font = bitmap28;
		
		//back button which is back to main menu
		buttonBack = new TextButton("Back", textButtonStyle);
		buttonBack.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				((CapstoneGame) Gdx.app.getApplicationListener()).setScreen(last_screen);
			}
		});
		//buttonBack.pad(5);
		
		//add together stuffs
		table.add(heading);
		table.row();
		table.getCell(heading).spaceTop(0);
		table.add(scroller).fillX().expandX();
		table.getCell(scroller).spaceBottom(100);
		table.row();
		table.add(buttonBack).spaceBottom(100);
		table.row();
		
		stage.addActor(table);
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
		//stage.setDebugAll(true);
	}

	@Override
	public void dispose() {
		stage.dispose();
		atlas.dispose();
		skin.dispose();
		atlas1.dispose();
		skin1.dispose();
		bitmap44.dispose();
		bitmap24.dispose();
		bitmap28.dispose();
	}
}
