package noxzet.fluxindustry.core.proxy;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import noxzet.fluxindustry.api.IFluxIndustryPacketHandler;
import noxzet.fluxindustry.core.item.ItemFlux;
import noxzet.fluxindustry.core.network.ResponsePacket;

public class ClientProxy extends CommonProxy {

	public List<Item> itemsColorHandler = new ArrayList<>();
	
	@Override
	public void registerItemRenderer(Item item, int meta, String id)
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	}
	
	@Override
	public void prepareColorHandler(Item item)
	{
		itemsColorHandler.add(item);
	}
	
	@Override
	public void registerColorHandlers()
	{
		for(Item item : itemsColorHandler)
		{
			Minecraft.getMinecraft().getItemColors().registerItemColorHandler(
					new IItemColor()
					{
						@Override
						public int getColorFromItemstack(ItemStack stack, int tintIndex)
						{
							if (stack.getItem() instanceof ItemFlux)
								return ((ItemFlux)stack.getItem()).getColor(stack, tintIndex);
							return 0xFFFFFF;
						}
					},
					item);
		}
	}

	@Override
	public void onPacket(IMessage message, MessageContext ctx)
	{
		if (message instanceof ResponsePacket)
		{
			ResponsePacket packet = (ResponsePacket) message;
			TileEntity tile = Minecraft.getMinecraft().world.getTileEntity(packet.getPos());
			if (tile != null && tile instanceof IFluxIndustryPacketHandler)
			{
				((IFluxIndustryPacketHandler) tile).fluxPacketHandleBytes(packet.getField(), packet.getBytes());
			}
		}
	}
	
}
