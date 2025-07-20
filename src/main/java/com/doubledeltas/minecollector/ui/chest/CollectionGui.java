package com.doubledeltas.minecollector.ui.chest;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.collection.CollectionManager;
import com.doubledeltas.minecollector.collection.Piece;
import com.doubledeltas.minecollector.data.GameData;
import com.doubledeltas.minecollector.data.GameStatistics;
import com.doubledeltas.minecollector.item.ItemManager;
import com.doubledeltas.minecollector.item.itemCode.GuiItem;
import com.doubledeltas.minecollector.util.SoundUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class CollectionGui extends AbstractChestGui {
    private static final int INDEX_PREV = 47;
    private static final int INDEX_CORE = 48;
    private static final int INDEX_NEXT = 49;
    private static final int INDEX_BACK = 52;

    private static final int CAPACITY = 45;

    private final int page;
    private final int pieceCount;
    private final boolean isLastPage;

    public CollectionGui(Player player, int page) {
        super(6, "gui.collection.title");
        this.page = page;

        ItemManager itemManager = MineCollector.getInstance().getItemManager();
        CollectionManager collectionManager = MineCollector.getInstance().getCollectionManager();

        this.pieceCount = collectionManager.getSize();
        this.isLastPage = getLastPage() == page;

        GameData data = plugin.getDataManager().getData(player);
        for (int i=0; i < CAPACITY; i++) {
            int idx = (page - 1) * CAPACITY + i;
            if (idx >= pieceCount) {
                inventory.setItem(i, itemManager.getItem(GuiItem.GRAY));
                continue;
            }
            Piece piece = collectionManager.getPieceAt(idx);

            inventory.setItem(i, piece.createDisplay(data).getItem());
        }

        for (int i=45; i<=53; i++)
            inventory.setItem(i, itemManager.getItem(GuiItem.BLACK));

        ItemStack coreItem = itemManager.createItem(
                GuiItem.CORE,
                new GameStatistics(plugin.getDataManager().getData(player)).toMap()
        );
        coreItem.setAmount(page);
        inventory.setItem(INDEX_CORE,   coreItem);

        inventory.setItem(INDEX_PREV,   itemManager.getItem((page == 1) ? GuiItem.NO_PREV : GuiItem.PREV));
        inventory.setItem(INDEX_NEXT,   itemManager.getItem(isLastPage ? GuiItem.NO_NEXT : GuiItem.NEXT));
        inventory.setItem(INDEX_BACK,   itemManager.getItem(GuiItem.BACK));
    }


    @Override
    public void onClick(Player player, InventoryClickEvent e) {
        e.setCancelled(true);

        int rawSlot = e.getRawSlot();
        boolean shiftClick = e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY;
        if (rawSlot == INDEX_PREV && page > 1) {
            if (shiftClick) {
                new CollectionGui(player, 1).openGui(player);
                SoundUtil.playPageAll(player);
            }
            else {
                new CollectionGui(player, page - 1).openGui(player);
                SoundUtil.playPage(player);
            }
        }
        else if (rawSlot == INDEX_NEXT && !isLastPage) {
            if (shiftClick) {
                new CollectionGui(player, getLastPage()).openGui(player);
                SoundUtil.playPageAll(player);
            }
            else {
                new CollectionGui(player, page + 1).openGui(player);
                SoundUtil.playPage(player);
            }
        }
        else if (rawSlot == INDEX_BACK) {
            new HubGui().openGui(player);
            SoundUtil.playPage(player);
        }
    }

    private int getLastPage() {
        return (int) Math.ceil((double) pieceCount / CAPACITY);
    }
}
