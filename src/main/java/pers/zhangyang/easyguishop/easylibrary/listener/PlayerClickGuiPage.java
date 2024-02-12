package pers.zhangyang.easyguishop.easylibrary.listener;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import pers.zhangyang.easyguishop.easylibrary.annotation.EventListener;
import pers.zhangyang.easyguishop.easylibrary.annotation.GuiDiscreteButtonHandler;
import pers.zhangyang.easyguishop.easylibrary.annotation.GuiSerialButtonHandler;
import pers.zhangyang.easyguishop.easylibrary.base.BackAble;
import pers.zhangyang.easyguishop.easylibrary.base.GuiPage;
import pers.zhangyang.easyguishop.easylibrary.base.MultipleGuiPageBase;
import pers.zhangyang.easyguishop.easylibrary.exception.NotExistNextPageException;
import pers.zhangyang.easyguishop.easylibrary.exception.NotExistPreviousPageException;
import pers.zhangyang.easyguishop.easylibrary.util.MessageUtil;
import pers.zhangyang.easyguishop.easylibrary.util.ResourceUtil;
import pers.zhangyang.easyguishop.easylibrary.yaml.DatabaseYaml;
import pers.zhangyang.easyguishop.easylibrary.yaml.MessageYaml;

public class PlayerClickGuiPage implements Listener {
   @EventHandler
   public void on(InventoryClickEvent event) {
      if (event.getWhoClicked() instanceof Player) {
         Player player = (Player)event.getWhoClicked();
         InventoryHolder inventoryHolder = event.getInventory().getHolder();
         int slot = event.getRawSlot();
         ItemStack itemStack = event.getCurrentItem();
         if (itemStack != null && !itemStack.getType().equals(Material.AIR)) {
            if (inventoryHolder instanceof GuiPage) {
               event.setCancelled(true);
               if (inventoryHolder instanceof BackAble) {
                  BackAble backAble = (BackAble)inventoryHolder;
                  if (backAble.getBackSlot() == slot) {
                     backAble.back();
                  }
               }

               MultipleGuiPageBase multipleGuiPageBase;
               if (inventoryHolder instanceof MultipleGuiPageBase) {
                  multipleGuiPageBase = (MultipleGuiPageBase)inventoryHolder;
                  if (multipleGuiPageBase.getNextPageSlot() == slot) {
                     try {
                        multipleGuiPageBase.nextPage();
                     } catch (NotExistNextPageException var28) {
                        MessageUtil.sendMessageTo((CommandSender)event.getWhoClicked(), (List)MessageYaml.INSTANCE.getStringList("message.chat.notExistNextPage"));
                     }
                  }
               }

               if (inventoryHolder instanceof MultipleGuiPageBase) {
                  multipleGuiPageBase = (MultipleGuiPageBase)inventoryHolder;
                  if (multipleGuiPageBase.getPreviousPageSlot() == slot) {
                     try {
                        multipleGuiPageBase.previousPage();
                     } catch (NotExistPreviousPageException var27) {
                        MessageUtil.sendMessageTo((CommandSender)event.getWhoClicked(), (List)MessageYaml.INSTANCE.getStringList("message.chat.notExistPreviousPage"));
                     }
                  }
               }
            }

            InputStream in = DatabaseYaml.class.getClassLoader().getResourceAsStream("easyLibrary.yml");
            YamlConfiguration yamlConfiguration = new YamlConfiguration();

            assert in != null;

            InputStreamReader inputStreamReader = new InputStreamReader(in, StandardCharsets.UTF_8);

            try {
               yamlConfiguration.load(inputStreamReader);
            } catch (InvalidConfigurationException | IOException var26) {
               throw new RuntimeException(var26);
            }

            List classList;
            try {
               classList = ResourceUtil.getClassesFromJarFile(yamlConfiguration.getStringList("guiButtonHandlerPackage"));
            } catch (URISyntaxException var25) {
               throw new RuntimeException(var25);
            }

            Iterator var10 = classList.iterator();

            while(true) {
               Class c;
               do {
                  do {
                     do {
                        if (!var10.hasNext()) {
                           return;
                        }

                        c = (Class)var10.next();
                     } while(!c.isAnnotationPresent(EventListener.class));
                  } while(Modifier.isInterface(c.getModifiers()));
               } while(Modifier.isAbstract(c.getModifiers()));

               Method[] methods = c.getMethods();
               Method[] var13 = methods;
               int var14 = methods.length;

               for(int var15 = 0; var15 < var14; ++var15) {
                  Method m = var13[var15];
                  if (m.isAnnotationPresent(GuiDiscreteButtonHandler.class)) {
                     GuiDiscreteButtonHandler guiDiscreteButtonHandler = (GuiDiscreteButtonHandler)m.getAnnotation(GuiDiscreteButtonHandler.class);
                     if (!guiDiscreteButtonHandler.guiPage().isInstance(inventoryHolder)) {
                        continue;
                     }

                     List<Integer> integerList = new ArrayList();
                     int[] var19 = guiDiscreteButtonHandler.slot();
                     int var20 = var19.length;

                     for(int var21 = 0; var21 < var20; ++var21) {
                        int e = var19[var21];
                        integerList.add(e);
                     }

                     if (!integerList.contains(slot)) {
                        continue;
                     }

                     try {
                        m.setAccessible(true);
                        m.invoke(c.newInstance(), event);
                     } catch (InvocationTargetException | InstantiationException | IllegalAccessException var24) {
                        var24.printStackTrace();
                     }

                     if (inventoryHolder instanceof GuiPage && guiDiscreteButtonHandler.refreshGui()) {
                        GuiPage guiPage = (GuiPage)inventoryHolder;
                        guiPage.refresh();
                     }

                     if (guiDiscreteButtonHandler.closeGui()) {
                        player.closeInventory();
                     }
                  }

                  if (m.isAnnotationPresent(GuiSerialButtonHandler.class)) {
                     GuiSerialButtonHandler guiSerialButtonHandler = (GuiSerialButtonHandler)m.getAnnotation(GuiSerialButtonHandler.class);
                     if (guiSerialButtonHandler.guiPage().isInstance(inventoryHolder) && slot <= Math.max(guiSerialButtonHandler.from(), guiSerialButtonHandler.to()) && slot >= Math.min(guiSerialButtonHandler.from(), guiSerialButtonHandler.to())) {
                        try {
                           m.setAccessible(true);
                           m.invoke(c.newInstance(), event);
                        } catch (InvocationTargetException | InstantiationException | IllegalAccessException var23) {
                           var23.printStackTrace();
                        }

                        if (inventoryHolder instanceof GuiPage && guiSerialButtonHandler.refreshGui()) {
                           GuiPage guiPage = (GuiPage)inventoryHolder;
                           guiPage.refresh();
                        }

                        if (guiSerialButtonHandler.closeGui()) {
                           player.closeInventory();
                        }
                     }
                  }
               }
            }
         }
      }
   }
}
