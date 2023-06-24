package com.doubledeltas.minecollector.data.mission;

import com.doubledeltas.minecollector.data.mission.items.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Getter
public enum Mission {
    POTION(new ItemStack(Material.POTION), "물약", Potion.values()),
    SPLASH_POTION(new ItemStack(Material.SPLASH_POTION), "투척용 물약", SplashPotion.values()),
    LINGERING_POTION(new ItemStack(Material.LINGERING_POTION), "잔류형 물약", LingeringPotion.values()),
    TIPPED_ARROW(new ItemStack(Material.TIPPED_ARROW), "물약이 발린 화살", TippedArrow.values()),
    SUSPICIOUS_STEW(new ItemStack(Material.SUSPICIOUS_STEW), "수상한 스튜", SuspiciousStew.values()),
    ENCHANTED_BOOK(new ItemStack(Material.ENCHANTED_BOOK), "마법이 부여된 책", EnchantedBook.values()),
    BANNER(new ItemStack(Material.WHITE_BANNER), "현수막", Banner.values()),
    ;

    private ItemStack icon;
    private String name;
    private List<? extends MissionItem> items;

    Mission(ItemStack icon, String name, MissionItem[] items) {
        this.icon = icon;
        this.name = name;
        this.items = Arrays.stream(items).toList();
    }
}
