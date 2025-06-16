package com.doubledeltas.minecollector.command.impl.mcol;

import com.doubledeltas.minecollector.command.CommandNode;
import com.doubledeltas.minecollector.util.MessageUtil;
import com.doubledeltas.minecollector.util.SoundUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SaveCommand extends CommandNode {

    @Override
    public List<String> getAliases() {
        return List.of("save", "저장");
    }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        if (plugin.getDataManager().saveAll()) {
            MessageUtil.send(sender, "command.save.save_success");
            if (sender instanceof Player player)
                SoundUtil.playHighRing(player);
        }
        else {
            MessageUtil.send(sender, "command.save.save_failed");
            if (sender instanceof Player player)
                SoundUtil.playFail(player);
        }

        return false;
    }

}
