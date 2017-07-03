package noxzet.fluxindustry.core.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import noxzet.fluxindustry.api.IFluxIndustryPacketHandler;
import noxzet.fluxindustry.core.FluxUtils;

public class ResponsePacket implements IMessage {
	
	private int uid;
	private BlockPos pos;
	private int field;
	private byte[] bytes;
	
	public ResponsePacket() {}
	
	public ResponsePacket(int uid, BlockPos pos, int field, byte[] bytes)
	{
		this.uid = uid;
		this.pos = pos;
		this.field = field;
		this.bytes = bytes;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		int x, y, z;
		byte[] intBuffer = new byte[]{0, 0, 0, 0};
		buf.readBytes(intBuffer);
		this.uid = FluxUtils.byteArrayToInt(intBuffer);
		buf.readBytes(intBuffer);
		x = FluxUtils.byteArrayToInt(intBuffer);
		buf.readBytes(intBuffer);
		y = FluxUtils.byteArrayToInt(intBuffer);
		buf.readBytes(intBuffer);
		z = FluxUtils.byteArrayToInt(intBuffer);
		this.pos = new BlockPos(x, y, z);
		buf.readBytes(intBuffer);
		this.field = FluxUtils.byteArrayToInt(intBuffer);
		this.bytes = new byte[buf.readableBytes()];
		buf.readBytes(this.bytes);
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
		buf.writeBytes(this.bytes);
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
	
	public byte[] getBytes()
	{
		return bytes;
	}
	
	public IMessage process(MessageContext ctx)
	{
		TileEntity tile = Minecraft.getMinecraft().world.getTileEntity(getPos());
		if (tile != null && tile instanceof IFluxIndustryPacketHandler)
		{
			((IFluxIndustryPacketHandler) tile).fluxPacketHandleBytes(getField(), getBytes());
		}
		return null;
	}
	
}
