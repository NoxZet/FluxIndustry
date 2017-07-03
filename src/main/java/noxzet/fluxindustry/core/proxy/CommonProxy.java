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
	
}
