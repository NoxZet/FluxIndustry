package noxzet.fluxindustry.api.energy;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

/* This interface is implemented by all FluxIndustry tileentities (including all possible addons).
 * If you are a creator of FluxIndustry addon, make sure to implement this, so possible plugins are compatible with your addon.
 * For creators of plugins, this API is mainly supposed to be used to retrieve info for mods like ComputerCraft or OpenComputers.
 */
public interface IFluxIndustryAPI {

	/* Gets simple name of machine. Valid names are:
	 * "electric_furnace", "electric_crusher", "generator_coal"
	 */
	public String getFluxName();
	
	/* It's still preferable to use Tesla Capabilities, Forge Energy Capabilities or RF API.
	 * Note that all those methods can be used from all directions with all TileEntities implementing this interface.
	 * "if (tileentity.hasCapability(..., ...))" can be ommited, if you do "if (tileentity instanceof IFluxIndustryAPI)"
	 * All energies are equivalent: 1 T = 1 FE = 1 RF
	 * 
	 * Using Tesla Capabilities:
	 * if (tileentity.hasCapability(TeslaCapabilities.CAPABILITY_HOLDER, facing))
	 *     long storedPower = ((ITeslaHolder)tileentity.getCapability(TeslaCapabilities.CAPABILITY_HOLDER, facing).getStoredPower();
	 *     
	 * Using Forge Energy Capabilities:
	 * if (tileentity.hasCapability(CapabilityEnergy.ENERGY, facing))
	 *     int storedPower = ((IEnergyStorage)tileentity.getCapability(CapabilityEnergy.ENERGY, facing).getEnergyStored();
	 * 
	 * Using CoFH RF API
	 * int storedPower = ((IEnergyHandler)tileentity).getEnergyStored(facing);
	 */
	public long getFluxStoredPower();
	
	/* Same as above, except:
	 * 
	 * Using Tesla Capabilities, method to use on retrieved capability is getMaxStoredPower()
	 * 
	 * Using Forge Energy Capabilities, method to use on retrieved capability is getMaxEnergyStored()
	 * 
	 * Using CoFH RF API, method to use on tileentity is getMaxEnergyStored(facing);
	 */
	public long getFluxMaxStoredPower();
	
	/* Wheter this Tile Entity can generate power without other power input
	 */
	public boolean getFluxIsGenerator();
	
	/* If true, Tile Entity should output energy at given time
	 */
	public boolean getFluxIsGeneratorRunning();
	
	/* Energy generated per tick by generator.
	 * Outputs energy made when running, ie. returns non-zero value even when inner storage is empty and it generates no new energy
	 * Exception for tiles such as Nuclear Generator, which vary in output, and thus may output zero
	 */
	public long getFluxGeneratedPerTick();
	
	/* Some machines, like Geothermal Generator running by placing over lava block, will return false
	 */
	public boolean getFluxIsFuelPowered();
	
	/* Returns remaining fuel level, usually how many ticks can generator run without interaction
	 * For example in Coal Generator, this will also return potential energy of stored coal
	 */
	public int getFluxFuelRemaining();
	
	/* Whether this Tile Entity turns inputted items into other items
	 */
	public boolean getFluxIsMachine();

	/* If true, Tile Entity is currently accepting energy
	 */
	public boolean getFluxIsMachineRunning();
	
	/* Returns non-zero value, even if machine has inner storage full and is not currently working
	 */
	public long getFluxAcceptedPerTick();
	
	/* Progress towards turning item into another
	 */
	public double getFluxProgress();
	
	/* Returns contents of input slots, usually left-handed in machines. Size of ArrayList shouldn't change.
	 * Should return non-empty ArrayList if getFluxIsFuelPowered() is true.
	 * Can be empty, if Tile isn't machine or fuel consuming generator.
	 * DO NOT MODIFY OUTPUT.
	 */
	public ArrayList<ItemStack> getFluxInputStacks();
	
	/* Returns contents of output slots, usually right-handed in machines. Size of ArrayList shouldn't change.
	 * Can be empty, if Tile isn't machine.
	 * DO NOT MODIFY OUTPUT.
	 */
	public ArrayList<ItemStack> getFluxOutputStacks();
	
	/* Zero for most machines
	 */
	public int getFluxTemperature();
	
	/* Returns -1, if this Tile isn't changing temperature at all.
	 * Critical temperature is temperature where something really bad happens, such as explosion.
	 */
	public int getFluxCriticalTemperature();
	
}
