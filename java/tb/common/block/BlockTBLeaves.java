package tb.common.block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import DummyCore.Client.Icon;
import DummyCore.Client.IconRegister;
import DummyCore.Client.RenderAccessLibrary;
import DummyCore.Utils.BlockStateMetadata;
import DummyCore.Utils.IOldCubicBlock;
import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MetadataBasedMethodsHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockGravel;
import net.minecraft.block.BlockNetherrack;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSoulSand;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import tb.core.TBCore;
import tb.init.TBBlocks;

public class BlockTBLeaves extends Block implements IOldCubicBlock, IShearable{

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
	
	public static Icon[] icons = new Icon[names.length];
	
	public BlockTBLeaves() {
		super(Material.leaves);
		this.setLightOpacity(1);
        this.setTickRandomly(true);
        this.setHardness(0.2F);
        this.setStepSound(soundTypeGrass);
	}
	
    public boolean canEntityDestroy(IBlockAccess world, BlockPos pos, Entity entity)
    {
	    if(BlockStateMetadata.getBlockMetadata(world, pos)%8==3)
	    	if(entity instanceof EntityDragon)
	    		return false;
    	
    	return super.canEntityDestroy(world, pos, entity);
    }
    
    public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face)
    {
    	if(BlockStateMetadata.getBlockMetadata(world, pos)%8==2)
    		return true;
    	
    	return super.isFlammable(world, pos, face);
    }
    
    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face)
    {
    	if(BlockStateMetadata.getBlockMetadata(world, pos)%8==2)
    		return 0;
    	
    	return super.getFlammability(world, pos, face);
    }
    
    public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face)
    {
    	if(BlockStateMetadata.getBlockMetadata(world, pos)%8==2)
    		return 0;
    	
    	return super.getFlammability(world, pos, face);
    }
    
    public boolean isFireSource(World world, BlockPos pos, EnumFacing side)
    {
    	if(BlockStateMetadata.getBlockMetadata(world, pos)%8==2)
    		return true;
    	
    	return super.isFireSource(world, pos, side);
    }
	
    public boolean isFullCube()
    {
        return false;
    }
    
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    public EnumWorldBlockLayer getBlockLayer()
    {
    	return EnumWorldBlockLayer.CUTOUT_MIPPED;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
    {
    	return true;
    }

	@Override
	public Icon getIcon(int side, int meta) {
		return icons[Math.min(icons.length-1, meta % 8)];
	}
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void getSubBlocks(Item i, CreativeTabs p_149666_2_, List p_149666_3_)
    {
    	for(int f = 0; f < names.length; ++f)
    		p_149666_3_.add(new ItemStack(i,1,f));
    }
    
	@Override
	public Icon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		return getIcon(side,BlockStateMetadata.getBlockMetadata(world, x, y, z));
	}
	
	
    public int damageDropped(IBlockState state)
    {
    	return BlockStateMetadata.getMetaFromState(state) % 8;
    }
	
    public IBlockState getStateFromMeta(int meta)
    {
    	return this.getDefaultState().withProperty(BlockStateMetadata.METADATA, meta);
    }
    
    public int getMetaFromState(IBlockState state)
    {
    	return BlockStateMetadata.getMetaFromState(state);
    }

    protected BlockState createBlockState()
    {
    	return new BlockState(this,BlockStateMetadata.METADATA);
    }

	@Override
	public List<IBlockState> listPossibleStates(Block b) {
		ArrayList<IBlockState> retLst = new ArrayList<IBlockState>();
		for(int i = 0; i < names.length; ++i)
		{
			retLst.add(getStateFromMeta(i));
			retLst.add(getStateFromMeta(i+8));
		}
		return retLst;
	}

	@Override
	public void registerBlockIcons(IconRegister ir) {
    	for(int i = 0; i < icons.length; ++i)
    		icons[i] = ir.registerBlockIcon(TBCore.modid+":"+textures[i]);
	}

	@Override
	public int getDCRenderID() {
		return RenderAccessLibrary.RENDER_ID_CUBE_AND_CROSS;
	}
	
    public void dropApple(World worldIn, BlockPos pos, IBlockState state, int chance)
    {
        if (BlockStateMetadata.getMetaFromState(state) == 0 && worldIn.rand.nextInt(chance) == 0)
            spawnAsEntity(worldIn, pos, new ItemStack(Items.golden_apple, 1, 0));
    }
    
    public int getSaplingDropChance(IBlockState state)
    {
    	return BlockStateMetadata.getMetaFromState(state) == 0 ? 50 : 30;
    }

    public ItemStack createStackedBlock(IBlockState state)
    {
    	return new ItemStack(state.getBlock(),1,BlockStateMetadata.getMetaFromState(state)%8);
    }
    
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te)
    {
        if (!worldIn.isRemote && player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == Items.shears)
            player.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
        else
            super.harvestBlock(worldIn, player, pos, state, te);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune)
    {
        return new ArrayList(Arrays.asList(new ItemStack(this, 1, BlockStateMetadata.getBlockMetadata(world, pos) % 8)));
    }

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
		return true;
	}
	
	@Override
	public boolean isLeaves(IBlockAccess world, BlockPos pos)
	{
		return true; 
	}
	
    @Override
    public void beginLeavesDecay(World world, BlockPos pos)
    {
    	int meta = BlockStateMetadata.getBlockMetadata(world, pos) % 8;
    	world.setBlockState(pos, getStateFromMeta(meta + 8));
    }
    
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
    	MetadataBasedMethodsHelper.breakLeaves(worldIn, pos, state);
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (worldIn.canLightningStrike(pos.up()) && !World.doesBlockHaveSolidTopSurface(worldIn, pos.down()) && rand.nextInt(15) == 1)
        {
            double d0 = pos.getX() + rand.nextFloat();
            double d1 = pos.getY() - 0.05D;
            double d2 = pos.getZ() + rand.nextFloat();
            worldIn.spawnParticle(EnumParticleTypes.DRIP_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
        }
        
        World w = worldIn;
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        Random rnd = rand;
    	
    	if(BlockStateMetadata.getBlockMetadata(worldIn, pos)%8 == 0)
    		w.spawnParticle(EnumParticleTypes.REDSTONE, x+rnd.nextDouble(), y+rnd.nextDouble(), z+rnd.nextDouble(), 1, 1, 0);
    	
    	if(BlockStateMetadata.getBlockMetadata(worldIn, pos)%8 == 1 && rnd.nextFloat() <= 0.01F)
    		w.spawnParticle(EnumParticleTypes.HEART, x+rnd.nextDouble(), y+rnd.nextDouble(), z+rnd.nextDouble(), 0, 10, 0);
    	
    	if(BlockStateMetadata.getBlockMetadata(worldIn, pos)%8 == 2)
    	{
    		if(w.isAirBlock(new BlockPos(x, y-1, z)))
    			w.spawnParticle(EnumParticleTypes.DRIP_LAVA, x+rnd.nextDouble(), y, z+rnd.nextDouble(), 0, 0, 0);
    	}
    	
    	if(BlockStateMetadata.getBlockMetadata(worldIn, pos)%8 == 3)
    		w.spawnParticle(EnumParticleTypes.PORTAL, x+rnd.nextDouble(), y+rnd.nextDouble(), z+rnd.nextDouble(), MathUtils.randomDouble(rnd), MathUtils.randomDouble(rnd), MathUtils.randomDouble(rnd));
    }
    
    public void spawnAction(World w, Random rnd, BlockPos pos)
    {
    	int x = pos.getX();
    	int y = pos.getY();
    	int z = pos.getZ();
    	if(BlockStateMetadata.getBlockMetadata(w,pos)%8 == 1)
    	{
    		if(rnd.nextDouble() > 0.03D)
    			return;
    		
    		int dy = y;
    		BiomeGenBase base = w.getBiomeGenForCoords(new BlockPos(x,0, z));
    		if(base != null)
    		{
    			@SuppressWarnings("unchecked")
				List<SpawnListEntry> l = base.getSpawnableList(EnumCreatureType.CREATURE);
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
	    			    			}
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
    	if(BlockStateMetadata.getBlockMetadata(w,pos)%8 == 2)
    	{
    		int dy = y;
    		while(--y >= dy-8)
    		{
    			Block b = w.getBlockState(new BlockPos(x,y,z)).getBlock();
    			if(!b.isAir(w, new BlockPos(x, y, z)))
    			{
    				boolean netheric = b instanceof BlockNetherrack || b instanceof BlockSoulSand || b == Blocks.quartz_ore;
    				if(netheric && rnd.nextDouble() <= 0.05D)
    				{
    					BiomeGenBase hellBiome = BiomeGenBase.hell;
    	    			@SuppressWarnings("unchecked")
    					List<SpawnListEntry> l = rnd.nextBoolean() ? hellBiome.getSpawnableList(EnumCreatureType.CREATURE) : hellBiome.getSpawnableList(EnumCreatureType.MONSTER);
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
    		    			    		el.onInitialSpawn(w.getDifficultyForLocation(new BlockPos(el)), null);
    		    			    		
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
					boolean flag = b instanceof BlockDirt || b instanceof BlockGrass || b instanceof BlockGravel || b instanceof BlockSand || b instanceof BlockStone;
					if(!flag)
					{
						ItemStack stk = new ItemStack(b,1,BlockStateMetadata.getBlockMetadata(w,pos));
						if(stk!=null&&stk.getItem()!=null)
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
						w.setBlockState(new BlockPos(x, y, z), setTo.getDefaultState());
						break;
					}
    			}
    		}
    	}
    	if(BlockStateMetadata.getBlockMetadata(w,pos)%8 == 3)
    	{
    		int dy = y;
    		while(--y >= dy-11)
    		{
    			Block b = w.getBlockState(new BlockPos(x, y, z)).getBlock();
    			if(!b.isAir(w, new BlockPos(x, y, z)))
    			{
    				boolean end = b == Blocks.end_stone || b instanceof BlockObsidian;
    				if(end && rnd.nextDouble() <= 0.02D)
    				{
    					BiomeGenBase hellBiome = BiomeGenBase.sky;
    	    			@SuppressWarnings("unchecked")
    					List<SpawnListEntry> l = rnd.nextBoolean() ? hellBiome.getSpawnableList(EnumCreatureType.CREATURE) : hellBiome.getSpawnableList(EnumCreatureType.MONSTER);
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
    		    			    		el.onInitialSpawn(w.getDifficultyForLocation(new BlockPos(el)), null);
    		    			    		
    		    			    		if(w.isAirBlock(new BlockPos(x, y+1, z)))
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
					boolean flag = b instanceof BlockDirt || b instanceof BlockGrass || b instanceof BlockGravel || b instanceof BlockSand || b instanceof BlockStone && !(b instanceof BlockObsidian) && !(b == Blocks.end_stone);
					if(!flag)
					{
						ItemStack stk = new ItemStack(b,1,BlockStateMetadata.getBlockMetadata(w,pos));
						if(stk!=null&&stk.getItem()!=null)
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
						w.setBlockState(new BlockPos(x, y, z), setTo.getDefaultState());
						break;
					}
    			}
    		}
    	}
    }
    
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
    	MetadataBasedMethodsHelper.leavesDecayTick(worldIn, pos, state, rand);
    	spawnAction(worldIn, rand, pos);
    }
    
    public int quantityDropped(Random random)
    {
        return random.nextInt(20) == 0 ? 1 : 0;
    }
    
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune)
    {
        super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
    }
    
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(TBBlocks.sapling);
    }
    
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        List<ItemStack> ret = new ArrayList<ItemStack>();
        Random rand = world instanceof World ? ((World)world).rand : new Random();
        int chance = this.getSaplingDropChance(state);

        if (fortune > 0)
        {
            chance -= 2 << fortune;
            if (chance < 10) chance = 10;
        }

        if (rand.nextInt(chance) == 0)
            ret.add(new ItemStack(getItemDropped(state, rand, fortune), 1, damageDropped(state)));

        chance = 200;
        if (fortune > 0)
        {
            chance -= 10 << fortune;
            if (chance < 40) chance = 40;
        }

        this.captureDrops(true);
        if (world instanceof World)
            this.dropApple((World)world, pos, state, chance); // Dammet mojang
        ret.addAll(this.captureDrops(false));
        return ret;
    }
}
