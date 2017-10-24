package com.jacl.capstone.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.jacl.capstone.data.Assets;
import com.jacl.capstone.data.enums.Direction;
import com.jacl.capstone.world.entities.MovingEntity;

public class TextureHelper
{
	public MovingEntity entity;
	public Direction direction;
	
	private final int MOVE_FRAMES = 2;
	private float move_check = 0.1f;
	private float frame_change;
	private float frame_change_current;
	private int frame_current = 1;
	private Texture[] up_frames;
	private Texture[] down_frames;
	private Texture[] left_frames;
	private Texture[] right_frames;
	
	private float dx;
	private float dy;
	
	public TextureHelper(MovingEntity entity, float movespeed)
	{
		this.entity = entity;
		
		up_frames = new Texture[MOVE_FRAMES];
		down_frames = new Texture[MOVE_FRAMES];
		left_frames = new Texture[MOVE_FRAMES];
		right_frames = new Texture[MOVE_FRAMES];
		
		//The player's move speed will determine how quickly the texture changes.
		frame_change = 1f / movespeed;
	}
	
	/**
	 * Go into the given folder and turn the images into sprites.
	 * @param folder Folder to search.
	 */
	public void setMovementSprites(String folder)
	{
		readFromFile(folder);
		
		//On top of loading the images, we're also interested in setting the sprite's texture after loading. Let's just use down_frames[1].
		direction = Direction.UP;
		entity.sprite.setRegion(down_frames[1]);
	}
	
	private void readFromFile(String folder)
	{
		String base_texture_folder = Assets.SPRITE_BASE + folder;
		for(String file : Gdx.files.internal(base_texture_folder).readString().split("\n"))
		{
			//Get the file that we're looking at.
			String file_name = base_texture_folder + file;
			String[] parts = file_name.split("_");
			
			//parts[1] is what we're really interested in. It will tell us the direction and the frame.
			if(parts[1].startsWith("bk"))
			{//Back
				up_frames[Character.getNumericValue(parts[1].charAt(2)) - 1] = entity.world.screen.game.assets.get(file_name, Texture.class);
			}
			else if(parts[1].startsWith("fr"))
			{//Front
				down_frames[Character.getNumericValue(parts[1].charAt(2)) - 1] = entity.world.screen.game.assets.get(file_name, Texture.class);
			}
			else if(parts[1].startsWith("lf"))
			{//Left
				left_frames[Character.getNumericValue(parts[1].charAt(2)) - 1] = entity.world.screen.game.assets.get(file_name, Texture.class);
			}
			else if(parts[1].startsWith("rt"))
			{//Right
				right_frames[Character.getNumericValue(parts[1].charAt(2)) - 1] = entity.world.screen.game.assets.get(file_name, Texture.class);
			}
		}
	}
	
	/**
	 * Set the correct sprite. The sprite direction we use will be based upon the amount we moved.
	 * @param delta
	 */
	public void update(float delta)
	{
		dx = entity.sprite.getX() - entity.last_location.x;
		dy = entity.sprite.getY() - entity.last_location.y;
		parseMovement(delta);
		setRegion();
	}
	
	/**
	 * Determine if we have moved. If we have, change the sprite.
	 * @param delta
	 */
	private void parseMovement(float delta)
	{
		if(Math.abs(dx) + Math.abs(dy) > move_check)
		{
			if(Math.abs(dx) - Math.abs(dy) > move_check)
			{
				if(Math.signum(dx) == -1)
				{//Left
					frameChange(delta, Direction.LEFT);
				}
				else
				{//Right
					frameChange(delta, Direction.RIGHT);
				}
			}
			else
			{
				if(Math.signum(dy) == -1)
				{//Down
					frameChange(delta, Direction.DOWN);
				}
				else
				{//Up
					frameChange(delta, Direction.UP);
				}
			}
		}
		else
		{
			resetFrame();
		}
	}
	
	private void frameChange(float delta, Direction direction_compare)
	{
		if(direction == direction_compare)
		{
			//Update timing.
			frame_change_current += delta;
			if(frame_change_current >= frame_change)
			{
				frame_change_current -= frame_change;
				frame_current = (frame_current + 1) % 2;
			}
		}
		else
		{//Reset timing 
			direction = direction_compare;
			frame_change_current = 0f;
			frame_current = 1;
		}
	}
	
	/**
	 * Reset frame because are no longer moving.
	 */
	private void resetFrame()
	{
		frame_change_current = 0f;
		frame_current = 1;
	}
	
	/**
	 * Change sprite's texture based upon the direction.
	 */
	private void setRegion()
	{
		switch(direction)
		{
			case DOWN:
				entity.sprite.setRegion(down_frames[frame_current]);
				break;
			case LEFT:
				entity.sprite.setRegion(left_frames[frame_current]);
				break;
			case RIGHT:
				entity.sprite.setRegion(right_frames[frame_current]);
				break;
			case UP:
				entity.sprite.setRegion(up_frames[frame_current]);
				break;
		}
	}
}
