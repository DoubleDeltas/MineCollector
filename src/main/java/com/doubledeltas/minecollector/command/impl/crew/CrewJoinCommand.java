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

public class CrewJoinCommand extends CommandNode {
    @Override
    public List<String> getAliases() {
        return List.of("join", "참여", "가입");
    }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        CrewManager crewManager = plugin.getCrewManager();

        if (!(sender instanceof Player applicant)) {
            MessageUtil.send(sender, "command.generic.player_only");
            return true;
        }
        if (crewManager.hasCrew(applicant)) {
            Crew crew = crewManager.getCrew(applicant);
            MessageUtil.send(sender, "command.crew.join.already_has_crew", crew.getName());
            return true;
        }
        if (args.length < 1) {
            MessageUtil.send(sender, "command.crew.join.no_args");
            return true;
        }

        Crew crew = crewManager.findCrew(args[0]);
        if (crew == null) {
            MessageUtil.send(sender, "command.crew.join.no_crew_found");
            return true;
        }

        if (crewManager.isInvitationPending(crew, applicant)) {
            crewManager.acceptInvitation(crew, applicant);
        }
        else {
            crewManager.invite(crew, applicant);
            MessageUtil.send(sender, "command.crew.join.success", crew.getName());
            SoundUtil.playHighRing(applicant);
        }

        return true;
    }

    @Override
    public String getRequiredPermissionKey() {
        return "minecollector.crew.join";
    }
}
