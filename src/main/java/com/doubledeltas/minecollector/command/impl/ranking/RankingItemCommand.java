package com.doubledeltas.minecollector.command.impl.ranking;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.command.CommandNode;
import com.doubledeltas.minecollector.data.GameData;
import com.doubledeltas.minecollector.util.MessageUtil;
import com.doubledeltas.minecollector.util.SoundUtil;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RankingItemCommand extends CommandNode {
    @Override
    public List<String> getAliases() {
        return List.of("아이템", "item");
    }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        Function<GameData, Integer> keyFunc;

        if (args.length == 0) {
            MessageUtil.send(sender, "command.ranking_item.no_arguments");
            if (sender instanceof Player player)
                SoundUtil.playFail(player);
            return false;
        }

        Material material = Material.matchMaterial(args[0]);
        if (material == null) {
            MessageUtil.send(sender, "command.ranking_item.no_item_exists", args[0]);
            if (sender instanceof Player player)
                SoundUtil.playFail(player);
            return false;
        }

        keyFunc = data -> data.getCollection(material);

        List<GameData> top10 = plugin.getDataManager().getTop10(keyFunc);
        int top10Size = top10.size();

        MessageUtil.sendRaw(sender, "");
        MessageUtil.send(sender,
                new TextComponent("§e\""),
                new TranslatableComponent(material.getItemTranslationKey()),
                new TextComponent("§e\" 수집 수 TOP 10 리스트:")
        );

        if (top10Size == 1) { // 아무도 수집하지 않음
            MessageUtil.send(sender, "command.ranking_item.nobody_collected");
        }
        else {
            for (int i=1; i < top10Size; i++) {
                GameData data = top10.get(i);
                int amount = keyFunc.apply(data);
                int quo = amount / 64;
                int rem = amount % 64;
                if (quo == 0) {
                    MessageUtil.send(
                            sender, "command.ranking_item.line_format",
                            (i < 10) ? "§70" : "", i, data.getName(), amount
                    );
                }
                else {
                    MessageUtil.send(
                            sender, "command.ranking_item.line_format_2",
                            (i < 10) ? "§70" : "", i, data.getName(), amount, quo, rem
                    );
                }
            }
        }

        if (sender instanceof Player player)
            SoundUtil.playHighRing(player);

        return true;
    }

    @Override
    public List<String> getTabRecommendation(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 1)
            return List.of();

        return Arrays.stream(Material.values())
                .filter(material -> sender.isOp()
                        || !MineCollector.getInstance().getMcolConfig().getGame().isHideUnknownCollection()
                        || plugin.getDataManager().getData((Player) sender).getCollection(material) > 0
                )
                .map(material -> material.getKey().toString())
                .collect(Collectors.toList());
    }
}
