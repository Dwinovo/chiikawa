package com.dwinovo.chiikawa.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.ToolMaterial;

public class HachiwareWeapon extends SwordItem {
    public HachiwareWeapon(Item.Properties properties) {
        super(ToolMaterial.STONE, 3.0F, -2.4F, properties);
    }
}
