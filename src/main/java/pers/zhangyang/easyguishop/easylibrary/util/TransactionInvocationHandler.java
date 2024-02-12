package pers.zhangyang.easyguishop.easylibrary.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.zhangyang.easyguishop.easylibrary.base.DaoBase;

public class TransactionInvocationHandler implements InvocationHandler {
   private final Object target;

   public TransactionInvocationHandler(Object target) {
      this.target = target;
   }

   public @Nullable Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      Connection connection = DaoBase.getConnection();

      Object obj;
      try {
         obj = method.invoke(this.target, args);
         connection.commit();
      } catch (Exception var10) {
         connection.rollback();
         throw (Throwable)(var10.getCause() == null ? var10 : var10.getCause());
      } finally {
         connection.close();
      }

      return obj;
   }

   public @NotNull Object getProxy() {
      return Proxy.newProxyInstance(this.target.getClass().getClassLoader(), this.target.getClass().getInterfaces(), this);
   }
}
