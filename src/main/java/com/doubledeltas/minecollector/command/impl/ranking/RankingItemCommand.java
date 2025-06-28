package com.doubledeltas.minecollector.command.impl.ranking;

import com.doubledeltas.minecollector.collection.Piece;
import com.doubledeltas.minecollector.command.CommandNode;
import com.doubledeltas.minecollector.data.GameData;
import com.doubledeltas.minecollector.util.MessageUtil;
import com.doubledeltas.minecollector.util.SoundUtil;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

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

        Optional<Piece> pieceOptional = plugin.getCollectionManager().findPieceOf(args[0]);
        if (pieceOptional.isEmpty()) {
            MessageUtil.send(sender, "command.ranking_item.no_item_exists", args[0]);
            if (sender instanceof Player player)
                SoundUtil.playFail(player);
            return false;
        }
        Piece piece = pieceOptional.get();

        keyFunc = piece::getAmount;

        List<GameData> top10 = plugin.getDataManager().getTop10(keyFunc);
        int top10Size = top10.size();

        MessageUtil.sendRaw(sender, "");
        BaseComponent itemComponent = piece.toChatComponent();
        MessageUtil.send(sender, "command.ranking_item.title", itemComponent);

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
        if (args.length == 1)
            return plugin.getCollectionManager().recommendItemKeys(sender, args[0]);
        else
            return List.of();
    }
}
