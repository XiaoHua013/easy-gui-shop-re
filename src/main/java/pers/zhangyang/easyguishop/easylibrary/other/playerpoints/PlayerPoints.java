package pers.zhangyang.easyguishop.easylibrary.other.playerpoints;

import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.Bukkit;

public class PlayerPoints {
   public static PlayerPointsAPI hook() {
      return Bukkit.getPluginManager().isPluginEnabled("PlayerPoints") ? ((org.black_ixx.playerpoints.PlayerPoints)Bukkit.getPluginManager().getPlugin("PlayerPoints")).getAPI() : null;
   }
}
