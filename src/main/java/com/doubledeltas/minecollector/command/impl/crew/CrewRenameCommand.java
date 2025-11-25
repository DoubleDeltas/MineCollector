package com.doubledeltas.minecollector.command.impl.crew;

import com.doubledeltas.minecollector.command.CommandNode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CrewRenameCommand extends CommandNode {
    @Override
    public List<String> getAliases() {
        return List.of("rename", "이름변경");
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
