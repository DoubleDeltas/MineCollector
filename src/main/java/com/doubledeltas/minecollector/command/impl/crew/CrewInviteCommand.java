package com.doubledeltas.minecollector.command.impl.crew;

import com.doubledeltas.minecollector.command.CommandNode;
import com.doubledeltas.minecollector.crew.Crew;
import com.doubledeltas.minecollector.crew.CrewManager;
import com.doubledeltas.minecollector.lang.LangManager;
import com.doubledeltas.minecollector.util.MessageUtil;
import com.doubledeltas.minecollector.util.SoundUtil;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

import static com.doubledeltas.minecollector.lang.LangManager.translateToComponents;

public class CrewInviteCommand extends CommandNode {
    @Override
    public List<String> getAliases() {
        return List.of("invite", "초대");
    }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        CrewManager crewManager = plugin.getCrewManager();

        if (!(sender instanceof Player inviter)) {
            MessageUtil.send(sender, "command.generic.player_only");
            return true;
        }
        if (!crewManager.hasCrew(inviter)) {
            MessageUtil.send(sender, "command.crew.generic.no_crew");
            return true;
        }
        if (args.length < 1) {
            MessageUtil.send(sender, "command.crew.invite.no_args");
            return true;
        }

        Crew crew = crewManager.getCrew(inviter);
        var offlineInviteeOptional = plugin.findOfflinePlayer(args[0]);

        if (offlineInviteeOptional.isEmpty()) {
            MessageUtil.send(sender, "command.crew.invite.no_player_found");
            return true;
        }

        OfflinePlayer offlineInvitee = offlineInviteeOptional.get();
        if (!offlineInvitee.isOnline()) {
            MessageUtil.send(sender, "command.crew.invite.invitee_not_online");
            return true;
        }

        Player invitee = offlineInvitee.getPlayer();
        if (crewManager.hasCrew(invitee)) {
            MessageUtil.send(sender, "command.crew.invite.player_has_team", invitee.getName());
            return true;
        }

        if (crewManager.isApplicationPending(invitee, crew)) {
            crewManager.acceptApplication(invitee, crew);
        }
        else {
            crewManager.invite(crew, invitee);
            MessageUtil.send(sender, "command.crew.invite.success", invitee.getName());
            SoundUtil.playHighRing(inviter);

            MessageUtil.sendRaw(invitee, "");
            MessageUtil.send(invitee, "command.crew.invite.got_invited_1", crew.getName());
            MessageUtil.send(invitee,"command.crew.invite.got_invited_2",
                    getAcceptButtonComponents(crew.getId()),
                    CrewManager.INVITATION_TTL.getSeconds()
            );
            MessageUtil.sendRaw(invitee, "");
            SoundUtil.playHighRing(invitee);
        }

        return true;
    }

    @Override
    public List<String> getTabRecommendation(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            return plugin.getServer().getOnlinePlayers().stream()
                    .map(Player::getName)
                    .sorted()
                    .toList();
        }
        else {
            return List.of();
        }
    }

    @Override
    public String getRequiredPermissionKey() {
        return "minecollector.crew.invite";
    }

    private BaseComponent[] getAcceptButtonComponents(String crewId) {
        BaseComponent[] components = translateToComponents("command.generic.here");
        ClickEvent clickEvent = new ClickEvent(
                ClickEvent.Action.RUN_COMMAND,
                "/crew join " + crewId  // TODO: remove hardcoding
        );
        for (BaseComponent component : components) {
            component.setClickEvent(clickEvent);
        }
        return components;
    }
}
