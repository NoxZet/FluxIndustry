package noxzet.fluxindustry.core.energy;

import net.darkhax.tesla.api.ITeslaProducer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.IEnergyStorage;

public class FluxUniversalProducer implements ITeslaProducer {

	private static final int TESLA = 0;
	private static final int FORGE = 1;
	private static final int COFH = 2;
	private ITeslaProducer producer;
	private IEnergyStorage forgeProducer;
	private cofh.api.energy.IEnergyProvider rfProducer;
	private EnumFacing rfFacing;
	private int mode;
	
	public FluxUniversalProducer(ITeslaProducer producer)
	{
		this.producer = producer;
		this.mode = TESLA;
	}
	
	public FluxUniversalProducer(IEnergyStorage producer)
	{
		this.forgeProducer = producer;
		this.mode = FORGE;
	}
	
	public FluxUniversalProducer(cofh.api.energy.IEnergyProvider producer, EnumFacing facing)
	{
		this.rfProducer = producer;
		this.rfFacing = facing;
		this.mode = COFH;
	}
	
	public long takePower(long Tesla, boolean simulate)
	{
		if (this.mode == TESLA)
			return producer.takePower(Tesla, simulate);
		else if (this.mode == FORGE)
			return forgeProducer.extractEnergy((int)Tesla, simulate);
		else if (this.mode == COFH)
			return rfProducer.extractEnergy(this.rfFacing, (int)Tesla, simulate);
		return 0;
	}
	
}
