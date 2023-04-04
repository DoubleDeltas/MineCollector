package com.doubledeltas.minecollector.command.impl;

import com.doubledeltas.minecollector.GameDirector;
import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.command.GameCommand;
import com.doubledeltas.minecollector.command.RootCommand;
import com.doubledeltas.minecollector.util.SoundUtil;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.stream.Stream;

public final class CollectCommand extends RootCommand {

    @Override
    public String getCommandName() { return "수집"; }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            MineCollector.send(sender, "§c이 명령어는 플레이어만 칠 수 있습니다!");
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
                MineCollector.send(player, "§c명령어를 정확히 입력해주세요! (/수집 or /수집 §7[숫자]§c)");
                SoundUtil.playFail(player);
                return false;
            }
        }

        if (amount < 1) {
            MineCollector.send(player, "§c수집할 아이템의 수는 1 이상이어야 합니다!");
            SoundUtil.playFail(player);
            return false;
        }
        if (amount > 64) {
            MineCollector.send(player, "§c수집할 아이템의 수는 64 이하이어야 합니다!");
            MineCollector.send(player, "§c('/수집' 명령어는 들고 있는 아이템만 수집할 수 있습니다)");
            SoundUtil.playFail(player);
            return false;
        }
        if (amount > handAmount && handType != Material.AIR) {
            MineCollector.send(player, "§c들고 있는 아이템의 수가 부족합니다!");
            SoundUtil.playFail(player);
            return false;
        }

        GameDirector.collect(player, new ItemStack(handItem.getType(), amount));
        handItem.setAmount(handAmount - amount);
        player.getInventory().setItemInMainHand(handItem);

        SoundUtil.playCollect(player);
        player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 100, 1, 1, 1);
        return true;
    }
}