package tb.common.block;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import DummyCore.Client.Icon;
import DummyCore.Client.IconRegister;
import DummyCore.Client.RenderAccessLibrary;
import DummyCore.Utils.IOldCubicBlock;
import DummyCore.Utils.MathUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCryingObelisk extends Block implements IOldCubicBlock{

	public Icon topbotIcon;
	public Icon genericIcon;
	public Icon topSide;
	public Icon botSide;
	String texture;
	
	public BlockCryingObelisk() 
	{
		super(Material.rock);
	}
	
	public BlockCryingObelisk setBlockName(String name)
	{
		this.setUnlocalizedName(name);
		return this;
	}
    
	public BlockCryingObelisk setBlockTextureName(String tex)
	{
		texture = tex;
		return this;
	}
	
	public String getTextureName()
	{
		return texture;
	}
	
    public Icon getIcon(IBlockAccess w, int x, int y, int z, int side)
    {
    	if(w.getBlockState(new BlockPos(x,y+1,z)).getBlock() == this && w.getBlockState(new BlockPos(x,y+2,z)).getBlock() != this && w.getBlockState(new BlockPos(x,y-1,z)).getBlock() != this)
    		return side == 0 || side == 1 ? topbotIcon : botSide;
    	
    	if(w.getBlockState(new BlockPos(x,y-1,z)).getBlock() == this && w.getBlockState(new BlockPos(x,y-2,z)).getBlock() != this && w.getBlockState(new BlockPos(x,y+1,z)).getBlock() != this)
    		return side == 0 || side == 1 ? topbotIcon : topSide;
    	
    	return genericIcon;
    }
    
    public Icon getIcon(int side, int metadata)
    {
    	return genericIcon;
    }

    public void randomDisplayTick(World w, BlockPos pos, IBlockState state, Random rnd)
    {
    	if((w.getBlockState(pos.up()).getBlock() == this && w.getBlockState(pos.up().up()).getBlock() != this && w.getBlockState(pos.down()).getBlock() != this) || (w.getBlockState(pos.down()).getBlock() == this && w.getBlockState(pos.down().down()).getBlock() != this && w.getBlockState(pos.up()).getBlock() != this))
    	{
    		int x = pos.getX();
    		int y = pos.getY();
    		int z = pos.getZ();
    		for(int i = 0; i < 10; ++i)
    		{
    			double rndY = rnd.nextDouble()*3;
    			w.spawnParticle(EnumParticleTypes.PORTAL, x+0.5D+MathUtils.randomDouble(rnd), y-3+rndY, z+0.5D+MathUtils.randomDouble(rnd), 0, rndY, 0);
    		}
    	}
    }
    
    public boolean isBed(IBlockAccess world, BlockPos pos, Entity player)
    {
    	return ((world.getBlockState(pos.up()).getBlock() == this && world.getBlockState(pos.up(2)).getBlock() != this && world.getBlockState(pos.down()).getBlock() != this) || (world.getBlockState(pos.down()).getBlock() == this && world.getBlockState(pos.down(2)).getBlock() != this && world.getBlockState(pos.up()).getBlock() != this));
    }
    
    public boolean onBlockActivated(World w, BlockPos pos, IBlockState state, EntityPlayer p, EnumFacing side, float hitX, float hitY, float hitZ)
    {
    	if((w.getBlockState(pos.up()).getBlock() == this && w.getBlockState(pos.up(2)).getBlock() != this && w.getBlockState(pos.down()).getBlock() != this) || (w.getBlockState(pos.down()).getBlock() == this && w.getBlockState(pos.down(2)).getBlock() != this && w.getBlockState(pos.up()).getBlock() != this))
    	{
    		int x = pos.getX();
    		int y = pos.getY();
    		int z = pos.getZ();
    		w.playSound(x+0.5D, y+0.5D, z+0.5D, "portal.travel", 1, 2, false);
    		p.setSpawnChunk(new BlockPos(x,y,z), false,p.dimension);
    		if(p.worldObj.isRemote)
    			p.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("tb.txt.spawnSet")).setChatStyle(new ChatStyle().setItalic(true).setColor(EnumChatFormatting.AQUA)));
    		return true;
    	}
    	return false;
    }
    
    public BlockPos getBedSpawnPosition(IBlockAccess world, BlockPos pos, EntityPlayer player)
    {
    	int x = pos.getX();
    	int y = pos.getY();
    	int z = pos.getZ();
    	for(int dy = 0; dy > -3; --dy)
	    	for(int dx = -2; dx <= 2; ++dx)
	    	{
	        	for(int dz = -2; dz <= 2; ++dz)
	        	{
                    if (World.doesBlockHaveSolidTopSurface(world,new BlockPos(x+dx, y+dy, z+dz)) && !world.getBlockState(new BlockPos(x+dx, y+dy, z+dz)).getBlock().getMaterial().isOpaque() && !world.getBlockState(new BlockPos(x+dx, y+dy+1, z+dz)).getBlock().getMaterial().isOpaque())
                    {
                    	return new BlockPos(x+dx, y+dy+1,z+dz);
                    }
	        	}
	    	}
    	return BlockBed.getSafeExitLocation((World) world, pos, 9);
    }
    
    public void registerBlockIcons(IconRegister reg)
    {
    	genericIcon = reg.registerBlockIcon(this.getTextureName()+"cryingObsidian");
    	topbotIcon = reg.registerBlockIcon(this.getTextureName()+"cryingObelisk_TB");
    	topSide = reg.registerBlockIcon(this.getTextureName()+"cryingObelisk_sideT");
    	botSide = reg.registerBlockIcon(this.getTextureName()+"cryingObelisk_sideB");
    }

	@Override
	public List<IBlockState> listPossibleStates(Block b) {
		return Arrays.asList(this.getDefaultState());
	}

	@Override
	public int getDCRenderID() {
		return RenderAccessLibrary.RENDER_ID_CUBE;
	}

}
