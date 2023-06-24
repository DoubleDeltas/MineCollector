package com.doubledeltas.minecollector.data.mission.items;

import com.doubledeltas.minecollector.data.mission.PotionLike;
import lombok.Getter;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

@Getter
public enum TippedArrow implements PotionLike {
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
    FIRE_RESISTANCE_STRONG(new PotionData(PotionType.FIRE_RESISTANCE, false, true)),
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
    WEAKNESS_STRONG(new PotionData(PotionType.WEAKNESS, false, true)),
    LUCK(new PotionData(PotionType.LUCK, false, false)),
    LUCK_LONG(new PotionData(PotionType.LUCK, true, false)),
    LUCK_STRONG(new PotionData(PotionType.LUCK, false, true)),
    TURTLE_MASTER(new PotionData(PotionType.TURTLE_MASTER, false, false)),
    TURTLE_MASTER_LONG(new PotionData(PotionType.TURTLE_MASTER, true, false)),
    TURTLE_MASTER_STRONG(new PotionData(PotionType.TURTLE_MASTER, false, true)),
    SLOW_FALLING(new PotionData(PotionType.SLOW_FALLING, false, false)),
    SLOW_FALLING_LONG(new PotionData(PotionType.SLOW_FALLING, true, false)),
    SLOW_FALLING_STRONG(new PotionData(PotionType.SLOW_FALLING, false, true)),
    ;

    Type type;
    PotionData potionData;

    TippedArrow(PotionData potionData) {
        this.type = Type.ARROW;
        this.potionData = potionData;
    }
}
