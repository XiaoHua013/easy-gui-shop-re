package pers.zhangyang.easyguishop.listener.managegoodpage;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import pers.zhangyang.easyguishop.domain.ManageGoodPage;
import pers.zhangyang.easyguishop.yaml.MessageYaml;
import pers.zhangyang.easyguishop.easylibrary.base.FiniteInputListenerBase;
import pers.zhangyang.easyguishop.easylibrary.util.MessageUtil;

public class PlayerInputAfterClickManageGoodPageSearchByGoodName extends FiniteInputListenerBase {

    private final ManageGoodPage manageGoodPage;

    public PlayerInputAfterClickManageGoodPageSearchByGoodName(Player player, OfflinePlayer owner, ManageGoodPage manageGoodPage) {
        super(player, owner, manageGoodPage, 1);
        this.manageGoodPage = manageGoodPage;
        MessageUtil.sendMessageTo(player, MessageYaml.INSTANCE.getStringList("message.chat.howToSearchByGoodNameInManageGoodPage"));
    }

    @Override
    public void run() {
        manageGoodPage.searchByGooName(messages[0]);
    }
}
