package noxzet.fluxindustry.core.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import noxzet.fluxindustry.core.block.IBlockFluxMulti;

public class ItemBlockMulti extends ItemBlock {

	IBlockFluxMulti blockOfThis;
	
	public ItemBlockMulti(IBlockFluxMulti block) {
		super((Block) block);
		super.setHasSubtypes(true);
		this.blockOfThis = block;
	}
	
	@Override
    public int getMetadata(int meta)
    {
        return meta;
    }
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return blockOfThis.getUnlocalizedNameFromMeta(stack.getMetadata());
	}
	
}
