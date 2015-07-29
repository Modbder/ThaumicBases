package tb.network.proxy;

import tb.core.TBCore;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import io.netty.channel.ChannelHandler;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

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
		TBCore.network.sendToAllAround(new PacketTB(tg,0), new TargetPoint(w.provider.dimensionId,x,y,z,32));
	}

}
