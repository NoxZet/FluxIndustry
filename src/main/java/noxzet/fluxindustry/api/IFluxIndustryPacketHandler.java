package noxzet.fluxindustry.api;

public interface IFluxIndustryPacketHandler {

	public byte[] fluxPacketGetBytes(int field);
	public void fluxPacketHandleBytes(int field, byte[] bytes);
	
}
