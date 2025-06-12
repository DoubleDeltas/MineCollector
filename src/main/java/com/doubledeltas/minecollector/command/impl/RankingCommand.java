package com.doubledeltas.minecollector.command.impl;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.command.CommandRoot;
import com.doubledeltas.minecollector.command.impl.ranking.RankingItemCommand;
import com.doubledeltas.minecollector.config.McolConfig;
import com.doubledeltas.minecollector.data.GameData;
import com.doubledeltas.minecollector.data.GameStatistics;
import com.doubledeltas.minecollector.util.MessageUtil;
import com.doubledeltas.minecollector.util.SoundUtil;
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
        McolConfig.Scoring scoringConfig = MineCollector.getInstance().getMcolConfig().getScoring();

        Function<GameData, BigDecimal> keyFunc;
        String categoryWord;
        boolean enabled;

        if (args.length == 0 || List.of("total", "전체점수").contains(args[0])) {
            keyFunc = data -> new GameStatistics(data).getTotalScore();
            categoryWord = "전체 컬렉션";
            enabled = true;
        }
        else if (List.of("collection", "수집점수").contains(args[0])) {
            keyFunc = data -> new GameStatistics(data).getCollectionScore();
            categoryWord = "수집";
            enabled = scoringConfig.isCollectionEnabled();
        }
        else if (List.of("stack", "쌓기점수").contains(args[0])) {
            keyFunc = data -> new GameStatistics(data).getStackScore();
            categoryWord = "쌓기";
            enabled = scoringConfig.isStackEnabled();
        }
        else if (List.of("advancement", "발전점수").contains(args[0])) {
            keyFunc = data -> new GameStatistics(data).getAdvScore();
            categoryWord = "발전";
            enabled = scoringConfig.isAdvancementEnabled();
        }
        else {
            MessageUtil.sendRaw(sender, "§c랭킹 카테고리 입력이 잘못되었습니다!");
            if (sender instanceof Player player)
                SoundUtil.playFail(player);
            return false;
        }

        if (!enabled) {
            MessageUtil.sendRaw(sender, "§c" + categoryWord + " 기능이 비활성화되어 있습니다!");
            if (sender instanceof Player player)
                SoundUtil.playFail(player);
            return false;
        }

        List<GameData> top10 = plugin.getDataManager().getTop10(keyFunc);
        int top10Size = top10.size();

        MessageUtil.sendRaw(sender, "");
        MessageUtil.sendRaw(sender,"§e%s 점수 TOP 10 리스트:".formatted(categoryWord));

        if (top10Size == 1) { // 아무도 수집하지 않음
            MessageUtil.sendRaw(sender, " §7- 아직 아무도 아이템을 수집하지 않았군요! :)");
        }
        else {
            for (int i=1; i < top10Size; i++) {
                GameData data = top10.get(i);
                MessageUtil.sendRaw(sender,
                        " §7- "
                        + ((i < 10) ? "§70" : "")
                        + "§e%s. §f%s§7: §e§l%.1f".formatted(i, data.getName(), keyFunc.apply(data))
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
