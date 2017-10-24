package com.jacl.capstone.world.entities.player.items.weapons;

import com.jacl.capstone.data.enums.ItemSelection;
import com.jacl.capstone.world.World;

public class ItemFactory
{
	public static Weapon spawn(ItemSelection type, World world)
	{
		switch(type)
		{
			case SWORD:
				return new Sword(world, world.data_handler.item_root.getChildByName("sword"), world.entity_handler.player.texture_helper.direction);
			default:
				return null;
		}
	}
}