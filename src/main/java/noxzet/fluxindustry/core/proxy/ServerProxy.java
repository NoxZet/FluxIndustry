package noxzet.fluxindustry.core.proxy;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import noxzet.fluxindustry.api.IFluxIndustryPacketHandler;
import noxzet.fluxindustry.core.network.RequestPacket;
import noxzet.fluxindustry.core.network.ResponsePacket;

public class ServerProxy extends CommonProxy {

	@Override
	public void onPacket(IMessage message, MessageContext ctx)
	{
		EntityPlayerMP player = ctx.getServerHandler().player;
		if (message instanceof RequestPacket)
		{
			RequestPacket packet = (RequestPacket) message;
			TileEntity tile = player.world.getTileEntity(packet.getPos());
			if (tile != null && tile instanceof IFluxIndustryPacketHandler)
			{
				ResponsePacket response = new ResponsePacket(
						packet.getUid(), packet.getPos(), packet.getField(),
						((IFluxIndustryPacketHandler) tile).fluxPacketGetBytes(packet.getField()));
			}
		}
	}
	
}
