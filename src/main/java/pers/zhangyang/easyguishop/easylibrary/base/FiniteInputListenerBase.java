package pers.zhangyang.easyguishop.easylibrary.base;

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

public abstract class FiniteInputListenerBase implements Listener {
   protected Player player;
   protected GuiPage previousPage;
   protected String[] messages;
   protected OfflinePlayer owner;

   public FiniteInputListenerBase(Player player, OfflinePlayer owner, GuiPage previousPage, int sequence) {
      this.player = player;
      this.owner = owner;
      this.previousPage = previousPage;
      this.messages = new String[sequence];
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
                  FiniteInputListenerBase.this.previousPage.refresh();
               }
            }).runTask(EasyPlugin.instance);
         } else {
            for(int i = 0; i < this.messages.length; ++i) {
               if (this.messages[i] == null) {
                  this.messages[i] = ChatColor.translateAlternateColorCodes('&', event.getMessage());
                  break;
               }
            }

            if (this.messages[this.messages.length - 1] != null) {
               AsyncPlayerChatEvent.getHandlerList().unregister(this);
               PlayerQuitEvent.getHandlerList().unregister(this);
               (new BukkitRunnable() {
                  public void run() {
                     FiniteInputListenerBase.this.run();
                     FiniteInputListenerBase.this.previousPage.refresh();
                  }
               }).runTask(EasyPlugin.instance);
            }
         }
      }
   }

   public abstract void run();
}
