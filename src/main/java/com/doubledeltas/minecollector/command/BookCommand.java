package com.doubledeltas.minecollector.command;

import com.doubledeltas.minecollector.MineCollector;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class BookCommand extends MineCollectorCommand {
    static {
        MineCollectorCommand.addCommand(new BookCommand());
    }

    @Override
    public String getCommandName() { return "도감"; }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        ItemStack testItem = MineCollector.getPlugin().itemManager.createItem("collect_book");

        player.getInventory().addItem(testItem);
        return false;
    }
}
