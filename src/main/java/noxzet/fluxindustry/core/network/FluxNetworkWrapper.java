package noxzet.fluxindustry.core.network;

import java.util.Random;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import noxzet.fluxindustry.core.FluxIndustry;

public class FluxNetworkWrapper {

	public static int packetId = 0;
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(FluxIndustry.MODID);
	public static final Random random = new Random();
	
	public static void registerPackets()
	{
		registerPacket(RequestPacket.class);
		registerPacket(RequestPacket.class);
	}
	
	public static void registerPacket(Class <? extends IMessage> packet)
	{
		INSTANCE.registerMessage(PacketHandler.class, (Class) packet, packetId, Side.SERVER);
		INSTANCE.registerMessage(PacketHandler.class, (Class) packet, packetId+1, Side.CLIENT);
		packetId+=2;
	}
	
}
