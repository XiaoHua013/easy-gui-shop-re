package pers.zhangyang.easyguishop.listener.allshoppage;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import pers.zhangyang.easyguishop.domain.AllShopPage;
import pers.zhangyang.easyguishop.domain.ManageShopPage;
import pers.zhangyang.easyguishop.easylibrary.annotation.EventListener;
import pers.zhangyang.easyguishop.easylibrary.annotation.GuiDiscreteButtonHandler;

@EventListener
public class PlayerClickAllShopPageManageShopPage implements Listener {

    @GuiDiscreteButtonHandler(guiPage = AllShopPage.class, slot = {48},closeGui = false,refreshGui = false)
    public void onPlayerClickAllShopNextPage(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        InventoryHolder holder = inventory.getHolder();


        AllShopPage allShopPage = (AllShopPage) holder;
        Player player = (Player) event.getWhoClicked();
        ManageShopPage manageShopPage = new ManageShopPage(allShopPage, player);

        manageShopPage.send();


    }

}
