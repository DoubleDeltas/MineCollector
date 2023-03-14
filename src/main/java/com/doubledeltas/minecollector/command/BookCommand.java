package com.doubledeltas.minecollector.command;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.util.NoticeUtil;
import com.doubledeltas.minecollector.util.SoundUtil;
import com.doubledeltas.minecollector.item.itemCode.StaticItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class BookCommand extends MineCollectorCommand {

    @Override
    public String getCommandName() { return "도감"; }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        ItemStack collectionBook = MineCollector.getPlugin().getItemManager().getItem(StaticItem.COLLECTION_BOOK);

        if (player.getInventory().contains(collectionBook)) {
            NoticeUtil.send(player, "이미 도감을 가지고 있군요! 인벤토리를 다시 찾아보실래요?");
            SoundUtil.playFail(player);
        }
        else {
            player.getInventory().addItem(collectionBook);
            NoticeUtil.send(player, "도감을 인벤토리에 넣어드렸어요! 다음엔 잃어버리지 않게 조심하세요!");
            SoundUtil.playHighRing(player);
        }


        return false;
    }
}
