package pers.zhangyang.easyguishop.listener.allshoppageshopoptionpage;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import pers.zhangyang.easyguishop.domain.AllShopCommentPage;
import pers.zhangyang.easyguishop.domain.AllShopPageShopOptionPage;
import pers.zhangyang.easyguishop.meta.ShopMeta;
import pers.zhangyang.easyguishop.easylibrary.annotation.EventListener;
import pers.zhangyang.easyguishop.easylibrary.annotation.GuiDiscreteButtonHandler;


@EventListener
public class PlayerClickAllShopPageShopOptionPageShopCommentPage implements Listener {

    @GuiDiscreteButtonHandler(guiPage = AllShopPageShopOptionPage.class, slot = {23},closeGui = false,refreshGui = false)
    public void onPlayerClickAllShopNextPage(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        InventoryHolder holder = inventory.getHolder();

        Player player = (Player) event.getWhoClicked();
        AllShopPageShopOptionPage allShopPageShopOptionPage = (AllShopPageShopOptionPage) holder;
        ShopMeta shopMeta = allShopPageShopOptionPage.getShopMeta();
        AllShopCommentPage allShopPageShopOptionPageShopCommentPage = new AllShopCommentPage(allShopPageShopOptionPage, player, shopMeta);
        allShopPageShopOptionPageShopCommentPage.send();


    }

}
