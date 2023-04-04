package com.doubledeltas.minecollector.command.impl.mcol;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.command.Subcommand;
import com.doubledeltas.minecollector.data.DataManager;
import com.doubledeltas.minecollector.util.SoundUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SaveCommand extends Subcommand {

    @Override
    public List<String> getAliases() {
        return List.of("save", "저장");
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
