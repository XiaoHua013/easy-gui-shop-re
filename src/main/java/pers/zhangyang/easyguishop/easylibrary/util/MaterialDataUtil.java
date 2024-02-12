package pers.zhangyang.easyguishop.easylibrary.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;
import org.jetbrains.annotations.NotNull;

public class MaterialDataUtil {
   public static @NotNull MaterialData deserializeMaterialData(@NotNull String data) {
      Gson gson = new Gson();
      Type mapType = (new TypeToken<Map<String, String>>() {
      }).getType();
      Map<String, String> map = (Map)gson.fromJson(data, mapType);
      Material material = Material.matchMaterial((String)map.get("type"));
      if (material == null) {
         throw new IllegalArgumentException();
      } else {
         return new MaterialData(material, Byte.parseByte((String)map.get("data")));
      }
   }

   public static @NotNull String serializeMaterialData(@NotNull MaterialData materialData) {
      Map<String, String> m = new HashMap();
      m.put("type", materialData.getItemType().name());
      m.put("data", String.valueOf(materialData.getData()));
      return (new Gson()).toJson(m);
   }
}
