package mjaroslav.mcmods.thaumores.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mjaroslav.mcmods.thaumores.lib.CategoryGeneral.CategoryClient;
import mjaroslav.mcmods.thaumores.lib.ModInfo;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import thaumcraft.common.blocks.BlockCustomOreItem;

import java.util.List;

public class ItemShardCluster extends Item {
    private IIcon[] icon = new IIcon[7];

    public ItemShardCluster() {
        super();
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int meta = 0; meta < 6; meta++)
            list.add(new ItemStack(item, 1, meta));
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        if (itemStack.getItemDamage() > 5)
            return super.getUnlocalizedName(itemStack);
        return super.getUnlocalizedName() + "." + itemStack.getItemDamage();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister register) {
        for (int meta = 0; meta < 6; meta++)
            icon[meta] = register.registerIcon(String.format("%s:%s_%s", ModInfo.MOD_ID, "heavyshard", meta));
        icon[6] = register.registerIcon(String.format("%s:%s", ModInfo.MOD_ID, "clustershard"));
    }

    @Override
    public IIcon getIconFromDamage(int meta) {
        return CategoryClient.clustersOldIcons ? meta < 6 ? icon[meta] : icon[6] : icon[6];
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int pass) {
        if (stack.getItemDamage() > 5 || CategoryClient.clustersOldIcons)
            return super.getColorFromItemStack(stack, pass);
        return BlockCustomOreItem.colors[stack.getItemDamage() + 1];
    }
}
