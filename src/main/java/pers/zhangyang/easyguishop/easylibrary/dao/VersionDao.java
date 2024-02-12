package pers.zhangyang.easyguishop.easylibrary.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.zhangyang.easyguishop.easylibrary.meta.VersionMeta;
import pers.zhangyang.easyguishop.easylibrary.base.DaoBase;

public class VersionDao extends DaoBase {
   public int init() {
      try {
         PreparedStatement ps = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS version (  big INT   ,  middle INT   ,  small INT  )");
         return ps.executeUpdate();
      } catch (SQLException var2) {
         throw new RuntimeException(var2);
      }
   }

   public @Nullable VersionMeta get() {
      try {
         PreparedStatement ps = getConnection().prepareStatement("select * from version");
         ResultSet rs = ps.executeQuery();
         return (VersionMeta)this.singleTransform(rs, VersionMeta.class);
      } catch (SQLException var3) {
         throw new RuntimeException(var3);
      }
   }

   public int insert(@NotNull VersionMeta version) {
      try {
         PreparedStatement ps = getConnection().prepareStatement("insert into version (big,middle,small)values(?,?,?)");
         ps.setInt(1, version.getBig());
         ps.setInt(2, version.getMiddle());
         ps.setInt(3, version.getSmall());
         return ps.executeUpdate();
      } catch (SQLException var3) {
         throw new RuntimeException(var3);
      }
   }

   public int delete() {
      try {
         PreparedStatement ps = getConnection().prepareStatement("delete from version ");
         return ps.executeUpdate();
      } catch (SQLException var2) {
         throw new RuntimeException(var2);
      }
   }
}
