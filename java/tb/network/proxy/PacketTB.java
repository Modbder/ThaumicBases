package tb.network.proxy;

import net.minecraft.nbt.NBTTagCompound;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketTB implements IMessage{

	int id;
	NBTTagCompound sent;
	
	public PacketTB()
	{
		//FML registry
	}
	
	public PacketTB(NBTTagCompound tag, int i)
	{
		id = i;
		sent = tag;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		id = ByteBufUtils.readVarInt(buf, 1);
		sent = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeVarInt(buf, id, 1);
		ByteBufUtils.writeTag(buf, sent);
	}

}
