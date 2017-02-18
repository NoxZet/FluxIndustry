package noxzet.fluxindustry.core.energy;

import net.darkhax.tesla.api.ITeslaProducer;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class FluxUniversalProducer implements ITeslaProducer {

	public static final int TESLA = 0;
	public static final int FORGE = 1;
	public static final int COFH = 2;
	private World world;
	private BlockPos pos;
	private EnumFacing facing;
	private int mode;
	
	public FluxUniversalProducer(int mode, World world, BlockPos pos, EnumFacing facing)
	{
		this.world = world;
		this.pos = pos;
		this.mode = mode;
		this.facing = facing;
	}
	
	public long takePower(long Tesla, boolean simulate)
	{
		if (this.mode == TESLA)
		{
			TileEntity entity = world.getTileEntity(pos);
			if (entity != null && entity.hasCapability(TeslaCapabilities.CAPABILITY_PRODUCER, facing.getOpposite()))
			{
				return ((ITeslaProducer) entity.getCapability(TeslaCapabilities.CAPABILITY_PRODUCER, facing.getOpposite())).takePower(Tesla, simulate);
			}
		}
		else if (this.mode == FORGE)
		{
			TileEntity entity = world.getTileEntity(pos);
			if (entity != null && entity.hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite()))
			{
				IEnergyStorage forgeEnergy = ((IEnergyStorage) entity.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite()));
				if (forgeEnergy.canExtract())
					return forgeEnergy.extractEnergy((int)Tesla, simulate);
			}
		}
		else if (this.mode == COFH)
		{
			TileEntity entity = world.getTileEntity(pos);
			if (entity != null && entity instanceof cofh.api.energy.IEnergyProvider)
			{
				cofh.api.energy.IEnergyProvider rfEnergy = (cofh.api.energy.IEnergyProvider) entity;
				return rfEnergy.extractEnergy(facing.getOpposite(), (int)Tesla, simulate);
			}
		}
		return 0;
	}
	
}
