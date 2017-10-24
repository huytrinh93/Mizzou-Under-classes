package com.jacl.capstone.world.entities;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.jacl.capstone.AudioPlayer;
import com.jacl.capstone.data.enums.Alignment;
import com.jacl.capstone.helpers.AttackHelper;
import com.jacl.capstone.helpers.DamageCalculator;
import com.jacl.capstone.helpers.InvincibleHelper;
import com.jacl.capstone.helpers.KnockbackHelper;
import com.jacl.capstone.helpers.TextureHelper;
import com.jacl.capstone.world.World;

/**
 * These are world objects that move. These include enemies and players.
 * On top of movement, however, these entities will interact with combat.
 * Getting hit by a sword will cause knockback, for instance. Thus, they
 * will have combat-related items like health or defense.
 * @author Lee
 */
public abstract class MovingEntity extends Entity
{	
	public KnockbackHelper knockback;
	public AttackHelper attack;
	public InvincibleHelper invincible;	
	public TextureHelper texture_helper;

	private AudioPlayer gettingHit;
	
	public float health_current;
	public float health_max;
	public float health_regen;
	
	public float move_speed;
	public boolean knockback_on_collide;
	public float damage_on_collide;
	public float defense;
	
	public ArrayList<MovingEntity> enemies;
	
	public MovingEntity(World world, float x, float y, Element data, Alignment alignment)
	{
		super(world, x, y, data, alignment);
		
		enemies = new ArrayList<MovingEntity>();
		knockback = new KnockbackHelper(this);
		attack = new AttackHelper(this);
		invincible = new InvincibleHelper(this);
		
		if(!data.get("move_speed", "").equals(""))
		{
			texture_helper = new TextureHelper(this, data.getFloat("move_speed"));
			move_speed = data.getFloat("move_speed") * world.map_handler.tile_size;				//Set in terms of tiles per second.
			texture_helper.setMovementSprites(data.get("texture_folder"));
		}
		
		knockback_on_collide = data.getBoolean("knockback_on_collide");
		damage_on_collide = data.getFloat("damage_on_collide");
	}
	
	@Override
	public void update(float delta)
	{
		if(knockback.is_being_knocked_back)
		{//During knockback, we need to update the knockback variables.
			knockback.update(delta);
		}
		else
		{//If not being knocked back, update normally with free movement.
			move(delta);
			attack.update(delta);
			entityCollision();
		}
		
		//Calculate regeneration (if we want this).
		changeCurrentHealthValueBy(health_regen * delta);
		
		//Calculate invincibility frames if necessary.
		invincible.update(delta);
		
		//Calculate solid block collision.
		world.collision_handler.cellCollision(this, last_location);
		
		//Set the correct sprite. The sprite direction we use will be based upon the amount we moved.
		texture_helper.update(delta);
		
		//Now that all movement is done, we can reset the last_location variable.
		last_location.set(sprite.getX(), sprite.getY());
	}
	
	public void entityCollision()
	{
		//We don't want to be hit while we're invincible. Completely skip this step if so.
		if(!invincible.is_invincible)
		{
			//The main logic happens in the CollisionHandler. Get a list of this entity's enemies and send it there.
			getEnemies();
			world.collision_handler.entityCollision(this, enemies);
		}
	}
	
	private void getEnemies()
	{
		//Clear the enemy list and begin adding the enemies.
		enemies.clear();
		for(Entity e : world.entity_handler.all_entities)
		{
			if(e instanceof MovingEntity && e.alignment != Alignment.NEUTRAL && this.alignment != e.alignment)
			{
				enemies.add((MovingEntity) e);
			}
		}
	}
	
	/**
	 * Entity was hit by an enemy entity. Set knockback and invincibility.
	 * @param e The enemy this collided with.
	 */
	public void hitBy(MovingEntity e)
	{
		if(!invincible.is_invincible && e.knockback_on_collide)
		{//Knockback and damage.
			//gettingHit = new AudioPlayer("sounds/GettingHit.wav");
			
			//gettingHit.play();
			knockback.doKnockback();
			health_current -= DamageCalculator.getDamage(e.damage_on_collide, defense);
		}
	}
	
	/**
	 * Change current health to the new value.
	 * @param new_value
	 */
	public void changeCurrentHealthValueTo(float new_value)
	{
		health_current = new_value;
		checkHealthOnChange();
	}
	
	/**
	 * Change current health by the change_by amount.
	 * @param change_by
	 */
	public void changeCurrentHealthValueBy(float change_by)
	{
		health_current += change_by;
		checkHealthOnChange();
	}
	
	/**
	 * Change max health to the new value.
	 * @param new_value
	 */
	public void changeMaxHealthValueTo(float new_value)
	{
		health_max = new_value;
		checkHealthOnChange();
	}
	
	/**
	 * Change max health by the change_by amount.
	 * @param change_by
	 */
	public void changeMaxHealthValueBy(float change_by)
	{
		health_max += change_by;
		checkHealthOnChange();
	}
	
	private void checkHealthOnChange()
	{
		checkMax();
		checkCurrent();
	}
	
	private void checkMax(){
		if(health_max < 0.0f){
			health_max = 0.0f;
		}
	}
	
	private void checkCurrent()
	{
		if(health_current > health_max){
			health_current = health_max;
		}
		if(health_current <= 0f){
			die();
		}
	}
	
	protected abstract void move(float delta);
	
	protected abstract void die();
}
