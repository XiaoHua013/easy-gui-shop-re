package pers.zhangyang.easyguishop.listener.shopcommentpage;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import pers.zhangyang.easyguishop.domain.AllShopCommentPage;
import pers.zhangyang.easyguishop.easylibrary.annotation.EventListener;
import pers.zhangyang.easyguishop.easylibrary.annotation.GuiDiscreteButtonHandler;

@EventListener
public class PlayerClickShopCommentPageSearchByCommenter implements Listener {

    @GuiDiscreteButtonHandler(guiPage = AllShopCommentPage.class, slot = {50},closeGui = true,refreshGui = false)
    public void onPlayerClickAllShopNextPage(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        InventoryHolder holder = inventory.getHolder();

        Player player = (Player) event.getWhoClicked();

        AllShopCommentPage allShopPageShopOptionPageShopCommentPage = (AllShopCommentPage) holder;
        new PlayerInputAfterClickShopCommentPageSearchByCommenterName(player, allShopPageShopOptionPageShopCommentPage.getOwner(), allShopPageShopOptionPageShopCommentPage);

    }
}