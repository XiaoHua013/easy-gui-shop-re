package pers.zhangyang.easyguishop.easylibrary.base;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class SingleGuiPageBase implements GuiPage {
   protected Inventory inventory;
   protected Player viewer;
   protected GuiPage backPage;
   protected OfflinePlayer owner;

   public SingleGuiPageBase(@Nullable String title, Player viewer, GuiPage backPage, OfflinePlayer owner, InventoryType inventoryType) {
      if (title != null) {
         this.inventory = Bukkit.createInventory(this, inventoryType, ChatColor.translateAlternateColorCodes('&', title));
      } else {
         this.inventory = Bukkit.createInventory(this, inventoryType);
      }

      this.owner = owner;
      this.viewer = viewer;
      this.backPage = backPage;
   }

   public SingleGuiPageBase(@Nullable String title, Player viewer, GuiPage backPage, OfflinePlayer owner, int size) {
      if (title != null) {
         this.inventory = Bukkit.createInventory(this, size, ChatColor.translateAlternateColorCodes('&', title));
      } else {
         this.inventory = Bukkit.createInventory(this, size);
      }

      this.owner = owner;
      this.viewer = viewer;
      this.backPage = backPage;
   }

   public void send() {
      this.refresh();
   }

   public abstract void refresh();

   public @NotNull Inventory getInventory() {
      return this.inventory;
   }

   public @NotNull Player getViewer() {
      return this.viewer;
   }

   public @NotNull OfflinePlayer getOwner() {
      return this.owner;
   }
}
