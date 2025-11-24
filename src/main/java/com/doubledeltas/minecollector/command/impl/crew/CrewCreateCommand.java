package com.doubledeltas.minecollector.command.impl.crew;

import com.doubledeltas.minecollector.command.CommandNode;
import com.doubledeltas.minecollector.crew.Crew;
import com.doubledeltas.minecollector.crew.DuplicatedIdException;
import com.doubledeltas.minecollector.util.MessageUtil;
import com.doubledeltas.minecollector.util.SoundUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CrewCreateCommand extends CommandNode {
    @Override
    public List<String> getAliases() {
        return List.of("create", "생성");
    }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = sender instanceof Player p ? p : null;
        if (args.length == 0) {
            fail(sender, "crew.generic.no_code");
            return false;
        }

        if (player != null) {
            if (!player.hasPermission("minecollector.crew.create.force") && plugin.getCrewManager().hasCrew(player)) {
                fail(sender, "command.crew.create.already_has_team");
                return false;
            }
        }

        String crewName = args.length >= 2 ? args[1] : null;
        Crew crew;
        try {
            crew = plugin.getCrewManager().createNewCrew(player, args[0], crewName);
            if (player != null && !plugin.getCrewManager().hasCrew(player))
                crew.addMember(player, true);
        } catch (DuplicatedIdException ex) {
            fail(sender, "command.crew.create.duplicated_id");
            return false;
        }

        MessageUtil.send(sender, "command.crew.create.success", crew.getName());
        if (player != null)
            SoundUtil.playHighRing(player);
        return true;
    }

    @Override
    public List<String> getTabRecommendation(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1)
            return List.of("<crew_id>");
        else if (args.length == 2)
            return List.of("[<crew_name>]");
        return List.of();
    }

    @Override
    public String getRequiredPermissionKey() {
        return "minecollector.crew.create";
    }

    private void fail(CommandSender sender, String messageKey) {
        MessageUtil.send(sender, messageKey);
        if (sender instanceof Player player)
            SoundUtil.playCollect(player);
    }
}
