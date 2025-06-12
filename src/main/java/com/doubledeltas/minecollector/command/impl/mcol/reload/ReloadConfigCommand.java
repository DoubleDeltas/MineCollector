package com.doubledeltas.minecollector.command.impl.mcol.reload;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.command.CommandNode;
import com.doubledeltas.minecollector.config.InvalidConfigException;
import com.doubledeltas.minecollector.util.MessageUtil;
import com.doubledeltas.minecollector.util.SoundUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ReloadConfigCommand extends CommandNode {
    @Override
    public List<String> getAliases() {
        return List.of("config", "콘피그");
    }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            MineCollector.getInstance().reloadMcolConfig();
            MessageUtil.sendRaw(sender, "콘피그를 리로드하였습니다!");
            if (sender instanceof Player player)
                SoundUtil.playHighRing(player);
        }
        catch (InvalidConfigException e) {
            MessageUtil.sendRaw(sender, "§c콘피그 로딩에 실패했습니다!");
            MessageUtil.sendRaw(sender, "§7 - " + e.getMessage());
            MessageUtil.sendRaw(sender, "§7    (자세한 내용은 버킷 창을 참고해주세요.)");
            e.printStackTrace();
            if (sender instanceof Player player)
                SoundUtil.playHighRing(player);
        }

        return false;
    }
}
