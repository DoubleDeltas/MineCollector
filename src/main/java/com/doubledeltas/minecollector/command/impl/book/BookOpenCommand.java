package com.doubledeltas.minecollector.command.impl.book;

import com.doubledeltas.minecollector.command.CommandNode;
import com.doubledeltas.minecollector.util.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class BookOpenCommand extends CommandNode {
    @Override
    public List<String> getAliases() {
        return List.of("열기", "open", "dufrl");
    }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            MessageUtil.send(sender, "command.generic.player_only");
            return false;
        }

        plugin.getGameDirector().tryOpenHubGui(player);
        return false;
    }

    @Override
    public List<String> getTabRecommendation(CommandSender sender, Command command, String label, String[] args) {
        return List.of("", "열기");
    }
}
