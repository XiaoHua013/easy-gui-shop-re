package pers.zhangyang.easyguishop.easylibrary.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pers.zhangyang.easyguishop.easylibrary.EasyPlugin;
import pers.zhangyang.easyguishop.easylibrary.util.NotifyVersionUtil;

public class PlayerJoin implements Listener {
   @EventHandler
   public void on(PlayerJoinEvent event) {
      Player player = event.getPlayer();
      if (player.hasPermission(EasyPlugin.instance.getName() + ".receiveVersionInformation")) {
         NotifyVersionUtil.notifyVersion(player);
      }
   }
}
