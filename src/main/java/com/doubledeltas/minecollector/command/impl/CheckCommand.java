package com.doubledeltas.minecollector.command.impl;

import com.doubledeltas.minecollector.collection.AirPiece;
import com.doubledeltas.minecollector.collection.Piece;
import com.doubledeltas.minecollector.command.StaticCommandRoot;
import com.doubledeltas.minecollector.data.GameData;
import com.doubledeltas.minecollector.util.MessageUtil;
import com.doubledeltas.minecollector.util.SoundUtil;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Optional;

public final class CheckCommand extends StaticCommandRoot {

    @Override
    public String getName() { return "체크"; }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            MessageUtil.send(sender, "command.generic.player_only");
            return false;
        }

        Piece piece;
        if (args.length == 0) {
            ItemStack item = player.getInventory().getItemInMainHand();
            piece = plugin.getCollectionManager().getPieceOf(item);
        }
        else {
            Optional<Piece> pieceOptional = plugin.getCollectionManager().findPieceOf(args[0]);
            if (pieceOptional.isEmpty()) {
                MessageUtil.send(player, "command.check.no_exist", args[0]);
                SoundUtil.playFail(player);
                return false;
            }
            piece = pieceOptional.get();
        }

        GameData data = plugin.getDataManager().getData(player);
        int level = piece.getLevel(data);
        int amount = piece.getAmount(data);
        int quo = amount / 64;
        int rem = amount % 64;
        BaseComponent itemComponent = piece.toChatComponent();

        if (amount == 0) {
            MessageUtil.send(player, "command.check.not_collected_yet", itemComponent);
            SoundUtil.playFail(player);
            return false;
        }

        if (piece == AirPiece.INSTANCE)
            MessageUtil.send(player, "command.check.collected_air", itemComponent);
        else if (quo == 0)
            MessageUtil.send(player, "command.check.collected", itemComponent, rem, level);
        else
            MessageUtil.send(player, "command.check.collected_2", itemComponent, quo, rem, level);

        SoundUtil.playHighRing(player);
        return true;
    }

    @Override
    public List<String> getTabRecommendation(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1)
            return plugin.getCollectionManager().recommendItemKeys(sender, args[0]);
        else
            return List.of();
    }
}
