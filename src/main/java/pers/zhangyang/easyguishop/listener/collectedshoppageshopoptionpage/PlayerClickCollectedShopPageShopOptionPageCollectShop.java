package pers.zhangyang.easyguishop.listener.collectedshoppageshopoptionpage;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import pers.zhangyang.easyguishop.domain.CollectedShopPageShopOptionPage;
import pers.zhangyang.easyguishop.exception.DuplicateShopCollectorException;
import pers.zhangyang.easyguishop.exception.NotExistShopException;
import pers.zhangyang.easyguishop.meta.ShopCollectorMeta;
import pers.zhangyang.easyguishop.service.GuiService;
import pers.zhangyang.easyguishop.service.impl.GuiServiceImpl;
import pers.zhangyang.easyguishop.yaml.MessageYaml;
import pers.zhangyang.easyguishop.easylibrary.annotation.EventListener;
import pers.zhangyang.easyguishop.easylibrary.annotation.GuiDiscreteButtonHandler;
import pers.zhangyang.easyguishop.easylibrary.util.MessageUtil;
import pers.zhangyang.easyguishop.easylibrary.util.TransactionInvocationHandler;

@EventListener
public class PlayerClickCollectedShopPageShopOptionPageCollectShop implements Listener {

    @GuiDiscreteButtonHandler(guiPage = CollectedShopPageShopOptionPage.class, slot = {13},closeGui = false,refreshGui = true)
    public void onPlayerClickAllShopNextPage(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        InventoryHolder holder = inventory.getHolder();


        Player player = (Player) event.getWhoClicked();
        GuiService guiService = (GuiService) new TransactionInvocationHandler(new GuiServiceImpl()).getProxy();
        CollectedShopPageShopOptionPage collectedShopPageShopPotionPage = (CollectedShopPageShopOptionPage) holder;

        ShopCollectorMeta shopCollectorMeta = new ShopCollectorMeta(collectedShopPageShopPotionPage.getShopMeta().getUuid(), player.getUniqueId().toString());

        try {
            guiService.collectShop(shopCollectorMeta);
        } catch (NotExistShopException e) {
            MessageUtil.sendMessageTo(player, MessageYaml.INSTANCE.getStringList("message.chat.notExistShop"));
            return;
        } catch (DuplicateShopCollectorException e) {
            MessageUtil.sendMessageTo(player, MessageYaml.INSTANCE.getStringList("message.chat.duplicateShopCollectorInCollectedShopPageShopOptionPage"));
            return;
        }
        MessageUtil.sendMessageTo(player, MessageYaml.INSTANCE.getStringList("message.chat.collectShopInCollectedShopPageShopOptionPage"));

    }

}