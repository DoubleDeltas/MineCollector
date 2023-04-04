package com.doubledeltas.minecollector.command.mcol;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.command.GameCommand;
import com.doubledeltas.minecollector.data.DataManager;
import com.doubledeltas.minecollector.util.SoundUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ConfigReloadCommand extends GameCommand {
    @Override
    public String getCommandName() {
        return "config";
    }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        DataManager.loadConfig();
        MineCollector.send(sender, "콘피그를 리로드하였습니다!");
        if (sender instanceof Player player)
            SoundUtil.playHighRing(player);

        return false;
    }
}
