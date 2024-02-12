package pers.zhangyang.easyguishop.easylibrary.yaml;

import pers.zhangyang.easyguishop.easylibrary.base.YamlBase;

public class CompleterYaml extends YamlBase {
   public static final CompleterYaml INSTANCE = new CompleterYaml();

   protected CompleterYaml() {
      super("display/" + SettingYaml.INSTANCE.getDisplay() + "/completer.yml");
   }
}
