package com.doubledeltas.minecollector.mission.item;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.mission.MissionItem;
import com.doubledeltas.minecollector.item.itemCode.StaticItem;
import lombok.AllArgsConstructor;
import org.bukkit.block.banner.Pattern;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
public enum Banner implements MissionItem {
    OMINOUS(MineCollector.getInstance().getItemManager().getItem(StaticItem.OMINOUS_BANNER)),
    ;

    ItemStack item;

    @Override
    public ItemStack getIcon() {
        return this.item;
    }

    @Override
    public boolean validate(ItemStack item) {
        if (this.item.getType() != item.getType())
            return false;

        BannerMeta otherBMeta;
        try {
            otherBMeta = (BannerMeta) item.getItemMeta();
        }
        catch (ClassCastException ex) {
            return false;
        }

        List<Pattern> thisPatterns = ((BannerMeta) this.item.getItemMeta()).getPatterns();
        List<Pattern> otherPatterns = otherBMeta.getPatterns();

        return Objects.equals(thisPatterns, otherPatterns);
    }

    @Override
    public MissionItem getByName(String name) {
        return Banner.valueOf(name);
    }
}
