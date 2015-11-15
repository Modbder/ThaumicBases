package tb.common.item;

import java.util.Hashtable;

import DummyCore.Utils.Coord3D;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import tb.utils.TBUtils;
import thaumcraft.common.Thaumcraft;

public class ItemSpawnerCompass extends Item{
	Hashtable<Entity, Object> beams = new Hashtable<Entity, Object>();
	
	@Override
	public void onUpdate(ItemStack stk, World w, Entity player, int slotNum, boolean isCurrentItem) 
	{
		if(w != null && w.isRemote && !isCurrentItem && player instanceof EntityPlayer && !(player instanceof FakePlayer))
		{
			if(beams.containsKey(player))
				beams.remove(player);
		}
		if(w != null && w.isRemote && player instanceof EntityPlayer && !(player instanceof FakePlayer) && isCurrentItem)
		{
			EntityPlayer p = (EntityPlayer) player;
			Coord3D closest = TBUtils.getClosestLoadedClientSpawner(p);
			if(closest != null)
			{
				Vec3 vec = player.getLookVec();
				
				Vec3 createdVec = new Vec3(closest.x+0.5D, closest.y+0.5D , closest.z+0.5D);
				Vec3 posTo0 = new Vec3(createdVec.xCoord - p.posX, createdVec.yCoord - (p.posY+p.getEyeHeight()), createdVec.zCoord - p.posZ);
				double xCoord = (posTo0.xCoord < 0 ? Math.max(posTo0.xCoord, -10) : Math.min(posTo0.xCoord, 10))/20;
				double yCoord = (posTo0.yCoord < 0 ? Math.max(posTo0.yCoord, -10) : Math.min(posTo0.yCoord, 10))/20;
				double zCoord = (posTo0.zCoord < 0 ? Math.max(posTo0.zCoord, -10) : Math.min(posTo0.zCoord, 10))/20;
				posTo0 = new Vec3(xCoord,yCoord,zCoord);
				createdVec = new Vec3(p.posX+vec.xCoord+posTo0.xCoord, p.posY+p.getEyeHeight()+vec.yCoord+posTo0.yCoord, p.posZ+vec.zCoord+posTo0.zCoord);
				
				Object beam = Thaumcraft.proxy.getFX().beamBore(p.posX+vec.xCoord,p.posY+p.getEyeHeight()+vec.yCoord,p.posZ+vec.zCoord, createdVec.xCoord, createdVec.yCoord, createdVec.zCoord, 2, 0xff0000, false, 0.08F, null, 1);
				beams.put(p, beam);
			}
		}
	}
}
