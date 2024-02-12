package pers.zhangyang.easyguishop.easylibrary.yaml;

import pers.zhangyang.easyguishop.easylibrary.base.YamlBase;

public class MessageYaml extends YamlBase {
   public static final MessageYaml INSTANCE = new MessageYaml();

   protected MessageYaml() {
      super("display/" + SettingYaml.INSTANCE.getDisplay() + "/message.yml");
   }
}
