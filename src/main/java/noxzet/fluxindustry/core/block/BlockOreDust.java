package noxzet.fluxindustry.core.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockOreDust extends BlockOre {
	
	private int min, max, damage;
	private Item item;
	
	public BlockOreDust(String unlocalizedName, int level, Item item, int min, int max, int damage)
	{
		super(unlocalizedName, level);
		this.min = min;
		this.max = max;
		this.item = item;
		this.damage = damage;
	}
	
	public BlockOreDust(String unlocalizedName, int level, Item item, int min, int max)
	{
		this(unlocalizedName, level, item, min, max, 0);
	}
	
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		List<ItemStack> list = new ArrayList<ItemStack>();
		list.add(new ItemStack(item, min + (new Random()).nextInt(1 + max - min)));
		return list;
	}
}
