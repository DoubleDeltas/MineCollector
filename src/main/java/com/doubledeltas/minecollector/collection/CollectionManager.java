package com.doubledeltas.minecollector.collection;

import com.doubledeltas.minecollector.McolInitializable;
import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.data.GameData;
import com.doubledeltas.minecollector.util.MessageUtil;
import com.doubledeltas.minecollector.util.page.Page;
import com.doubledeltas.minecollector.util.page.PageRange;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class CollectionManager implements McolInitializable {
    protected MineCollector plugin;
    @Getter
    protected List<Piece> pieces = new ArrayList<>();

    protected Map<String, Piece> keyPieceMap = new HashMap<>();
    protected Map<Material, PlainItemPiece> plainItemPieceMap = new HashMap<>();

    @Override
    public void init(MineCollector plugin) {
        this.plugin = plugin;
    }

    public void generatePieces() {
        pieces.clear();

        for (Material material : Material.values()) {
            if (!material.isItem())
                continue;

            PlainItemPiece plainItemPiece;
            if (material == Material.AIR)
                plainItemPiece = AirPiece.INSTANCE;
            else
                plainItemPiece = new PlainItemPiece(material);
            pieces.add(plainItemPiece);
            keyPieceMap.put(plainItemPiece.toPieceKey(), plainItemPiece);
            plainItemPieceMap.put(material, plainItemPiece);
        }

        MessageUtil.log("collection.loaded", pieces.size());
    }

    public Piece getPieceAt(int idx) {
        return pieces.get(idx);
    }

    public Piece getPieceOf(ItemStack item) {
        return plainItemPieceMap.get(item.getType());
    }

    public Page<Piece> getPiecePage(GameData data, PageRange range, PieceFiltering filtering, PieceSort sort) {
        return pieces.stream()
                .filter(filtering.generate(this, data))
                .sorted(sort.getGenerator().apply(this, data))
                .collect(Page.collector(range));
    }

    public Optional<Piece> findPieceOf(String key) {
        return Optional.ofNullable(keyPieceMap.get(key));
    }

    public int getSize() {
        return pieces.size();
    }

    public List<String> recommendItemKeys(CommandSender sender, String cur) {
        return plugin.getCollectionManager().getPieces().stream()
                .filter(piece -> piece.toPieceKey().contains(cur))
                .filter(piece -> {
                    if (sender.isOp())
                        return true;
                    if (!plugin.getMcolConfig().getGame().isHideUnknownCollection())
                        return true;
                    if (!(sender instanceof Player player))
                        return false;
                    return piece.getAmount(plugin.getDataManager().getData(player)) > 0;
                })
                .sorted(Comparator.comparingInt(piece -> {
                    String key = piece.toPieceKey();
                    if (piece instanceof PlainItemPiece && key.substring("minecraft:".length()).startsWith(cur))
                        return 1;
                    if (key.startsWith(cur))
                        return 1;
                    return 2;
                }))
                .map(Piece::toPieceKey)
                .toList();
    }
}
