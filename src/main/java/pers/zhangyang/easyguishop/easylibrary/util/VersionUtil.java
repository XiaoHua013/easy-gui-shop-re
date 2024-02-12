package pers.zhangyang.easyguishop.easylibrary.util;

import org.bukkit.Bukkit;
import pers.zhangyang.easyguishop.easylibrary.EasyPlugin;

public class VersionUtil {
   public static int getMinecraftBigVersion() {
      return Integer.parseInt(Bukkit.getBukkitVersion().split("\\.")[0]);
   }

   public static int getMinecraftMiddleVersion() {
      return Integer.parseInt(Bukkit.getBukkitVersion().split("\\.")[1].split("-")[0]);
   }

   public static int getPluginBigVersion() {
      return Integer.parseInt(EasyPlugin.instance.getDescription().getVersion().split("\\.")[0]);
   }

   public static int getPluginMiddleVersion() {
      return Integer.parseInt(EasyPlugin.instance.getDescription().getVersion().split("\\.")[1]);
   }

   public static int getPluginSmallVersion() {
      return Integer.parseInt(EasyPlugin.instance.getDescription().getVersion().split("\\.")[2]);
   }

   public static boolean isOlderThan(int currentBig, int currentMiddle, int currentSmall, int big, int middle, int small) {
      if (currentBig > big) {
         return false;
      } else if (currentBig < big) {
         return true;
      } else if (currentMiddle > middle) {
         return false;
      } else if (currentMiddle < middle) {
         return true;
      } else {
         return currentSmall < small;
      }
   }

   public static boolean isOlderThan(int currentBig, int currentMiddle, int big, int middle) {
      if (currentBig > big) {
         return false;
      } else if (currentBig < big) {
         return true;
      } else {
         return currentMiddle < middle;
      }
   }
}
