package com.doubledeltas.minecollector.command.impl.mcol;

import com.doubledeltas.minecollector.command.AbstractCommandNode;
import com.doubledeltas.minecollector.command.impl.mcol.reload.ReloadConfigCommand;
import com.doubledeltas.minecollector.command.impl.mcol.reload.ReloadDataCommand;
import com.doubledeltas.minecollector.util.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ReloadCommand extends AbstractCommandNode {

    @Override
    public List<String> getAliases() {
        return List.of("reload", "리로드");
    }

    public ReloadCommand() {
        this.subcommands = List.of(
                new ReloadConfigCommand(),
                new ReloadDataCommand()
        );
    }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        MessageUtil.send(sender, "command.generic.invalid");
        MessageUtil.send(sender, "command.generic.correct_command", "/mcol reload [config | data | lang] ...");
        return false;
    }

    @Override
    public String getRequiredPermissionKey() {
        return null;
    }
}
