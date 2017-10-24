package com.jacl.capstone.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jacl.capstone.CapstoneGame;

public class ScreenInventory extends ScreenAdapter {
	public ScreenInventory(CapstoneGame game, TextureRegion textureRegion) {
		this.game = game;
		this.last_screen = game.getScreen();
		this.texture = textureRegion;
	}
	
	ArrayList<String> inventory = new ArrayList<String>();
	//private InventoryActor inventoryActor;
	private TextureAtlas atlas;
	private Skin skin;
	private Table table;
	private BitmapFont bitmap28;
	private TextButton buttonBack;
	public CapstoneGame game;
	private Screen last_screen;
	public static Stage stage;
	private Label heading;
	private TextureRegion texture;
	private SpriteBatch batch;
	
	@Override
	public void show() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		atlas = new TextureAtlas("atlas.pack");
		skin = new Skin(atlas);
		
		
		Skin skin1 = new Skin(Gdx.files.internal("uiskin.json"));
		bitmap28 = new BitmapFont(Gdx.files.internal("hud/fonts/font28.fnt"), false);
		
		// heading
		String label = "Character Name";
		LabelStyle headingStyle = new LabelStyle(bitmap28, Color.WHITE);
		heading = new Label(label, headingStyle);
		heading.setFontScale(2);
		
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
		buttonBack.pad(5);
		/*
		//create row and column of actors 
		//then show it in grid / table
		int rowActors = 1;
		int columnActors = 8;
		//int actorWidth = Gdx.graphics.getWidth() / rowActors;
		int actorWidth = 2;
		//int actorHeight = Gdx.graphics.getHeight() / columnActors;
		int actorHeight = 2;
		Actor[] actors = new Actor[rowActors * columnActors];
		table.add(heading).padBottom(70);
		table.add();
		table.row();
		for (int i = 0 ; i < rowActors ; i++){
			for(int j = 0; j < columnActors ; j++)
			{
				Actor actor = actors[(i * columnActors) + j];
				//table.add(actor).width(actorWidth).height(actorHeight);
			}
			table.row();
		}
		*/

		table.setFillParent(true);
		/*
		final Table scrollTable = new Table();
		ScrollPaneStyle scrollStyle = new ScrollPaneStyle();
		scrollStyle.background = skin1.getDrawable("default-rect"); 
		scrollStyle.vScrollKnob = skin1.getDrawable("default-round-large");
		final ScrollPane scroller = new ScrollPane(scrollTable,scrollStyle);
		*/
		final Tree tree = new Tree(skin1);

		inventory.add("Axe");
		inventory.add("Sword");
		inventory.add("Dagger");
		inventory.add("Health Potion");
		inventory.add("Health Potion");
		inventory.add("Health Potion");
		inventory.add("Mana Potion");
		inventory.add("Hammer");
		
		for(String n : inventory){
			// create Node for tree act as items and its choices
			final Node newNode = new Node(new TextButton(n.toString(), skin1));
			final Node equipNode = new Node(new TextButton("Equip", skin1));
			final Node cancelNode = new Node(new TextButton("Cancel", skin1));
			// add functions for node when clicked
			newNode.getActor().addListener(new ClickListener(){
				public void clicked(InputEvent event, float x, float y){
					if(newNode.isExpanded())
						newNode.collapseAll();
					else{
						tree.collapseAll();
						newNode.expandAll();
					}
				}
			});
			equipNode.getActor().addListener(new ClickListener() {
				public void clicked (InputEvent event, float x, float y) {
					tree.remove(newNode);
				}
			});
			cancelNode.getActor().addListener(new ClickListener() {
				public void clicked (InputEvent event, float x, float y) {
					newNode.collapseAll();
				}
			});
			// add Node to tree and choices to each node
			newNode.add(equipNode);
			newNode.add(cancelNode);
			tree.add(newNode);
		}
		tree.setPadding(100);
		tree.setBounds(50, 50, 100, 100);
		//scrollTable.add(tree);
		table.add(heading).padBottom(70);
		table.add();
		table.row();
		table.add(tree).fill().expand();
		table.row();
		table.add(buttonBack);
		table.center();
		
		stage.addActor(table);
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch = new SpriteBatch();
		batch.begin();
			Color c = batch.getColor();
			batch.setColor(c.r, c.g, c.b, 0.3f);
			batch.draw(texture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();
		
		stage.act(delta);
		stage.draw();
		//stage.setDebugAll(true);
	}

	@Override
	public void dispose() {
		stage.dispose();
		atlas.dispose();
		skin.dispose();
		bitmap28.dispose();
		
	}

}
