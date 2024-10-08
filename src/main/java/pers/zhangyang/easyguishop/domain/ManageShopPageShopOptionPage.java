package pers.zhangyang.easyguishop.domain;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import pers.zhangyang.easyguishop.meta.ShopMeta;
import pers.zhangyang.easyguishop.service.GuiService;
import pers.zhangyang.easyguishop.service.impl.GuiServiceImpl;
import pers.zhangyang.easyguishop.yaml.GuiYaml;
import pers.zhangyang.easyguishop.easylibrary.base.BackAble;
import pers.zhangyang.easyguishop.easylibrary.base.GuiPage;
import pers.zhangyang.easyguishop.easylibrary.base.SingleGuiPageBase;
import pers.zhangyang.easyguishop.easylibrary.util.TransactionInvocationHandler;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ManageShopPageShopOptionPage extends SingleGuiPageBase implements BackAble {
    private ShopMeta shopMeta;

    public ManageShopPageShopOptionPage(GuiPage previousHolder, Player player, ShopMeta shopMeta) {
        super(GuiYaml.INSTANCE.getString("gui.title.manageShopPageShopOptionPage"), player, previousHolder, previousHolder.getOwner(),54);

        this.shopMeta = shopMeta;

    }


    @Override
    public void refresh() {


        GuiService guiService = (GuiService) new TransactionInvocationHandler(new GuiServiceImpl()).getProxy();
        this.shopMeta = guiService.getShop(shopMeta.getUuid());
        if (this.shopMeta == null) {
            backPage.send();
            return;
        }
        this.inventory.clear();
        ItemStack setLocation = GuiYaml.INSTANCE.getButtonDefault("gui.button.manageShopPageShopOptionPage.setShopLocation");
        inventory.setItem(22, setLocation);
        Gson gson = new Gson();
        Type stringListType = new TypeToken<ArrayList<String>>() {
        }.getType();
        List<String> stringList = gson.fromJson(shopMeta.getDescription(), stringListType);

            ItemStack setShopDescription = GuiYaml.INSTANCE.getButtonDefault("gui.button.manageShopPageShopOptionPage.updateShopDescription");
            inventory.setItem(30, setShopDescription);


        ItemStack setName = GuiYaml.INSTANCE.getButtonDefault("gui.button.manageShopPageShopOptionPage.setShopName");
        inventory.setItem(21, setName);

        ItemStack manageShop = GuiYaml.INSTANCE.getButtonDefault("gui.button.manageShopPageShopOptionPage.manageGoodPage");
        inventory.setItem(31, manageShop);

        ItemStack searchOwner = GuiYaml.INSTANCE.getButtonDefault("gui.button.manageShopPageShopOptionPage.manageIconPage");
        inventory.setItem(40, searchOwner);
            ItemStack resetLocation = GuiYaml.INSTANCE.getButtonDefault("gui.button.manageShopPageShopOptionPage.resetShopLocation");
            inventory.setItem(39, resetLocation);


            ItemStack resetShopDescription = GuiYaml.INSTANCE.getButtonDefault("gui.button.manageShopPageShopOptionPage.resetShopDescription");
            inventory.setItem(41, resetShopDescription);



        ItemStack set = GuiYaml.INSTANCE.getButtonDefault("gui.button.manageShopPageShopOptionPage.setShopDescription");
        inventory.setItem(14, set);
        ItemStack addShopDescription = GuiYaml.INSTANCE.getButtonDefault("gui.button.manageShopPageShopOptionPage.addShopDescription");
        inventory.setItem(13, addShopDescription);


            ItemStack removeShopDescription = GuiYaml.INSTANCE.getButtonDefault("gui.button.manageShopPageShopOptionPage.removeShopDescription");
            inventory.setItem(32, removeShopDescription);

        ItemStack lookComment = GuiYaml.INSTANCE.getButtonDefault("gui.button.manageShopPageShopOptionPage.allShopCommentPage");
        inventory.setItem(23, lookComment);

        ItemStack buyShopPopularity = GuiYaml.INSTANCE.getButtonDefault("gui.button.manageShopPageShopOptionPage.buyShopPopularity");
        inventory.setItem(12, buyShopPopularity);

        ItemStack back = GuiYaml.INSTANCE.getButtonDefault("gui.button.manageShopPageShopOptionPage.back");
        inventory.setItem(49, back);



        viewer.openInventory(inventory);


    }

    @NotNull
    public ShopMeta getShopMeta() {
        return shopMeta;
    }


    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public InventoryHolder getPreviousHolder() {
        return backPage;
    }

    @Override
    public void back() {
        backPage.refresh();
    }

    @Override
    public int getBackSlot() {
        return 49;
    }
}

