package pers.zhangyang.easyguishop.listener.manageshoppage;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import pers.zhangyang.easyguishop.domain.ManageShopPage;
import pers.zhangyang.easyguishop.exception.NotExistShopException;
import pers.zhangyang.easyguishop.exception.ShopNotEmptyException;
import pers.zhangyang.easyguishop.meta.ShopMeta;
import pers.zhangyang.easyguishop.service.GuiService;
import pers.zhangyang.easyguishop.service.impl.GuiServiceImpl;
import pers.zhangyang.easyguishop.yaml.MessageYaml;
import pers.zhangyang.easyguishop.easylibrary.base.FiniteInputListenerBase;
import pers.zhangyang.easyguishop.easylibrary.util.MessageUtil;
import pers.zhangyang.easyguishop.easylibrary.util.TransactionInvocationHandler;

public class PlayerInputAfterClickManageShopPageDeleteShop extends FiniteInputListenerBase {

    private final ManageShopPage manageShopPage;

    public PlayerInputAfterClickManageShopPageDeleteShop(Player player, OfflinePlayer owner, ManageShopPage manageShopPage) {
        super(player, owner, manageShopPage, 1);
        this.manageShopPage = manageShopPage;
        MessageUtil.sendMessageTo(player, MessageYaml.INSTANCE.getStringList("message.chat.howToDeleteShop"));
    }

    @Override
    public void run() {


        GuiService guiService = (GuiService) new TransactionInvocationHandler(new GuiServiceImpl()).getProxy();

        ShopMeta shopMeta=guiService.getShopByName(messages[0]);
        if (shopMeta==null){
            MessageUtil.sendMessageTo(player, MessageYaml.INSTANCE.getStringList("message.chat.notExistShop"));
            return;
        }
        if (!shopMeta.getOwnerUuid().equals(owner.getUniqueId().toString())){
            MessageUtil.sendMessageTo(player, MessageYaml.INSTANCE.getStringList("message.chat.notShopOwner"));
            return;
        }


        try {
            guiService.deleteShop(messages[0]);
        } catch (NotExistShopException e) {
            e.printStackTrace();
        } catch (ShopNotEmptyException e) {
            MessageUtil.sendMessageTo(player, MessageYaml.INSTANCE.getStringList("message.chat.shopNotEmpty"));
            return;
        }
        MessageUtil.sendMessageTo(player, MessageYaml.INSTANCE.getStringList("message.chat.deleteShop"));
    }
}
