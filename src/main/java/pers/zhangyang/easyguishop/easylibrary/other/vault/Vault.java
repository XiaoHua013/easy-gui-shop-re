package pers.zhangyang.easyguishop.easylibrary.other.vault;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Vault {
   public static Economy hook() {
      if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
         return null;
      } else {
         RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);
         return rsp == null ? null : (Economy)rsp.getProvider();
      }
   }
}
