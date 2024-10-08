package pers.zhangyang.easyguishop.listener.manageitemstockpage;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import pers.zhangyang.easyguishop.domain.ManageItemStockPage;
import pers.zhangyang.easyguishop.yaml.MessageYaml;
import pers.zhangyang.easyguishop.yaml.SettingYaml;
import pers.zhangyang.easyguishop.easylibrary.annotation.EventListener;
import pers.zhangyang.easyguishop.easylibrary.annotation.GuiDiscreteButtonHandler;
import pers.zhangyang.easyguishop.easylibrary.util.MessageUtil;

@EventListener
public class PlayerClickManageItemStockPageTeleportBankLocation implements Listener {

    @GuiDiscreteButtonHandler(guiPage = ManageItemStockPage.class, slot = {47},closeGui = true,refreshGui = false)
    public void onPlayerClickAllShopNextPage(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        InventoryHolder holder = inventory.getHolder();

        Location location = SettingYaml.INSTANCE.getLocationDefault("setting.bankLocation");
        Player player = (Player) event.getWhoClicked();
        player.teleport(location);
        MessageUtil.sendMessageTo(player, MessageYaml.INSTANCE.getStringList("message.chat.teleportBankLocation"));

    }

}