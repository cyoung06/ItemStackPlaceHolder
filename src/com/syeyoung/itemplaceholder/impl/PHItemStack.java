package com.syeyoung.itemplaceholder.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import com.google.common.collect.ImmutableMap;

public class PHItemStack extends ItemStack {
	private int type = 0;
	private int amount = 0;
	private MaterialData data = null;
	private short durability = 0;
	private ItemMeta meta;

	protected PHItemStack() {
	}

	public PHItemStack(int type) {
		this(type, 1);
	}

	public PHItemStack(Material type) {
		this(type, 1);
	}

	public PHItemStack(int type, int amount) {
		this(type, amount, (short) 0);
	}

	public PHItemStack(Material type, int amount) {
		this(type.getId(), amount);
	}

	public PHItemStack(int type, int amount, short damage) {
		this.type = type;
		this.amount = amount;
		this.durability = damage;
	}

	public PHItemStack(Material type, int amount, short damage) {
		this(type.getId(), amount, damage);
	}

	@Deprecated
	public PHItemStack(int type, int amount, short damage, Byte data) {
		this.type = type;
		this.amount = amount;
		this.durability = damage;
		if (data != null) {
			createData(data.byteValue());
			this.durability = ((short) data.byteValue());
		}
	}

	@Deprecated
	public PHItemStack(Material type, int amount, short damage, Byte data) {
		this(type.getId(), amount, damage, data);
	}

	public PHItemStack(ItemStack stack) throws IllegalArgumentException {
		Validate.notNull(stack, "Cannot copy null stack");
		this.type = stack.getTypeId();
		this.amount = stack.getAmount();
		this.durability = stack.getDurability();
		this.data = stack.getData();
		if (stack.hasItemMeta()) {
			setItemMeta0(stack.getItemMeta(), getType0());
		}
	}

	private Material getType0() {
		return getType0(this.type);
	}

	private static Material getType0(int id) {
		Material material = Material.getMaterial(id);
		return material == null ? Material.AIR : material;
	}

	public void setType(Material type) {
		Validate.notNull(type, "Material cannot be null");
		setTypeId(type.getId());
	}

	public int getTypeId() {
		return this.type;
	}

	public void setTypeId(int type) {
		this.type = type;
		if (this.meta != null) {
			this.meta = Bukkit.getItemFactory()
					.asMetaFor(this.meta, getType0());
		}
		createData((byte) 0);
	}

	public int getAmount() {
		return this.amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public MaterialData getData() {
		Material mat = getType();
		if ((this.data == null) && (mat != null) && (mat.getData() != null)) {
			this.data = mat.getNewData((byte) getDurability());
		}
		return this.data;
	}

	public void setData(MaterialData data) {
		Material mat = getType();
		if ((data == null) || (mat == null) || (mat.getData() == null)) {
			this.data = data;
		} else if ((data.getClass() == mat.getData())
				|| (data.getClass() == MaterialData.class)) {
			this.data = data;
		} else {
			throw new IllegalArgumentException("Provided data is not of type "
					+ mat.getData().getName() + ", found "
					+ data.getClass().getName());
		}
	}

	public void setDurability(short durability) {
		this.durability = durability;
	}

	public short getDurability() {
		return this.durability;
	}

	public int getMaxStackSize() {
		Material material = getType();
		if (material != null) {
			return material.getMaxStackSize();
		}
		return -1;
	}

	private void createData(byte data) {
		Material mat = Material.getMaterial(this.type);
		if (mat == null) {
			this.data = new MaterialData(this.type, data);
		} else {
			this.data = mat.getNewData(data);
		}
	}

	public ItemStack clone() {
		PHItemStack itemStack = (PHItemStack) super.clone();
		if (this.meta != null) {
			itemStack.meta = this.meta.clone();
		}
		if (this.data != null) {
			itemStack.data = this.data.clone();
		}
		return itemStack;
	}

	public boolean containsEnchantment(Enchantment ench) {
		return this.meta == null ? false : this.meta.hasEnchant(ench);
	}

	public int getEnchantmentLevel(Enchantment ench) {
		return this.meta == null ? 0 : this.meta.getEnchantLevel(ench);
	}

	public Map<Enchantment, Integer> getEnchantments() {
		return this.meta == null ? ImmutableMap.of() : this.meta.getEnchants();
	}


	public void addUnsafeEnchantment(Enchantment ench, int level) {
		(this.meta == null ? (this.meta = Bukkit.getItemFactory().getItemMeta(
				getType0())) : this.meta).addEnchant(ench, level, true);
	}

	public int removeEnchantment(Enchantment ench) {
		int level = getEnchantmentLevel(ench);
		if ((level == 0) || (this.meta == null)) {
			return level;
		}
		this.meta.removeEnchant(ench);
		return level;
	}
	
	public static PHItemStack deserialize(Map<String, Object> args) {
		Material type = Material.getMaterial((String) args.get("type"));
		short damage = 0;
		int amount = 1;
		if (args.containsKey("damage")) {
			damage = ((Number) args.get("damage")).shortValue();
		}
		if (args.containsKey("amount")) {
			amount = ((Integer) args.get("amount")).intValue();
		}
		PHItemStack result = new PHItemStack(type, amount, damage);
		if (args.containsKey("enchantments")) {
			Object raw = args.get("enchantments");
			if ((raw instanceof Map)) {
				Map<?, ?> map = (Map) raw;
				for (Map.Entry<?, ?> entry : map.entrySet()) {
					Enchantment enchantment = Enchantment.getByName(entry
							.getKey().toString());
					if ((enchantment != null)
							&& ((entry.getValue() instanceof Integer))) {
						result.addUnsafeEnchantment(enchantment,
								((Integer) entry.getValue()).intValue());
					}
				}
			}
		} else if (args.containsKey("meta")) {
			Object raw = args.get("meta");
			if ((raw instanceof ItemMeta)) {
				result.setItemMeta((ItemMeta) raw);
			}
		}
		return result;
	}

	public ItemMeta getItemMeta() {
		return this.meta == null ? Bukkit.getItemFactory().getItemMeta(
				getType0()) : this.meta.clone();
	}

	public boolean hasItemMeta() {
		return !Bukkit.getItemFactory().equals(this.meta, null);
	}

	public boolean setItemMeta(ItemMeta itemMeta) {
		return setItemMeta0(itemMeta, getType0());
	}

	private boolean setItemMeta0(ItemMeta itemMeta, Material material) {
		if (itemMeta == null) {
			this.meta = null;
			return true;
		}
		if (!Bukkit.getItemFactory().isApplicable(itemMeta, material)) {
			return false;
		}
		this.meta = itemMeta.clone();
		return true;
	}
}
