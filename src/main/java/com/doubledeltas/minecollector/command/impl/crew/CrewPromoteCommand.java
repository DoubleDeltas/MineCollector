package com.doubledeltas.minecollector.command.impl.crew;

import com.doubledeltas.minecollector.command.CommandNode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CrewPromoteCommand extends CommandNode {
    @Override
    public List<String> getAliases() {
        return List.of("promote", "리더임명");
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
