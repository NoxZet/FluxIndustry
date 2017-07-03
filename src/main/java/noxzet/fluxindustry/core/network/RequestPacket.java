package noxzet.fluxindustry.core.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import noxzet.fluxindustry.api.IFluxIndustryPacketHandler;
import noxzet.fluxindustry.core.FluxUtils;

public class RequestPacket implements IMessage {
	
	private int uid;
	private BlockPos pos;
	private int field;
	
	public RequestPacket() {}
	
	public RequestPacket(int uid, BlockPos pos, int field)
	{
		this.uid = uid;
		this.pos = pos;
		this.field = field;
	}
	
	public RequestPacket(BlockPos pos, int field)
	{
		this(FluxNetworkWrapper.randomUid(), pos, field);
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		int x, y, z;
		byte[] intBuffer = new byte[]{0, 0, 0, 0};
		buf.readBytes(intBuffer);
		uid = FluxUtils.byteArrayToInt(intBuffer);
		buf.readBytes(intBuffer);
		x = FluxUtils.byteArrayToInt(intBuffer);
		buf.readBytes(intBuffer);
		y = FluxUtils.byteArrayToInt(intBuffer);
		buf.readBytes(intBuffer);
		z = FluxUtils.byteArrayToInt(intBuffer);
		pos = new BlockPos(x, y, z);
		buf.readBytes(intBuffer);
		field = FluxUtils.byteArrayToInt(intBuffer);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		int x, y, z;
		x = this.pos.getX();
		y = this.pos.getY();
		z = this.pos.getZ();
		buf.writeBytes(FluxUtils.intToByteArray(this.uid));
		buf.writeBytes(FluxUtils.intToByteArray(x));
		buf.writeBytes(FluxUtils.intToByteArray(y));
		buf.writeBytes(FluxUtils.intToByteArray(z));
		buf.writeBytes(FluxUtils.intToByteArray(this.field));
	}
	
	public int getUid()
	{
		return uid;
	}
	
	public BlockPos getPos()
	{
		return pos;
	}
	
	public int getField()
	{
		return field;
	}
	
	public IMessage process(MessageContext ctx)
	{
		EntityPlayerMP player = ctx.getServerHandler().player;
		TileEntity tile = player.world.getTileEntity(getPos());
		if (tile != null && tile instanceof IFluxIndustryPacketHandler)
		{
			ResponsePacket response = new ResponsePacket(
					getUid(), getPos(), getField(),
					((IFluxIndustryPacketHandler) tile).fluxPacketGetBytes(getField()));
			return response;
			//FluxNetworkWrapper.INSTANCE.sendTo(response, player);
		}
		return null;
	}
	
}
