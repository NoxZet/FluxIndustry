package noxzet.fluxindustry.core.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CommonProxy {
	
	public void registerItemRenderer(Item item, int meta, String id) {}
	public void prepareColorHandler(Item item) {}
	public void registerColorHandlers() {}
	public void onPacket(IMessage message, MessageContext ctx) {}
	
}
