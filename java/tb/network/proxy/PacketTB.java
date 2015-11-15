package tb.network.proxy;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

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
