package com.doubledeltas.minecollector.command.impl;

import com.doubledeltas.minecollector.command.CommandRoot;
import com.doubledeltas.minecollector.util.MessageUtil;
import com.doubledeltas.minecollector.util.SoundUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class GuideCommand extends CommandRoot {
    public static final String GUIDE_LINK = "http://mzpedia.kro.kr/wiki/index.php/%EB%A7%88%EC%9D%B8%EC%BD%9C%EB%A0%89%ED%84%B0/%EA%B0%80%EC%9D%B4%EB%93%9C";

    @Override
    public String getName() { return "가이드"; }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        BaseComponent component = new TextComponent("여기");
        component.setColor(ChatColor.GREEN);
        component.setUnderlined(true);
        component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, GUIDE_LINK));

        MessageUtil.sendRaw(sender,
                component,
                new TextComponent("§e를 눌러 마인콜렉터에 대해 알아보세요!")
        );

        if (sender instanceof Player player)
            SoundUtil.playHighRing(player);

        return false;
    }
}
