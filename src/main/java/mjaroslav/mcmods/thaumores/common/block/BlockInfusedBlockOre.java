package mjaroslav.mcmods.thaumores.common.block;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mjaroslav.mcmods.thaumores.ThaumOresMod;
import mjaroslav.mcmods.thaumores.common.config.TOConfig;
import mjaroslav.mcmods.thaumores.common.init.TOBlocks;
import mjaroslav.mcmods.thaumores.common.init.TOItems;
import mjaroslav.mcmods.thaumores.common.init.TOThaum;
import mjaroslav.mcmods.thaumores.common.tile.TileInfusedOre;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.nodes.IRevealer;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.relics.ItemThaumometer;

public class BlockInfusedBlockOre extends Block implements ITileEntityProvider {
	/**
	 * Base block for ore (Overworld - stone, Nether - netherrack, End -
	 * endstone)
	 */
	private Block baseBlock;

	/** Base block meta */
	private int baseMeta;

	/**
	 * Block icons: 0-5 {@link BlockInfusedBlockOre#baseBlock} icons, 6 -
	 * Thaumcraft infused ore icon
	 */
	public IIcon[] icon = new IIcon[7];

	public BlockInfusedBlockOre(Block base, int baseMeta) {
		super(base.getMaterial());
		if (!base.isNormalCube() || !base.isOpaqueCube())
			throw new IllegalArgumentException("Block is not normal opaque cube");
		this.baseBlock = base;
		setStepSound(base.stepSound);
		setBlockName("infusedOre");
	}

	@Override
	public Block setResistance(float res) {
		return baseBlock.setResistance(res);
	}

	@Override
	public float getExplosionResistance(Entity ent) {
		return baseBlock.getExplosionResistance(ent);
	}

	@Override
	public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX,
			double explosionY, double explosionZ) {
		return baseBlock.getExplosionResistance(par1Entity, world, x, y, z, explosionX, explosionY, explosionZ);
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
		double viewDistance = 0D;
		double currentDistance = player.getDistance(x + 0.5D, y + 0.5D, z + 0.5D);
		boolean hasVisualAcuity = false;
		boolean hasWarpVisualAcuity = false;
		boolean canView = false;
		if (player != null) {
			hasVisualAcuity = ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(),
					TOThaum.riVisualAcuity);
			hasWarpVisualAcuity = ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(),
					TOThaum.riWarpVisualAcuity);
			if ((player.inventory.armorItemInSlot(3) != null)
					&& (player.inventory.armorItemInSlot(3).getItem() instanceof IRevealer)
					&& (((IRevealer) player.inventory.armorItemInSlot(3).getItem())
							.showNodes(player.inventory.armorItemInSlot(3), player))) {
				canView = true;
				viewDistance = hasVisualAcuity ? 32.0D : 16.0D;
			} else if ((player.inventory.getCurrentItem() != null)
					&& (player.inventory.getCurrentItem().getItem() instanceof ItemThaumometer)
					&& (UtilsFX.isVisibleTo(0.44F, player, x, y, z))) {
				canView = true;
				viewDistance = hasVisualAcuity ? 16.0D : 8.0D;
			}
			if (hasWarpVisualAcuity) {
				int warp = ThaumOresMod.getTotalWarp(player);
				canView = true;
				double warpViewDistance = warp * TOConfig.generalWarpVisualAcuityModifier;
				if (warpViewDistance > viewDistance)
					viewDistance = warpViewDistance;
			}
		}
		if (canView && currentDistance <= viewDistance) {
			return new ItemStack(this, 1, world.getBlockMetadata(x, y, z));
		} else
			return new ItemStack(baseBlock, 1, baseMeta);
	}

	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		return baseBlock.getBlockHardness(world, x, y, z);
	}

	@Override
	public boolean isBlockSolid(IBlockAccess world, int x, int y, int z, int metadata) {
		return baseBlock.isBlockSolid(world, x, y, z, baseMeta);
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
		return baseBlock.isSideSolid(world, x, y, z, side);
	}

	@Override
	public int getHarvestLevel(int metadata) {
		return baseBlock.getHarvestLevel(this.baseMeta);
	}

	@Override
	public String getHarvestTool(int metadata) {
		return baseBlock.getHarvestTool(this.baseMeta);
	}

	/** Get base block of infused ore */
	public Block getBaseBlock() {
		return this.baseBlock;
	}

	/** Get base block meta */
	public int getBaseMeta() {
		return this.baseMeta;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister register) {
		for (int side = 0; side < 6; side++)
			icon[side] = baseBlock.getIcon(side, baseMeta);
		icon[6] = register.registerIcon("Thaumcraft:infusedore");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int meta) {
		return icon[side];
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int meta = 0; meta < 6; meta++)
			list.add(new ItemStack(item, 1, meta));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer) {
		int md = worldObj.getBlockMetadata(target.blockX, target.blockY, target.blockZ);
		if (md < 6)
			UtilsFX.infusedStoneSparkle(worldObj, target.blockX, target.blockY, target.blockZ, md + 1);
		return super.addHitEffects(worldObj, target, effectRenderer);
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int metadata) {
		super.onBlockDestroyedByPlayer(world, x, y, z, metadata);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		super.setBlockBoundsBasedOnState(world, x, y, z);
	}

	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axisalignedbb, List arraylist,
			Entity par7Entity) {
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		super.addCollisionBoxesToList(world, x, y, z, axisalignedbb, arraylist, par7Entity);
	}

	@Override
	public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata) {
		return true;
	}

	@Override
	public int getDamageValue(World world, int x, int y, int z) {
		return world.getBlockMetadata(x, y, z);
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return TOBlocks.renderInfusedBlockOreID;
	}

	@Override
	public int getExpDrop(IBlockAccess world, int metadata, int fortune) {
		return 2 + (fortune > 3 ? 6 : fortune * 2);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
		if (metadata < 6) {
			int q = 1 + world.rand.nextInt(2 + fortune);
			for (int a = 0; a < q; ++a) {
				drops.add(new ItemStack(ConfigItems.itemShard, 1, metadata));
			}
		}
		drops.addAll(baseBlock.getDrops(world, x, y, z, baseMeta, fortune));
		return drops;
	}

	/** Get drop for wand action */
	public ArrayList<ItemStack> getDropsWand(World world, int x, int y, int z, int metadata, boolean isPrimal) {
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
		if (metadata < 6)
			if (isPrimal)
				drops.add(new ItemStack(TOItems.shardCluster, 1, metadata));
			else {
				int count = 1;
				for (int i = 0; i < 3; i++)
					if (world.rand.nextInt(100) + 1 <= 30)
						count++;
				drops.add(new ItemStack(ConfigItems.itemShard, count, metadata));
			}
		return drops;
	}

	@Override
	public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity) {
		return baseBlock.canEntityDestroy(world, x, y, z, entity);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileInfusedOre();
	}
}
