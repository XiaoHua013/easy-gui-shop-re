package pers.zhangyang.easyguishop.listener.allshoppage;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import pers.zhangyang.easyguishop.domain.AllShopPage;
import pers.zhangyang.easyguishop.domain.ManageShopCommentPage;
import pers.zhangyang.easyguishop.easylibrary.annotation.EventListener;
import pers.zhangyang.easyguishop.easylibrary.annotation.GuiDiscreteButtonHandler;

@EventListener
public class PlayerClickAllShopPageManageCommentPage implements Listener {

    @GuiDiscreteButtonHandler(guiPage = AllShopPage.class, slot = {52},closeGui = false,refreshGui = false)
    public void onPlayerClickAllShopNextPage(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        InventoryHolder holder = inventory.getHolder();

        AllShopPage allShopPage = (AllShopPage) holder;
        Player player = (Player) event.getWhoClicked();
        ManageShopCommentPage manageShopCommentPage = new ManageShopCommentPage(allShopPage, player);

        manageShopCommentPage.send();


    }

}
