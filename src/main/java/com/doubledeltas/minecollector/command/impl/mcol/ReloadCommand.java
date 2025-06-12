package com.doubledeltas.minecollector.command.impl.mcol;

import com.doubledeltas.minecollector.command.CommandNode;
import com.doubledeltas.minecollector.command.impl.mcol.reload.ReloadConfigCommand;
import com.doubledeltas.minecollector.command.impl.mcol.reload.ReloadDataCommand;
import com.doubledeltas.minecollector.command.impl.mcol.reload.ReloadLangCommand;
import com.doubledeltas.minecollector.util.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ReloadCommand extends CommandNode {

    @Override
    public List<String> getAliases() {
        return List.of("reload", "리로드");
    }

    public ReloadCommand() {
        this.subcommands = List.of(
                new ReloadConfigCommand(),
                new ReloadDataCommand(),
                new ReloadLangCommand()
        );
    }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        MessageUtil.sendRaw(sender, "명령어가 잘못되었습니다!");
        MessageUtil.sendRaw(sender, "올바른 명령어: §e/mcol reload [config | data | lang] ...");
        return false;
    }
}
