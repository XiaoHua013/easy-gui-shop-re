package pers.zhangyang.easyguishop.listener.manageiconpage;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import pers.zhangyang.easyguishop.domain.ManageIconPage;
import pers.zhangyang.easyguishop.exception.NotExistShopException;
import pers.zhangyang.easyguishop.service.GuiService;
import pers.zhangyang.easyguishop.service.impl.GuiServiceImpl;
import pers.zhangyang.easyguishop.yaml.MessageYaml;
import pers.zhangyang.easyguishop.easylibrary.annotation.EventListener;
import pers.zhangyang.easyguishop.easylibrary.annotation.GuiDiscreteButtonHandler;
import pers.zhangyang.easyguishop.easylibrary.util.MessageUtil;
import pers.zhangyang.easyguishop.easylibrary.util.TransactionInvocationHandler;

@EventListener
public class PlayerClickManageIconPageResetShopIcon implements Listener {

    @GuiDiscreteButtonHandler(guiPage = ManageIconPage.class, slot = {48},closeGui = false,refreshGui = true)
    public void onPlayerClickAllShopNextPage(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        InventoryHolder holder = inventory.getHolder();

        Player player = (Player) event.getWhoClicked();
        ManageIconPage manageIconPage = (ManageIconPage) holder;
        GuiService guiService = (GuiService) new TransactionInvocationHandler(new GuiServiceImpl()).getProxy();

        assert manageIconPage != null;
        try {
            guiService.resetShopIcon(manageIconPage.getShopMeta().getUuid());
        } catch (NotExistShopException e) {
            MessageUtil.sendMessageTo(player, MessageYaml.INSTANCE.getStringList("message.chat.notExistShop"));
            return;
        }
        MessageUtil.sendMessageTo(player, MessageYaml.INSTANCE.getStringList("message.chat.resetShopIcon"));

    }
}
