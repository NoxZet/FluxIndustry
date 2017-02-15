package noxzet.fluxindustry.core.world;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;
import noxzet.fluxindustry.core.block.BlockOreBasic;
import noxzet.fluxindustry.core.block.FluxBlocks;

public class FluxGenOverworld implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		if (world.provider.getDimension() == 0)
		{
			generateOverworld(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
		}
	}

	private void generateOverworld(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
	{
		generateOre(FluxBlocks.oreBasic.getDefaultState().withProperty(BlockOreBasic.VARIANT, BlockOreBasic.COPPER), world, random, chunkX*16, chunkZ*16, 5, 96, 10, 16);
		generateOre(FluxBlocks.oreBasic.getDefaultState().withProperty(BlockOreBasic.VARIANT, BlockOreBasic.TIN), world, random, chunkX*16, chunkZ*16, 5, 96, 7, 15);
		generateOre(FluxBlocks.oreBasic.getDefaultState().withProperty(BlockOreBasic.VARIANT, BlockOreBasic.ZINC), world, random, chunkX*16, chunkZ*16, 5, 60, 11, 9);
		generateOre(FluxBlocks.oreBasic.getDefaultState().withProperty(BlockOreBasic.VARIANT, BlockOreBasic.ALUMINUM), world, random, chunkX*16, chunkZ*16, 5, 75, 8, 10);
		generateOre(FluxBlocks.oreBasic.getDefaultState().withProperty(BlockOreBasic.VARIANT, BlockOreBasic.LEAD), world, random, chunkX*16, chunkZ*16, 20, 64, 6, 6);
		generateOreCustom(FluxBlocks.oreBasic.getDefaultState().withProperty(BlockOreBasic.VARIANT, BlockOreBasic.URANIUM), world, random, chunkX*16, chunkZ*16, 5, 37, 2, 3);
	}
	
	private void generateOre(IBlockState ore, World world, Random random, int x, int z, int minY, int maxY, int maxSize, int chance)
	{
		int rangeY = maxY-minY;
		for (int i = 0; i < chance; i++)
		{
			WorldGenMinable generator = new WorldGenMinable(ore, (int)Math.round(((float)maxSize)*0.75)+random.nextInt((int)Math.round(((float)maxSize)*0.5)));
			BlockPos pos = new BlockPos(x + random.nextInt(16), minY + random.nextInt(rangeY), z + random.nextInt(16));
			generator.generate(world, random, pos);
		}
	}
	
	private void generateOreCustom(IBlockState ore, World world, Random random, int x, int z, int minY, int maxY, int maxSize, int chance)
	{
		int rangeY = maxY-minY;
		for (int e, i = 0; i < chance; i++)
		{
			int ct = (int)Math.round(((float)maxSize)*0.75)+random.nextInt((int)Math.round(((float)maxSize)*0.5));
			BlockPos pos = new BlockPos(x + random.nextInt(16), minY + random.nextInt(rangeY), z + random.nextInt(16));
			if (world.getBlockState(pos).getBlock()==Blocks.STONE || world.getBlockState(pos).getBlock()==Blocks.DIRT || world.getBlockState(pos).getBlock()==Blocks.GRAVEL)
				world.setBlockState(pos, ore);
			for(e = 1; e < maxSize; e++)
			{
				BlockPos pos2 = pos.offset(EnumFacing.random(random));
				if (world.getBlockState(pos2).getBlock()==Blocks.STONE || world.getBlockState(pos2).getBlock()==Blocks.DIRT || world.getBlockState(pos).getBlock()==Blocks.GRAVEL)
					world.setBlockState(pos2, ore);
			}
		}
	}
	
}
