package pers.zhangyang.easyguishop.domain;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import pers.zhangyang.easyguishop.easylibrary.util.*;
import pers.zhangyang.easyguishop.enumration.ManageShopPageStatsEnum;
import pers.zhangyang.easyguishop.exception.NotExistShopException;
import pers.zhangyang.easyguishop.meta.GoodMeta;
import pers.zhangyang.easyguishop.meta.ShopMeta;
import pers.zhangyang.easyguishop.service.GuiService;
import pers.zhangyang.easyguishop.service.impl.GuiServiceImpl;
import pers.zhangyang.easyguishop.yaml.GuiYaml;
import pers.zhangyang.easyguishop.easylibrary.base.BackAble;
import pers.zhangyang.easyguishop.easylibrary.base.GuiPage;
import pers.zhangyang.easyguishop.easylibrary.base.MultipleGuiPageBase;
import pers.zhangyang.easyguishop.easylibrary.exception.NotApplicableException;
import pers.zhangyang.easyguishop.easylibrary.exception.NotExistNextPageException;
import pers.zhangyang.easyguishop.easylibrary.exception.NotExistPreviousPageException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AllGoodPage extends MultipleGuiPageBase implements BackAble {
    private List<GoodMeta> goodMetaList = new ArrayList<>();
    private int pageIndex;
    private ManageShopPageStatsEnum stats;
    private String searchContent;
    private ShopMeta shopMeta;

    public AllGoodPage(GuiPage backPage, Player viewer, ShopMeta shopMeta) {
        super(GuiYaml.INSTANCE.getString("gui.title.allGoodPage"), viewer, backPage, backPage.getOwner(),54);
        this.shopMeta = shopMeta;
        stats = ManageShopPageStatsEnum.NORMAL;
    }

    public void send() {
        this.stats = ManageShopPageStatsEnum.NORMAL;
        this.searchContent = null;
        this.pageIndex = 0;
        refresh();
    }

    public void searchByGooName(@NotNull String name) {
        this.stats = ManageShopPageStatsEnum.SEARCH_SHOP_NAME;
        this.searchContent = name;
        this.pageIndex = 0;
        refresh();
    }


    public void refresh() {

        this.inventory.clear();

        GuiService guiService = (GuiService) new TransactionInvocationHandler(new GuiServiceImpl()).getProxy();
        this.shopMeta = guiService.getShop(this.shopMeta.getUuid());
        if (this.shopMeta == null) {
            if (backPage instanceof AllShopPageShopOptionPage) {
                backPage.send();
            }
            if (backPage instanceof CollectedShopPageShopOptionPage) {
                backPage.send();
            }
            return;
        }

        try {
            this.goodMetaList = guiService.listShopGood(shopMeta.getUuid());
        } catch (NotExistShopException e) {
            if (backPage instanceof AllShopPageShopOptionPage) {
                backPage.send();
            }
            if (backPage instanceof CollectedShopPageShopOptionPage) {
                backPage.send();
            }
            return;
        }

        if (stats.equals(ManageShopPageStatsEnum.SEARCH_SHOP_NAME)) {
            this.goodMetaList.removeIf(shopMeta2 -> !shopMeta2.getName().contains(searchContent));
        }

        int maxIndex = PageUtil.computeMaxPageIndex(this.goodMetaList.size(), 45);
        if (pageIndex > maxIndex) {
            this.pageIndex = maxIndex;
        }
        if (pageIndex > 0) {
            ItemStack previous = GuiYaml.INSTANCE.getButtonDefault("gui.button.allGoodPage.previousPage");
            inventory.setItem(45, previous);
        }
        if (pageIndex < maxIndex) {
            ItemStack next = GuiYaml.INSTANCE.getButtonDefault("gui.button.allGoodPage.nextPage");
            inventory.setItem(53, next);
        }


        this.goodMetaList = (PageUtil.page(pageIndex, 45, goodMetaList));
        //设置内容
        for (int i = 0; i < 45; i++) {
            if (i >= goodMetaList.size()) {
                break;
            }
            GoodMeta goodMeta = goodMetaList.get(i);
            HashMap<String, String> rep = new HashMap<>();
            rep.put("{name}", goodMeta.getName());
            rep.put("{type}", goodMeta.getType());
            if (goodMeta.isSystem()) {
                rep.put("{stock}", GuiYaml.INSTANCE.getStringDefault("gui.replace.systemStock"));
            } else {
                rep.put("{stock}", String.valueOf(goodMeta.getStock()));
            }
            if (goodMeta.getLimitTime() != null) {
                rep.put("{limit_time}", String.valueOf(goodMeta.getLimitTime()));
            } else {
                rep.put("{limit_time}", "\\");
            }
            if (goodMeta.getLimitFrequency() != null) {
                rep.put("{limit_frequency}", String.valueOf(goodMeta.getLimitFrequency()));
            } else {
                rep.put("{limit_frequency}", "\\");
            }
            rep.put("{create_time}", TimeUtil.getTimeFromTimeMill(goodMeta.getCreateTime()));
            ItemStack itemStack;
            if (GuiYaml.INSTANCE.getBooleanDefault("gui.option.enableGoodUseGoodItem")) {
                itemStack = ItemStackUtil.itemStackDeserialize(goodMeta.getGoodItemStack());
                ItemStack tem = GuiYaml.INSTANCE.getButtonDefault("gui.button.allGoodPage.allGoodPageGoodOptionPage");
                try {
                    ItemStackUtil.apply(tem, itemStack);
                } catch (NotApplicableException e) {
                    itemStack = tem;
                }
            } else {
                itemStack = GuiYaml.INSTANCE.getButtonDefault("gui.button.allGoodPage.allGoodPageGoodOptionPage");
            }
            ReplaceUtil.replaceDisplayName(itemStack, rep);
            ReplaceUtil.replaceLore(itemStack, rep);
            inventory.setItem(i, itemStack);
        }


        ItemStack search = GuiYaml.INSTANCE.getButtonDefault("gui.button.allGoodPage.searchByGoodName");
        inventory.setItem(47, search);
        ItemStack back = GuiYaml.INSTANCE.getButtonDefault("gui.button.allGoodPage.back");
        inventory.setItem(49, back);
        viewer.openInventory(this.inventory);
    }


    public void nextPage() throws NotExistNextPageException {
        GuiService guiService = (GuiService) new TransactionInvocationHandler(new GuiServiceImpl()).getProxy();
        this.shopMeta = guiService.getShop(this.shopMeta.getUuid());
        if (this.shopMeta == null) {
            if (backPage instanceof AllShopPageShopOptionPage) {
                backPage.send();
            }
            if (backPage instanceof CollectedShopPageShopOptionPage) {
                backPage.send();
            }
            return;
        }
        try {
            this.goodMetaList = guiService.listShopGood(this.shopMeta.getUuid());
        } catch (NotExistShopException e) {
            if (backPage instanceof AllShopPageShopOptionPage) {
                backPage.send();
            }
            if (backPage instanceof CollectedShopPageShopOptionPage) {
                backPage.send();
            }
            return;
        }

        int maxIndex = PageUtil.computeMaxPageIndex(goodMetaList.size(), 45);
        if (maxIndex <= pageIndex) {
            throw new NotExistNextPageException();
        }
        this.pageIndex++;
        refresh();
    }

    public void previousPage() throws NotExistPreviousPageException {
        if (0 >= pageIndex) {
            throw new NotExistPreviousPageException();
        }
        this.pageIndex--;
        refresh();
    }

    @Override
    public int getPreviousPageSlot() {
        return 45;
    }

    @Override
    public int getNextPageSlot() {
        return 53;
    }
    @Override
    public int getBackSlot() {
        return 49;
    }

    public ShopMeta getShopMeta() {
        return shopMeta;
    }

    @NotNull
    public List<GoodMeta> getGoodMetaList() {
        return new ArrayList<>(goodMetaList);
    }

    public InventoryHolder getPreviousHolder() {
        return backPage;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public void back() {
        backPage.refresh();
    }


}

