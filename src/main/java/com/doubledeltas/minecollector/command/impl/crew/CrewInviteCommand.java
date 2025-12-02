package com.doubledeltas.minecollector.command.impl.crew;

import com.doubledeltas.minecollector.command.CommandNode;
import com.doubledeltas.minecollector.crew.CrewManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CrewInviteCommand extends CommandNode {
    @Override
    public List<String> getAliases() {
        return List.of("invite", "초대");
    }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        CrewManager crewManager = plugin.getCrewManager();


        return false;
    }

    @Override
    public String getRequiredPermissionKey() {
        return null;
    }
}
