package com.doubledeltas.minecollector.command.impl;

import com.doubledeltas.minecollector.command.CommandRoot;
import com.doubledeltas.minecollector.util.MessageUtil;
import com.doubledeltas.minecollector.util.SoundUtil;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.doubledeltas.minecollector.lang.LangManager.translateToComponents;

public final class GuideCommand extends CommandRoot {
    public static final String GUIDE_LINK = "https://github.com/DoubleDeltas/MineCollector/blob/main/README.md";

    @Override
    public String getName() { return "가이드"; }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        BaseComponent[] hereComponents = translateToComponents("command.guide.here");
        for (BaseComponent hereComponent : hereComponents)
            hereComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, GUIDE_LINK));

        MessageUtil.send(sender, "command.guide.message", (Object[]) hereComponents);

        if (sender instanceof Player player)
            SoundUtil.playHighRing(player);

        return false;
    }
}
