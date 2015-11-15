package tb.utils;

import java.util.ArrayList;

import DummyCore.Utils.Coord3D;
import DummyCore.Utils.Pair;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.FakePlayer;
import thaumcraft.api.internal.EnumWarpType;
import thaumcraft.api.research.ResearchHelper;

public class TBUtils {
	
	public static ArrayList<Pair<Integer,Coord3D>> loadedClientSpawners = new ArrayList<Pair<Integer,Coord3D>>();
	
	public static Coord3D getClosestLoadedClientSpawner(EntityPlayer player)
	{
		if(player == null)
			return null;
		
		if(player instanceof FakePlayer)
			return null;
		
		if(player.worldObj == null)
			return null;
		
		if(!player.worldObj.isRemote)
			return null;
		
		double closestDistance = Short.MAX_VALUE;
		int index = -1;
		
		for(Pair<Integer,Coord3D> p : loadedClientSpawners)
		{
			if(p.getFirst() != player.dimension)
				continue;
			
			double distance = Math.sqrt(player.getDistance(p.getSecond().x, p.getSecond().y, p.getSecond().z));
			if(distance < closestDistance)
			{
				closestDistance = distance;
				index = loadedClientSpawners.indexOf(p);
			}
		}
		
		if(index != -1)
			return loadedClientSpawners.get(index).getSecond();
		
		return null;
	}
	
	/**
	 * @param type 0 = Temporary, 1 = Regular, 2 = Permanent
	 */
	public static void addWarpToPlayer(EntityPlayer addTo, int amount, int type)
	{
		switch(type)
		{
			case 2:
			{
				ResearchHelper.addWarpToPlayer(addTo, amount, EnumWarpType.PERMANENT);
				return;
			}
			case 1:
			{
				ResearchHelper.addWarpToPlayer(addTo, amount, EnumWarpType.NORMAL);
				return;
			}
			case 0:
			{
				ResearchHelper.addWarpToPlayer(addTo, amount, EnumWarpType.TEMPORARY);
				return;
			}
			default:
			{
				ResearchHelper.addWarpToPlayer(addTo, amount, EnumWarpType.NORMAL);
				return;
			}
		}
	}

}
