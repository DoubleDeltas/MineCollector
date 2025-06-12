package com.doubledeltas.minecollector.command.impl;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.command.CommandRoot;
import com.doubledeltas.minecollector.data.GameData;
import com.doubledeltas.minecollector.util.MessageUtil;
import com.doubledeltas.minecollector.util.SoundUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class CheckCommand extends CommandRoot {

    @Override
    public String getName() { return "체크"; }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            MessageUtil.send(sender, "command.generic.player_only");
            return false;
        }

        Material material;
        if (args.length == 0)
            material = player.getInventory().getItemInMainHand().getType();
        else
            material = Material.matchMaterial(args[0]);

        if (material == null) {
            MessageUtil.sendRaw(player, "§e%s §c아이템은 존재하지 않습니다!".formatted(args[0]));
            SoundUtil.playFail(player);
            return false;
        }

        GameData data = plugin.getDataManager().getData(player);
        int level = data.getLevel(material);
        int amount = data.getCollection(material);
        int quo = amount / 64;
        int rem = amount % 64;
        BaseComponent itemNameComponent = new TranslatableComponent(material.getItemTranslationKey());
        itemNameComponent.setColor(ChatColor.YELLOW);

        if (amount == 0) {
            MessageUtil.send(player,
                    itemNameComponent,
                    new TextComponent(" §c아이템은 아직 수집되지 않았습니다!")
            );
            SoundUtil.playFail(player);
            return false;
        }

        if (material == Material.AIR)
            MessageUtil.send(player,
                    itemNameComponent,
                    new TextComponent(" §a아이템은 수집되었습니다!")
            );
        else if (quo == 0)
            MessageUtil.send(player,
                    itemNameComponent,
                    new TextComponent(" §a아이템은 §e%s§a개 수집되었습니다! (§e%s§a단계)".formatted(rem, level))
            );
        else
            MessageUtil.send(player,
                    itemNameComponent,
                    new TextComponent(" §a아이템은 §e%s§a셋 §e%s§a개 수집되었습니다! (§e%s§a단계)".formatted(quo, rem, level))
            );

        SoundUtil.playHighRing(player);
        return true;
    }

    @Override
    public List<String> getTabRecommendation(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1)
            return Arrays.stream(Material.values())
                    .filter(material -> sender.isOp()
                            || !MineCollector.getInstance().getMcolConfig().getGame().isHideUnknownCollection()
                            || plugin.getDataManager().getData((Player) sender).getCollection(material) > 0
                    )
                    .map(material -> material.getKey().toString())
                    .collect(Collectors.toList());
        return List.of();
    }
}
