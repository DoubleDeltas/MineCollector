package com.doubledeltas.minecollector.command.impl;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.command.CommandRoot;
import com.doubledeltas.minecollector.lang.LangManager;
import com.doubledeltas.minecollector.lang.MessageKey;
import com.doubledeltas.minecollector.util.MessageUtil;
import com.doubledeltas.minecollector.util.SoundUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class GuideCommand extends CommandRoot {
    public static final String GUIDE_LINK = "https://github.com/DoubleDeltas/MineCollector/blob/main/README.md";

    @Override
    public String getName() { return "가이드"; }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        BaseComponent[] hereComponents = plugin.getLangManager().translate(MessageKey.of("command.guide.here"));
        MessageUtil.send(sender, "command.guide.message", (Object[]) hereComponents);

        if (sender instanceof Player player)
            SoundUtil.playHighRing(player);

        return false;
    }
}
