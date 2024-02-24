package pers.zhangyang.easyguishop.easylibrary;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.handyplus.lib.adapter.HandySchedulerUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.zhangyang.easyguishop.easylibrary.annotation.EventListener;
import pers.zhangyang.easyguishop.easylibrary.base.ExecutorBase;
import pers.zhangyang.easyguishop.easylibrary.base.GuiPage;
import pers.zhangyang.easyguishop.easylibrary.base.YamlBase;
import pers.zhangyang.easyguishop.easylibrary.executor.CorrectYamlExecutor;
import pers.zhangyang.easyguishop.easylibrary.executor.HelpExecutor;
import pers.zhangyang.easyguishop.easylibrary.executor.ReloadPluginExecutor;
import pers.zhangyang.easyguishop.easylibrary.listener.PlayerClickGuiPage;
import pers.zhangyang.easyguishop.easylibrary.listener.PlayerJoin;
import pers.zhangyang.easyguishop.easylibrary.service.BaseService;
import pers.zhangyang.easyguishop.easylibrary.service.impl.BaseServiceImpl;
import pers.zhangyang.easyguishop.easylibrary.util.MessageUtil;
import pers.zhangyang.easyguishop.easylibrary.util.NotifyVersionUtil;
import pers.zhangyang.easyguishop.easylibrary.util.ResourceUtil;
import pers.zhangyang.easyguishop.easylibrary.util.TransactionInvocationHandler;
import pers.zhangyang.easyguishop.easylibrary.yaml.CompleterYaml;
import pers.zhangyang.easyguishop.easylibrary.yaml.DatabaseYaml;
import pers.zhangyang.easyguishop.easylibrary.yaml.MessageYaml;
import pers.zhangyang.easyguishop.easylibrary.yaml.SettingYaml;

public abstract class EasyPlugin extends JavaPlugin {
   public static EasyPlugin instance;

   public void onEnable() {
      HandySchedulerUtil.init(this);
      instance = this;

      try {
         SettingYaml.INSTANCE.init();
         CompleterYaml.INSTANCE.init();
         DatabaseYaml.INSTANCE.init();
         MessageYaml.INSTANCE.init();
         InputStream in = DatabaseYaml.class.getClassLoader().getResourceAsStream("easyLibrary.yml");
         YamlConfiguration yamlConfiguration = new YamlConfiguration();

         assert in != null;

         InputStreamReader inputStreamReader = new InputStreamReader(in, StandardCharsets.UTF_8);
         yamlConfiguration.load(inputStreamReader);
         List<Class> classList = ResourceUtil.getClassesFromJarFile(yamlConfiguration.getStringList("yamlPackage"));
         Iterator var5 = classList.iterator();

         while(var5.hasNext()) {
            Class c = (Class)var5.next();
            if (!Modifier.isInterface(c.getModifiers()) && !Modifier.isAbstract(c.getModifiers()) && YamlBase.class.isAssignableFrom(c)) {
               Class.forName(c.getName());
            }
         }
      } catch (InvalidConfigurationException | ClassNotFoundException | URISyntaxException | IOException var10) {
         throw new RuntimeException(var10);
      }

      this.onOpen();
      BaseService baseService = (BaseService)(new TransactionInvocationHandler(new BaseServiceImpl())).getProxy();
      baseService.initDatabase();
      Bukkit.getPluginManager().registerEvents(new PlayerClickGuiPage(), this);
      Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);

      try {
         InputStream in = DatabaseYaml.class.getClassLoader().getResourceAsStream("easyLibrary.yml");
         YamlConfiguration yamlConfiguration = new YamlConfiguration();

         assert in != null;

         InputStreamReader inputStreamReader = new InputStreamReader(in, StandardCharsets.UTF_8);
         yamlConfiguration.load(inputStreamReader);
         List<Class> classList = ResourceUtil.getClassesFromJarFile(yamlConfiguration.getStringList("listenerPackage"));
         Iterator var16 = classList.iterator();

         while(var16.hasNext()) {
            Class c = (Class)var16.next();
            if (c.isAnnotationPresent(EventListener.class) && !Modifier.isInterface(c.getModifiers()) && !Modifier.isAbstract(c.getModifiers()) && Listener.class.isAssignableFrom(c)) {
               Listener listener = (Listener)c.newInstance();
               Bukkit.getPluginManager().registerEvents(listener, instance);
            }
         }
      } catch (IllegalAccessException | IOException | InvalidConfigurationException | URISyntaxException | InstantiationException var9) {
         throw new RuntimeException(var9);
      }

