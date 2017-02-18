package noxzet.fluxindustry.core.tileentity;

import net.minecraft.block.state.IBlockState;
import noxzet.fluxindustry.core.block.electric.BlockEnergyStorage;
import noxzet.fluxindustry.core.energy.EnumEnergyHandling;

public class TileEnergyStorage extends TileElectricInventory {
	
	public static final int TYPE_BASIC = 0;
	protected final int storageType;
	private boolean updateRotation = true;
	public static final int FIELD_ENERGY_CAPACITY = 0;
	public static final int FIELD_ENERGY_STORED = 1;

	public TileEnergyStorage()
	{
		this(0, 200000, 80, 80, 2, TYPE_BASIC);
	}
	
	public TileEnergyStorage(long stored, long capacity, long inputRate, long outputRate, int slots, int storageType)
	{
		super(stored, capacity, inputRate, outputRate, slots);
		this.storageType = storageType;
	}
	
	public void update()
	{
		if (!world.isRemote)
		{
			if (this.updateRotation)
			{
				onRotated();
				this.updateRotation = false;
			}
			this.slotGiveEnergy(0, container.getMaxOutputTick(false), false);
			this.slotTakeEnergy(1, container.getMaxInputTick(false), false);
		}
		super.update();
	}
	
	@Override
	public void onRotated()
	{
		IBlockState thisBlock = world.getBlockState(pos);
		if (thisBlock.getBlock() instanceof BlockEnergyStorage)
		{
			this.setAllEnergyHandling(EnumEnergyHandling.INPUT);
			this.setSideEnergyHandling(thisBlock.getValue(BlockEnergyStorage.FACING), EnumEnergyHandling.OUTPUT);
		}
	}
	
	@Override
	public int getField(int id)
	{
		switch (id)
		{
			case FIELD_ENERGY_STORED: return (int)container.getStoredPower();
			case FIELD_ENERGY_CAPACITY: return (int)container.getCapacity();
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
		}
	}

	public int getStorageType()
	{
		return storageType; 
	}
	
}
