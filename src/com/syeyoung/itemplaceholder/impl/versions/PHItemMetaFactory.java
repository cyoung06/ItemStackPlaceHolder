package com.syeyoung.itemplaceholder.impl.versions;

import org.bukkit.Bukkit;
import org.bukkit.inventory.meta.ItemMeta;

import com.syeyoung.itemplaceholder.ItemPlaceHolder;
import com.syeyoung.itemplaceholder.api.PlaceHolderableItemMeta;
import com.syeyoung.itemplaceholder.impl.versions.v1_12_R1.PHMetaItem;

public class PHItemMetaFactory {
	public static PlaceHolderableItemMeta getPHItemMeta(ItemMeta meta) {
		String version = ItemPlaceHolder.getVersion(Bukkit.getServer());
		
		switch(version) {
		case "v1_12_R1":
			return new com.syeyoung.itemplaceholder.impl.versions.v1_12_R1.PHMetaItem(meta);
		case "v1_5_R3":
			return new com.syeyoung.itemplaceholder.impl.versions.v1_5_R3.PHMetaItem(meta);
		}
		
		throw new UnsupportedOperationException("not supported bukkit version");
	}
}
