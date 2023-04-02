package com.doubledeltas.minecollector.command.mcol;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.command.GameCommand;
import com.doubledeltas.minecollector.data.DataManager;
import com.doubledeltas.minecollector.util.SoundUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SaveCommand extends GameCommand {

    @Override
    public String getCommandName() {
        return "save";
    }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        if (DataManager.saveAll()) {
            MineCollector.send(sender, "게임 데이터 저장 완료!");
            if (sender instanceof Player player)
                SoundUtil.playHighRing(player);
        }
        else {
            MineCollector.send(sender, "게임 데이터 저장에 실패했습니다.");
            if (sender instanceof Player player)
                SoundUtil.playFail(player);
        }

        return false;
    }
}
