package pers.zhangyang.easyguishop.listener.managegoodpage;


import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pers.zhangyang.easyguishop.domain.ManageGoodPage;
import pers.zhangyang.easyguishop.easylibrary.util.*;
import pers.zhangyang.easyguishop.exception.DuplicateGoodException;
import pers.zhangyang.easyguishop.meta.GoodMeta;
import pers.zhangyang.easyguishop.service.GuiService;
import pers.zhangyang.easyguishop.service.impl.GuiServiceImpl;
import pers.zhangyang.easyguishop.yaml.MessageYaml;
import pers.zhangyang.easyguishop.easylibrary.base.FiniteInputListenerBase;

public class PlayerInputAfterClickManageGoodPageCreateGood extends FiniteInputListenerBase {

    private final ManageGoodPage manageGoodPage;

    public PlayerInputAfterClickManageGoodPageCreateGood(Player player, OfflinePlayer owner, ManageGoodPage manageGoodPage) {
        super(player, owner, manageGoodPage, 2);
        this.manageGoodPage = manageGoodPage;
        MessageUtil.sendMessageTo(player, MessageYaml.INSTANCE.getStringList("message.chat.howToCreateGood"));
    }

    @Override
    public void run() {
        ItemStack itemStack = PlayerUtil.getItemInMainHand(player).clone();
        itemStack.setAmount(1);
        if (itemStack.getType().equals(Material.AIR)) {
            MessageUtil.sendMessageTo(player, MessageYaml.INSTANCE.getStringList("message.chat.notItemInMainHand"));
            return;
        }
        String buy = MessageYaml.INSTANCE.getNonemptyStringDefault("message.input.buy");
        String sell = MessageYaml.INSTANCE.getNonemptyStringDefault("message.input.sell");
        if (!messages[1].equalsIgnoreCase(buy) && !messages[1].equalsIgnoreCase(sell)) {
            MessageUtil.sendMessageTo(player, MessageYaml.INSTANCE.getStringList("message.chat.unknownGoodType"));
            return;
        }
        GuiService guiService = (GuiService) new TransactionInvocationHandler(new GuiServiceImpl()).getProxy();
        GoodMeta goodMeta = new GoodMeta(UuidUtil.getUUID(), messages[0], ItemStackUtil.itemStackSerialize(itemStack), messages[1], System.currentTimeMillis(),
                false, 0, manageGoodPage.getShopMeta().getUuid());
        try {
            guiService.createGood(goodMeta);
        } catch (DuplicateGoodException e) {
            MessageUtil.sendMessageTo(player, MessageYaml.INSTANCE.getStringList("message.chat.duplicateGoodWhenCreateGood"));
            return;
        }
        MessageUtil.sendMessageTo(player, MessageYaml.INSTANCE.getStringList("message.chat.createGood"));
    }
}
