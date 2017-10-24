package com.jacl.capstone.helpers.handlers.world;

import java.util.ArrayList;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.jacl.capstone.data.enums.Direction;
import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.Entity;
import com.jacl.capstone.world.entities.MovingEntity;
import com.jacl.capstone.world.entities.player.Player;
import com.jacl.capstone.world.entities.player.items.collectibles.CollectibleItem;

/**
 * Handles collision of entities.
 * @author Lee
 */
public class CollisionHandler
{
	public World world;
	
	//Collision objects will be contained on a collision layer.
	public final String COLLISION_LAYER = "collisionobjects";
	public Array<RectangleMapObject> collision_objects;
	
	//Intersection will require further analysis.
	public Rectangle intersector;
	public Vector2 center_holder1, center_holder2;
	public float COMPARE_DISTANCE;								//The distance of the two sprites should be less than this if we're going to compare them. Square root of this is the real distance, but square root is slow. We'll do it this way instead.
	public float COMPARE_BLOCKS = 7.5f;							//How many blocks apart the blocks will be when calculating the above variable.
	
	public CollisionHandler(World world)
	{
		this.world = world;
	}
	
	public void handlerInit()
	{
		COMPARE_DISTANCE = (float) Math.pow(world.map_handler.tile_size, 2.0f) * COMPARE_BLOCKS;
		
		MapObjects objects = world.map_handler.map.getLayers().get(COLLISION_LAYER).getObjects();
		collision_objects = new Array<RectangleMapObject>();
		intersector = new Rectangle();
		center_holder1 = new Vector2();
		center_holder2 = new Vector2();
		
		//Get collision object shapes.
		for(int i = 0; i < objects.getCount(); i++)
		{
			collision_objects.add((RectangleMapObject) objects.get(i));
		}
	}
	
	/**
	 * Do the sprite collision detection with solid blocks.
	 */
	public void cellCollision(MovingEntity entity, Vector2 last_location)
	{
		entity.sprite.getBoundingRectangle().getCenter(center_holder2);
		for(RectangleMapObject obj : world.collision_handler.collision_objects)
		{
			//We want to speed up this search by only looking at objects within a small distance of the sprite.
			obj.getRectangle().getCenter(center_holder1);
			if(entity.sprite.getBoundingRectangle().overlaps(obj.getRectangle()))
			{
				//There was a collision. Stop further checking and return to last location. Because we made it here, we've overlapped. We want to get the intersection of the overlap.
				Intersector.intersectRectangles(entity.sprite.getBoundingRectangle(), obj.getRectangle(), intersector);
				if(intersector.width > intersector.height)
				{//Reset Y.
					//Don't stop trapped players from walking away if they get stuck.
					if(entity instanceof Player)
					{
						if(entity.sprite.getY() + entity.sprite.getHeight() > obj.getRectangle().getY() + obj.getRectangle().getHeight() && Player.class.cast(entity).up != true)
						{
							entity.sprite.setY(last_location.y);
						}
						if(entity.sprite.getY() < obj.getRectangle().getY() && Player.class.cast(entity).down != true)
						{
							entity.sprite.setY(last_location.y);
						}
					}
					else
					{
						entity.sprite.setY(last_location.y);
					}
				}
				if(intersector.width < intersector.height)
				{//Reset X.
					//Don't stop trapped players from walking away if they get stuck.
					if(entity instanceof Player)
					{
						if(entity.sprite.getX() + entity.sprite.getWidth() > obj.getRectangle().getX() + obj.getRectangle().getWidth() && Player.class.cast(entity).right != true)
						{
							entity.sprite.setX(last_location.x);
						}
						if(entity.sprite.getX() < obj.getRectangle().getX() && Player.class.cast(entity).left != true)
						{
							entity.sprite.setX(last_location.x);
						}
					}
					else
					{
						entity.sprite.setX(last_location.x);
					}
				}
			}
		}
	}
	
	/**
	 * Scan all the enemies of this entity. Look for collisions.
	 * @param entity Entity to compare to.
	 * @param enemies List of enemies to this entity.
	 */
	public void entityCollision(MovingEntity entity, ArrayList<MovingEntity> enemies)
	{
		entity.sprite.getBoundingRectangle().getCenter(center_holder2);
		for(MovingEntity e : enemies)
		{
			//We want to speed up this search by only looking at objects within a small distance of the sprite.
			e.sprite.getBoundingRectangle().getCenter(center_holder1);
			if(center_holder1.dst2(center_holder2) < COMPARE_DISTANCE && entity.sprite.getBoundingRectangle().overlaps(e.sprite.getBoundingRectangle()))
			{
				//There was a collision. Stop further checking and return to last location. Because we made it here, we've overlapped. We want to get the intersection of the overlap.
				if(Intersector.intersectRectangles(entity.sprite.getBoundingRectangle(), e.sprite.getBoundingRectangle(), intersector))
				{					
					//Determine the colliding edges for knockback calculation.
					Rectangle r1 = entity.sprite.getBoundingRectangle();
					if(intersector.x > r1.x && intersector.width < intersector.height)
					{
						entity.knockback.knockback_direction = Direction.RIGHT;
						e.knockback.knockback_direction = Direction.LEFT;
					}
					else if(intersector.y > r1.y && intersector.width >= intersector.height)
					{
						entity.knockback.knockback_direction = Direction.UP;
						e.knockback.knockback_direction = Direction.DOWN;
					}
					else if(intersector.x + intersector.width < r1.x + r1.width && intersector.width < intersector.height)  
					{
						entity.knockback.knockback_direction = Direction.LEFT;
						e.knockback.knockback_direction = Direction.RIGHT;
					}
					else if(intersector.y + intersector.height < r1.y + r1.height && intersector.width >= intersector.height)
					{
						entity.knockback.knockback_direction = Direction.DOWN;
						e.knockback.knockback_direction = Direction.UP;
					}
					
					//Do the collision effects. This includes damage, knockback, and other residual effects. Should only happen if not invincible.
					entity.hitBy(e);
					e.hitBy(entity);
					
					return;
				}
			}
		}
	}
	
	public void collectibleCollision(Player player){
		//Look at all entities. If colliding with a collectible, collect it.
		for(Entity e : world.entity_handler.all_entities){
			if(e instanceof CollectibleItem){
				if(player.sprite.getBoundingRectangle().overlaps(e.sprite.getBoundingRectangle())){
					((CollectibleItem) e).collect();
				}
			}
		}
	}
}
