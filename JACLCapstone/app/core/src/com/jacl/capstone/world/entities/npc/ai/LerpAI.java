package com.jacl.capstone.world.entities.npc.ai;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.jacl.capstone.world.entities.npc.NPC;

/**
 * Linear interpolation AI. Move slightly toward the player with every update of time.
 * 
 * @author Lee
 *
 */
public class LerpAI extends AI
{
	//There will be a radius around the AI unit where the unit is close enough to attack the player.
	private final float ATTACK_RADIUS;
	private Vector2 player_position;
	
	public LerpAI(NPC npc)
	{
		super(npc);
		
		ATTACK_RADIUS = (float) Math.pow(1.5f * npc.world.map_handler.tile_size, 2);
		player_position = new Vector2();
	}

	@Override
	public void updateThinking(float delta)
	{
		//Get player's position (referencing the center).
		player_position.set(handler.player.getCenterX() - npc.getCenterX(), handler.player.getCenterY() - npc.getCenterY());
	}

	@Override
	public void updatePosition(float delta)
	{
		//Move toward player's center by reference of this entity's center.
		npc.sprite.translateX(MathUtils.cosDeg(player_position.angle()) * delta * npc.move_speed);
		npc.sprite.translateY(MathUtils.sinDeg(player_position.angle()) * delta * npc.move_speed);
	}

	@Override
	public void updateAction(float delta)
	{
		/* If we're within the attack radius of the player, attack.
		 * Note: We're using the squared distance between the two
		 * entities to avoid having to do a square root operation.
		 * Square roots are costly for a computer to calculate,
		 * and we don't need an exact answer due to the nature of 
		 * the project. The only thing we have to be aware of is
		 * the fact that, because our distance is squared, our
		 * attack radius must also be squared to compensate. 
		 */
		/*if(Vector2.dst2(npc.getCenterX(), npc.getCenterY(), handler.player.getCenterX(), handler.player.getCenterY()) <= ATTACK_RADIUS)
			System.out.println("Can attack.");
		else
			System.out.println("Can't attack.");*/
	}
}
