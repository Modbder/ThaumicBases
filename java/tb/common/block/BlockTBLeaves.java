package tb.common.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import DummyCore.Utils.MathUtils;
import tb.core.TBCore;
import tb.init.TBBlocks;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockGravel;
import net.minecraft.block.BlockNetherrack;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSoulSand;
import net.minecraft.block.BlockStone;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;

public class BlockTBLeaves extends BlockOldLeaf{
	
	public static final String[] names = new String[]{
		"goldenOakLeaves",
		"peacefullTreeLeaves",
		"netherTreeLeaves",
		"enderTreeLeaves"
	};
	
	public static final String[] textures = new String[]{
		"goldenOak/leaves",
		"peacefullTree/leaves",
		"netherTree/leaves",
		"enderTree/leaves"
	};
	
    public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity)
    {
	    if(world.getBlockMetadata(x, y, z)%8==3)
	    	if(entity instanceof EntityDragon)
	    		return false;
    	
    	return super.canEntityDestroy(world, x, y, z, entity);
    }
    
    public boolean isFlammable(IBlockAccess world, int x, int y, int z, ForgeDirection face)
    {
    	if(world.getBlockMetadata(x, y, z)%8==2)
    		return true;
    	
    	return super.isFlammable(world, x, y, z, face);
    }
    
    public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face)
    {
    	if(world.getBlockMetadata(x, y, z)%8==2)
    		return 0;
    	
    	return super.getFlammability(world, x, y, z, face);
    }
    
    public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face)
    {
    	if(world.getBlockMetadata(x, y, z)%8==2)
    		return 0;
    	
    	return super.getFlammability(world, x, y, z, face);
    }
    
    public boolean isFireSource(World world, int x, int y, int z, ForgeDirection side)
    {
    	if(world.getBlockMetadata(x, y, z)%8==2)
    		return true;
    	
    	return super.isFireSource(world, x, y, z, side);
    }
	
	public static IIcon[] icons = new IIcon[names.length];

    @SideOnly(Side.CLIENT)
    public int getBlockColor()
    {
    	return 0xffffff;
    }
    
    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    public int getLightValue(IBlockAccess world, int x, int y, int z)
    {
    	if(world.getBlockMetadata(x, y, z)%8 != 3)
    		return super.getLightValue(world, x, y, z);
    	else
    		return 11;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess w, int x, int y, int z, int meta)
    {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World w, int x, int y, int z, Random rnd)
    {
    	super.randomDisplayTick(w, x, y, z, rnd);
    	
    	if(w.getBlockMetadata(x, y, z)%8 == 0)
    		w.spawnParticle("reddust", x+rnd.nextDouble(), y+rnd.nextDouble(), z+rnd.nextDouble(), 1, 1, 0);
    	
    	if(w.getBlockMetadata(x, y, z)%8 == 1 && rnd.nextFloat() <= 0.01F)
    		w.spawnParticle("heart", x+rnd.nextDouble(), y+rnd.nextDouble(), z+rnd.nextDouble(), 0, 10, 0);
    	
    	if(w.getBlockMetadata(x, y, z)%8 == 2)
    	{
    		if(w.isAirBlock(x, y-1, z))
    			w.spawnParticle("dripLava", x+rnd.nextDouble(), y, z+rnd.nextDouble(), 0, 0, 0);
    	}
    	
    	if(w.getBlockMetadata(x, y, z)%8 == 3)
    		w.spawnParticle("portal", x+rnd.nextDouble(), y+rnd.nextDouble(), z+rnd.nextDouble(), MathUtils.randomDouble(rnd), MathUtils.randomDouble(rnd), MathUtils.randomDouble(rnd));
    }
    
    public Item getItemDropped(int meta, Random rnd, int fortune)
    {
        return Item.getItemFromBlock(TBBlocks.sapling);
    }
	
    @SideOnly(Side.CLIENT)
    public int getRenderColor(int p_149741_1_)
    {
    	return 0xffffff;
    }
    
    public void updateTick(World w, int x, int y, int z, Random rnd)
    {
    	super.updateTick(w, x, y, z, rnd);
    	if(w.getBlockMetadata(x, y, z)%8 == 1)
    	{
    		if(rnd.nextDouble() > 0.03D)
    			return;
    		
    		int dy = y;
    		BiomeGenBase base = w.getBiomeGenForCoords(x, z);
    		if(base != null)
    		{
    			@SuppressWarnings("unchecked")
				List<SpawnListEntry> l = base.getSpawnableList(EnumCreatureType.creature);
    			if(l != null && !l.isEmpty())
    			{
    				SpawnListEntry entry = l.get(rnd.nextInt(l.size()));
    				if(entry != null && entry.entityClass != null)
    				{
    					Class<?> c = entry.entityClass;
    					if(EntityLiving.class.isAssignableFrom(c))
    					{
    						try
    						{
	    						EntityLiving el = EntityLiving.class.cast(c.getConstructor(World.class).newInstance(w));
	    			    		while(--y >= dy-6)
	    			    		{
	    			    			el.setPositionAndRotation(x+0.5D, y, z+0.5D, 0, 0);
	    			    			if(el.getCanSpawnHere())
	    			    			{
	    			    				w.spawnEntityInWorld(el);
	    			    				break;
	    			    			}else
	    			    				continue;
	    			    		}
    						}catch(Exception e)
    						{
    							FMLLog.warning("[TB]Tried to create an entity of class "+c+" but failed! The exception is listed below:",new Object[0]);
    							e.printStackTrace();
    						}
    					}
    				}
    			}
    		}
    	}
    	if(w.getBlockMetadata(x, y, z)%8 == 2)
    	{
    		int dy = y;
    		while(--y >= dy-8)
    		{
    			Block b = w.getBlock(x, y, z);
    			if(!b.isAir(w, x, y, z))
    			{
    				boolean netheric = b instanceof BlockNetherrack || b instanceof BlockSoulSand || b == Blocks.quartz_ore;
    				if(netheric && rnd.nextDouble() <= 0.05D)
    				{
    					BiomeGenBase hellBiome = BiomeGenBase.hell;
    	    			@SuppressWarnings("unchecked")
    					List<SpawnListEntry> l = rnd.nextBoolean() ? hellBiome.getSpawnableList(EnumCreatureType.creature) : hellBiome.getSpawnableList(EnumCreatureType.monster);
    	    			if(l != null && !l.isEmpty())
    	    			{
    	    				SpawnListEntry entry = l.get(rnd.nextInt(l.size()));
    	    				if(entry != null && entry.entityClass != null)
    	    				{
    	    					Class<?> c = entry.entityClass;
    	    					if(EntityLiving.class.isAssignableFrom(c))
    	    					{
    	    						try
    	    						{
    		    						EntityLiving el = EntityLiving.class.cast(c.getConstructor(World.class).newInstance(w));
    		    						
    		    			    		el.setPositionAndRotation(x+0.5D, y+1, z+0.5D, 0, 0);
    		    			    		el.onSpawnWithEgg(null);
    		    			    		
    		    			    		if(el.getCanSpawnHere())
    		    			    		{
    		    			    			w.spawnEntityInWorld(el);
    		    			    			break;
    		    			    		}
    		    			    		
    	    						}catch(Exception e)
    	    						{
    	    							FMLLog.warning("[TB]Tried to create an entity of class "+c+" but failed! The exception is listed below:",new Object[0]);
    	    							e.printStackTrace();
    	    						}
    	    					}
    	    				}
    	    			}
    	    			
    					break;
    				}
    				else
    				{
	    				boolean flag = b instanceof BlockDirt || b instanceof BlockGrass || b instanceof BlockGravel || b instanceof BlockSand || b instanceof BlockStone;
	    				if(!flag)
	    				{
	    					ItemStack stk = new ItemStack(b,1,w.getBlockMetadata(x, y, z));
	    					if(OreDictionary.getOreIDs(stk) != null && OreDictionary.getOreIDs(stk).length > 0)
	    					{
	    						OreDict:for(int i = 0; i < OreDictionary.getOreIDs(stk).length; ++i)
	    						{
	    							int id = OreDictionary.getOreIDs(stk)[i];
	    							if(id != -1)
	    							{
	    								String ore = OreDictionary.getOreName(id);
	    								if(ore != null && ! ore.isEmpty())
	    								{
	    									flag = ore.contains("dirt") || ore.contains("grass") || ore.contains("sand") || ore.contains("gravel") || ore.contains("stone");
	    									if(flag)
	    										break OreDict;
	    								}
	    							}
	    						}
	    					}
	    				}
	    				if(flag)
	    				{
	    					double random = rnd.nextDouble();
	    					Block setTo = random <= 0.6D ? Blocks.netherrack : random <= 0.9D ? Blocks.soul_sand : Blocks.quartz_ore;
	    					w.setBlock(x, y, z, setTo, 0, 3);
	    					break;
	    				}
    				}
    			}
    		}
    	}
    	if(w.getBlockMetadata(x, y, z)%8 == 3)
    	{
    		int dy = y;
    		while(--y >= dy-11)
    		{
    			Block b = w.getBlock(x, y, z);
    			if(!b.isAir(w, x, y, z))
    			{
    				boolean end = b == Blocks.end_stone || b instanceof BlockObsidian;
    				if(end && rnd.nextDouble() <= 0.02D)
    				{
    					BiomeGenBase hellBiome = BiomeGenBase.sky;
    	    			@SuppressWarnings("unchecked")
    					List<SpawnListEntry> l = rnd.nextBoolean() ? hellBiome.getSpawnableList(EnumCreatureType.creature) : hellBiome.getSpawnableList(EnumCreatureType.monster);
    					if(l != null && !l.isEmpty())
    	    			{
    	    				SpawnListEntry entry = l.get(rnd.nextInt(l.size()));
    	    				if(entry != null && entry.entityClass != null)
    	    				{
    	    					Class<?> c = entry.entityClass;
    	    					if(EntityLiving.class.isAssignableFrom(c))
    	    					{
    	    						try
    	    						{
    		    						EntityLiving el = EntityLiving.class.cast(c.getConstructor(World.class).newInstance(w));
    		    						
    		    			    		el.setPositionAndRotation(x+0.5D, y+1, z+0.5D, 0, 0);
    		    			    		el.onSpawnWithEgg(null);
    		    			    		
    		    			    		if(w.isAirBlock(x, y+1, z))
    		    			    		{
    		    			    			w.spawnEntityInWorld(el);
    		    			    			break;
    		    			    		}
    		    			    		
    	    						}catch(Exception e)
    	    						{
    	    							FMLLog.warning("[TB]Tried to create an entity of class "+c+" but failed! The exception is listed below:",new Object[0]);
    	    							e.printStackTrace();
    	    						}
    	    					}
    	    				}
    	    			}
    	    			
    					break;
    				}
    				else
    				{
	    				boolean flag = b instanceof BlockDirt || b instanceof BlockGrass || b instanceof BlockGravel || b instanceof BlockSand || b instanceof BlockStone && !(b instanceof BlockObsidian) && !(b == Blocks.end_stone);
	    				if(!flag)
	    				{
	    					ItemStack stk = new ItemStack(b,1,w.getBlockMetadata(x, y, z));
	    					if(OreDictionary.getOreIDs(stk) != null && OreDictionary.getOreIDs(stk).length > 0)
	    					{
	    						OreDict:for(int i = 0; i < OreDictionary.getOreIDs(stk).length; ++i)
	    						{
	    							int id = OreDictionary.getOreIDs(stk)[i];
	    							if(id != -1)
	    							{
	    								String ore = OreDictionary.getOreName(id);
	    								if(ore != null && ! ore.isEmpty())
	    								{
	    									flag = ore.contains("dirt") || ore.contains("grass") || ore.contains("sand") || ore.contains("gravel") || ore.contains("stone");
	    									if(flag)
	    										break OreDict;
	    								}
	    							}
	    						}
	    					}
	    				}
	    				if(flag)
	    				{
	    					double random = rnd.nextDouble();
	    					Block setTo = random <= 0.9D ? Blocks.end_stone : Blocks.obsidian;
	    					w.setBlock(x, y, z, setTo, 0, 3);
	    					break;
	    				}
    				}
    			}
    		}
    	}
    }
    
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess p_149720_1_, int p_149720_2_, int p_149720_3_, int p_149720_4_)
    {
    	return 0xffffff;
    }
    
    //getSaplingDropRate
    protected int func_150123_b(int meta)
    {
        return meta == 0 ? 50 : 30;
    }
    
    //dropRareItem
    protected void func_150124_c(World w, int x, int y, int z, int meta, int chance)
    {
        if (meta == 0 && w.rand.nextInt(chance) == 0)
        {
            this.dropBlockAsItem(w, x, y, z, new ItemStack(Items.golden_apple, 1, 0));
        }
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
    	return icons[meta % 8];
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item i, CreativeTabs p_149666_2_, List p_149666_3_)
    {
    	for(int f = 0; f < names.length; ++f)
    		p_149666_3_.add(new ItemStack(i,1,f));
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg)
    {
    	for(int i = 0; i < icons.length; ++i)
    		icons[i] = reg.registerIcon(TBCore.modid+":"+textures[i]);
    	
    	blockIcon = reg.registerIcon(getTextureName());
    }
    
    @Override
    public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, int x, int y, int z, int fortune)
    {
    	if(world.getBlockMetadata(x, y, z)%8 == 1)
    		return new ArrayList<ItemStack>();
    	else
    		return super.onSheared(item, world, x, y, z, fortune);
    }
    
}
