package pers.zhangyang.easyguishop.listener.managecommentpage;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import pers.zhangyang.easyguishop.domain.ManageShopCommentPage;
import pers.zhangyang.easyguishop.exception.NotExistShopCommentException;
import pers.zhangyang.easyguishop.service.GuiService;
import pers.zhangyang.easyguishop.service.impl.GuiServiceImpl;
import pers.zhangyang.easyguishop.yaml.MessageYaml;
import pers.zhangyang.easyguishop.easylibrary.annotation.EventListener;
import pers.zhangyang.easyguishop.easylibrary.annotation.GuiSerialButtonHandler;
import pers.zhangyang.easyguishop.easylibrary.util.MessageUtil;
import pers.zhangyang.easyguishop.easylibrary.util.TransactionInvocationHandler;

@EventListener
public class PlayerClickManageCommentPageDeleteShopComment implements Listener {

    @GuiSerialButtonHandler(guiPage = ManageShopCommentPage.class,from = 0,to = 44,closeGui = false,refreshGui = true)
    public void onPlayerClickAllShopNextPage(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        InventoryHolder holder = inventory.getHolder();

        int slot = event.getRawSlot();


        Player player = (Player) event.getWhoClicked();
        ManageShopCommentPage manageShopCommentPage = (ManageShopCommentPage) holder;

        GuiService guiService = (GuiService) new TransactionInvocationHandler(new GuiServiceImpl()).getProxy();


        try {
            guiService.deleteShopComment(manageShopCommentPage.getShopCommentMetaList().get(slot).getUuid());
        } catch (NotExistShopCommentException e) {
            MessageUtil.sendMessageTo(player, MessageYaml.INSTANCE.getStringList("message.chat.notExistShopComment"));
            return;
        }
        MessageUtil.sendMessageTo(player, MessageYaml.INSTANCE.getStringList("message.chat.deleteShopComment"));


    }

}

