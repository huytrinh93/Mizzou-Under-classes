package com.jacl.capstone.world.entities.npc.enemies;

import com.badlogic.gdx.utils.XmlReader.Element;
import com.jacl.capstone.data.enums.Alignment;
import com.jacl.capstone.world.World;
import com.jacl.capstone.world.entities.npc.NPC;
import com.jacl.capstone.world.entities.player.items.collectibles.CollectibleMoneyItem;

/**
 * Parent enemy class. All enemies will hurt the hero upon being hit, and they will
 * react to being hit by a weapon. 
 * 
 * @author Lee
 *
 */
public abstract class Enemy extends NPC
{	
	public Enemy(World world, float x, float y, Element data, Alignment alignment)
	{
		super(world, x, y, data, alignment);
	}
	
	public void die(){
		remove = true;
		world.entity_handler.to_add.add(new CollectibleMoneyItem(world, sprite.getX() / world.map_handler.tile_size,  sprite.getY() / world.map_handler.tile_size, world.data_handler.collectible_root.getChildByName("money")));
	}
}
