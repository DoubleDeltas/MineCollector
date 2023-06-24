package com.doubledeltas.minecollector.data.mission.items;

import com.doubledeltas.minecollector.data.mission.MissionItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SuspiciousStewMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public enum SuspiciousStew implements MissionItem {
    FIRE_RESISTANCE(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 80, 1)),
    BLINDNESS(new PotionEffect(PotionEffectType.BLINDNESS, 160, 1)),
    SATURATION(new PotionEffect(PotionEffectType.SATURATION, 7, 1)),
    JUMP_BOOST(new PotionEffect(PotionEffectType.JUMP, 120, 1)),
    POISON(new PotionEffect(PotionEffectType.POISON, 240, 1)),
    REGENERATION(new PotionEffect(PotionEffectType.REGENERATION, 160, 1)),
    NIGHT_VISION(new PotionEffect(PotionEffectType.NIGHT_VISION, 100, 1)),
    WEAKNESS(new PotionEffect(PotionEffectType.WEAKNESS, 180, 1)),
    WITHER(new PotionEffect(PotionEffectType.WITHER, 160, 1)),
    ;

    PotionEffect effect;

    SuspiciousStew(PotionEffect effect) {
        this.effect = effect;
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Material.SUSPICIOUS_STEW);
    }

    @Override
    public boolean validate(ItemStack item) {
        if (item.getType() != Material.SUSPICIOUS_STEW)
            return false;

        SuspiciousStewMeta sMeta = (SuspiciousStewMeta) item.getItemMeta();
        PotionEffect effect = sMeta.getCustomEffects().get(0);

        return Objects.equals(effect, this.effect);
    }
}
