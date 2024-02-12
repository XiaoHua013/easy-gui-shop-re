package pers.zhangyang.easyguishop.easylibrary.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.zhangyang.easyguishop.easylibrary.yaml.MessageYaml;

public class MessageUtil {
   public static void notPlayer(CommandSender sender) {
      sendMessageTo(sender, MessageYaml.INSTANCE.getStringList("message.chat.notPlayer"));
   }

   public static void notItemInMainHand(CommandSender sender) {
      sendMessageTo(sender, MessageYaml.INSTANCE.getStringList("message.chat.notItemStackInMainHand"));
   }

   public static void invalidArgument(@NotNull CommandSender sender, String arg) {
      List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.invalidArgument");
      if (list != null) {
         ReplaceUtil.replace(list, Collections.singletonMap("{argument}", arg));
      }

      sendMessageTo(sender, list);
   }

   public static void sendTitleTo(@NotNull Player player, @Nullable String title, @Nullable String subtitle) {
      if (title != null) {
         if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            title = PlaceholderAPI.setPlaceholders(player, title);
         }

         title = ChatColor.translateAlternateColorCodes('&', title);
      }

      if (subtitle != null) {
         if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            subtitle = PlaceholderAPI.setPlaceholders(player, subtitle);
         }

         subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
      }

      player.sendTitle(title, subtitle, 10, 10, 20);
   }

   public static void sendMessageTo(@NotNull CommandSender sender, @Nullable List<String> strings) {
      if (strings != null) {
         String s;
         for(Iterator var2 = strings.iterator(); var2.hasNext(); sender.sendMessage(ChatColor.translateAlternateColorCodes('&', s))) {
            s = (String)var2.next();
            if (sender instanceof Player && Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
               s = PlaceholderAPI.setPlaceholders((Player)sender, s);
            }
         }

      }
   }

   public static void sendMessageTo(@NotNull Collection<? extends CommandSender> senderList, @Nullable List<String> strings) {
      if (strings != null) {
         Iterator var2 = senderList.iterator();

         while(var2.hasNext()) {
            CommandSender sender = (CommandSender)var2.next();

            String s;
            for(Iterator var4 = strings.iterator(); var4.hasNext(); sender.sendMessage(ChatColor.translateAlternateColorCodes('&', s))) {
               s = (String)var4.next();
               if (sender instanceof Player && Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                  s = PlaceholderAPI.setPlaceholders((Player)sender, s);
               }
            }
         }

      }
   }

   public static void sendMessageTo(@NotNull Collection<? extends CommandSender> senderList, @Nullable String s) {
      if (s != null) {
         CommandSender sender;
         for(Iterator var2 = senderList.iterator(); var2.hasNext(); sender.sendMessage(ChatColor.translateAlternateColorCodes('&', s))) {
            sender = (CommandSender)var2.next();
            if (sender instanceof Player && Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
               s = PlaceholderAPI.setPlaceholders((Player)sender, s);
            }
         }

      }
   }

   public static void sendMessageTo(@NotNull CommandSender sender, @Nullable String s) {
      if (s != null) {
         if (sender instanceof Player && Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            s = PlaceholderAPI.setPlaceholders((Player)sender, s);
         }

         sender.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
      }
   }
}
