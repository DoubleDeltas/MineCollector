package com.doubledeltas.minecollector.util;

import org.bukkit.Material;

/**
 * 마인크래프트 희귀도와 점수 배분에 관한 유틸 클래스
 * @author DoubleDeltas
 */
public class RarityUtil {
    enum Rarity {
        COMMON(1),
        UNCOMMON(2),
        RARE(3),
        UNIQUE(4);

        private final int amplifier;
        Rarity(int amplifier) {
            this.amplifier = amplifier;
        }
        public int getAmplifier() {
            return amplifier;
        }
    }

    /**
     * 마인크래프트 Material의 희귀도(Rarity)를 반환합니다.
     * @param material 마인크래프트 Material
     * @return 희귀도
     */
    public static Rarity getRarity(Material material) {
        switch (material) {
            case CREEPER_BANNER_PATTERN:
            case SKULL_BANNER_PATTERN:
            case EXPERIENCE_BOTTLE:
            case DRAGON_BREATH:
            case ELYTRA:
            case CREEPER_HEAD:
            case DRAGON_HEAD:
            case PIGLIN_HEAD:
            case PLAYER_HEAD:
            case ZOMBIE_HEAD:
            case HEART_OF_THE_SEA:
            case NETHER_STAR:
            case TOTEM_OF_UNDYING:
            case ENCHANTED_BOOK:
                return Rarity.UNCOMMON;

            case BEACON:
            case CONDUIT:
            case END_CRYSTAL:
            case GOLDEN_APPLE:
            case MUSIC_DISC_5:
            case MUSIC_DISC_11:
            case MUSIC_DISC_13:
            case MUSIC_DISC_BLOCKS:
            case MUSIC_DISC_CAT:
            case MUSIC_DISC_CHIRP:
            case MUSIC_DISC_FAR:
            case MUSIC_DISC_MALL:
            case MUSIC_DISC_MELLOHI:
            case MUSIC_DISC_OTHERSIDE:
            case MUSIC_DISC_PIGSTEP:
            case MUSIC_DISC_STAL:
            case MUSIC_DISC_STRAD:
            case MUSIC_DISC_WAIT:
            case MUSIC_DISC_WARD:
                return Rarity.RARE;

            case MOJANG_BANNER_PATTERN:
            case COMMAND_BLOCK:
            case CHAIN_COMMAND_BLOCK:
            case REPEATING_COMMAND_BLOCK:
            case DRAGON_EGG:
            case STRUCTURE_BLOCK:
            case STRUCTURE_VOID:
            case JIGSAW:
            case LIGHT:
            case BARRIER:
            case COMMAND_BLOCK_MINECART:
            case DEBUG_STICK:
            case KNOWLEDGE_BOOK:
            case ENCHANTED_GOLDEN_APPLE:
                return Rarity.UNIQUE;

            default:
                return Rarity.COMMON;
        }
    }

    /**
     * 마인크래프트 Material의 희귀도(Rarity)에 따른 배수를 반환합니다.
     * @param material 마인크래프트 Material
     * @return 희귀도 배수
     */
    public static int getAmplifier(Material material) {
        return getRarity(material).getAmplifier();
    }
}
