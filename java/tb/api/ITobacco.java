package tb.api;

import net.minecraft.entity.player.EntityPlayer;

public interface ITobacco {
	
	public abstract void performTobaccoEffect(EntityPlayer smoker, int metadata, boolean isSilverwood);

}