      NotifyVersionUtil.notifyVersion(Bukkit.getConsoleSender());
      MessageUtil.sendMessageTo((CommandSender)Bukkit.getConsoleSender(), (List)MessageYaml.INSTANCE.getStringList("message.chat.enablePlugin"));
   }

   public void onDisable() {
      GuiPage.revoke();
      this.onClose();
      MessageUtil.sendMessageTo((CommandSender)Bukkit.getConsoleSender(), (List)MessageYaml.INSTANCE.getStringList("message.chat.disablePlugin"));
   }

   public abstract void onOpen();

   public abstract void onClose();

   public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
      if (!command.getName().equalsIgnoreCase(instance.getName())) {
         return true;
      } else {
         for(int i = 0; i < args.length; ++i) {
            args[i] = ChatColor.translateAlternateColorCodes('&', args[i]);
         }

         if (args.length == 0) {
            return true;
         } else {
            String[] argument = new String[args.length - 1];
            System.arraycopy(args, 1, argument, 0, args.length - 1);
            if (args[0].equalsIgnoreCase("CorrectYaml")) {
               (new CorrectYamlExecutor(sender, args[0], argument)).process();
            }

            if (args[0].equalsIgnoreCase("Help")) {
               (new HelpExecutor(sender, args[0], argument)).process();
            }

            if (args[0].equalsIgnoreCase("ReloadPlugin")) {
               (new ReloadPluginExecutor(sender, args[0], argument)).process();
            }

            try {
               InputStream in = DatabaseYaml.class.getClassLoader().getResourceAsStream("easyLibrary.yml");
               YamlConfiguration yamlConfiguration = new YamlConfiguration();

               assert in != null;

               InputStreamReader inputStreamReader = new InputStreamReader(in, StandardCharsets.UTF_8);
               yamlConfiguration.load(inputStreamReader);
               List<Class> classList = ResourceUtil.getClassesFromJarFile(yamlConfiguration.getStringList("executorPackage"));
               Iterator var10 = classList.iterator();

               while(var10.hasNext()) {
                  Class c = (Class)var10.next();
                  if (!Modifier.isInterface(c.getModifiers()) && !Modifier.isAbstract(c.getModifiers()) && ExecutorBase.class.isAssignableFrom(c)) {
                     Constructor<ExecutorBase> constructor = c.getDeclaredConstructor(CommandSender.class, String.class, String[].class);
                     constructor.setAccessible(true);
                     ExecutorBase executorBase = (ExecutorBase)constructor.newInstance(sender, args[0], argument);
                     String name = executorBase.getClass().getName();
                     name = name.substring(0, name.length() - 8);
                     name = name.split("\\.")[name.split("\\.").length - 1];
                     if (name.equalsIgnoreCase(args[0])) {
                        executorBase.process();
                     }
                  }
               }

               return true;
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException | IOException | InvalidConfigurationException | URISyntaxException | InstantiationException var15) {
               throw new RuntimeException(var15);
            }
         }
      }
   }

   public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
      if (args.length == 0) {
         return new ArrayList();
      } else {
         char[] pluginNameChars = this.getName().toCharArray();
         pluginNameChars[0] = Character.toLowerCase(pluginNameChars[0]);
         StringBuilder key = new StringBuilder(String.valueOf(pluginNameChars));
         List list;
         if (args.length == 1) {
            list = CompleterYaml.INSTANCE.getStringList("completer." + key);
            String ll = args[args.length - 1].toLowerCase();
            if (list != null) {
               list.removeIf((k) -> {
                  return !k.toString().toLowerCase().startsWith(ll);
               });
            }

            return (List)(list == null ? new ArrayList() : list);
         } else if (!sender.hasPermission(instance.getName() + "." + args[0])) {
            return new ArrayList();
         } else {
            char[] chars = args[0].toCharArray();
            if (chars.length != 0) {
               chars[0] = Character.toUpperCase(chars[0]);
            }

            key.append(chars);

            for(int i = 0; i < args.length - 2; ++i) {
               key.append("$");
            }

            list = CompleterYaml.INSTANCE.getStringList("completer." + key);
            String ll = args[args.length - 1].toLowerCase();
            if (list != null) {
               list.removeIf((k) -> {
                  return !k.toString().toLowerCase().startsWith(ll);
               });
            }

            return (List)(list == null ? new ArrayList() : list);
         }
      }
   }
}
