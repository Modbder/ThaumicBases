package tb.common.itemblock;

import DummyCore.Utils.BlockStateMetadata;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tb.common.block.BlockTBSlab;

public class ItemTBSlab extends ItemBlock{
	
    public final BlockTBSlab singleSlab;
    public final BlockTBSlab doubleSlab;

    public ItemTBSlab(Block block, BlockTBSlab singleSlab, BlockTBSlab doubleSlab)
    {
        super(block);
        this.singleSlab = singleSlab;
        this.doubleSlab = doubleSlab;
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }
    
    public int getMetadata(int damage)
    {
        return damage;
    }
    
    public String getUnlocalizedName(ItemStack stack)
    {
        return this.singleSlab.getUnlocalizedName(stack.getMetadata());
    }
    
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (stack.stackSize == 0)
        {
            return false;
        }
        else if (!playerIn.canPlayerEdit(pos.offset(side), side, stack))
        {
            return false;
        }
        else
        {
            int meta = BlockStateMetadata.getBlockMetadata(worldIn, pos) % 8;
            IBlockState iblockstate = worldIn.getBlockState(pos);

            if (iblockstate.getBlock() == this.singleSlab)
            {
            	boolean top = this.singleSlab.isTopSlab(BlockStateMetadata.getMetaFromState(iblockstate));
            	
                if ((side == EnumFacing.UP && !top || side == EnumFacing.DOWN && top) && meta == stack.getMetadata())
                {
                    IBlockState iblockstate1 = this.doubleSlab.getStateFromMeta(meta);

                    if (worldIn.checkNoEntityCollision(this.doubleSlab.getCollisionBoundingBox(worldIn, pos, iblockstate1)) && worldIn.setBlockState(pos, iblockstate1, 3))
                    {
                        worldIn.playSoundEffect(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, this.doubleSlab.stepSound.getPlaceSound(), (this.doubleSlab.stepSound.getVolume() + 1.0F) / 2.0F, this.doubleSlab.stepSound.getFrequency() * 0.8F);
                        --stack.stackSize;
                    }

                    return true;
                }
            }

            return this.tryPlace(stack, worldIn, pos.offset(side), meta) ? true : super.onItemUse(stack, playerIn, worldIn, pos, side, hitX, hitY, hitZ);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack)
    {
        BlockPos blockpos1 = pos;
        int meta = stack.getMetadata();
        IBlockState iblockstate = worldIn.getBlockState(pos);

        if (iblockstate.getBlock() == this.singleSlab)
        {
            boolean flag = BlockStateMetadata.getMetaFromState(iblockstate) < 8;

            if ((side == EnumFacing.UP && !flag || side == EnumFacing.DOWN && flag) && meta == BlockStateMetadata.getMetaFromState(iblockstate) % 8)
                return true;
        }

        pos = pos.offset(side);
        IBlockState iblockstate1 = worldIn.getBlockState(pos);
        return iblockstate1.getBlock() == this.singleSlab && meta == BlockStateMetadata.getMetaFromState(iblockstate1) % 8 ? true : super.canPlaceBlockOnSide(worldIn, blockpos1, side, player, stack);
    }
    
    private boolean tryPlace(ItemStack stack, World worldIn, BlockPos pos, int metaVariant)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);

        if (iblockstate.getBlock() == this.singleSlab)
        {
            int meta = BlockStateMetadata.getMetaFromState(iblockstate) % 8;

            if (meta == metaVariant)
            {
                IBlockState iblockstate1 = this.doubleSlab.getStateFromMeta(meta);

                if (worldIn.checkNoEntityCollision(this.doubleSlab.getCollisionBoundingBox(worldIn, pos, iblockstate1)) && worldIn.setBlockState(pos, iblockstate1, 3))
                {
                    worldIn.playSoundEffect(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, this.doubleSlab.stepSound.getPlaceSound(), (this.doubleSlab.stepSound.getVolume() + 1.0F) / 2.0F, this.doubleSlab.stepSound.getFrequency() * 0.8F);
                    --stack.stackSize;
                }

                return true;
            }
        }

        return false;
    }
}
