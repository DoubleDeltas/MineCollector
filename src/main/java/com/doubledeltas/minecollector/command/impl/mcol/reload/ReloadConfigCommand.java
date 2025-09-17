package com.doubledeltas.minecollector.command.impl.mcol.reload;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.command.AbstractCommandNode;
import com.doubledeltas.minecollector.config.InvalidConfigException;
import com.doubledeltas.minecollector.util.MessageUtil;
import com.doubledeltas.minecollector.util.SoundUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ReloadConfigCommand extends AbstractCommandNode {
    @Override
    public List<String> getAliases() {
        return List.of("config", "콘피그");
    }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            plugin.reloadMcolConfig();
            boolean langReloadResult = MineCollector.getInstance().getLangManager().loadLang();
            if (!langReloadResult) {
                String lang = plugin.getMcolConfig().getLang();
                MessageUtil.send(sender, "command.reload_config.lang_failed");
                MessageUtil.send(sender, "command.reload_config.lang_failed_2", lang);
                if (sender instanceof Player player)
                    SoundUtil.playFail(player);
                return false;
            }
            MessageUtil.send(sender, "command.reload_config.config_reloaded");
            if (sender instanceof Player player)
                SoundUtil.playHighRing(player);
        }
        catch (InvalidConfigException e) {
            MessageUtil.send(sender, "command.reload_config.failed_to_reload");
            MessageUtil.sendRaw(sender, "§7 - " + e.getMessage());
            e.printStackTrace();
            if (sender instanceof Player player)
                SoundUtil.playHighRing(player);
        }

        return false;
    }

    @Override
    public String getRequiredPermissionKey() {
        return "minecollector.mcol.reload.config";
    }
}
