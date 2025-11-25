package com.doubledeltas.minecollector.command.impl.crew;

import com.doubledeltas.minecollector.command.CommandNode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CrewJoinCommand extends CommandNode {
    @Override
    public List<String> getAliases() {
        return List.of("join", "참여", "가입");
    }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }

    @Override
    public String getRequiredPermissionKey() {
        return null;
    }
}
