package tb.network.proxy;

import io.netty.channel.ChannelHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import tb.core.TBCore;

@ChannelHandler.Sharable
public class TBNetworkManager implements IMessageHandler<PacketTB,IMessage>
{

	@Override
	public IMessage onMessage(PacketTB message, MessageContext ctx)
	{
		if(message.id == 0)
		{
			NBTTagCompound tag = message.sent;
			World w = TBCore.proxy.clientWorld();
			w.playSound(tag.getDouble("x"), tag.getDouble("y"), tag.getDouble("z"), tag.getString("snd"), tag.getFloat("v"), tag.getFloat("p"), false);
		}
		return null;
	}
	
	public static void playSoundOnServer(World w, String sound, double x, double y, double z, float volume, float pitch)
	{
		NBTTagCompound tg = new NBTTagCompound();
		tg.setString("snd", sound);
		tg.setDouble("x", x);
		tg.setDouble("y", y);
		tg.setDouble("z", z);
		tg.setFloat("v", volume);
		tg.setFloat("p", pitch);
		TBCore.network.sendToAllAround(new PacketTB(tg,0), new TargetPoint(w.provider.getDimensionId(),x,y,z,32));
	}

}
