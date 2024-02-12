package pers.zhangyang.easyguishop.easylibrary.base;

import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public interface GuiPage extends InventoryHolder {
   static void revoke() {
      Iterator var0 = Bukkit.getOnlinePlayers().iterator();

      while(var0.hasNext()) {
         Player p = (Player)var0.next();
         if (p.getOpenInventory().getTopInventory().getHolder() instanceof GuiPage) {
            p.closeInventory();
         }
      }

   }

   void send();

   void refresh();

   @NotNull Player getViewer();

   @NotNull OfflinePlayer getOwner();
}
