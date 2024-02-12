package pers.zhangyang.easyguishop.easylibrary.base;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.zhangyang.easyguishop.easylibrary.exception.NotApplicableException;
import pers.zhangyang.easyguishop.easylibrary.exception.UnsupportedMinecraftVersionException;
import pers.zhangyang.easyguishop.easylibrary.EasyPlugin;
import pers.zhangyang.easyguishop.easylibrary.util.ItemStackUtil;
import pers.zhangyang.easyguishop.easylibrary.util.VersionUtil;
import pers.zhangyang.easyguishop.easylibrary.yaml.DatabaseYaml;

public abstract class YamlBase {
   protected YamlConfiguration yamlConfiguration;
   protected String filePath;
   protected YamlConfiguration backUpConfiguration;

   protected YamlBase(@NotNull String filePath) {
      this.filePath = filePath;
      this.yamlConfiguration = new YamlConfiguration();
      this.backUpConfiguration = new YamlConfiguration();
      this.init();
   }

   public void init() {
      try {
         File file = new File(EasyPlugin.instance.getDataFolder() + "/" + this.filePath);
         int i;
         if (!file.exists()) {
            File dir = file.getParentFile();
            if (!dir.exists() && !dir.mkdirs()) {
               throw new IOException();
            }

            InputStream in = DatabaseYaml.class.getClassLoader().getResourceAsStream(this.filePath);
            if (in == null) {
               throw new IOException();
            }

            OutputStream out = Files.newOutputStream(file.toPath());
            byte[] buf = new byte[1024];

            while((i = in.read(buf)) > 0) {
               out.write(buf, 0, i);
            }

            out.close();
            in.close();
         }

         this.yamlConfiguration.load(file);
         InputStream in = YamlBase.class.getClassLoader().getResourceAsStream(this.filePath);
         if (this.filePath.startsWith("display/")) {
            List<String> argList = new ArrayList();
            String[] after = this.filePath.split("/");
            String[] var14 = after;
            i = after.length;

            for(int var7 = 0; var7 < i; ++var7) {
               String s = var14[var7];
               if (!s.contains("\\")) {
                  argList.add(s);
               } else {
                  argList.addAll(Arrays.asList(s.split("\\\\")));
               }
            }

            StringBuilder defaultFilePath = new StringBuilder();

            for(i = 0; i < argList.size(); ++i) {
               if (i == 1) {
                  defaultFilePath.append("default/");
               } else if (i != argList.size() - 1) {
                  defaultFilePath.append((String)argList.get(i)).append("/");
               } else {
                  defaultFilePath.append((String)argList.get(i));
               }
            }

            in = YamlBase.class.getClassLoader().getResourceAsStream(defaultFilePath.toString());
         }

         if (in == null) {
            throw new IOException();
         } else {
            InputStreamReader inputStreamReader = new InputStreamReader(in, StandardCharsets.UTF_8);
            this.backUpConfiguration.load(inputStreamReader);
         }
      } catch (InvalidConfigurationException | IOException var9) {
         throw new RuntimeException(var9);
      }
   }

   public void correct() {
      Iterator var1 = this.yamlConfiguration.getKeys(true).iterator();

      String pathBase;
      Object ob;
      while(var1.hasNext()) {
         pathBase = (String)var1.next();
         if (!this.backUpConfiguration.getKeys(true).contains(pathBase)) {
            ob = this.yamlConfiguration.get(pathBase);
            this.yamlConfiguration.set(pathBase, (Object)null);

            try {
               this.yamlConfiguration.save(EasyPlugin.instance.getDataFolder() + "/" + this.filePath);
            } catch (IOException var6) {
               this.yamlConfiguration.set(pathBase, ob);
               throw new RuntimeException(var6);
            }
         }
      }

      var1 = this.backUpConfiguration.getKeys(true).iterator();

      while(var1.hasNext()) {
         pathBase = (String)var1.next();
         if (!this.yamlConfiguration.getKeys(true).contains(pathBase)) {
            ob = this.yamlConfiguration.get(pathBase);
            this.yamlConfiguration.set(pathBase, this.backUpConfiguration.get(pathBase));

            try {
               this.yamlConfiguration.save(EasyPlugin.instance.getDataFolder() + "/" + this.filePath);
            } catch (IOException var5) {
               this.yamlConfiguration.set(pathBase, ob);
               throw new RuntimeException(var5);
            }
         }
      }

   }

   public @Nullable Boolean getBoolean(@NotNull String path) {
      return !this.yamlConfiguration.isBoolean(path) ? null : this.yamlConfiguration.getBoolean(path);
   }

