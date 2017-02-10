package noxzet.fluxindustry.core.item;

import java.util.List;

import cofh.api.energy.IEnergyContainerItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noxzet.fluxindustry.core.FluxIndustry;
import noxzet.fluxindustry.core.block.electric.BlockFluxCable.CableVariant;
import noxzet.fluxindustry.core.energy.FluxBatteryProvider;

public class ItemFluxBattery extends ItemFlux implements IEnergyContainerItem {

	public String group;
	public int[] capacity;
	public short[] capacityGrade;
	public String[] name;
	public boolean[] chargeable;
	
	public ItemFluxBattery(String unlocalizedName)
	{
		super(unlocalizedName);
	}
	
	public ItemFluxBattery(String unlocalizedName, String group, int[] capacity, short[] capacityGrade, String[] name, boolean[] chargeable)
	{
		this(unlocalizedName);
		this.group = group;
		this.capacity = capacity;
		this.capacityGrade = capacityGrade;
		this.name = name;
		this.chargeable = chargeable;
	}
	
	public static ItemFluxBattery itemFluxBatteryBasic(String unlocalizedName, String group) 
	{
		return new ItemFluxBattery(unlocalizedName, group,
				new int[]{10000, 32000, 100000, 300000, 1200000},
				new short[]{1, 1, 1, 1, 2},
				new String[]{"basic", "redstone", "lead", "lithium", "quartz"},
				new boolean[]{true, true, true, true, true});
	}
	
	@Override
	public void registerItemModel()
	{
		int i, len = this.capacity.length;
		for(i = 0; i < len; i++)
		{
			FluxIndustry.proxy.registerItemRenderer(this, i, "variant=" + this.name[i]);
		}
	}
	
	@Override
    public void getSubItems(Item item, CreativeTabs tab, NonNullList<ItemStack> subItems)
    {
		ItemStack currentStack;
		int i, len = this.capacity.length;
		for(i = 0; i < len; i++)
		{
			currentStack = new ItemStack(item, 1, i, new NBTTagCompound());
			currentStack.getTagCompound().setLong("Tesla", 0);
			subItems.add(currentStack);
		}
    }
	
	public int getMetaWithBounds(ItemStack stack)
	{
		if (stack.getMetadata() < 0 || stack.getMetadata() >= capacity.length)
			return 0;
		return stack.getMetadata();
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		int meta = stack.getMetadata();
		if (meta < 0 || meta >= capacity.length)
			meta = 0;
		return "item." + FluxIndustry.MODID + "." + group + name[meta];
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemstack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
		int meta = itemstack.getMetadata();
		if (meta < 0 || meta >= capacity.length)
			meta = 0;
		// Setting NBT energy
		if (itemstack.getTagCompound()==null)
			itemstack.setTagCompound(new NBTTagCompound());
		if (!itemstack.getTagCompound().hasKey("Tesla", NBT.TAG_LONG))
			itemstack.getTagCompound().setLong("Tesla", 0);
		// Tooltip itself
		if (capacityGrade[meta] < 1)
			tooltip.add(String.format("%d %s / %d %s", itemstack.getTagCompound().getLong("Tesla"), FluxIndustry.unit,
					capacity[meta], FluxIndustry.unit));
		else
		{
			int floatPlacesAmount = 1;
			double divider = Math.pow(1000, capacityGrade[meta]);
			double shownAmount = itemstack.getTagCompound().getLong("Tesla")/divider;
			double shownCapacity = capacity[meta]/divider;
			if (shownCapacity<10)
				floatPlacesAmount = 2;
			tooltip.add(
					String.format("%."+floatPlacesAmount+"f %s", shownAmount,
							"kMGTHE".charAt(capacityGrade[meta]-1)+FluxIndustry.unit)+ " / " +
					String.format("%."+floatPlacesAmount+"f %s", shownCapacity,
							"kMGTHE".charAt(capacityGrade[meta]-1)+FluxIndustry.unit));
		}
    }
	
	@Override
	public ICapabilityProvider initCapabilities (ItemStack stack, NBTTagCompound nbt)
	{
		int meta = stack.getMetadata();
		if (meta < 0 || meta >= capacity.length)
			meta = 0;
		if (stack.getTagCompound()==null)
		{
			stack.setTagCompound(new NBTTagCompound());
		}
		if (!stack.getTagCompound().hasKey("Tesla", NBT.TAG_LONG))
		{
			stack.getTagCompound().setLong("Tesla", 0);
		}
		return new FluxBatteryProvider(stack, capacity[meta], chargeable[meta]);
	}

	@Override
	public int receiveEnergy(ItemStack stack, int Tesla, boolean simulated)
	{
		if (stack.getCount()==1)
		{
			int meta = this.getMetaWithBounds(stack);
			if (this.chargeable[meta])
			{
				long stored = stack.getTagCompound().getLong("Tesla");
				long acceptedTesla = Math.min(this.capacity[meta] - stored, Tesla);
				if (!simulated)
					stack.getTagCompound().setLong("Tesla", stored + acceptedTesla);
				return (int)acceptedTesla;
			}
		}
		return 0;
	}

	@Override
	public int extractEnergy(ItemStack stack, int Tesla, boolean simulated)
	{
		if (stack.getCount()==1)
		{
			int meta = this.getMetaWithBounds(stack);
			long stored = stack.getTagCompound().getLong("Tesla");
			long removedTesla = Math.min(stored, Tesla);
			if (!simulated)
				stack.getTagCompound().setLong("Tesla", stored - removedTesla);
			return (int)removedTesla;
		}
		return 0;
	}

	@Override
	public int getEnergyStored(ItemStack stack)
	{
		return (int)stack.getTagCompound().getLong("Tesla");
	}

	@Override
	public int getMaxEnergyStored(ItemStack stack)
	{
		return (int)this.capacity[this.getMetaWithBounds(stack)];
	}
	
}
