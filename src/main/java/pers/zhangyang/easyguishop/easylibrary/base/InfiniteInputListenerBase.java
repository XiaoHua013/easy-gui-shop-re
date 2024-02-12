package pers.zhangyang.easyguishop.easylibrary.base;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import pers.zhangyang.easyguishop.easylibrary.EasyPlugin;
import pers.zhangyang.easyguishop.easylibrary.yaml.MessageYaml;

public abstract class InfiniteInputListenerBase implements Listener {
   protected Player player;
   protected OfflinePlayer owner;
   protected GuiPage previousPage;
   protected List<String> messageList = new ArrayList();

   public InfiniteInputListenerBase(Player player, OfflinePlayer owner, GuiPage previousPage) {
      this.owner = owner;
      this.player = player;
      this.previousPage = previousPage;
      Bukkit.getPluginManager().registerEvents(this, EasyPlugin.instance);
   }

   @EventHandler
   public void on(AsyncPlayerChatEvent event) {
      if (event.getPlayer().equals(this.player)) {
         event.setCancelled(true);
         if (event.getMessage().equalsIgnoreCase(MessageYaml.INSTANCE.getNonemptyStringDefault("message.input.cancel"))) {
            AsyncPlayerChatEvent.getHandlerList().unregister(this);
            PlayerQuitEvent.getHandlerList().unregister(this);
            (new BukkitRunnable() {
               public void run() {
                  InfiniteInputListenerBase.this.previousPage.refresh();
               }
            }).runTask(EasyPlugin.instance);
         } else if (!event.getMessage().equalsIgnoreCase(MessageYaml.INSTANCE.getNonemptyStringDefault("message.input.submit"))) {
            this.messageList.add(ChatColor.translateAlternateColorCodes('&', event.getMessage()));
         } else {
            AsyncPlayerChatEvent.getHandlerList().unregister(this);
            PlayerQuitEvent.getHandlerList().unregister(this);
            (new BukkitRunnable() {
               public void run() {
                  InfiniteInputListenerBase.this.run();
                  InfiniteInputListenerBase.this.previousPage.refresh();
               }
            }).runTask(EasyPlugin.instance);
         }
      }
   }

   public abstract void run();
}
