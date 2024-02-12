package pers.zhangyang.easyguishop.easylibrary.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.zhangyang.easyguishop.easylibrary.exception.FailureDeleteFileException;

public class ResourceUtil {
   public static List<Class> getClassesFromJarFile(List<String> packageList) throws URISyntaxException {
      String jarPaht = ResourceUtil.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
      List<Class> clazzs = new ArrayList();

      JarFile jarFile;
      try {
         jarFile = new JarFile(jarPaht);
      } catch (IOException var11) {
         throw new RuntimeException(var11);
      }

      List<JarEntry> jarEntryList = new ArrayList();
      Enumeration<JarEntry> ee = jarFile.entries();

      String className;
      while(ee.hasMoreElements()) {
         JarEntry entry = (JarEntry)ee.nextElement();
         Iterator var7 = packageList.iterator();

         while(var7.hasNext()) {
            className = (String)var7.next();
            if (entry.getName().startsWith(className) && entry.getName().endsWith(".class")) {
               jarEntryList.add(entry);
            }
         }
      }

      Iterator var12 = jarEntryList.iterator();

      while(var12.hasNext()) {
         JarEntry entry = (JarEntry)var12.next();
         className = entry.getName().replace('/', '.');
         className = className.substring(0, className.length() - 6);

         try {
            clazzs.add(ResourceUtil.class.getClassLoader().loadClass(className));
         } catch (ClassNotFoundException var10) {
            var10.printStackTrace();
         }
      }

      return clazzs;
   }

   public static void deleteFile(@NotNull File file) throws FailureDeleteFileException {
      File[] files = file.listFiles();
      if (files != null) {
         File[] var2 = files;
         int var3 = files.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            File deleteFile = var2[var4];
            if (deleteFile.isDirectory()) {
               deleteFile(deleteFile);
            } else {
               deleteFile(deleteFile);
            }
         }

         if (!file.delete()) {
            throw new FailureDeleteFileException();
         }
      }
   }

   public static @Nullable String readFirstLine(URL url) throws IOException {
      if (url == null) {
         throw new NullPointerException();
      } else {
         InputStream is = url.openStream();
         BufferedReader br = new BufferedReader(new InputStreamReader(is));
         return br.readLine();
      }
   }
}
