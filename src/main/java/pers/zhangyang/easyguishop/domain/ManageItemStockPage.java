package pers.zhangyang.easyguishop.domain;


import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import pers.zhangyang.easyguishop.meta.ItemStockMeta;
import pers.zhangyang.easyguishop.service.GuiService;
import pers.zhangyang.easyguishop.service.impl.GuiServiceImpl;
import pers.zhangyang.easyguishop.yaml.GuiYaml;
import pers.zhangyang.easyguishop.easylibrary.base.BackAble;
import pers.zhangyang.easyguishop.easylibrary.base.GuiPage;
import pers.zhangyang.easyguishop.easylibrary.base.MultipleGuiPageBase;
import pers.zhangyang.easyguishop.easylibrary.exception.NotApplicableException;
import pers.zhangyang.easyguishop.easylibrary.exception.NotExistNextPageException;
import pers.zhangyang.easyguishop.easylibrary.exception.NotExistPreviousPageException;
import pers.zhangyang.easyguishop.easylibrary.util.ItemStackUtil;
import pers.zhangyang.easyguishop.easylibrary.util.PageUtil;
import pers.zhangyang.easyguishop.easylibrary.util.TransactionInvocationHandler;

import java.util.ArrayList;
import java.util.List;

public class ManageItemStockPage extends MultipleGuiPageBase implements BackAble {

    private List<ItemStockMeta> itemStockMetaList = new ArrayList<>();
    private int pageIndex;

    public ManageItemStockPage(GuiPage previousHolder, Player player) {
        super(GuiYaml.INSTANCE.getString("gui.title.manageItemStockPage"), player, previousHolder,
                previousHolder.getOwner(),54);

    }

    public void send() {
        this.pageIndex = 0;
        refresh();
    }


    public void refresh() {

        this.inventory.clear();

        GuiService guiService = (GuiService) new TransactionInvocationHandler(new GuiServiceImpl()).getProxy();
        this.itemStockMetaList.clear();
        this.itemStockMetaList.addAll(guiService.listPlayerItemStock(owner.getUniqueId().toString()));

        if (pageIndex > 0) {
            ItemStack previous = GuiYaml.INSTANCE.getButtonDefault("gui.button.manageItemStockPage.previousPage");
            inventory.setItem(45, previous);
        } else {
            inventory.setItem(45, null);
        }
        int maxIndex = PageUtil.computeMaxPageIndex(itemStockMetaList.size(), 45);
        if (pageIndex < maxIndex) {
            ItemStack next = GuiYaml.INSTANCE.getButtonDefault("gui.button.manageItemStockPage.nextPage");
            inventory.setItem(53, next);
        } else {
            inventory.setItem(53, null);
        }


        this.itemStockMetaList = (PageUtil.page(pageIndex, 45, itemStockMetaList));
        //设置内容
        for (int i = 0; i < 45; i++) {
            if (i >= itemStockMetaList.size()) {
                break;
            }

            ItemStockMeta itemStockMeta = itemStockMetaList.get(i);
            ItemStack itemStack;
            if (GuiYaml.INSTANCE.getBooleanDefault("gui.option.enableItemStockUseItemStockItem")) {
                itemStack = ItemStackUtil.itemStackDeserialize(itemStockMeta.getItemStack());
                ItemStack tem = GuiYaml.INSTANCE.getButtonDefault("gui.button.manageItemStockPage.manageItemStockPageItemStockOptionPage");
                try {
                    ItemStackUtil.apply(tem, itemStack);
                } catch (NotApplicableException e) {
                    itemStack = tem;
                }
            } else {
                itemStack = GuiYaml.INSTANCE.getButtonDefault("gui.button.manageItemStockPage.manageItemStockPageItemStockOptionPage");
            }
            inventory.setItem(i, itemStack);
        }


        ItemStack back = GuiYaml.INSTANCE.getButtonDefault("gui.button.manageItemStockPage.back");
        inventory.setItem(49, back);
        ItemStack createItemStock = GuiYaml.INSTANCE.getButtonDefault("gui.button.manageItemStockPage.createItemStock");
        inventory.setItem(51, createItemStock);
        ItemStack goBankLocation = GuiYaml.INSTANCE.getButtonDefault("gui.button.manageItemStockPage.teleportBankLocation");
        inventory.setItem(47, goBankLocation);
        viewer.openInventory(this.inventory);
    }


    public void nextPage() throws NotExistNextPageException {
        GuiService guiService = (GuiService) new TransactionInvocationHandler(new GuiServiceImpl()).getProxy();
        this.itemStockMetaList = guiService.listPlayerItemStock(owner.getUniqueId().toString());
        int maxIndex = PageUtil.computeMaxPageIndex(itemStockMetaList.size(), 45);
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


    @NotNull
    public List<ItemStockMeta> getItemStockMetaList() {
        return new ArrayList<>(itemStockMetaList);
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
}