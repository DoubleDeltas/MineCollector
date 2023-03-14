package com.doubledeltas.minecollector.command;

import com.doubledeltas.minecollector.util.SoundUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCommand extends MineCollectorCommand {

    @Override
    public String getCommandName() {
        return "테스트";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player))
            return false;

//        SoundUtil.playPageAll(player);

        return true;
    }
}
