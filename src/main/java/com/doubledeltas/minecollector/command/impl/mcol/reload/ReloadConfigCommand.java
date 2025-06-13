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
//            MessageUtil.sendRaw(sender, "콘피그를 리로드하였습니다!");
            MessageUtil.send(sender, "command.reload_config.config_reloaded");
            if (sender instanceof Player player)
                SoundUtil.playHighRing(player);
        }
        catch (InvalidConfigException e) {
            MessageUtil.send(sender, "command.reload_config.failed_to_reload");
            MessageUtil.sendRaw(sender, "§7 - " + e.getMessage());
            e.printStackTrace();
            if (sender instanceof Player player)
                SoundUtil.playHighRing(player);
        }

        return false;
    }
}
