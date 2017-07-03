package noxzet.fluxindustry.core.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import noxzet.fluxindustry.core.FluxIndustry;

public class PacketHandler implements IMessageHandler<IMessage, IMessage> {

	@Override
	public IMessage onMessage(IMessage message, MessageContext ctx)
	{
		if (message instanceof RequestPacket && ctx.side == Side.SERVER) {
			return ((RequestPacket) message).process(ctx);
		}
		else if (message instanceof ResponsePacket && ctx.side == Side.CLIENT) {
			return ((ResponsePacket) message).process(ctx);
		}
		return null;
	}
	
}
