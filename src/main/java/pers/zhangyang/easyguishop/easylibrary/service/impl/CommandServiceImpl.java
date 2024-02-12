package pers.zhangyang.easyguishop.easylibrary.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import pers.zhangyang.easyguishop.easylibrary.service.CommandService;
import pers.zhangyang.easyguishop.easylibrary.base.DaoBase;
import pers.zhangyang.easyguishop.easylibrary.dao.VersionDao;
import pers.zhangyang.easyguishop.easylibrary.meta.VersionMeta;
import pers.zhangyang.easyguishop.easylibrary.util.ResourceUtil;
import pers.zhangyang.easyguishop.easylibrary.util.VersionUtil;
import pers.zhangyang.easyguishop.easylibrary.yaml.DatabaseYaml;

public class CommandServiceImpl implements CommandService {
   public void initDatabase() {
      VersionDao versionDao = new VersionDao();
      versionDao.init();
      if (versionDao.get() == null) {
         versionDao.delete();
         versionDao.insert(new VersionMeta(VersionUtil.getPluginBigVersion(), VersionUtil.getPluginMiddleVersion(), VersionUtil.getPluginSmallVersion()));
      }

      InputStream in = DatabaseYaml.class.getClassLoader().getResourceAsStream("easyLibrary.yml");
      YamlConfiguration yamlConfiguration = new YamlConfiguration();
      InputStreamReader inputStreamReader = new InputStreamReader(in, StandardCharsets.UTF_8);

      try {
         yamlConfiguration.load(inputStreamReader);
      } catch (InvalidConfigurationException | IOException var12) {
         throw new RuntimeException(var12);
      }

      List<Class> classList = null;

      try {
         classList = ResourceUtil.getClassesFromJarFile(yamlConfiguration.getStringList("daoPackage"));
      } catch (URISyntaxException var11) {
         throw new RuntimeException(var11);
      }

      Iterator var6 = classList.iterator();

      while(var6.hasNext()) {
         Class c = (Class)var6.next();
         if (!Modifier.isInterface(c.getModifiers()) && !Modifier.isAbstract(c.getModifiers()) && DaoBase.class.isAssignableFrom(c)) {
            DaoBase daoBase = null;

            try {
               daoBase = (DaoBase)c.newInstance();
            } catch (IllegalAccessException | InstantiationException var10) {
               var10.printStackTrace();
            }

            assert daoBase != null;

            daoBase.init();
         }
      }

   }
}
