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
            MessageUtil.sendRaw(sender, "게임 데이터 저장 완료!");
            if (sender instanceof Player player)
                SoundUtil.playHighRing(player);
        }
        else {
            MessageUtil.sendRaw(sender, "게임 데이터 저장에 실패했습니다.");
            if (sender instanceof Player player)
                SoundUtil.playFail(player);
        }

        return false;
    }

}
