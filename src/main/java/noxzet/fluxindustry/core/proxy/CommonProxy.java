package noxzet.fluxindustry.core.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import noxzet.fluxindustry.api.IFluxIndustryPacketHandler;
import noxzet.fluxindustry.core.network.FluxNetworkWrapper;
import noxzet.fluxindustry.core.network.RequestPacket;
import noxzet.fluxindustry.core.network.ResponsePacket;

public class CommonProxy {
	
	public void registerItemRenderer(Item item, int meta, String id) {}
	public void prepareColorHandler(Item item) {}
	public void registerColorHandlers() {}
	
	public void onPacket(IMessage message, MessageContext ctx)
	{
		System.out.println("CommonProxy received message");
		EntityPlayerMP player = ctx.getServerHandler().player;
		if (message instanceof RequestPacket)
		{
			System.out.println("CommonProxy got request");
			RequestPacket packet = (RequestPacket) message;
			TileEntity tile = player.world.getTileEntity(packet.getPos());
			if (tile != null && tile instanceof IFluxIndustryPacketHandler)
			{
				System.out.println("It matches existing TileEntity");
				ResponsePacket response = new ResponsePacket(
						packet.getUid(), packet.getPos(), packet.getField(),
						((IFluxIndustryPacketHandler) tile).fluxPacketGetBytes(packet.getField()));
				FluxNetworkWrapper.INSTANCE.sendTo(response, player);
				System.out.println("Sending response to " + player.getName());
			}
		}
	}
	
}
