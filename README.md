# ItemStackPlaceHolder
A plugin library for Spigot 1.5.2

# Usage
```
PHItemStack item = new PHItemStack(Material.STONE);
PlaceHolderableItemMeta meta = PHItemMetaFactory.getPHItemMeta(item.getItemMeta());
meta.setDisplayName("§eSpecial Item for {player}");
meta.setLore(Arrays.asList("§fWoah! This Item is given to §e{player}§f!", "", "{multirow}"));
meta.setPlaceHolder("player", cs.getName());
meta.setPlaceHolder("multirow", Arrays.asList("§a(Clap)1", "§b(Clap)2", "§c(Clap)3"));
{Player}.getInventory().addItem(item);
```
