package com.doubledeltas.minecollector.command.impl.mcol.reload;

import com.doubledeltas.minecollector.command.CommandNode;
import com.doubledeltas.minecollector.config.Config;
import com.doubledeltas.minecollector.util.MessageUtil;
import com.doubledeltas.minecollector.util.SoundUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.FileNotFoundException;
import java.util.List;

public class ReloadConfigCommand extends CommandNode {
    @Override
    public List<String> getAliases() {
        return List.of("config", "콘피그");
    }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        Config.load();
        MessageUtil.send(sender, "콘피그를 리로드하였습니다!");
        if (sender instanceof Player player)
            SoundUtil.playHighRing(player);

        return false;
    }
}
