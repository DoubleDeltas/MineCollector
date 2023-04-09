package com.doubledeltas.minecollector.command.impl.mcol;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.command.CommandNode;
import com.doubledeltas.minecollector.data.DataManager;
import com.doubledeltas.minecollector.util.MessageUtil;
import com.doubledeltas.minecollector.util.SoundUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ReloadDataCommand extends CommandNode {
    @Override
    public List<String> getAliases() {
        return List.of("data", "데이터");
    }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        DataManager.loadData();
        MessageUtil.send(sender, "데이터를 리로드하였습니다!");
        if (sender instanceof Player player)
            SoundUtil.playHighRing(player);
        
        return false;
    }
}
