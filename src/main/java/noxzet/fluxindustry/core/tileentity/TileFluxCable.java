package noxzet.fluxindustry.core.tileentity;

import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.energy.CapabilityEnergy;
import noxzet.fluxindustry.core.energy.FluxCableConsumer;
import noxzet.fluxindustry.core.energy.FluxCableContainer;
import noxzet.fluxindustry.core.energy.IFluxCable;

public class TileFluxCable extends TileEntityFlux implements IFluxCable, ITickable, cofh.api.energy.IEnergyReceiver {

	private FluxCableContainer container;
	private FluxCableConsumer[] consumer;
	public boolean[] connection;
	
	public TileFluxCable()
	{
		container = new FluxCableContainer(40, 0.5F, 20, this);
		consumer = new FluxCableConsumer[6];
		connection = new boolean[6];
		for(int i = 0; i < 6; i++)
		{
			consumer[i] = new FluxCableConsumer(container, this, EnumFacing.getFront(i));
			connection[i] = false;
		}
	}
	
	@Override
	public void update()
	{
		if (!world.isRemote)
			container.update();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		if (compound.hasKey("teslaCable", NBT.TAG_COMPOUND))
			container.fromNBT((NBTTagCompound)compound.getTag("teslaCable"));
	}
	
	@Override
	public NBTTagCompound writeToNBT (NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		if (container != null)
			compound.setTag("teslaCable", container.toNBT());
		return compound;
	}
	
	@Override
	public NBTTagCompound getUpdateTag()
	{
		return writeToNBT(new NBTTagCompound());
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet)
	{
		super.onDataPacket(net, packet);
		if (world.isRemote)
		{
			readFromNBT(packet.getNbtCompound());
		}
	}

	@Override
	public FluxCableContainer getFluxCableContainer()
	{
		return container;
	}
	
	@Override
	public boolean hasCapability (Capability<?> capability, EnumFacing facing)
	{
		if (capability == TeslaCapabilities.CAPABILITY_CONSUMER)
			return true;
		else if (capability == CapabilityEnergy.ENERGY)
			return true;
		return super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability (Capability<T> capability, EnumFacing facing)
	{
		if (capability == TeslaCapabilities.CAPABILITY_CONSUMER || capability == CapabilityEnergy.ENERGY)
			return (T) consumer[facing.getIndex()];
		return super.getCapability(capability, facing);
	}

	@Override
	public int getEnergyStored(EnumFacing from)
	{
		return 0;
	}

	@Override
	public int getMaxEnergyStored(EnumFacing from)
	{
		return (int)container.getCapacity();
	}

	@Override
	public boolean canConnectEnergy(EnumFacing from)
	{
		return true;
	}

	@Override
	public int receiveEnergy(EnumFacing facing, int Tesla, boolean simulated)
	{
		TileEntity tile = world.getTileEntity(pos.offset(facing));
		if (tile instanceof cofh.api.energy.IEnergyProvider)
		{
			cofh.api.energy.IEnergyProvider provider = (cofh.api.energy.IEnergyProvider) tile;
			return consumer[facing.getIndex()].receiveRF(Tesla, simulated, provider);
		}
		return 0;
	}
	
}
