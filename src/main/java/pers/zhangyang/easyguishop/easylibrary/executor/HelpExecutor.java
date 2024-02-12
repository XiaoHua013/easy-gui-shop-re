package pers.zhangyang.easyguishop.easylibrary.executor;

import java.util.Collections;
import java.util.List;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import pers.zhangyang.easyguishop.easylibrary.EasyPlugin;
import pers.zhangyang.easyguishop.easylibrary.util.MessageUtil;
import pers.zhangyang.easyguishop.easylibrary.util.ReplaceUtil;
import pers.zhangyang.easyguishop.easylibrary.yaml.MessageYaml;

public class HelpExecutor {
   protected CommandSender sender;
   protected String[] args;
   protected String commandName;

   public HelpExecutor(@NotNull CommandSender sender, String commandName, @NotNull String[] args) {
      this.sender = sender;
      this.commandName = commandName;
      this.args = args;
   }

   public void process() {
      String permission = EasyPlugin.instance.getName() + "." + this.commandName;
      if (!this.sender.hasPermission(permission)) {
         List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notPermission");
         if (list != null) {
            ReplaceUtil.replace(list, Collections.singletonMap("{permission}", permission));
         }

         MessageUtil.sendMessageTo(this.sender, list);
      } else {
         this.run();
      }
   }

   protected void run() {
      MessageUtil.sendMessageTo(this.sender, MessageYaml.INSTANCE.getStringList("message.chat.help"));
   }
}
