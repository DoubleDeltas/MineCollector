package com.doubledeltas.minecollector.command.impl.crew;

import com.doubledeltas.minecollector.command.CommandNode;
import com.doubledeltas.minecollector.crew.Crew;
import com.doubledeltas.minecollector.crew.CrewManager;
import com.doubledeltas.minecollector.util.MessageUtil;
import com.doubledeltas.minecollector.util.SoundUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

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
            MessageUtil.send(sender, "command.generic.no_crew");
            return true;
        }
        if (args.length < 1) {
            MessageUtil.send(sender, "command.crew.invite.no_args");
            return true;
        }

        Crew crew = crewManager.getCrew(inviter);
        Player invitee = plugin.getServer().getPlayer(args[0]);

        if (invitee == null) {
            MessageUtil.send(sender, "command.crew.invite.no_player_found");
            return true;
        }
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
}
