package pers.zhangyang.easyguishop.easylibrary.meta;

public class VersionMeta {
   private int big;
   private int middle;
   private int small;

   public VersionMeta() {
   }

   public VersionMeta(int big, int middle, int small) {
      this.big = big;
      this.middle = middle;
      this.small = small;
   }

   public int getBig() {
      return this.big;
   }

   public int getMiddle() {
      return this.middle;
   }

   public int getSmall() {
      return this.small;
   }
}
