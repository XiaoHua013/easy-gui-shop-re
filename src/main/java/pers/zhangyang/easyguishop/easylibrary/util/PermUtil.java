package pers.zhangyang.easyguishop.easylibrary.util;

import java.util.Iterator;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.jetbrains.annotations.Nullable;

public class PermUtil {
   public static @Nullable Integer getMaxNumberPerm(String startWith, Player player2) {
      Integer max = null;
      Iterator var3 = player2.getEffectivePermissions().iterator();

      while(true) {
         int current;
         do {
            while(true) {
               PermissionAttachmentInfo player;
               int endIndex;
               do {
                  do {
                     if (!var3.hasNext()) {
                        return max;
                     }

                     player = (PermissionAttachmentInfo)var3.next();
                  } while(!player.getPermission().startsWith(startWith.toLowerCase()));

                  endIndex = player.getPermission().split("\\.").length - 1;
               } while(endIndex < 0);

               try {
                  current = Integer.parseInt(player.getPermission().split("\\.")[endIndex]);
                  break;
               } catch (NumberFormatException var8) {
               }
            }
         } while(max != null && current <= max);

         max = current;
      }
   }

   public static @Nullable Integer getMinNumberPerm(String startWith, Player player2) {
      Integer min = null;
      Iterator var3 = player2.getEffectivePermissions().iterator();

      while(true) {
         int current;
         do {
            while(true) {
               PermissionAttachmentInfo player;
               int endIndex;
               do {
                  do {
                     if (!var3.hasNext()) {
                        return min;
                     }

                     player = (PermissionAttachmentInfo)var3.next();
                  } while(!player.getPermission().startsWith(startWith.toLowerCase()));

                  endIndex = player.getPermission().split("\\.").length - 1;
               } while(endIndex < 0);

               try {
                  current = Integer.parseInt(player.getPermission().split("\\.")[endIndex]);
                  break;
               } catch (NumberFormatException var8) {
               }
            }
         } while(min != null && current >= min);

         min = current;
      }
   }
}
