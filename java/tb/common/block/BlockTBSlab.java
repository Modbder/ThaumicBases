package tb.common.block;

import java.util.List;
import java.util.Random;

import DummyCore.Client.RenderAccessLibrary;
import DummyCore.Utils.BlockStateMetadata;
import DummyCore.Utils.IOldCubicBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumcraft.api.crafting.IInfusionStabiliser;

public abstract class BlockTBSlab extends Block implements IOldCubicBlock, IInfusionStabiliser{
	final boolean isFullBlock;
	public boolean isAStabiliser;
	public BlockTBSlab(Material materialIn, boolean b) {
		super(materialIn);
		isFullBlock = b;
	}
	
	public abstract String getUnlocalizedName(int meta);
	
	public boolean isTopSlab(int meta)
	{
		return !isFullBlock ? meta > 7 : false;
	}
	
    public boolean isFullCube()
    {
        return isFullBlock;
    }

    public boolean isOpaqueCube()
    {
        return isFullBlock;
    }
    
    public int damageDropped(IBlockState state)
    {
    	return isTopSlab(BlockStateMetadata.getMetaFromState(state)) ? BlockStateMetadata.getMetaFromState(state) % 8 : BlockStateMetadata.getMetaFromState(state);
    }

    public boolean canStabaliseInfusion(World paramWorld, BlockPos paramBlockPos)
    {
    	return isAStabiliser;
    }
    
    public IBlockState getStateFromMeta(int meta)
    {
    	return this.getDefaultState().withProperty(BlockStateMetadata.METADATA, BlockStateMetadata.MetadataValues.values()[meta]);
    }
    
    public int getMetaFromState(IBlockState state)
    {
    	return BlockStateMetadata.getMetaFromState(state);
    }
    
    protected BlockState createBlockState()
    {
    	return new BlockState(this,BlockStateMetadata.METADATA);
    }
    
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
    	if(isFullBlock)
    		return this.getStateFromMeta(meta);
    	
    	return facing != EnumFacing.DOWN && (facing == EnumFacing.UP || hitY <= 0.5D) ? this.getStateFromMeta(meta) : this.getStateFromMeta(meta+8); 
    }
    
    public void setBlockBoundsForItemRender()
    {
        if (isFullBlock)
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        else
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
    }
    
    @SuppressWarnings("rawtypes")
	public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity)
    {
        this.setBlockBoundsBasedOnState(worldIn, pos);
        super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
    }
    
    public int quantityDropped(Random random)
    {
        return isFullBlock ? 2 : 1;
    }
    
    public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
    {
        if (isFullBlock)
            return super.shouldSideBeRendered(worldIn, pos, side);
        
        else if (side != EnumFacing.UP && side != EnumFacing.DOWN && !super.shouldSideBeRendered(worldIn, pos, side))
            return false;
        else
        {
            BlockPos blockpos1 = pos.offset(side.getOpposite());
            IBlockState iblockstate = worldIn.getBlockState(pos);
            IBlockState iblockstate1 = worldIn.getBlockState(blockpos1);
            boolean flag = isSlab(iblockstate.getBlock()) && iblockstate.getBlock().getMetaFromState(iblockstate) > 7;
            boolean flag1 = isSlab(iblockstate1.getBlock()) && iblockstate1.getBlock().getMetaFromState(iblockstate1) > 7;
            return flag1 ? (side == EnumFacing.DOWN ? true : (side == EnumFacing.UP && super.shouldSideBeRendered(worldIn, pos, side) ? true : !isSlab(iblockstate.getBlock()) || !flag)) : (side == EnumFacing.UP ? true : (side == EnumFacing.DOWN && super.shouldSideBeRendered(worldIn, pos, side) ? true : !isSlab(iblockstate.getBlock()) || flag));
        }
    }
    
    public boolean isSlab(Block b)
    {
    	return b == this ? isFullBlock : false;
    }
    
    public int getDamageValue(World worldIn, BlockPos pos)
    {
        return super.getDamageValue(worldIn, pos) & 7;
    }
    
    public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos)
    {
        if (isFullBlock)
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        else
        {
            IBlockState iblockstate = worldIn.getBlockState(pos);

            if (iblockstate.getBlock() == this)
            {
                if (this.isTopSlab(BlockStateMetadata.getMetaFromState(iblockstate)))
                    this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
                else
                    this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
            }
        }
    }
    
    protected boolean canSilkHarvest()
    {
        return false;
    }
    
    public int getDCRenderID()
    {
    	return RenderAccessLibrary.RENDER_ID_CUBE;
    }
}
