package pers.zhangyang.easyguishop.easylibrary.base;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.zhangyang.easyguishop.easylibrary.exception.NotExistNextPageException;
import pers.zhangyang.easyguishop.easylibrary.exception.NotExistPreviousPageException;

public abstract class MultipleGuiPageBase implements GuiPage {
   protected Inventory inventory;
   protected Player viewer;
   protected int pageIndex;
   protected GuiPage backPage;
   protected OfflinePlayer owner;

   public MultipleGuiPageBase(@Nullable String title, @NotNull Player viewer, @Nullable GuiPage backPage, OfflinePlayer owner, InventoryType inventoryType) {
      if (title != null) {
         this.inventory = Bukkit.createInventory(this, inventoryType, ChatColor.translateAlternateColorCodes('&', title));
      } else {
         this.inventory = Bukkit.createInventory(this, inventoryType);
      }

      this.owner = owner;
      this.viewer = viewer;
      this.pageIndex = 0;
      this.backPage = backPage;
   }

   public MultipleGuiPageBase(@Nullable String title, @NotNull Player viewer, @Nullable GuiPage backPage, OfflinePlayer owner, int size) {
      if (title != null) {
         this.inventory = Bukkit.createInventory(this, size, ChatColor.translateAlternateColorCodes('&', title));
      } else {
         this.inventory = Bukkit.createInventory(this, size);
      }

      this.owner = owner;
      this.viewer = viewer;
      this.pageIndex = 0;
      this.backPage = backPage;
   }

   public abstract void send();

   public abstract void refresh();

   public void nextPage() throws NotExistNextPageException {
      ++this.pageIndex;
      this.refresh();
   }

   public void previousPage() throws NotExistPreviousPageException {
      --this.pageIndex;
      this.refresh();
   }

   public abstract int getPreviousPageSlot();

   public abstract int getNextPageSlot();

   public Inventory getInventory() {
      return this.inventory;
   }

   public @NotNull Player getViewer() {
      return this.viewer;
   }

   public @NotNull OfflinePlayer getOwner() {
      return this.owner;
   }
}
