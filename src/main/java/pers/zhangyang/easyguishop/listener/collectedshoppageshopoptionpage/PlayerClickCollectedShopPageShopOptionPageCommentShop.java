package pers.zhangyang.easyguishop.listener.collectedshoppageshopoptionpage;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import pers.zhangyang.easyguishop.domain.CollectedShopPageShopOptionPage;
import pers.zhangyang.easyguishop.easylibrary.annotation.EventListener;
import pers.zhangyang.easyguishop.easylibrary.annotation.GuiDiscreteButtonHandler;

@EventListener
public class PlayerClickCollectedShopPageShopOptionPageCommentShop implements Listener {

    @GuiDiscreteButtonHandler(guiPage = CollectedShopPageShopOptionPage.class, slot = {21},closeGui = true,refreshGui = false)
    public void onPlayerClickAllShopNextPage(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        InventoryHolder holder = inventory.getHolder();

        Player player = (Player) event.getWhoClicked();
        CollectedShopPageShopOptionPage collectedShopPageShopPotionPage = (CollectedShopPageShopOptionPage) holder;

        assert collectedShopPageShopPotionPage != null;
        new PlayerInputAfterClickCollectedShopPageShopOptionPageCommentShop(player, collectedShopPageShopPotionPage.getOwner(), collectedShopPageShopPotionPage);
    }

}
