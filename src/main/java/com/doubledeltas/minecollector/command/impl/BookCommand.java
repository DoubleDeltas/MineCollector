package com.doubledeltas.minecollector.command.impl;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.command.CommandRoot;
import com.doubledeltas.minecollector.command.impl.book.BookOpenCommand;
import com.doubledeltas.minecollector.item.itemCode.StaticItem;
import com.doubledeltas.minecollector.util.MessageUtil;
import com.doubledeltas.minecollector.util.SoundUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public final class BookCommand extends CommandRoot {

    public BookCommand() {
        this.subcommands = List.of(new BookOpenCommand());
    }

    @Override
    public String getName() { return "도감"; }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            MessageUtil.sendRaw(sender, "이 명령어는 플레이어만 사용할 수 있습니다!");
            return false;
        }

        ItemStack collectionBook = MineCollector.getInstance().getItemManager().getItem(StaticItem.COLLECTION_BOOK);

        if (player.getInventory().contains(collectionBook) || player.getInventory().getItemInOffHand().equals(collectionBook)) {
            MessageUtil.sendRaw(player, "이미 도감을 가지고 있군요! 인벤토리를 다시 찾아보실래요?");
            SoundUtil.playFail(player);
        }
        else {
            player.getInventory().addItem(collectionBook);
            MessageUtil.sendRaw(player, "도감을 인벤토리에 넣어드렸어요! 다음엔 잃어버리지 않게 조심하세요!");
            SoundUtil.playHighRing(player);
        }

        return false;
    }
}
