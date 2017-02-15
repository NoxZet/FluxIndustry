package noxzet.fluxindustry.core.block;

import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import noxzet.fluxindustry.core.block.electric.BlockElectricCrusher;
import noxzet.fluxindustry.core.block.electric.BlockElectricFurnace;
import noxzet.fluxindustry.core.block.electric.BlockFluxCable;
import noxzet.fluxindustry.core.block.electric.BlockGeneratorCoal;
import noxzet.fluxindustry.core.item.FluxCreativeTabs;
import noxzet.fluxindustry.core.item.ItemBlockMulti;

public class FluxBlocks {
	
	public static BlockFlux oreBasic, blockMetal, blockMachine, treetapBlock, fluxCable,
			generatorCoal, electricFurnace, electricCrusher;
	
	public static void init()
	{
		oreBasic = register(new BlockOreBasic("ore_basic"));
		blockMetal = register(new BlockMetalBasic("block_metal"));
		blockMachine = register(new BlockMachine("block_machine"));
		treetapBlock = registerWithoutItemBlock(new BlockTreetap("treetap_block"));
		fluxCable = registerWithoutItemBlock(new BlockFluxCable("flux_cable"));
		generatorCoal = register(new BlockGeneratorCoal("generator_coal"));
		electricFurnace = register(new BlockElectricFurnace("electric_furnace"));
		electricCrusher = register(new BlockElectricCrusher("electric_crusher"));
	}
	
	public static void postInit()
	{
		oreBasic.setCreativeTab(FluxCreativeTabs.FIBLOCKS);
		blockMetal.setCreativeTab(FluxCreativeTabs.FIBLOCKS);
		blockMachine.setCreativeTab(FluxCreativeTabs.FIBLOCKS).setHarvestLevel("pickaxe", 1);
		treetapBlock.setCreativeTab(FluxCreativeTabs.FIBLOCKS);
		generatorCoal.setCreativeTab(FluxCreativeTabs.FIMACHINES);
		electricFurnace.setCreativeTab(FluxCreativeTabs.FIMACHINES);
		electricCrusher.setCreativeTab(FluxCreativeTabs.FIMACHINES);
	}
	
	public static BlockFlux register (BlockFlux block, ItemBlock itemBlock)
	{
		GameRegistry.register(block);
		GameRegistry.register(itemBlock);
		block.registerItemModel(itemBlock);
		if (block instanceof ITileEntityClassProvider)
			GameRegistry.registerTileEntity(((ITileEntityClassProvider)block).getTileEntityClass(), block.getRegistryName().toString());
		return block;
	}
	
	public static BlockFlux register (BlockFlux block)
	{
		ItemBlock itemBlock;
		if (block instanceof IBlockFluxMulti)
			itemBlock = new ItemBlockMulti((IBlockFluxMulti) block);
		else
			itemBlock = new ItemBlock(block);
		itemBlock.setRegistryName(block.getRegistryName());
		return register(block, itemBlock);
	}
	
	public static BlockFlux registerWithoutItemBlock (BlockFlux block)
	{
		GameRegistry.register(block);
		if (block instanceof ITileEntityClassProvider)
			GameRegistry.registerTileEntity(((ITileEntityClassProvider)block).getTileEntityClass(), block.getRegistryName().toString());
		return block;
	}
	
}
