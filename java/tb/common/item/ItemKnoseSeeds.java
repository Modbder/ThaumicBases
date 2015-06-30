package tb.common.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemKnoseSeeds extends ItemSeeds{

    private Block field_150925_a;
    private Block soilBlockID;
	
	public ItemKnoseSeeds(Block p_i45352_1_, Block p_i45352_2_) {
		super(p_i45352_1_, p_i45352_2_);
        this.field_150925_a = p_i45352_1_;
        this.soilBlockID = p_i45352_2_;
	}
	
    public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
        if (p_77648_7_ != 1)
        {
            return false;
        }
        else if (p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_) && p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_ + 1, p_77648_6_, p_77648_7_, p_77648_1_))
        {
            if (p_77648_3_.getBlock(p_77648_4_, p_77648_5_, p_77648_6_) == soilBlockID)
            {
                p_77648_3_.setBlock(p_77648_4_, p_77648_5_ + 1, p_77648_6_, this.field_150925_a);
                --p_77648_1_.stackSize;
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

}