   public @NotNull Boolean getBooleanDefault(@NotNull String path) {
      return !this.yamlConfiguration.isBoolean(path) ? this.backUpConfiguration.getBoolean(path) : this.yamlConfiguration.getBoolean(path);
   }

   public @NotNull String getStringDefault(@NotNull String path) {
      return !this.yamlConfiguration.isString(path) ? (String)Objects.requireNonNull(this.backUpConfiguration.getString(path)) : (String)Objects.requireNonNull(this.yamlConfiguration.getString(path));
   }

   public @Nullable String getString(@NotNull String path) {
      return !this.yamlConfiguration.isString(path) ? null : this.yamlConfiguration.getString(path);
   }

   public @Nullable Integer getInteger(@NotNull String path) {
      return !this.yamlConfiguration.isInt(path) ? null : this.yamlConfiguration.getInt(path);
   }

   public @NotNull Integer getIntegerDefault(@NotNull String path) {
      return !this.yamlConfiguration.isInt(path) ? this.backUpConfiguration.getInt(path) : this.yamlConfiguration.getInt(path);
   }

   public @Nullable Long getLong(@NotNull String path) {
      return !this.yamlConfiguration.isLong(path) ? null : this.yamlConfiguration.getLong(path);
   }

   public @NotNull Long getLongDefault(@NotNull String path) {
      return !this.yamlConfiguration.isLong(path) ? this.backUpConfiguration.getLong(path) : this.yamlConfiguration.getLong(path);
   }

   public @Nullable Double getDouble(@NotNull String path) {
      return !this.yamlConfiguration.isDouble(path) ? null : this.yamlConfiguration.getDouble(path);
   }

   public @NotNull Double getDoubleDefault(@NotNull String path) {
      return !this.yamlConfiguration.isDouble(path) ? this.backUpConfiguration.getDouble(path) : this.yamlConfiguration.getDouble(path);
   }

   public @Nullable List<String> getStringList(@NotNull String path) {
      return !this.yamlConfiguration.isList(path) ? null : this.yamlConfiguration.getStringList(path);
   }

   public @NotNull List<String> getStringListDefault(@NotNull String path) {
      return !this.yamlConfiguration.isList(path) ? this.backUpConfiguration.getStringList(path) : this.yamlConfiguration.getStringList(path);
   }

   public @NotNull Location getLocationDefault(@NotNull String path) {
      Double x = this.getDoubleDefault(path + ".x");
      Double y = this.getDoubleDefault(path + ".y");
      Double z = this.getDoubleDefault(path + ".z");
      Double yaw = this.getDoubleDefault(path + ".yaw");
      Double pitch = this.getDoubleDefault(path + ".pitch");
      String worldName = this.getStringDefault(path + ".worldName");
      World world = Bukkit.getWorld(worldName);
      if (world == null) {
         worldName = this.backUpConfiguration.getString(path + ".worldName");

         assert worldName != null;

         world = Bukkit.getWorld(worldName);
      }

      return new Location(world, x, y, z, yaw.floatValue(), pitch.floatValue());
   }

   public @Nullable Location getLocation(@NotNull String path) {
      Double x = this.getDouble(path + ".x");
      Double y = this.getDouble(path + ".y");
      Double z = this.getDouble(path + ".z");
      Double yaw = this.getDouble(path + ".yaw");
      Double pitch = this.getDouble(path + ".pitch");
      String worldName = this.getString(path + ".worldName");
      if (worldName != null && x != null && y != null && z != null && yaw != null && pitch != null) {
         World world = Bukkit.getWorld(worldName);
         return world == null ? null : new Location(world, x, y, z, yaw.floatValue(), pitch.floatValue());
      } else {
         return null;
      }
   }

   public @Nullable Integer getNonnegativeInteger(@NotNull String path) {
      Integer var = this.getInteger(path);
      if (var != null && var < 0) {
         var = this.backUpConfiguration.getInt(path);
      }

      return var;
   }

   public @Nullable ItemStack getItemStack(@NotNull String path) {
      return this.yamlConfiguration.getItemStack(path);
   }

   public @NotNull ItemStack getItemStackDefault(@NotNull String path) {
      ItemStack var = this.yamlConfiguration.getItemStack(path);
      if (var == null) {
         var = this.backUpConfiguration.getItemStack(path);
      }

      assert var != null;

      return var;
   }

