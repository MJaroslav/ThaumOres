package mjaroslav.mcmods.thaumores.client.render.tile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mjaroslav.mcmods.mjutils.util.UtilsThaumcraft;
import mjaroslav.mcmods.thaumores.common.block.BlockInfusedBlockOre;
import mjaroslav.mcmods.thaumores.common.module.ModuleThaumcraft;
import mjaroslav.mcmods.thaumores.common.tile.TileInfusedOre;
import mjaroslav.mcmods.thaumores.lib.CategoryGeneral;
import mjaroslav.mcmods.thaumores.lib.CategoryGeneral.CategoryClient;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;
import thaumcraft.api.nodes.IRevealer;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.common.blocks.BlockCustomOreItem;
import thaumcraft.common.items.relics.ItemThaumometer;

@SideOnly(Side.CLIENT)
public class TileInfusedBlockOreRenderer extends TileEntitySpecialRenderer {
    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTicks) {
        GL11.glPushMatrix();
        if (tile != null && tile.getWorldObj() != null && tile.getBlockType() instanceof BlockInfusedBlockOre)
            renderTileEntityAt((BlockInfusedBlockOre) tile.getBlockType(), (TileInfusedOre) tile, x, y, z);
        GL11.glPopMatrix();
    }

    private void renderTileEntityAt(BlockInfusedBlockOre block, TileInfusedOre tile, double x, double y, double z) {
        GL11.glPushMatrix();
        GL11.glTranslated(x - 0.001D, y - 0.001D, z - 0.001D);
        double scale = 1.002F;
        GL11.glScaled(scale, scale, scale);
        Tessellator tessellator = Tessellator.instance;
        int metadata = tile.getWorldObj().getBlockMetadata(tile.xCoord, tile.yCoord, tile.zCoord);
        if (metadata < 6) {
            EntityLivingBase viewer = Minecraft.getMinecraft().renderViewEntity;
            double viewDistance = 0D;
            double currentDistance = viewer.getDistance(tile.xCoord + 0.5D, tile.yCoord + 0.5D, tile.zCoord + 0.5D);
            boolean hasVisualAcuity;
            boolean hasWarpVisualAcuity;
            boolean canView = false;
            if (viewer instanceof EntityPlayer) {
                hasVisualAcuity = UtilsThaumcraft.isComplete((EntityPlayer) viewer, ModuleThaumcraft.riVisualAcuity);
                hasWarpVisualAcuity = UtilsThaumcraft.isComplete((EntityPlayer) viewer, ModuleThaumcraft.riWarpVisualAcuity);
                if ((((EntityPlayer) viewer).inventory.armorItemInSlot(3) != null)
                        && (((EntityPlayer) viewer).inventory.armorItemInSlot(3).getItem() instanceof IRevealer)
                        && (((IRevealer) ((EntityPlayer) viewer).inventory.armorItemInSlot(3).getItem())
                        .showNodes(((EntityPlayer) viewer).inventory.armorItemInSlot(3), viewer))) {
                    canView = true;
                    viewDistance = hasVisualAcuity ? 32.0D : 16.0D;
                } else if ((((EntityPlayer) viewer).inventory.getCurrentItem() != null)
                        && (((EntityPlayer) viewer).inventory.getCurrentItem().getItem() instanceof ItemThaumometer)
                        && (UtilsFX.isVisibleTo(0.44F, viewer, tile.xCoord, tile.yCoord, tile.zCoord))) {
                    canView = true;
                    viewDistance = hasVisualAcuity ? 16.0D : 8.0D;
                }
                if (hasWarpVisualAcuity) {
                    int warp = UtilsThaumcraft.getWarpTotal((EntityPlayer) viewer);
                    canView = true;
                    double warpViewDistance = warp * CategoryGeneral.warpVisualAcuityModifier;
                    if (warpViewDistance > viewDistance)
                        viewDistance = warpViewDistance;
                }
            }
            if (canView && currentDistance <= viewDistance) {
                if (CategoryClient.enableInfusedOreParticlesInGlasses && viewer.worldObj.rand
                        .nextInt(CategoryClient.infusedOreParticlesChanceInGlasses) == 0)
                    UtilsFX.infusedStoneSparkle(viewer.worldObj, tile.xCoord, tile.yCoord, tile.zCoord, metadata + 1);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                int brightness = setBrightness(tessellator, tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord,
                        block);
                draw(tessellator, block.icon[6], brightness, metadata, (float) (1 - currentDistance / viewDistance));
                GL11.glDisable(GL11.GL_BLEND);
            } else if (CategoryClient.enableInfusedOreParticles
                    && viewer.worldObj.rand.nextInt(CategoryClient.infusedOreParticlesChance) == 0)
                UtilsFX.infusedStoneSparkle(viewer.worldObj, tile.xCoord, tile.yCoord, tile.zCoord, metadata + 1);
        }
        GL11.glPopMatrix();
    }

    private int setBrightness(Tessellator tessellator, IBlockAccess blockAccess, int x, int y, int z, Block block) {
        int mb = block.getMixedBrightnessForBlock(blockAccess, x, y, z);
        tessellator.setBrightness(mb);
        float f = 1.0F;
        int l = block.colorMultiplier(blockAccess, x, y, z);
        float f1 = (l >> 16 & 0xFF) / 255.0F;
        float f2 = (l >> 8 & 0xFF) / 255.0F;
        float f3 = (l & 0xFF) / 255.0F;
        if (EntityRenderer.anaglyphEnable) {
            float f6 = (f1 * 30.0F + f2 * 59.0F + f3 * 11.0F) / 100.0F;
            float f4 = (f1 * 30.0F + f2 * 70.0F) / 100.0F;
            float f7 = (f1 * 30.0F + f3 * 70.0F) / 100.0F;
            f1 = f6;
            f2 = f4;
            f3 = f7;
        }
        tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
        return mb;
    }

    private void draw(Tessellator tessellator, IIcon icon, int brightness, int metadata, float alpha) {
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        int color = BlockCustomOreItem.colors[metadata + 1];
        double red = (color >> 16) & 0xFF;
        double green = (color >> 8) & 0xFF;
        double blue = color & 0xFF;
        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F((float) (red / 256), (float) (green / 256), (float) (blue / 256), alpha);
        tessellator.setBrightness(Math.max(brightness, 160));
        tessellator.addVertexWithUV(0, 0, 0, icon.getMaxU(), icon.getMaxV());
        tessellator.addVertexWithUV(0, 1, 0, icon.getMaxU(), icon.getMinV());
        tessellator.addVertexWithUV(1, 1, 0, icon.getMinU(), icon.getMinV());
        tessellator.addVertexWithUV(1, 0, 0, icon.getMinU(), icon.getMaxV());

        tessellator.addVertexWithUV(1, 0, 0, icon.getMaxU(), icon.getMaxV());
        tessellator.addVertexWithUV(1, 1, 0, icon.getMaxU(), icon.getMinV());
        tessellator.addVertexWithUV(1, 1, 1, icon.getMinU(), icon.getMinV());
        tessellator.addVertexWithUV(1, 0, 1, icon.getMinU(), icon.getMaxV());

        tessellator.addVertexWithUV(1, 0, 1, icon.getMaxU(), icon.getMaxV());
        tessellator.addVertexWithUV(1, 1, 1, icon.getMaxU(), icon.getMinV());
        tessellator.addVertexWithUV(0, 1, 1, icon.getMinU(), icon.getMinV());
        tessellator.addVertexWithUV(0, 0, 1, icon.getMinU(), icon.getMaxV());

        tessellator.addVertexWithUV(0, 0, 1, icon.getMaxU(), icon.getMaxV());
        tessellator.addVertexWithUV(0, 1, 1, icon.getMaxU(), icon.getMinV());
        tessellator.addVertexWithUV(0, 1, 0, icon.getMinU(), icon.getMinV());
        tessellator.addVertexWithUV(0, 0, 0, icon.getMinU(), icon.getMaxV());

        tessellator.addVertexWithUV(1, 1, 1, icon.getMaxU(), icon.getMaxV());
        tessellator.addVertexWithUV(1, 1, 0, icon.getMaxU(), icon.getMinV());
        tessellator.addVertexWithUV(0, 1, 0, icon.getMinU(), icon.getMinV());
        tessellator.addVertexWithUV(0, 1, 1, icon.getMinU(), icon.getMaxV());

        tessellator.addVertexWithUV(0, 0, 1, icon.getMaxU(), icon.getMaxV());
        tessellator.addVertexWithUV(0, 0, 0, icon.getMaxU(), icon.getMinV());
        tessellator.addVertexWithUV(1, 0, 0, icon.getMinU(), icon.getMinV());
        tessellator.addVertexWithUV(1, 0, 1, icon.getMinU(), icon.getMaxV());
        tessellator.draw();
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }
}
