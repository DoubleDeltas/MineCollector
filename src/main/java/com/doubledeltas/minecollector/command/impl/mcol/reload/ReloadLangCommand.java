package com.doubledeltas.minecollector.command.impl.mcol.reload;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.command.CommandNode;
import com.doubledeltas.minecollector.config.InvalidConfigException;
import com.doubledeltas.minecollector.lang.InvalidLangException;
import com.doubledeltas.minecollector.util.MessageUtil;
import com.doubledeltas.minecollector.util.SoundUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ReloadLangCommand extends CommandNode {
    @Override
    public List<String> getAliases() {
        return List.of("lang", "언어");
    }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            MineCollector.getInstance().getLangManager().reloadLang();
            MessageUtil.send(sender, "command.reload_lang.lang_reloaded");
            if (sender instanceof Player player)
                SoundUtil.playHighRing(player);
        }
        catch (InvalidLangException e) {
            MessageUtil.send(sender, "command.reload_lang.failed_to_reload");
            MessageUtil.sendRaw(sender, "§7 - " + e.getMessage());
            e.printStackTrace();
            if (sender instanceof Player player)
                SoundUtil.playHighRing(player);
        }

        return false;
    }
}