   public @NotNull Integer getNonnegativeIntegerDefault(@NotNull String path) {
      int var = this.getIntegerDefault(path);
      if (var < 0) {
         var = this.backUpConfiguration.getInt(path);
      }

      return var;
   }

   public @Nullable Double getNonnegativeDouble(@NotNull String path) {
      Double var = this.getDouble(path);
      if (var != null && var < 0.0) {
         var = this.backUpConfiguration.getDouble(path);
      }

      return var;
   }

   public @NotNull Double getNonnegativeDoubleDefault(@NotNull String path) {
      double var = this.getDoubleDefault(path);
      if (var < 0.0) {
         var = this.backUpConfiguration.getDouble(path);
      }

      return var;
   }

   public @NotNull ItemStack getButtonDefault(@NotNull String path) {
      String materialName = this.getStringDefault(path + ".materialName");
      Material material = Material.matchMaterial(materialName);
      if (material == null) {
         materialName = this.backUpConfiguration.getString(path + ".materialName");

         assert materialName != null;

         material = Material.matchMaterial(materialName);
      }

      assert material != null;

      if (material.equals(Material.AIR)) {
         return new ItemStack(Material.AIR);
      } else {
         String displayName = this.getString(path + ".displayName");
         List<String> lore = this.getStringList(path + ".lore");
         int amount = this.getIntegerDefault(path + ".amount");
         List<String> itemFlagName = this.getStringListDefault(path + ".itemFlag");
         List<ItemFlag> itemFlagList = new ArrayList();
         Integer customModelData = this.getInteger(path + ".amount");
         Iterator var10 = itemFlagName.iterator();

         while(var10.hasNext()) {
            String s = (String)var10.next();

            try {
               itemFlagList.add(ItemFlag.valueOf(s));
            } catch (IllegalArgumentException var16) {
            }
         }

         if (VersionUtil.getMinecraftBigVersion() == 1 && VersionUtil.getMinecraftMiddleVersion() < 13) {
            try {
               return ItemStackUtil.getItemStack(material, displayName, lore, itemFlagList, amount);
            } catch (NotApplicableException var13) {
               return new ItemStack(material);
            }
         } else {
            try {
               return ItemStackUtil.getItemStack(material, displayName, lore, itemFlagList, amount, customModelData);
            } catch (NotApplicableException var14) {
               return new ItemStack(material);
            } catch (UnsupportedMinecraftVersionException var15) {
               var15.printStackTrace();
               return null;
            }
         }
      }
   }

   public @Nullable ItemStack getButton(@NotNull String path) {
      String materialName = this.getStringDefault(path + ".materialName");
      Material material = Material.matchMaterial(materialName);
      if (material == null) {
         return null;
      } else if (material.equals(Material.AIR)) {
         return new ItemStack(Material.AIR);
      } else {
         String displayName = this.getString(path + ".displayName");
         List<String> lore = this.getStringList(path + ".lore");
         int amount = this.getIntegerDefault(path + ".amount");
         List<String> itemFlagName = this.getStringListDefault(path + ".itemFlag");
         List<ItemFlag> itemFlagList = new ArrayList();
         Integer customModelData = this.getInteger(path + ".amount");
         Iterator var10 = itemFlagName.iterator();

         while(var10.hasNext()) {
            String s = (String)var10.next();

            try {
               itemFlagList.add(ItemFlag.valueOf(s));
            } catch (IllegalArgumentException var16) {
            }
         }

         if (VersionUtil.getMinecraftBigVersion() == 1 && VersionUtil.getMinecraftMiddleVersion() < 13) {
            try {
               return ItemStackUtil.getItemStack(material, displayName, lore, itemFlagList, amount);
            } catch (NotApplicableException var13) {
               return new ItemStack(material);
            }
         } else {
            try {
               return ItemStackUtil.getItemStack(material, displayName, lore, itemFlagList, amount, customModelData);
            } catch (NotApplicableException var14) {
               return new ItemStack(material);
            } catch (UnsupportedMinecraftVersionException var15) {
               var15.printStackTrace();
               return null;
            }
         }
      }
   }

   public @NotNull String getNonemptyStringDefault(@NotNull String path) {
      String display = this.getStringDefault(path);
      if (display.isEmpty()) {
         display = this.backUpConfiguration.getString(path);
      }

      assert display != null;

      return display;
   }

   public @Nullable String getNonemptyString(@NotNull String path) {
      String display = this.getString(path);
      if (display != null && display.isEmpty()) {
         display = this.backUpConfiguration.getString(path);
      }

      assert display != null;

      return display;
   }
}
