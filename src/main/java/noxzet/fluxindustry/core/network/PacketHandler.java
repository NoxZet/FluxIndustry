package noxzet.fluxindustry.core.network;

import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import noxzet.fluxindustry.core.FluxIndustry;

public class PacketHandler implements IMessageHandler<IMessage, IMessage> {

	@Override
	public IMessage onMessage(IMessage message, MessageContext ctx)
	{
		System.out.println("PacketHandler got message");
		if (message instanceof RequestPacket)
			System.out.println("it is RequestPacket");
		else if (message instanceof ResponsePacket)
			System.out.println("it is ResponsePacket");
		FluxIndustry.proxy.onPacket(message, ctx);
		return null;
	}
	
}
