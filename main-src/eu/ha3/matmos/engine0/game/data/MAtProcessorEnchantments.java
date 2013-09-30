package eu.ha3.matmos.engine0.game.data;

import java.util.Set;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Minecraft;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import eu.ha3.matmos.engine0.conv.MAtmosConvLogger;
import eu.ha3.matmos.engine0.core.implem.StringData;
import eu.ha3.matmos.engine0.game.system.MAtMod;

/*
            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE 
                    Version 2, December 2004 

 Copyright (C) 2004 Sam Hocevar <sam@hocevar.net> 

 Everyone is permitted to copy and distribute verbatim or modified 
 copies of this license document, and changing it is allowed as long 
 as the name is changed. 

            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE 
   TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION 

  0. You just DO WHAT THE FUCK YOU WANT TO. 
*/

public abstract class MAtProcessorEnchantments extends MAtProcessorModel
{
	public MAtProcessorEnchantments(MAtMod modIn, StringData dataIn, String normalNameIn, String deltaNameIn)
	{
		super(modIn, dataIn, normalNameIn, deltaNameIn);
	}
	
	@Override
	protected void doProcess()
	{
		// Sets everything to 0 if no such armor
		
		Set<String> required = getRequired();
		
		for (String i : required)
		{
			setValue(i, 0);
		}
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		ItemStack item = getItem(player);
		
		if (item != null && item.getEnchantmentTagList() != null && item.getEnchantmentTagList().tagCount() > 0)
		{
			int total = item.getEnchantmentTagList().tagCount();
			
			NBTTagList enchantments = item.getEnchantmentTagList();
			for (int i = 0; i < total; i++)
			{
				int id = ((NBTTagCompound) enchantments.tagAt(i)).getShort("id");
				
				if (id < 64 && id >= 0)
				{
					if (required.contains(id))
					{
						short lvl = ((NBTTagCompound) enchantments.tagAt(i)).getShort("lvl");
						setValue(id, lvl);
					}
				}
				else
				{
					MAtmosConvLogger.warning("Found enchantment which ID is " + id + "!!!");
				}
			}
		}
	}
	
	protected abstract ItemStack getItem(EntityPlayer player);
	
}