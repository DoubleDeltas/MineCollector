package com.doubledeltas.minecollector.command.mcol;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.command.GameCommand;
import com.doubledeltas.minecollector.util.SoundUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DataReloadCommand extends GameCommand {
    @Override
    public String getCommandName() {
        return "data";
    }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        MineCollector.send(sender, "데이터를 리로드하였습니다!");
        if (sender instanceof Player player)
            SoundUtil.playHighRing(player);
        
        return false;
    }
}
