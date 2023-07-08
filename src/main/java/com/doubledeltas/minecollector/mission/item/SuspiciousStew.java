package com.doubledeltas.minecollector.mission.item;

import com.doubledeltas.minecollector.item.ItemBuilder;
import com.doubledeltas.minecollector.mission.MissionItem;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SuspiciousStewMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

@AllArgsConstructor
public enum SuspiciousStew implements MissionItem {
    FIRE_RESISTANCE(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 80, 1), "§9화염 저항"),
    BLINDNESS(new PotionEffect(PotionEffectType.BLINDNESS, 160, 1), "§9실명"),
    SATURATION(new PotionEffect(PotionEffectType.SATURATION, 7, 1), "§9포화"),
    JUMP_BOOST(new PotionEffect(PotionEffectType.JUMP, 120, 1), "§9점프 강화"),
    POISON(new PotionEffect(PotionEffectType.POISON, 240, 1), "§c독"),
    REGENERATION(new PotionEffect(PotionEffectType.REGENERATION, 160, 1), "§9재생"),
    NIGHT_VISION(new PotionEffect(PotionEffectType.NIGHT_VISION, 100, 1), "§9야간 투시"),
    WEAKNESS(new PotionEffect(PotionEffectType.WEAKNESS, 180, 1), "§c나약함"),
    WITHER(new PotionEffect(PotionEffectType.WITHER, 160, 1), "§c시듦"),
    ;

    PotionEffect effect;
    String effectName;

    @Override
    public ItemStack getIcon() {
        return new ItemBuilder(Material.SUSPICIOUS_STEW)
                .lore("§r§9%s".formatted(this.effectName))
                .build();
    }

    @Override
    public boolean validate(ItemStack item) {
        if (item.getType() != Material.SUSPICIOUS_STEW)
            return false;

        SuspiciousStewMeta sMeta = (SuspiciousStewMeta) item.getItemMeta();
        PotionEffect effect = sMeta.getCustomEffects().get(0);

        return Objects.equals(effect, this.effect);
    }

    @Override
    public MissionItem getByName(String name) {
        return SuspiciousStew.valueOf(name);
    }
}
