package com.doubledeltas.minecollector.mission.item;

import com.doubledeltas.minecollector.item.ItemBuilder;
import com.doubledeltas.minecollector.mission.MissionItem;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.MusicInstrument;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MusicInstrumentMeta;

@AllArgsConstructor
public enum GoatHorn implements MissionItem {
    PONDER(MusicInstrument.PONDER),
    SING(MusicInstrument.SING),
    SEEK(MusicInstrument.SEEK),
    FEEL(MusicInstrument.FEEL),
    ADMIRE(MusicInstrument.ADMIRE),
    CALL(MusicInstrument.CALL),
    YEARN(MusicInstrument.YEARN),
    DREAM(MusicInstrument.DREAM),
    ;

    MusicInstrument instrument;

    @Override
    public ItemStack getIcon() {
        return new ItemBuilder(Material.GOAT_HORN)
                .musicInstrument(instrument)
                .build();
    }

    @Override
    public boolean validate(ItemStack item) {
        if (item.getType() != Material.GOAT_HORN)
            return false;

        MusicInstrumentMeta miMeta = (MusicInstrumentMeta) item.getItemMeta();
        if (miMeta == null)
            return false;

        return miMeta.getInstrument() == instrument;
    }

    @Override
    public MissionItem getByName(String name) {
        return GoatHorn.valueOf(name);
    }
}
