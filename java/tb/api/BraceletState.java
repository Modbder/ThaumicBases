package tb.api;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.wands.WandCap;
import thaumcraft.api.wands.WandRod;

public abstract class BraceletState {
	
	public static final ArrayList<BraceletState> braceletStates = new ArrayList<BraceletState>();

	public static BraceletState forMetadata(int meta)
	{
		return braceletStates.get(Math.min(braceletStates.size()-1, meta));
	}
	
	public final int metadataForState;
	
	public BraceletState()
	{
		braceletStates.add(this);
		metadataForState = braceletStates.size()-1;
	}
	
	public abstract String getName(ItemStack bracelet);
	
	public abstract ResourceLocation getBraceletTexture(ItemStack bracelet);
	
	public abstract WandCap getCaps(ItemStack bracelet);
	
	public abstract WandRod getRod(ItemStack bracelet);
	
	public abstract int getCapacity(ItemStack bracelet);
	
	public static class BraceletStateGeneric extends BraceletState
	{
		public final String name;
		public final ResourceLocation tex;
		public final WandCap cap;
		public final WandRod rod;
		public final int capacity;
		
		public BraceletStateGeneric(String par1, ResourceLocation par2, WandCap par3, WandRod par4, int par5)
		{
			super();
			name = par1;
			tex = par2;
			cap = par3;
			rod = par4;
			capacity = par5;
		}

		@Override
		public String getName(ItemStack bracelet) {
			return name;
		}

		@Override
		public ResourceLocation getBraceletTexture(ItemStack bracelet) {
			return tex;
		}

		@Override
		public WandCap getCaps(ItemStack bracelet) {
			return cap;
		}

		@Override
		public WandRod getRod(ItemStack bracelet) {
			return rod;
		}

		@Override
		public int getCapacity(ItemStack bracelet) {
			return capacity;
		}
		
	}
}
