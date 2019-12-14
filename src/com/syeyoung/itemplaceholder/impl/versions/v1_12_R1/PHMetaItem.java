package com.syeyoung.itemplaceholder.impl.versions.v1_12_R1;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagString;

import org.apache.commons.lang.text.StrSubstitutor;
import org.bukkit.craftbukkit.v1_12_R1.inventory.MiddlemanMetaItem;
import org.bukkit.inventory.meta.ItemMeta;

import com.syeyoung.itemplaceholder.api.PlaceHolderableItemMeta;
import com.syeyoung.itemplaceholder.impl.ListChangingLookup;

public class PHMetaItem extends MiddlemanMetaItem implements PlaceHolderableItemMeta {

	private HashMap<String, Object> placeholders = new HashMap<>();

	public PHMetaItem(ItemMeta meta) {
		super(meta);
	}

	@Override
	public void setPlaceHolder(String name, Object value) {
		placeholders.put(name, value);
	}

	@Override
	public Object getPlaceHolder(String name) {
		return placeholders.get(name);
	}

	@Override
	public String getPlaceHolderString(String name) {
		Object obj = getPlaceHolder(name);
		return obj == null ? null : (String) obj;
	}

	@Override
	public Integer getPlaceHolderInteger(String name) {
		Object obj = getPlaceHolder(name);
		return obj == null ? null : (Integer) obj;
	}

	@Override
	public Long getPlaceHolderLong(String name) {
		Object obj = getPlaceHolder(name);
		return obj == null ? null : (Long) obj;
	}

	@Override
	public Double getPlaceHolderDouble(String name) {
		Object obj = getPlaceHolder(name);
		return obj == null ? null : (Double) obj;
	}

	@Override
	public Float getPlaceHolderFloat(String name) {
		Object obj = getPlaceHolder(name);
		return obj == null ? null : (Float) obj;
	}

	@Override
	public Character getPlaceHolderCharacter(String name) {
		Object obj = getPlaceHolder(name);
		return obj == null ? null : (Character) obj;
	}
	
	@Override
	public Boolean getPlaceHolderBoolean(String name) {
		Object obj = getPlaceHolder(name);
		return obj == null ? null : (Boolean) obj;
	}

	@Override
	public List<?> getPlaceHolderList(String name) {
		Object obj = getPlaceHolder(name);
		return obj == null ? null : (List) obj;
	}

	@Override
	public List<String> getPlaceHolderStringList(String name) {
		Object obj = getPlaceHolder(name);
		return obj == null ? null : (List<String>) obj;
	}
	
	@Override
	public Map<String, Object> getPlaceHolders() {
		return Collections.unmodifiableMap(placeholders);
	}

	@Override
	public void applyToItem2(NBTTagCompound itemTag) {
		StrSubstitutor sub = new StrSubstitutor(placeholders, "{", "}");
		sub.setVariableResolver(new ListChangingLookup(placeholders));
		if (hasDisplayName()) {
			setDisplayTag2(itemTag, getNameMetaKeyNBT(), new NBTTagString(sub.replace(getDisplayName())));
		}
		if (hasLore()) {
			List<String> lore = this.getLore();
			String new_line_separated = String.join("\n", lore.toArray(new String[0]));
			new_line_separated = sub.replace(new_line_separated);
			List<String> new_lore = Arrays.asList(new_line_separated.split("\n"));
			setDisplayTag2(itemTag, getLoreMetaKeyNBT(), createStringList2(new_lore, getLoreMetaKey()));
		}
	}
	
	@Override
	public PHMetaItem clone() {
		PHMetaItem clone = (PHMetaItem) super.clone();
		clone.placeholders = new HashMap<>(placeholders);
		
		return clone;
	}

}
