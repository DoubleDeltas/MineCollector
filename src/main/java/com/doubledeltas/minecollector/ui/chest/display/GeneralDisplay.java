package com.doubledeltas.minecollector.ui.chest.display;

import com.doubledeltas.minecollector.item.ItemBuilder;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.inventory.ItemStack;

import static com.doubledeltas.minecollector.lang.LangManager.translateToComponents;
import static com.doubledeltas.minecollector.lang.LangManager.translateToText;

public abstract class GeneralDisplay extends BaseDisplay {
    protected final int amount;
    protected final int level;

    public GeneralDisplay(int amount, int level) {
        super(amount > 0);
        this.amount = amount;
        this.level = level;
    }

    public ItemStack getCollectedItem() {
        int quo = amount / 64;
        int rem = amount % 64;

        BaseComponent[] countDisplay = (quo > 0)
                ? translateToComponents("game.stack_and_pcs", quo, rem)
                : translateToComponents("game.pcs", rem);

        BaseComponent[] components = new ComponentBuilder()
                .append(translateToComponents("gui.collection.count"))
                .append(" ")
                .append(countDisplay)
                .create();

        ItemStack baseItem = getBaseItem();
        baseItem.setAmount(level);
        ItemBuilder builder = new ItemBuilder(baseItem)
                .lore(BaseComponent.toLegacyText(components));

        if (level >= 5)
            builder.glowing();

        return builder.build();
    }

    @Override
    public ItemStack getUncollectedItem() {
        return new ItemBuilder(getBaseItem())
                .lore(translateToText("gui.collection.not_collected_yet"))
                .build();
    }

    public abstract ItemStack getBaseItem();
}
