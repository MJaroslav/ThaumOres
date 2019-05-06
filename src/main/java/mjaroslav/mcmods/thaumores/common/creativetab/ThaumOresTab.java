package mjaroslav.mcmods.thaumores.common.creativetab;

import mjaroslav.mcmods.thaumores.common.module.ModuleItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ThaumOresTab extends CreativeTabs {
    public ThaumOresTab(String lable) {
        super(lable);
    }

    @Override
    public ItemStack getIconItemStack() {
        return new ItemStack(ModuleItems.shardCluster, 1, 1);
    }

    @Override
    public Item getTabIconItem() {
        return null;
    }

}
