package mjaroslav.mcmods.thaumores.client.render.block;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import mjaroslav.mcmods.thaumores.common.block.BlockInfusedBlockOre;
import mjaroslav.mcmods.thaumores.common.module.ModuleBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.renderers.block.BlockRenderer;
import thaumcraft.common.blocks.BlockCustomOreItem;

import java.awt.*;

public class BlockInfusedBlockOreRenderer extends BlockRenderer implements ISimpleBlockRenderingHandler {
    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        block.setBlockBounds(0.005F, 0.005F, 0.005F, 0.995F, 0.995F, 0.995F);
        renderer.setRenderBoundsFromBlock(block);
        if (metadata < 6) {
            drawFaces(renderer, block, ((BlockInfusedBlockOre) block).icon[0], ((BlockInfusedBlockOre) block).icon[1],
                    ((BlockInfusedBlockOre) block).icon[2], ((BlockInfusedBlockOre) block).icon[3],
                    ((BlockInfusedBlockOre) block).icon[4], ((BlockInfusedBlockOre) block).icon[5], false);
            Color c = new Color(BlockCustomOreItem.colors[metadata + 1]);
            float r = c.getRed() / 255.0F;
            float g = c.getGreen() / 255.0F;
            float b = c.getBlue() / 255.0F;
            GL11.glColor3f(r, g, b);
            block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            renderer.setRenderBoundsFromBlock(block);
            drawFaces(renderer, block, ((BlockInfusedBlockOre) block).icon[6], false);
            GL11.glColor3f(1.0F, 1.0F, 1.0F);
        }
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
                                    RenderBlocks renderer) {
        if (world != null && block instanceof BlockInfusedBlockOre)
            return renderWorldBlock(x, y, z, (BlockInfusedBlockOre) block, renderer);
        return false;
    }

    private boolean renderWorldBlock(int x, int y, int z, BlockInfusedBlockOre block, RenderBlocks renderer) {
        renderer.setRenderBoundsFromBlock(block);
        renderer.renderStandardBlock(block.getBaseBlock(), x, y, z);
        renderer.clearOverrideBlockTexture();
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return ModuleBlocks.renderInfusedBlockOreID;
    }
}
