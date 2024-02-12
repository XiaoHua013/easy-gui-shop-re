package pers.zhangyang.easyguishop.easylibrary.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.bukkit.inventory.InventoryHolder;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface GuiSerialButtonHandler {
   Class<? extends InventoryHolder> guiPage();

   int from();

   boolean closeGui();

   boolean refreshGui();

   int to();
}
