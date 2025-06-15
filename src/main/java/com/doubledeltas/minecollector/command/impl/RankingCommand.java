package com.doubledeltas.minecollector.command.impl;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.command.CommandRoot;
import com.doubledeltas.minecollector.command.impl.ranking.RankingItemCommand;
import com.doubledeltas.minecollector.config.McolConfig;
import com.doubledeltas.minecollector.data.GameData;
import com.doubledeltas.minecollector.data.GameStatistics;
import com.doubledeltas.minecollector.lang.LangManager;
import com.doubledeltas.minecollector.lang.MessageKey;
import com.doubledeltas.minecollector.util.MessageUtil;
import com.doubledeltas.minecollector.util.SoundUtil;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

public final class RankingCommand extends CommandRoot {

    public RankingCommand() {
        this.subcommands = List.of(new RankingItemCommand());
    }

    @Override
    public String getName() { return "랭킹"; }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        McolConfig.Scoring scoringConfig = plugin.getMcolConfig().getScoring();
        LangManager langManager = plugin.getLangManager();

        Function<GameData, BigDecimal> keyFunc;
        BaseComponent[] categoryWord;
        boolean enabled;

        if (args.length == 0 || List.of("total", "전체점수").contains(args[0])) {
            keyFunc = data -> new GameStatistics(data).getTotalScore();
            categoryWord = langManager.translate(MessageKey.of("command.ranking.category_total"));
            enabled = true;
        }
        else if (List.of("collection", "수집점수").contains(args[0])) {
            keyFunc = data -> new GameStatistics(data).getCollectionScore();
            categoryWord = langManager.translate(MessageKey.of("command.ranking.category_collection"));
            enabled = scoringConfig.isCollectionEnabled();
        }
        else if (List.of("stack", "쌓기점수").contains(args[0])) {
            keyFunc = data -> new GameStatistics(data).getStackScore();
            categoryWord = langManager.translate(MessageKey.of("command.ranking.category_stack"));
            enabled = scoringConfig.isStackEnabled();
        }
        else if (List.of("advancement", "발전점수").contains(args[0])) {
            keyFunc = data -> new GameStatistics(data).getAdvScore();
            categoryWord = langManager.translate(MessageKey.of("command.ranking.category_advancement"));
            enabled = scoringConfig.isAdvancementEnabled();
        }
        else {
            MessageUtil.send(sender, "command.ranking.invalid_category");
            if (sender instanceof Player player)
                SoundUtil.playFail(player);
            return false;
        }

        if (!enabled) {
            MessageUtil.send(sender, "command.ranking.disabled_category", (Object[]) categoryWord);
            if (sender instanceof Player player)
                SoundUtil.playFail(player);
            return false;
        }

        List<GameData> top10 = plugin.getDataManager().getTop10(keyFunc);
        int top10Size = top10.size();

        MessageUtil.sendRaw(sender, "");
        MessageUtil.send(sender, "command.ranking.title", (Object[]) categoryWord);

        if (top10Size == 1) { // 아무도 수집하지 않음
            MessageUtil.send(sender, "command.ranking.nobody_scored");
        }
        else {
            for (int i=1; i < top10Size; i++) {
                GameData data = top10.get(i);
                MessageUtil.send(
                        sender, "command.ranking.line_format",
                        (i < 10) ? "0" : "", i, data.getName(), keyFunc.apply(data)
                );
            }
        }

        if (sender instanceof Player player)
            SoundUtil.playHighRing(player);

        return true;
    }

    @Override
    public List<String> getTabRecommendation(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1)
            return List.of(
                    "total", "collection", "stack", "advancement",
                    "전체점수", "수집점수", "쌓기점수", "발전점수"
            );
        return List.of();
    }
}
