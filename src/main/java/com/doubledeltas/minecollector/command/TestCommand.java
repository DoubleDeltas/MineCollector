package com.doubledeltas.minecollector.command;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.gui.HubGui;
import com.doubledeltas.minecollector.item.itemCode.GuiItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCommand extends MineCollectorCommand {

    @Override
    public String getCommandName() {
        return "테스트";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player))
            return false;
//        new HubGui().openGui(player);

        player.getInventory().addItem(MineCollector.getPlugin().getItemManager().getItem(GuiItem.RANKING));

        return true;
    }
}
