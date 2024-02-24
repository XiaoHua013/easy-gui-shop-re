package pers.zhangyang.easyguishop.easylibrary.util;

import java.net.URL;
import java.util.HashMap;
import java.util.List;

import cn.handyplus.lib.adapter.HandySchedulerUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;
import pers.zhangyang.easyguishop.easylibrary.yaml.MessageYaml;
import pers.zhangyang.easyguishop.easylibrary.EasyPlugin;

public class NotifyVersionUtil {
   public static void notifyVersion(final CommandSender sender) {
      HandySchedulerUtil.runTaskAsynchronously(() -> {
         final String latestVersion;
         String latestVersion1;
         try {
            // String urlStringPart1 = "https://zhangyang0204.github.io/"; // 原作者的网站，现已无法访问
            String urlStringPart1 = "https://warskygod.github.io/";
            String urlStringPart3 = "/index.html";
            String urlStringPart2 = ReplaceUtil.replaceToRepositoryName(EasyPlugin.instance.getName());
            latestVersion1 = ResourceUtil.readFirstLine(new URL(urlStringPart1 + urlStringPart2 + urlStringPart3));
         } catch (Throwable var5) {
            latestVersion1 = null;
         }

         latestVersion = latestVersion1;

         HandySchedulerUtil.runTask(() -> {
            List list;
            if (latestVersion != null) {
               list = MessageYaml.INSTANCE.getStringList("message.chat.notifyVersion");
               HashMap<String, String> hashMap = new HashMap();
               hashMap.put("{current_version}", EasyPlugin.instance.getDescription().getVersion());
               hashMap.put("{latest_version}", latestVersion);
               if (list != null) {
                  ReplaceUtil.replace((List)list, hashMap);
               }
            } else {
               list = MessageYaml.INSTANCE.getStringList("message.chat.failureGetLatestVersion");
            }

            MessageUtil.sendMessageTo(sender, list);
         });
      });
   }
}
