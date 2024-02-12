package pers.zhangyang.easyguishop.easylibrary.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.jetbrains.annotations.NotNull;

public class Md5Util {
   public static String getMd5Value(@NotNull String str) {
      if (str != null && str.length() != 0) {
         StringBuffer hexString = new StringBuffer();

         try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] hash = md.digest();

            for(int i = 0; i < hash.length; ++i) {
               if ((255 & hash[i]) < 16) {
                  hexString.append("0" + Integer.toHexString(255 & hash[i]));
               } else {
                  hexString.append(Integer.toHexString(255 & hash[i]));
               }
            }
         } catch (NoSuchAlgorithmException var5) {
            var5.printStackTrace();
         }

         return hexString.toString();
      } else {
         throw new IllegalArgumentException("String to encript cannot be null or zero length");
      }
   }
}
