package noxzet.fluxindustry.core.energy;

public enum EnumEnergyHandling {

	NONE(false, false),
	INPUT(true, false),
	OUTPUT(false, true),
	BOTH(true, true);
	
	private final boolean input, output;
	
	private EnumEnergyHandling(boolean input, boolean output)
	{
		this.input = input;
		this.output = output;
	}
	
	public boolean hasInput()
	{
		return this.input;
	}
	
	public boolean hasOutput()
	{
		return this.output;
	}
	
}
