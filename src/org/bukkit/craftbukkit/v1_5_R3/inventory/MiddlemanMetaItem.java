package org.bukkit.craftbukkit.v1_5_R3.inventory;

import java.util.List;

import net.minecraft.server.v1_5_R3.NBTBase;
import net.minecraft.server.v1_5_R3.NBTTagCompound;
import net.minecraft.server.v1_5_R3.NBTTagList;

import org.bukkit.inventory.meta.ItemMeta;

public class MiddlemanMetaItem extends CraftMetaItem {
	public MiddlemanMetaItem(ItemMeta meta) {
		super((CraftMetaItem)meta);
	}

	@Override
	void applyToItem(NBTTagCompound itemTag) {
		super.applyToItem(itemTag);
		applyToItem2(itemTag);
	}
	
	public String getNameMetaKeyNBT() {
		return NAME.NBT;
	}
	
	public String getLoreMetaKeyNBT() {
		return LORE.NBT;
	}
	public ItemMetaKey getLoreMetaKey() {
		return LORE;
	}
	
	public void applyToItem2(NBTTagCompound itemTag) {}
	
	public void setDisplayTag2(NBTTagCompound tag, String key, NBTBase value) {
		super.setDisplayTag(tag, key, value);
	}
	
	public NBTTagList createStringList2(List<String> arg0, ItemMetaKey arg1) {
		return super.createStringList(arg0, arg1);
	}
	
	@Override
	public MiddlemanMetaItem clone() {
		return (MiddlemanMetaItem) super.clone();
	}
}
