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
            MessageUtil.send(sender, "command.generic.player_only");
            return false;
        }

        if (!MineCollector.getInstance().getMcolConfig().isEnabled()) {
            MessageUtil.send(sender, "command.collect.cant_collect_now");
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
                MessageUtil.send(player, "command.collect.invalid_format");
                SoundUtil.playFail(player);
                return false;
            }
        }

        if (!plugin.getGameDirector().isCollectable(handItem)) {
            MessageUtil.send(player, "command.collect.uncollectable_item");
            SoundUtil.playFail(player);
            return false;
        }        
        if (amount < 1) {
            MessageUtil.send(player, "command.collect.amount_1");
            SoundUtil.playFail(player);
            return false;
        }
        if (amount > 64) {
            MessageUtil.send(player, "command.collect.amount_64_1");
            MessageUtil.send(player, "command.collect.amount_64_2");
            SoundUtil.playFail(player);
            return false;
        }
        if (amount > handAmount && handType != Material.AIR) {
            MessageUtil.send(player, "command.collect.amount_insufficient");
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
