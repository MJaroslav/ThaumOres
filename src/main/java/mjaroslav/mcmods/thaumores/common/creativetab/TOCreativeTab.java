package mjaroslav.mcmods.thaumores.common.creativetab;

import mjaroslav.mcmods.thaumores.common.init.TOItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TOCreativeTab extends CreativeTabs {
	public TOCreativeTab(String lable) {
		super(lable);
	}

	@Override
	public ItemStack getIconItemStack() {
		return new ItemStack(TOItems.shardCluster, 1, 1);
	}

	@Override
	public Item getTabIconItem() {
		return null;
	}

}
