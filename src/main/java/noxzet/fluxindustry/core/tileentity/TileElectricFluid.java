package noxzet.fluxindustry.core.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import noxzet.fluxindustry.core.FluxUtils;
import noxzet.fluxindustry.core.fluid.FluxFluidContainer;

public class TileElectricFluid extends TileElectricInventory {

	FluxFluidContainer fluidContainer;
	public static final int FIELD_ENERGY_CAPACITY = 0;
	public static final int FIELD_ENERGY_STORED = 1;
	public static final int FLUID_COLOR = 2;
	public static final int FLUID_CAPACITY = 3;
	public static final int FLUID_AMOUNT = 4;
	public int fluidColor = 0;
	public int fluidCapacity = 0;
	public int fluidAmount = 0;
	public String fluidUnlocalizedName = "";
	
	public TileElectricFluid()
	{
		this(0, 0, 0, 0, 0, 0);
	}
	
	public TileElectricFluid(long stored, long capacity, long inputRate, long outputRate, int slots, int fluid)
	{
		super(stored, capacity, inputRate, outputRate, slots);
		this.fluidContainer = new FluxFluidContainer(fluid);
	}
	
	@Override
	public int getField(int id)
	{
		switch (id)
		{
			case FIELD_ENERGY_STORED: return (int)container.getStoredPower();
			case FIELD_ENERGY_CAPACITY: return (int)container.getCapacity();
			case FLUID_COLOR:
				if (fluidContainer.stack == null) return 0xFF888888;
				else return FluxUtils.getFluidColor(fluidContainer.stack.getFluid());
			case FLUID_CAPACITY: return fluidContainer.getCapacity();
			case FLUID_AMOUNT:
				if (fluidContainer.stack == null) return 0;
				else return fluidContainer.stack.amount;
			default: return 0;
		}
	}
	
	@Override
	public void setField(int id, int data)
	{
		switch (id)
		{
			case FIELD_ENERGY_STORED: container.setStoredPower(data); break;
			case FIELD_ENERGY_CAPACITY: container.setCapacity(data); break;
			case FLUID_COLOR: this.fluidColor = data; break;
			case FLUID_CAPACITY: this.fluidCapacity = data; break;
			case FLUID_AMOUNT: this.fluidAmount = data; break;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		if (compound.hasKey("FluidStack"))
			fluidContainer.readFromNBT((NBTTagCompound)compound.getTag("FluidStack"));
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		if (fluidContainer.stack != null)
			compound.setTag("FluidStack", fluidContainer.stack.writeToNBT(new NBTTagCompound()));
		else if (compound.hasKey("FluidStack"))
			compound.removeTag("FluidStack");
		return compound;
	}
	
	@Override
	public boolean hasCapability (Capability<?> capability, EnumFacing facing)
	{
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return true;
		else
			return super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability (Capability<T> capability, EnumFacing facing)
	{
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return (T)fluidContainer;
		else
			return super.getCapability(capability, facing);
	}
	
	@Override
	public byte[] fluxPacketGetBytes(int field)
	{
		switch (field)
		{
			case 0: return FluxUtils.stringToByteArray(this.fluidContainer.stack.getFluid().getUnlocalizedName());
			default: return new byte[0];
		}
	}
	
	@Override
	public void fluxPacketHandleBytes(int field, byte[] bytes)
	{
		switch (field)
		{
			case 0: this.fluidUnlocalizedName = FluxUtils.byteArrayToString(bytes); break;
		}
	}

}
