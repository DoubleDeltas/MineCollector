package com.doubledeltas.minecollector.mission;

import com.doubledeltas.minecollector.item.ItemBuilder;
import com.doubledeltas.minecollector.mission.item.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

@Getter
public enum Mission {
    POTION(Material.POTION, "물약", Potion.values()),
    SPLASH_POTION(Material.SPLASH_POTION, "투척용 물약", SplashPotion.values()),
    LINGERING_POTION(Material.LINGERING_POTION, "잔류형 물약", LingeringPotion.values()),
    TIPPED_ARROW(Material.TIPPED_ARROW, "물약이 발린 화살", TippedArrow.values()),
    SUSPICIOUS_STEW(Material.SUSPICIOUS_STEW, "수상한 스튜", SuspiciousStew.values()),
    ENCHANTED_BOOK(Material.ENCHANTED_BOOK, "마법이 부여된 책", EnchantedBook.values()),
    BANNER(Material.WHITE_BANNER, "현수막", Banner.values()),
    ;

    private ItemStack icon;
    private String name;
    private MissionItem[] items;

    Mission(Material iconMaterial, String name, MissionItem[] items) {
        ItemBuilder builder = new ItemBuilder(iconMaterial)
                .displayName("§e§o%s".formatted(name))
                .lore(
                        "",
                        "§7달성률: 0 / %d".formatted(items.length)
                )
                .potionData(new PotionData(PotionType.WATER))
                .itemFlags(ItemFlag.HIDE_POTION_EFFECTS);

        this.icon = builder.build();
        this.name = name;
        this.items = items;
    }
}
