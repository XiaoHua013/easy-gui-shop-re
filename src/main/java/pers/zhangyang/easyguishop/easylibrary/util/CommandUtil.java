package pers.zhangyang.easyguishop.easylibrary.util;

import java.util.Iterator;
import java.util.List;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandUtil {
   public static void dispatchCommandList(@NotNull CommandSender commandSender, @NotNull List<String> cmdList) {
      Iterator var2 = cmdList.iterator();

      while(var2.hasNext()) {
         String s = (String)var2.next();
         if (commandSender instanceof Player && Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            s = PlaceholderAPI.setPlaceholders((Player)commandSender, s);
         }

         s = ChatColor.translateAlternateColorCodes('&', s);
         String[] args = s.split(":");
         if (args.length == 2) {
            String way = args[0];
            String command = args[1];
            if ("console".equalsIgnoreCase(way)) {
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
            } else if ("self".equalsIgnoreCase(way)) {
               Bukkit.dispatchCommand(commandSender, command);
            } else if ("operator".equalsIgnoreCase(way)) {
               boolean op = commandSender.isOp();
               commandSender.setOp(true);
               Bukkit.dispatchCommand(commandSender, command);
               commandSender.setOp(op);
            }
         }
      }

   }
}
