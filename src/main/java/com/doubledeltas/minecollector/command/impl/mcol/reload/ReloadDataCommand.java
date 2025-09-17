package com.doubledeltas.minecollector.command.impl.mcol.reload;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.command.AbstractCommandNode;
import com.doubledeltas.minecollector.util.MessageUtil;
import com.doubledeltas.minecollector.util.SoundUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ReloadDataCommand extends AbstractCommandNode {
    @Override
    public List<String> getAliases() {
        return List.of("data", "데이터");
    }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        MineCollector.getInstance().getDataManager().loadData();
        MessageUtil.send(sender, "command.reload_data.data_reloaded");
        if (sender instanceof Player player)
            SoundUtil.playHighRing(player);
        
        return false;
    }

    @Override
    public String getRequiredPermissionKey() {
        return "minecollector.mcol.reload.data";
    }
}
