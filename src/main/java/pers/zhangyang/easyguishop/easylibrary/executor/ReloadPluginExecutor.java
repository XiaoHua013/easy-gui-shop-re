package pers.zhangyang.easyguishop.easylibrary.executor;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import pers.zhangyang.easyguishop.easylibrary.EasyPlugin;
import pers.zhangyang.easyguishop.easylibrary.base.GuiPage;
import pers.zhangyang.easyguishop.easylibrary.base.YamlBase;
import pers.zhangyang.easyguishop.easylibrary.service.CommandService;
import pers.zhangyang.easyguishop.easylibrary.service.impl.CommandServiceImpl;
import pers.zhangyang.easyguishop.easylibrary.util.MessageUtil;
import pers.zhangyang.easyguishop.easylibrary.util.ReplaceUtil;
import pers.zhangyang.easyguishop.easylibrary.util.ResourceUtil;
import pers.zhangyang.easyguishop.easylibrary.util.TransactionInvocationHandler;
import pers.zhangyang.easyguishop.easylibrary.yaml.CompleterYaml;
import pers.zhangyang.easyguishop.easylibrary.yaml.DatabaseYaml;
import pers.zhangyang.easyguishop.easylibrary.yaml.MessageYaml;
import pers.zhangyang.easyguishop.easylibrary.yaml.SettingYaml;

public class ReloadPluginExecutor {
   protected CommandSender sender;
   protected String[] args;
   protected String commandName;

   public ReloadPluginExecutor(@NotNull CommandSender sender, String commandName, @NotNull String[] args) {
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
      try {
         CompleterYaml.INSTANCE.init();
         DatabaseYaml.INSTANCE.init();
         MessageYaml.INSTANCE.init();
         SettingYaml.INSTANCE.init();
         InputStream in = DatabaseYaml.class.getClassLoader().getResourceAsStream("easyLibrary.yml");
         YamlConfiguration yamlConfiguration = new YamlConfiguration();
         InputStreamReader inputStreamReader = new InputStreamReader(in, StandardCharsets.UTF_8);
         yamlConfiguration.load(inputStreamReader);
         List<Class> classList = ResourceUtil.getClassesFromJarFile(yamlConfiguration.getStringList("yamlPackage"));
         Iterator var5 = classList.iterator();

         while(var5.hasNext()) {
            Class c = (Class)var5.next();
            if (!Modifier.isInterface(c.getModifiers()) && !Modifier.isAbstract(c.getModifiers()) && YamlBase.class.isAssignableFrom(c)) {
               YamlBase yamlBase = (YamlBase)c.getField("INSTANCE").get(c);
               yamlBase.init();
            }
         }
      } catch (IllegalAccessException | IOException | InvalidConfigurationException | URISyntaxException | NoSuchFieldException var8) {
         throw new RuntimeException(var8);
      }

      GuiPage.revoke();
      CommandService guiService = (CommandService)(new TransactionInvocationHandler(new CommandServiceImpl())).getProxy();
      guiService.initDatabase();
   }
}
