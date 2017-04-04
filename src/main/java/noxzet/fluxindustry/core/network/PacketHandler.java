package noxzet.fluxindustry.core.network;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import noxzet.fluxindustry.core.FluxIndustry;

public class PacketHandler implements IMessageHandler<IMessage, IMessage> {

	@Override
	public IMessage onMessage(IMessage message, MessageContext ctx)
	{
		FluxIndustry.proxy.onPacket(message, ctx);
		return null;
	}
	
}
