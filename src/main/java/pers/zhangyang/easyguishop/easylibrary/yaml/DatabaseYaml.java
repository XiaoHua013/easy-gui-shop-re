package pers.zhangyang.easyguishop.easylibrary.yaml;

import pers.zhangyang.easyguishop.easylibrary.base.YamlBase;

public class DatabaseYaml extends YamlBase {
   public static final DatabaseYaml INSTANCE = new DatabaseYaml();

   protected DatabaseYaml() {
      super("database.yml");
   }
}
