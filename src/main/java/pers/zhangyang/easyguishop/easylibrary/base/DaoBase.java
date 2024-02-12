package pers.zhangyang.easyguishop.easylibrary.base;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.zhangyang.easyguishop.easylibrary.util.ReplaceUtil;
import pers.zhangyang.easyguishop.easylibrary.yaml.DatabaseYaml;

public abstract class DaoBase {
   private static final ThreadLocal<Connection> t = new ThreadLocal();

   public static Connection getConnection() {
      Connection connection = (Connection)t.get();

      try {
         if (connection == null || connection.isClosed()) {
            DatabaseYaml databaseYamlManager = DatabaseYaml.INSTANCE;
            Class.forName("org.sqlite.JDBC");
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(databaseYamlManager.getStringDefault("database.url"), databaseYamlManager.getString("database.username"), databaseYamlManager.getString("database.password"));
            connection.setAutoCommit(false);
            t.set(connection);
         }
      } catch (SQLException var2) {
         throw new RuntimeException(var2);
      } catch (ClassNotFoundException var3) {
         var3.printStackTrace();
      }

      return connection;
   }

   public <T> @Nullable T singleTransform(ResultSet rs, Class<T> cls) {
      try {
         if (rs.next()) {
            T obj = cls.newInstance();
            Field[] arrf = cls.getDeclaredFields();
            Field[] var5 = arrf;
            int var6 = arrf.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               Field f = var5[var7];
               f.setAccessible(true);
               if (f.getType().isAssignableFrom(Boolean.TYPE)) {
                  f.set(obj, rs.getBoolean(ReplaceUtil.replaceToDatabaseTableName(f.getName())));
               } else {
                  f.set(obj, rs.getObject(ReplaceUtil.replaceToDatabaseTableName(f.getName())));
               }
            }

            return obj;
         } else {
            return null;
         }
      } catch (Exception var9) {
         throw new RuntimeException(var9);
      }
   }

   public <T> @NotNull List<T> multipleTransform(ResultSet rs, Class<T> cls) {
      List<T> list = new ArrayList();

      try {
         while(rs.next()) {
            T obj = cls.newInstance();
            Field[] arrf = cls.getDeclaredFields();
            Field[] var6 = arrf;
            int var7 = arrf.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               Field f = var6[var8];
               f.setAccessible(true);
               if (f.getType().isAssignableFrom(Boolean.TYPE)) {
                  f.set(obj, rs.getBoolean(ReplaceUtil.replaceToDatabaseTableName(f.getName())));
               } else {
                  f.set(obj, rs.getObject(ReplaceUtil.replaceToDatabaseTableName(f.getName())));
               }
            }

            list.add(obj);
         }

         return list;
      } catch (Exception var10) {
         throw new RuntimeException(var10);
      }
   }

   public abstract int init();
}
