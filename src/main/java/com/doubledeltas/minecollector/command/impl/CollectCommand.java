package com.doubledeltas.minecollector.command.impl;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.command.CommandRoot;
import com.doubledeltas.minecollector.util.MessageUtil;
import com.doubledeltas.minecollector.util.SoundUtil;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.Stream;

public final class CollectCommand extends CommandRoot {

    @Override
    public String getName() { return "수집"; }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            MessageUtil.sendRaw(sender, "§c이 명령어는 플레이어만 칠 수 있습니다!");
            return false;
        }

        if (!MineCollector.getInstance().getMcolConfig().isEnabled()) {
            MessageUtil.sendRaw(sender, "§c지금은 수집할 수 없습니다!");
            SoundUtil.playFail(player);
            return false;
        }

        int amount;

        ItemStack handItem = player.getInventory().getItemInMainHand();
        int handAmount = handItem.getAmount();
        Material handType = handItem.getType();

        if (handType == Material.AIR)
            amount = 1;
        else if (args.length < 1)
            amount = 1;
        else if (Stream.of("다", "전부", "모두", "all", "ek", "wjsqn", "ahen").anyMatch(args[0]::equalsIgnoreCase))
            amount = handAmount;
        else {
            try {
                amount = Integer.parseInt(args[0]);
            }
            catch (NumberFormatException e) {
                MessageUtil.sendRaw(player, "§c명령어를 정확히 입력해주세요! (/수집 or /수집 §7[숫자]§c)");
                SoundUtil.playFail(player);
                return false;
            }
        }

        if (!plugin.getGameDirector().isCollectable(handItem)) {
            MessageUtil.sendRaw(player, "§c이 아이템은 수집할 수 없습니다!");
            SoundUtil.playFail(player);
            return false;
        }        
        if (amount < 1) {
            MessageUtil.sendRaw(player, "§c수집할 아이템의 수는 1 이상이어야 합니다!");
            SoundUtil.playFail(player);
            return false;
        }
        if (amount > 64) {
            MessageUtil.sendRaw(player, "§c수집할 아이템의 수는 64 이하이어야 합니다!");
            MessageUtil.sendRaw(player, "§c('/수집' 명령어는 들고 있는 아이템만 수집할 수 있습니다)");
            SoundUtil.playFail(player);
            return false;
        }
        if (amount > handAmount && handType != Material.AIR) {
            MessageUtil.sendRaw(player, "§c들고 있는 아이템의 수가 부족합니다!");
            SoundUtil.playFail(player);
            return false;
        }

        plugin.getGameDirector().collect(player, new ItemStack(handItem.getType(), amount));
        handItem.setAmount(handAmount - amount);
        player.getInventory().setItemInMainHand(handItem);

        SoundUtil.playCollect(player);
        player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 100, 1, 1, 1);
        return true;
    }

    @Override
    public List<String> getTabRecommendation(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 1) return List.of();

        return List.of("1", "all", "모두");
    }
}
