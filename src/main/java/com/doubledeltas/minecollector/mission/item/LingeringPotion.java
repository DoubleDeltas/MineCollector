package com.doubledeltas.minecollector.mission.item;

import com.doubledeltas.minecollector.mission.PotionLike;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

@Getter
public enum LingeringPotion implements PotionLike {
    WATER(new PotionData(PotionType.WATER, false, false)),
    MUNDANE(new PotionData(PotionType.MUNDANE, false, false)),
    THICK(new PotionData(PotionType.THICK, false, false)),
    AWKWARD(new PotionData(PotionType.AWKWARD, false, false)),
    NIGHT_VISION(new PotionData(PotionType.NIGHT_VISION, false, false)),
    NIGHT_VISION_LONG(new PotionData(PotionType.NIGHT_VISION, true, false)),
    INVISIBILITY(new PotionData(PotionType.INVISIBILITY, false, false)),
    INVISIBILITY_LONG(new PotionData(PotionType.INVISIBILITY, true, false)),
    JUMP(new PotionData(PotionType.JUMP, false, false)),
    JUMP_LONG(new PotionData(PotionType.JUMP, true, false)),
    JUMP_STRONG(new PotionData(PotionType.JUMP, false, true)),
    FIRE_RESISTANCE(new PotionData(PotionType.FIRE_RESISTANCE, false, false)),
    FIRE_RESISTANCE_LONG(new PotionData(PotionType.FIRE_RESISTANCE, true, false)),
    SPEED(new PotionData(PotionType.SPEED, false, false)),
    SPEED_LONG(new PotionData(PotionType.SPEED, true, false)),
    SPEED_STRONG(new PotionData(PotionType.SPEED, false, true)),
    SLOWNESS(new PotionData(PotionType.SLOWNESS, false, false)),
    SLOWNESS_LONG(new PotionData(PotionType.SLOWNESS, true, false)),
    SLOWNESS_STRONG(new PotionData(PotionType.SLOWNESS, false, true)),
    WATER_BREATHING(new PotionData(PotionType.WATER_BREATHING, false, false)),
    WATER_BREATHING_LONG(new PotionData(PotionType.WATER_BREATHING, true, false)),
    INSTANT_HEAL(new PotionData(PotionType.INSTANT_HEAL, false, false)),
    INSTANT_HEAL_STRONG(new PotionData(PotionType.INSTANT_HEAL, false, true)),
    INSTANT_DAMAGE(new PotionData(PotionType.INSTANT_DAMAGE, false, false)),
    INSTANT_DAMAGE_STRONG(new PotionData(PotionType.INSTANT_DAMAGE, false, true)),
    POISON(new PotionData(PotionType.POISON, false, false)),
    POISON_LONG(new PotionData(PotionType.POISON, true, false)),
    POISON_STRONG(new PotionData(PotionType.POISON, false, true)),
    REGENERATION(new PotionData(PotionType.REGEN, false, false)),
    REGENERATION_LONG(new PotionData(PotionType.REGEN, true, false)),
    REGENERATION_STRONG(new PotionData(PotionType.REGEN, false, true)),
    STRENGTH(new PotionData(PotionType.STRENGTH, false, false)),
    STRENGTH_LONG(new PotionData(PotionType.STRENGTH, true, false)),
    STRENGTH_STRONG(new PotionData(PotionType.STRENGTH, false, true)),
    WEAKNESS(new PotionData(PotionType.WEAKNESS, false, false)),
    WEAKNESS_LONG(new PotionData(PotionType.WEAKNESS, true, false)),
    LUCK(new PotionData(PotionType.LUCK, false, false)),
    TURTLE_MASTER(new PotionData(PotionType.TURTLE_MASTER, false, false)),
    TURTLE_MASTER_LONG(new PotionData(PotionType.TURTLE_MASTER, true, false)),
    TURTLE_MASTER_STRONG(new PotionData(PotionType.TURTLE_MASTER, false, true)),
    SLOW_FALLING(new PotionData(PotionType.SLOW_FALLING, false, false)),
    SLOW_FALLING_LONG(new PotionData(PotionType.SLOW_FALLING, true, false)),
    ;

    Material material;
    PotionData potionData;

    LingeringPotion(PotionData potionData) {
        this.material = Material.LINGERING_POTION;
        this.potionData = potionData;
    }
}
