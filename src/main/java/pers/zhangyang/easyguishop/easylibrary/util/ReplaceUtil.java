package pers.zhangyang.easyguishop.easylibrary.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ReplaceUtil {
   public static String replaceToRepositoryName(String oldName) {
      char[] pluginNameChars = oldName.toCharArray();
      if (pluginNameChars[0] >= 'A' && pluginNameChars[0] <= 'Z') {
         pluginNameChars[0] = (char)(pluginNameChars[0] + 32);
      }

      boolean end = false;

      while(true) {
         while(!end) {
            for(int i = 0; i < pluginNameChars.length; ++i) {
               if (pluginNameChars[i] >= 'A' && pluginNameChars[i] <= 'Z') {
                  StringBuilder stringBuilder = new StringBuilder(String.valueOf(pluginNameChars));
                  stringBuilder.replace(i, i + 1, String.valueOf((char)(pluginNameChars[i] + 32)));
                  stringBuilder.insert(i, "-");
                  pluginNameChars = stringBuilder.toString().toCharArray();
                  break;
               }
            }

            end = true;
            char[] var7 = pluginNameChars;
            int var8 = pluginNameChars.length;

            for(int var5 = 0; var5 < var8; ++var5) {
               char pluginNameChar = var7[var5];
               if (pluginNameChar >= 'A' && pluginNameChar <= 'Z') {
                  end = false;
                  break;
               }
            }
         }

         return String.valueOf(pluginNameChars);
      }
   }

   public static String replaceToDatabaseTableName(String oldName) {
      char[] pluginNameChars = oldName.toCharArray();
      if (pluginNameChars[0] >= 'A' && pluginNameChars[0] <= 'Z') {
         pluginNameChars[0] = (char)(pluginNameChars[0] + 32);
      }

      boolean end = false;

      while(true) {
         while(!end) {
            for(int i = 0; i < pluginNameChars.length; ++i) {
               if (pluginNameChars[i] >= 'A' && pluginNameChars[i] <= 'Z') {
                  StringBuilder stringBuilder = new StringBuilder(String.valueOf(pluginNameChars));
                  stringBuilder.replace(i, i + 1, String.valueOf((char)(pluginNameChars[i] + 32)));
                  stringBuilder.insert(i, "_");
                  pluginNameChars = stringBuilder.toString().toCharArray();
                  break;
               }
            }

            end = true;
            char[] var7 = pluginNameChars;
            int var8 = pluginNameChars.length;

            for(int var5 = 0; var5 < var8; ++var5) {
               char pluginNameChar = var7[var5];
               if (pluginNameChar >= 'A' && pluginNameChar <= 'Z') {
                  end = false;
                  break;
               }
            }
         }

         return String.valueOf(pluginNameChars);
      }
   }

   public static void formatLore(@NotNull ItemStack itemStack, @NotNull String pattern, @Nullable List<String> replaceTo) {
      ItemMeta itemMeta = itemStack.getItemMeta();
      if (itemMeta != null) {
         if (itemMeta.getLore() != null) {
            List<String> lore = itemMeta.getLore();
            format(lore, pattern, replaceTo);
            itemMeta.setLore(lore);
         }

         itemStack.setItemMeta(itemMeta);
      }
   }

   public static void replaceDisplayName(@NotNull ItemStack itemStack, @NotNull Map<String, String> replaceMap) {
      ItemMeta itemMeta = itemStack.getItemMeta();
      if (itemMeta != null) {
         itemMeta.setDisplayName(replace(itemMeta.getDisplayName(), replaceMap));
         itemStack.setItemMeta(itemMeta);
      }
   }

   public static void replaceLore(@NotNull ItemStack itemStack, @NotNull Map<String, String> replaceMap) {
      ItemMeta itemMeta = itemStack.getItemMeta();
      if (itemMeta != null) {
         if (itemMeta.getLore() != null) {
            List<String> lore = itemMeta.getLore();
            replace(lore, replaceMap);
            itemMeta.setLore(lore);
         }

         itemStack.setItemMeta(itemMeta);
      }
   }

   public static void format(@NotNull List<String> list, @NotNull String pattern, @Nullable List<String> replaceTo) {
      List<Integer> integerList = new ArrayList();

      int i;
      for(i = 0; i < list.size(); ++i) {
         String s = (String)list.get(i);
         if (s.contains(pattern)) {
            integerList.add(i);
         }
      }

      for(i = integerList.size() - 1; i >= 0; --i) {
         int ii = (Integer)integerList.get(i);
         String s = (String)list.get(ii);
         list.remove(ii);
         if (replaceTo != null) {
            for(int j = replaceTo.size() - 1; j >= 0; --j) {
               list.add(ii, replace(s, Collections.singletonMap(pattern, replaceTo.get(j))));
            }
         }
      }

   }

   public static @NotNull String replace(@NotNull String s, @NotNull Map<String, String> rep) {
      String key;
      for(Iterator var2 = rep.keySet().iterator(); var2.hasNext(); s = s.replace(key, ChatColor.translateAlternateColorCodes('&', (String)rep.get(key)))) {
         key = (String)var2.next();
      }

      return s;
   }

   public static void replace(@NotNull List<String> s, @NotNull Map<String, String> rep) {
      Iterator var2 = rep.keySet().iterator();

      while(var2.hasNext()) {
         String key = (String)var2.next();

         for(int i = 0; i < s.size(); ++i) {
            s.set(i, ((String)s.get(i)).replace(key, ChatColor.translateAlternateColorCodes('&', (String)rep.get(key))));
         }
      }

   }
}
