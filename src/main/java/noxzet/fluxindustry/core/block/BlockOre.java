package noxzet.fluxindustry.core.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockOre extends BlockFlux {
	
	public BlockOre(String unlocalizedName, int level) {
		this(unlocalizedName);
		this.setHarvestLevel("pickaxe", level);
	}
	public BlockOre(String unlocalizedName) {
		super(Material.ROCK, unlocalizedName);
		this.setResistance(30);
		this.setHardness(5);
		this.setSoundType(SoundType.STONE);
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
	}
	
}
