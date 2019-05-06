package mjaroslav.mcmods.thaumores.common.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemInfusedBlockOre extends ItemBlock {
    public ItemInfusedBlockOre(Block block) {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }

    @Override
    public String getItemStackDisplayName(ItemStack item) {
        return super.getItemStackDisplayName(item).replace("{base}",
                ((BlockInfusedBlockOre) this.field_150939_a).getBaseBlock().getLocalizedName());
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        return super.getUnlocalizedName() + "." + itemStack.getItemDamage();
    }
}
