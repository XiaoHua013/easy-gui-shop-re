package pers.zhangyang.easyguishop.easylibrary.yaml;

import org.jetbrains.annotations.NotNull;
import pers.zhangyang.easyguishop.easylibrary.base.YamlBase;

public class SettingYaml extends YamlBase {
   public static final SettingYaml INSTANCE = new SettingYaml();

   protected SettingYaml() {
      super("setting.yml");
   }

   public @NotNull String getDisplay() {
      String display = this.getStringDefault("setting.display");
      if (SettingYaml.class.getClassLoader().getResource("display/" + display) == null) {
         display = this.backUpConfiguration.getString("setting.display");
      }

      assert display != null;

      return display;
   }
}
