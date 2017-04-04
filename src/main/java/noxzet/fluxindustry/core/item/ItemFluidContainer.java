package noxzet.fluxindustry.core.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noxzet.fluxindustry.api.fluid.IFluxIndustryFluidItem;
import noxzet.fluxindustry.core.FluxIndustry;
import noxzet.fluxindustry.core.FluxUtils;

public class ItemFluidContainer extends ItemFlux implements IFluxIndustryFluidItem {
	
	public int capacity;
	public List<ItemStack> subItems = new ArrayList<>();
	
	public ItemFluidContainer(String unlocalizedName)
	{
		super(unlocalizedName);
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
		this.setContainerItem(this);
		capacity = Fluid.BUCKET_VOLUME;
		subItems.add(new ItemStack(this, 1, 0));
	}
	
	@Override
	public void registerItemModel()
	{
		super.registerItemModel();
		this.registerColorHandler();
	}
	
	public ItemFluidContainer registerAllFluids()
	{
		ItemStack currentStack;
		NBTTagCompound compound, totalCompound;
		for (String fluid : FluidRegistry.getRegisteredFluids().keySet())
		{
			compound = new NBTTagCompound();
			compound.setString("FluidName", fluid);
			compound.setInteger("Amount", capacity);
			totalCompound = new NBTTagCompound();
			totalCompound.setTag("FluidStack", compound);
			currentStack = new ItemStack(this, 1, 0);
			currentStack.setTagCompound(totalCompound);
			subItems.add(currentStack);
		}
		return this;
	}
	
	@Override
    public void getSubItems(Item item, CreativeTabs tab, NonNullList<ItemStack> subItems)
    {
		for (ItemStack stack : this.subItems)
			subItems.add(stack);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemstack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		if (itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey("FluidStack"))
		{
			FluidStack fluidstack = FluidStack.loadFluidStackFromNBT((NBTTagCompound)itemstack.getTagCompound().getTag("FluidStack"));
			if (fluidstack == null)
			{
				tooltip.add(I18n.translateToLocal("item."+FluxIndustry.MODID+".tip.fluid_invalid"));
				return;
			}
			tooltip.add(fluidstack.getLocalizedName());
			tooltip.add(fluidstack.amount + "/" + this.capacity + " mB");
			return;
		}
		tooltip.add(I18n.translateToLocal("item."+FluxIndustry.MODID+".tip.empty"));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getColor(ItemStack stack, int tintIndex)
	{
		if (tintIndex == 1)
		{
			if (stack.hasTagCompound() && stack.getTagCompound().hasKey("FluidStack"))
			{
				String fluidName = ((NBTTagCompound)stack.getTagCompound().getTag("FluidStack")).getString("FluidName");
				return FluxUtils.getFluidColor(fluidName);
			}
		}
		return 0xFFFFFF;
	}
	
	/*@Override
	public ICapabilityProvider initCapabilities (ItemStack stack, NBTTagCompound nbt)
	{
		if (stack != null && stack.getItem() == this && stack.hasTagCompound())
		{
			return new FluxItemFluidContainer(stack, capacity, new ItemStack(this, 1, 0));
		}
		return null;
	}*/

	private FluidStack loadFromItem(ItemStack itemstack)
	{
		if (itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey("FluidStack"))
		{
			return FluidStack.loadFluidStackFromNBT((NBTTagCompound) itemstack.getTagCompound().getTag("FluidStack"));
		}
		return null;
	}
	
	private void saveToItem(ItemStack itemstack, FluidStack fluidstack)
	{
		NBTTagCompound compound;
		if (itemstack.getTagCompound() != null)
			compound = itemstack.getTagCompound();
		else
			compound = new NBTTagCompound();
		if (fluidstack == null || fluidstack.amount == 0)
			compound.removeTag("FluidStack");
		else
			compound.setTag("FluidStack", fluidstack.writeToNBT(new NBTTagCompound()));
	}
	
	@Override
	public int fluxFluidFill(ItemStack itemstack, FluidStack fluid, boolean simulate)
	{
		FluidStack fluidstack = loadFromItem(itemstack);
		if (fluidstack != null && fluid != null && fluidstack.getFluid() == fluid.getFluid())
		{
			int filledAmount = Math.min(fluid.amount, this.capacity - fluidstack.amount);
			if (!simulate && filledAmount > 0)
			{
				fluidstack.amount += filledAmount;
				this.saveToItem(itemstack, fluidstack);
			}
			return filledAmount;
		}
		return 0;
	}
	
	private FluidStack fluxFluidDrain(ItemStack itemstack, FluidStack fluidstack, int fluid, boolean simulate)
	{
		int drainedAmount = Math.min(fluid, fluidstack.amount);
		if (drainedAmount > 0)
		{
			if (!simulate)
			{
				fluidstack.amount -= drainedAmount;
				this.saveToItem(itemstack, fluidstack);
			}
			return new FluidStack(fluidstack.getFluid(), drainedAmount);
		}
		return null;
	}

	@Override
	public FluidStack fluxFluidDrain(ItemStack itemstack, FluidStack fluid, boolean simulate)
	{
		FluidStack fluidstack = loadFromItem(itemstack);
		if (fluidstack != null && fluid != null && fluidstack.getFluid() == fluid.getFluid())
		{
			return fluxFluidDrain(itemstack, fluidstack, fluid.amount, simulate);
		}
		return null;
	}

	@Override
	public FluidStack fluxFluidDrain(ItemStack itemstack, int fluid, boolean simulate)
	{
		FluidStack fluidstack = loadFromItem(itemstack);
		if (fluidstack != null)
		{
			return fluxFluidDrain(itemstack, fluidstack, fluid, simulate);
		}
		return null;
	}
	
}
