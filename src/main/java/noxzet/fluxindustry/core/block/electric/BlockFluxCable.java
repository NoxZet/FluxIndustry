package noxzet.fluxindustry.core.block.electric;

import java.util.Random;

import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.energy.CapabilityEnergy;
import noxzet.fluxindustry.core.FluxIndustry;
import noxzet.fluxindustry.core.block.BlockFlux;
import noxzet.fluxindustry.core.block.ITileEntityClassProvider;
import noxzet.fluxindustry.core.item.FluxItems;
import noxzet.fluxindustry.core.tileentity.TileFluxCable;

public class BlockFluxCable extends BlockFlux implements ITileEntityClassProvider {

	public final static PropertyEnum<CableVariant> VARIANT = PropertyEnum.<CableVariant>create("variant", CableVariant.class);
    public static final PropertyBool DOWN = PropertyBool.create("down");
    public static final PropertyBool UP = PropertyBool.create("up");
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");
    public static final PropertyBool EAST = PropertyBool.create("east");
    
	public BlockFluxCable(String unlocalizedName)
	{
		super(Material.CLOTH, unlocalizedName);
		this.setResistance(30);
		this.setHardness(1);
		this.setSoundType(SoundType.CLOTH);
		this.setIsCube(false);
	}
	
	public void registerItemModel(ItemBlock itemBlock)
	{
		FluxIndustry.proxy.registerItemRenderer(itemBlock, 0, "down=false,east=true,north=false,south=false,up=false,variant=copper,west=true");
	}
	
	public Class getTileEntityClass()
	{
		return TileFluxCable.class;
	}
	
	public TileEntity getTileEntity (IBlockAccess world, BlockPos pos)
	{
		return (TileEntity)world.getTileEntity(pos);
	}

	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World worldIn, IBlockState state)
	{
		return createNewTileEntity(worldIn, this.getMetaFromState(state));
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileFluxCable();
	}
	
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, VARIANT, DOWN, UP, NORTH, SOUTH, WEST, EAST);
    }

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(VARIANT, CableVariant.fromMeta(meta));
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return this.getDefaultState().getValue(VARIANT).getMeta();
	}
	
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return state.withProperty(DOWN, connectToSide(EnumFacing.DOWN, world, pos))
				.withProperty(UP, connectToSide(EnumFacing.UP, world, pos))
				.withProperty(NORTH, connectToSide(EnumFacing.NORTH, world, pos))
				.withProperty(SOUTH, connectToSide(EnumFacing.SOUTH, world, pos))
				.withProperty(WEST, connectToSide(EnumFacing.WEST, world, pos))
				.withProperty(EAST, connectToSide(EnumFacing.EAST, world, pos));
	}
	
	public boolean connectToSide(EnumFacing facing, IBlockAccess world, BlockPos pos)
	{
		TileEntity entity = world.getTileEntity(pos.offset(facing));
		EnumFacing opposite = facing.getOpposite();
		if (entity != null)
		{
			if (entity.hasCapability(TeslaCapabilities.CAPABILITY_CONSUMER, opposite) ||
					entity.hasCapability(TeslaCapabilities.CAPABILITY_PRODUCER, opposite) ||
					entity.hasCapability(CapabilityEnergy.ENERGY, opposite) ||
					entity instanceof cofh.api.energy.IEnergyProvider ||
					entity instanceof cofh.api.energy.IEnergyReceiver)
				return true;
		}
		return false;
	}

	@Override
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
    {
        return false;
    }
	
	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Items.AIR;
    }
	
	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		if (!player.isCreative())
			spawnAsEntity(world, pos, getPickBlock(state, null, world, pos, player));
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
	{
		ItemStack itemstack = new ItemStack(FluxItems.cableBasic, 1, world.getBlockState(pos).getValue(VARIANT).getMeta());
		TileEntity entity = world.getTileEntity(pos);
		if (entity instanceof TileFluxCable)
		{
			TileFluxCable entityCable = (TileFluxCable) entity;
			NBTTagCompound itemTag = new NBTTagCompound();
			NBTTagCompound itemCableTag = new NBTTagCompound();
			NBTTagCompound cableCompound = entityCable.writeToNBT(new NBTTagCompound());
			NBTTagCompound cableContainerCompound;
			if (cableCompound.hasKey("teslaCable", NBT.TAG_COMPOUND))
			{
				cableContainerCompound = (NBTTagCompound) cableCompound.getTag("teslaCable");
				if (cableContainerCompound.hasKey("TeslaCapacity", NBT.TAG_LONG))
					itemCableTag.setLong("TeslaCapacity", cableContainerCompound.getLong("TeslaCapacity"));
				if (cableContainerCompound.hasKey("TeslaMinPower", NBT.TAG_LONG))
					itemCableTag.setLong("TeslaMinPower", cableContainerCompound.getLong("TeslaMinPower"));
				if (cableContainerCompound.hasKey("TeslaLoss", NBT.TAG_FLOAT))
					itemCableTag.setFloat("TeslaLoss", cableContainerCompound.getFloat("TeslaLoss"));
				itemTag.setTag("teslaCable", itemCableTag);
				itemstack.setTagCompound(itemTag);
				return itemstack;
			}
		}
		return itemstack;
	}
	
	/*@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float side, float hitX, float hitY)
	{
		System.out.println(((IFluxCable)world.getTileEntity(pos)).getFluxCableContainer().getEnergyAvailable());
		return true;
	}*/
	
	public static enum CableVariant implements IStringSerializable
	{
		//Meta, name, harvest level
		COPPER(0, "copper"),
		TIN(1, "tin"),
		ALUMINUM(2, "aluminum"),
		BRONZE(3, "bronze"),
		IRON(4, "iron"),
		GOLD(5, "gold");
		
		private static final CableVariant[] META = new CableVariant[values().length];
		private int meta;
		private final String name;

		private CableVariant(int meta, String name) {
			this.meta = meta;
			this.name = name;
		}
		public static CableVariant fromMeta(int meta)
		{
			if (meta >= 0 && meta < META.length)
				return META[meta];
			else
				return META[0];
		}
		public int getMeta()
		{
			return this.meta;
		}
		public String getName()
		{
			return this.name;
		}
		
		static
		{
			for (CableVariant cablevariant : values())
			{
				META[cablevariant.getMeta()] = cablevariant;
			}
		}
	}
	
}
