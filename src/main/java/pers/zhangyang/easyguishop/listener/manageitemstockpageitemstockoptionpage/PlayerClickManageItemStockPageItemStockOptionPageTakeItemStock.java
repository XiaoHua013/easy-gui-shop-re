package pers.zhangyang.easyguishop.listener.manageitemstockpageitemstockoptionpage;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import pers.zhangyang.easyguishop.domain.ManageItemStockPageItemStockOptionPage;
import pers.zhangyang.easyguishop.meta.ItemStockMeta;
import pers.zhangyang.easyguishop.service.GuiService;
import pers.zhangyang.easyguishop.service.impl.GuiServiceImpl;
import pers.zhangyang.easyguishop.yaml.MessageYaml;
import pers.zhangyang.easyguishop.yaml.SettingYaml;
import pers.zhangyang.easyguishop.easylibrary.annotation.EventListener;
import pers.zhangyang.easyguishop.easylibrary.annotation.GuiDiscreteButtonHandler;
import pers.zhangyang.easyguishop.easylibrary.util.MessageUtil;
import pers.zhangyang.easyguishop.easylibrary.util.TransactionInvocationHandler;

@EventListener
public class PlayerClickManageItemStockPageItemStockOptionPageTakeItemStock implements Listener {

    @GuiDiscreteButtonHandler(guiPage = ManageItemStockPageItemStockOptionPage.class, slot = {21},closeGui = true,refreshGui = false)
    public void onPlayerClickAllShopNextPage(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        InventoryHolder holder = inventory.getHolder();

        Player player = (Player) event.getWhoClicked();
        ManageItemStockPageItemStockOptionPage manageItemStockPageItemStockOptionPage = (ManageItemStockPageItemStockOptionPage) holder;


        GuiService guiService = (GuiService) new TransactionInvocationHandler(new GuiServiceImpl()).getProxy();

        ItemStockMeta itemStockMeta;
        assert manageItemStockPageItemStockOptionPage != null;
        itemStockMeta = guiService.getItemStock(player.getUniqueId().toString(), manageItemStockPageItemStockOptionPage.getItemStockMeta().getItemStack());


        Double range=SettingYaml.INSTANCE.getNonnegativeDouble("setting.manageItemStockRange");

        if (range!=null) {
            if (itemStockMeta == null) {
                return;
            }
            Location location = SettingYaml.INSTANCE.getLocationDefault("setting.bankLocation");
            if (location.getWorld() == null) {
                MessageUtil.sendMessageTo(player, MessageYaml.INSTANCE.getStringList("message.chat.notNearBankLocationWhenTakeItemStock"));
                return;
            }
            if (!location.getWorld().equals(player.getLocation().getWorld())) {
                MessageUtil.sendMessageTo(player, MessageYaml.INSTANCE.getStringList("message.chat.notNearBankLocationWhenTakeItemStock"));
                return;
            }
            if (location.distance(player.getLocation()) > range) {
                MessageUtil.sendMessageTo(player, MessageYaml.INSTANCE.getStringList("message.chat.notNearBankLocationWhenTakeItemStock"));
                return;
            }

        }

        new PlayerInputAfterClickManageItemStockPageItemStockOptionPageTakeItemStock(player,
                manageItemStockPageItemStockOptionPage.getOwner(), manageItemStockPageItemStockOptionPage.getItemStockMeta(),
                manageItemStockPageItemStockOptionPage);
    }

}
