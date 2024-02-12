package pers.zhangyang.easyguishop.easylibrary.util;

import java.util.Iterator;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.zhangyang.easyguishop.easylibrary.exception.NotApplicableException;
import pers.zhangyang.easyguishop.easylibrary.exception.UnsupportedMinecraftVersionException;

public class ItemStackUtil {
   public static @NotNull ItemStack getPlayerSkullItem() {
      return VersionUtil.getMinecraftBigVersion() == 1 && VersionUtil.getMinecraftMiddleVersion() < 13 ? new ItemStack(Material.valueOf("SKULL_ITEM"), 1, (short)3) : new ItemStack(Material.valueOf("PLAYER_HEAD"));
   }

   public static void apply(@NotNull ItemStack itemStack, @NotNull ItemStack target) throws NotApplicableException {
      ItemMeta itemMeta = itemStack.getItemMeta();
      ItemMeta targetMeta = target.getItemMeta();
      if (targetMeta == null) {
         throw new NotApplicableException();
      } else if (itemMeta != null) {
         targetMeta.setDisplayName(itemMeta.getDisplayName());
         targetMeta.setLore(itemMeta.getLore());
         targetMeta.getItemFlags().clear();
         Iterator var4 = itemMeta.getItemFlags().iterator();

         while(var4.hasNext()) {
            ItemFlag i = (ItemFlag)var4.next();
            targetMeta.addItemFlags(new ItemFlag[]{i});
         }

         if (VersionUtil.getMinecraftBigVersion() == 1 && VersionUtil.getMinecraftMiddleVersion() >= 13 && itemMeta.hasCustomModelData()) {
            targetMeta.setCustomModelData(itemMeta.getCustomModelData());
         }

         target.setItemMeta(targetMeta);
      }
   }

   public static @NotNull ItemStack getItemStack(@NotNull Material material, @Nullable String displayName, @Nullable List<String> lore, @Nullable List<ItemFlag> flagList, int amount) throws NotApplicableException {
      ItemStack itemStack = new ItemStack(material, amount);
      ItemMeta itemMeta = itemStack.getItemMeta();
      if (itemMeta == null) {
         throw new NotApplicableException();
      } else {
         if (lore != null) {
            for(int i = 0; i < lore.size(); ++i) {
               lore.set(i, ChatColor.translateAlternateColorCodes('&', (String)lore.get(i)));
            }

            itemMeta.setLore(lore);
         }

         if (displayName != null) {
            displayName = ChatColor.translateAlternateColorCodes('&', displayName);
            itemMeta.setDisplayName(displayName);
         }

         if (flagList != null) {
            Iterator var9 = flagList.iterator();

            while(var9.hasNext()) {
               ItemFlag itemFlag = (ItemFlag)var9.next();
               itemMeta.addItemFlags(new ItemFlag[]{itemFlag});
            }
         }

         if (!itemStack.setItemMeta(itemMeta)) {
            throw new IllegalArgumentException();
         } else {
            return itemStack;
         }
      }
   }

   public static @NotNull ItemStack getItemStack(@NotNull Material material, @Nullable String displayName, @Nullable List<String> lore, @Nullable List<ItemFlag> flagList, int amount, @Nullable Integer customModelData) throws NotApplicableException, UnsupportedMinecraftVersionException {
      if (VersionUtil.getMinecraftBigVersion() == 1 && VersionUtil.getMinecraftMiddleVersion() < 13) {
         throw new UnsupportedMinecraftVersionException();
      } else {
         ItemStack itemStack = getItemStack(material, displayName, lore, flagList, amount);
         ItemMeta itemMeta = itemStack.getItemMeta();

         assert itemMeta != null;

         itemMeta.setCustomModelData(customModelData);

         assert itemStack.setItemMeta(itemMeta);

         return itemStack;
      }
   }

   public static @NotNull String itemStackSerialize(@NotNull ItemStack itemStack) {
      YamlConfiguration yml = new YamlConfiguration();
      yml.set("item", itemStack);
      return yml.saveToString();
   }

   public static @NotNull ItemStack itemStackDeserialize(@NotNull String str) {
      YamlConfiguration yml = new YamlConfiguration();

      try {
         yml.loadFromString(str);
      } catch (InvalidConfigurationException var3) {
         throw new IllegalArgumentException();
      }

      Object obj = yml.get("item");
      if (obj == null) {
         throw new IllegalArgumentException();
      } else {
         return (ItemStack)obj;
      }
   }
}
