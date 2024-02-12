package pers.zhangyang.easyguishop.easylibrary.util;

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class PageUtil {
   public static <T> List<T> page(int pageIndex, int capacity, @NotNull List<T> list) {
      List<T> rl = new ArrayList();

      for(int i = pageIndex * capacity; i < pageIndex * capacity + capacity && list.size() > i; ++i) {
         rl.add(list.get(i));
      }

      return rl;
   }

   public static int computeMaxPageIndex(int all, int capacity) {
      int maxPage = 0;
      if (all != 0) {
         maxPage = all % capacity == 0 ? all / capacity - 1 : all / capacity;
      }

      return maxPage;
   }
}
