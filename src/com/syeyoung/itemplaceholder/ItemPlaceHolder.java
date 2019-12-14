package com.syeyoung.itemplaceholder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import com.syeyoung.itemplaceholder.api.PlaceHolderableItemMeta;
import com.syeyoung.itemplaceholder.impl.PHItemStack;
import com.syeyoung.itemplaceholder.impl.versions.PHItemMetaFactory;
import com.syeyoung.itemplaceholder.impl.versions.v1_5_R3.PHMetaItem;

public class ItemPlaceHolder extends JavaPlugin {
	static {

	}
	private Class<?> theForbiddenClass;
	private Constructor<?> theForbiddenConstructor;
	public void onEnable() {
		
		String version = getVersion(getServer());
		switch(getVersion(getServer())) {
//		case "v1_12_R1": 상위에서는 nms ItemStack 에서 PHMetaItem 보존 불가 
		case "v1_5_R3":
			break;
		default:
			getPluginLoader().disablePlugin(this);
			getLogger().log(Level.SEVERE, "지원하지 않는 버전입니다 : "+version+" [v1_5_R3만 사용가능]");
			return;
		}
		
		ClassLoader cl = Bukkit.class.getClassLoader();

		InputStream is = getClass()
				.getResourceAsStream("customdefined/"+version+"_MiddlemanMetaItem.loadlater");
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		byte[] buffer = new byte[1024];
		int len;

		// read bytes from the input stream and store them in buffer
		try {
			while ((len = is.read(buffer)) != -1) {
				// write bytes from the buffer into output stream
				bos.write(buffer, 0, len);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		byte[] result = bos.toByteArray();

		try {
			Method m = ClassLoader.class.getDeclaredMethod("defineClass",
					String.class, byte[].class, int.class, int.class);
			m.setAccessible(true);
			Object result2 = m.invoke(cl,
					"org.bukkit.craftbukkit."+version+".inventory.MiddlemanMetaItem",
					result, 0, result.length);
			theForbiddenClass= (Class<?>) result2;
			theForbiddenConstructor = theForbiddenClass.getConstructor(ItemMeta.class);
		} catch (NoSuchMethodException | SecurityException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public void onDisable() {

	}
	
	public static String getVersion(Server server) {
	    final String packageName = server.getClass().getPackage().getName();

	    return packageName.substring(packageName.lastIndexOf('.') + 1);
	}

	@Override
	public boolean onCommand(CommandSender cs, Command c, String label,
			String[] args) {
		PHItemStack item = new PHItemStack(Material.STONE);
		PlaceHolderableItemMeta meta = PHItemMetaFactory.getPHItemMeta(item.getItemMeta());
		meta.setDisplayName("§e특별한 아이템 for {player}");
		meta.setLore(Arrays.asList("§f와! §e{player}§f에게 지급된 아이템이다!", "", "{multirow}"));
		meta.setPlaceHolder("player", cs.getName());
		meta.setPlaceHolder("multirow", Arrays.asList("§a짝짝짝1", "§b짝짝짝2", "§c짝짝짝3"));
		System.out.println(item.setItemMeta(meta));
		System.out.println(item.getItemMeta().getClass().getName());
		((Player) cs).getInventory().addItem(item);
		return true;
	}
}
