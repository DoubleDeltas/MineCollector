package com.doubledeltas.minecollector.command.impl.mcol;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.command.Subcommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ReloadCommand extends Subcommand {

    @Override
    public List<String> getAliases() {
        return List.of("reload", "리로드");
    }

    public ReloadCommand() {
        this.subcommands.add(new ConfigReloadCommand());
        this.subcommands.add(new DataReloadCommand());
    }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        MineCollector.send(sender, "명령어가 잘못되었습니다!");
        MineCollector.send(sender, "올바른 명령어: §e/mcol reload [config | data] ...");
        return false;
    }
}
