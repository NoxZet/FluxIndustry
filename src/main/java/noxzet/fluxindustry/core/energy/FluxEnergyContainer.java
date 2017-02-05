package noxzet.fluxindustry.core.energy;

import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.api.ITeslaProducer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants.NBT;

public class FluxEnergyContainer implements ITeslaHolder, ITeslaConsumer, ITeslaProducer {
	
	private long stored, capacity, inputRate, outputRate, inputTick, outputTick;
	
	public FluxEnergyContainer()
	{
		this(0);
	}
	
	public FluxEnergyContainer(long capacity)
	{
		this(capacity, 0);
	}
	
	public FluxEnergyContainer(long capacity, long rate)
	{
		this(capacity, rate, rate);
	}
	
	public FluxEnergyContainer(long capacity, long inputRate, long outputRate)
	{
		this(0, capacity, inputRate, outputRate);
	}
	
	public FluxEnergyContainer(long stored, long capacity, long inputRate, long outputRate)
	{
		setCapacity(capacity);
		setStoredPower(stored);
		this.inputRate = inputRate;
		this.outputRate = outputRate;
	}
	
	public FluxEnergyContainer(NBTTagCompound compound)
	{
		this(0, 0, 0, 0);
		fromNBT(compound);
	}
	
	public void update()
	{
		inputTick = inputRate;
		outputTick = outputRate;
	}
	
	public void fromNBT(NBTTagCompound compound)
	{
		if (compound.hasKey("TeslaCapacity", NBT.TAG_LONG))
			setCapacity(compound.getLong("TeslaCapacity"));
		if (compound.hasKey("TeslaPower", NBT.TAG_LONG))
			setStoredPower(compound.getLong("TeslaPower"));
		if (compound.hasKey("TeslaInput", NBT.TAG_LONG))
			setInputRate(compound.getLong("TeslaInput"));
		if (compound.hasKey("TeslaOutput", NBT.TAG_LONG))
			setOutputRate(compound.getLong("TeslaOutput"));
	}
	
	public NBTTagCompound toNBT()
	{
		return this.toNBT(new NBTTagCompound());
	}
	
	public NBTTagCompound toNBT(NBTTagCompound compound)
	{
		compound.setLong("TeslaCapacity", capacity);
		compound.setLong("TeslaPower", stored);
		compound.setLong("TeslaInput", inputRate);
		compound.setLong("TeslaOutput", outputRate);
		return compound;
	}
	
	public long givePower (long Tesla, boolean simulated)
	{
		return givePower(Tesla, simulated, true);
	}
	
	public long givePower (long Tesla, boolean simulated, boolean tickable)
	{
		final long acceptedTesla;
		if (tickable == true)
			acceptedTesla = Math.min(this.capacity - this.stored, Math.min(this.inputTick, Tesla));
		else
			acceptedTesla = Math.min(this.capacity - this.stored, Math.min(this.inputRate, Tesla));
		if (simulated == false)
		{
			this.stored += acceptedTesla;
			if (tickable == true)
				inputTick -= acceptedTesla;
		}
		return acceptedTesla;
	}
	
	public long takePower (long Tesla, boolean simulated)
	{
		return takePower(Tesla, simulated, true);
	}
	
	public long takePower (long Tesla, boolean simulated, boolean tickable)
	{
		final long removedTesla;
		if (tickable == true)
			removedTesla = Math.min(this.stored, Math.min(this.outputTick, Tesla));
		else
			removedTesla = Math.min(this.stored, Math.min(this.outputRate, Tesla));
		if (simulated == false)
		{
			this.stored -= removedTesla;
			if (tickable == true)
				outputTick -= removedTesla;
		}
		return removedTesla;
	}
	
	public void changePower (long Tesla)
	{
		this.stored = Math.min(Math.max(0, this.stored + Tesla), this.capacity);
	}
	
	public void setCapacity(long capacity)
	{
		this.capacity = Math.max(0, capacity);
	}
	
	public void setStoredPower(long stored)
	{
		this.stored = Math.max(0, Math.min(this.capacity, stored));
	}
	
	public void setRate(long rate)
	{
		setRate(rate, rate);
	}
	
	public void setRate(long inputRate, long outputRate)
	{
		setInputRate(inputRate);
		setOutputRate(inputRate);
	}
	
	public void setInputRate(long inputRate)
	{
		this.inputRate = Math.max(0, inputRate);
	}
	
	public void setOutputRate(long outputRate)
	{
		this.outputRate = Math.max(0, outputRate);
	}
	
	@Override
	public long getCapacity()
	{
		return capacity;
	}

	@Override
	public long getStoredPower()
	{
		return stored;
	}
	
	public long getMaxInputTick(boolean tickable)
	{
		if (tickable)
			return Math.min(this.capacity - this.stored, this.inputTick);
		else
			return Math.min(this.capacity - this.stored, this.inputRate);
	}

	public long getInputRate()
	{
		return inputRate;
	}
	
	public long getMaxOutputTick(boolean tickable)
	{
		if (tickable)
			return Math.min(this.stored, this.outputTick);
		else
			return Math.min(this.stored, this.outputRate);
	}

	public long getOutputRate()
	{
		return outputRate;
	}
}
