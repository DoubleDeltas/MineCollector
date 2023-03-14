package com.doubledeltas.minecollector.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TestCommand extends MineCollectorCommand {
    static {
        MineCollectorCommand.addCommand(new TestCommand());
    }

    @Override
    public String getCommandName() {
        return "테스트";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player))
            return false;

        ItemStack holdingItem = player.getInventory().getItemInMainHand();
        player.sendMessage(holdingItem.serialize().toString());

        return true;
    }
}
