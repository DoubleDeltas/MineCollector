package com.doubledeltas.minecollector.ui.book;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.ui.book.component.BookComponent;
import com.google.common.base.Preconditions;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public abstract class AbstractBookGui implements BookGui, Listener {
    protected MineCollector plugin;

    @Getter protected int numberOfPages;
    protected BookGuiPage[] pages;

    private static final int PAGE_MAX = 100;

    public AbstractBookGui() {
        this.plugin = MineCollector.getInstance();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        this.pages = new BookGuiPage[PAGE_MAX + 1];
        setNumberOfPages(1);
    }

    @Override
    public void setNumberOfPages(int numberOfPages) {
        Preconditions.checkArgument(1 <= numberOfPages && numberOfPages <= PAGE_MAX, "number of pages must be in 1-" + PAGE_MAX);

        if (numberOfPages < this.numberOfPages) {
            for (int i = numberOfPages + 1; i <= this.numberOfPages; i++) {
                pages[i] = null;
            }
        }
        else if (numberOfPages > this.numberOfPages) {
            for (int i = this.numberOfPages + 1; i <= numberOfPages; i++) {
                pages[i] = new BookGuiPageImpl();
            }
        }
        this.numberOfPages = numberOfPages;
    }

    protected void setPage(int page, BookComponent... components) {
        if (page > numberOfPages)
            setNumberOfPages(page);
        pages[page].clear();
        pages[page].addComponents(components);
    }

    @EventHandler
    public void handleEvent(PlayerEditBookEvent event) {
        event.getPlayer().sendMessage("what");
    }

    @Override
    public void openGui(Player player) {
        ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) item.getItemMeta();
        for (int i=1; i<=numberOfPages; i++)
            meta.spigot().addPage(pages[i].render());
        item.setItemMeta(meta);
        player.openBook(item);
    }
}
