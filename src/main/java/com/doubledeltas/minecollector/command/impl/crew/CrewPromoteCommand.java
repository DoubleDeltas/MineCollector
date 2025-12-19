package com.doubledeltas.minecollector.command.impl.crew;

import com.doubledeltas.minecollector.command.CommandNode;
import com.doubledeltas.minecollector.crew.Crew;
import com.doubledeltas.minecollector.crew.CrewManager;
import com.doubledeltas.minecollector.util.MessageUtil;
import com.doubledeltas.minecollector.util.SoundUtil;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CrewPromoteCommand extends CommandNode {
    @Override
    public List<String> getAliases() {
        return List.of("promote", "리더임명");
    }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        CrewManager crewManager = plugin.getCrewManager();

        if (args.length < 1) {
            MessageUtil.send(sender, "command.crew.promote.no_args");
            return true;
        }

        var targetOptional = plugin.findOfflinePlayer(args[0]);
        if (targetOptional.isEmpty()) {
            MessageUtil.send(sender, "command.generic.no_player_found");
            return true;
        }
        OfflinePlayer target = targetOptional.get();

        boolean permitted =
                (sender instanceof Player player && crewManager.getCrew(player) == crewManager.getCrew(target))
                || hasForcePromotePermission(sender);

        if (!permitted) {
            MessageUtil.send(sender, "command.crew.promote.not_permitted");
            return true;
        }

        Crew crew = crewManager.getCrew(target);
        boolean result = crew.getMembers().setLeader(target, true);
        if (!result) {
            MessageUtil.send(
                    sender, "command.crew.promote.already_leader",
                    target.getName(), crew.getName()
            );
            return true;
        }

        MessageUtil.send(
                sender, "command.crew.promote.success",
                target.getName(), crew.getName()
        );
        if (sender instanceof Player player) {
            SoundUtil.playHighRing(player);
        }
        if (target.isOnline()) {
            Player onlineTarget = target.getPlayer();
            MessageUtil.send(onlineTarget, "command.crew.promote.got_promoted", crew.getName());
            SoundUtil.playHighRing(onlineTarget);
        }

        return true;
    }

    @Override
    public String getRequiredPermissionKey() {
        return null;
    }

    private boolean hasForcePromotePermission(CommandSender sender) {
        return sender.hasPermission("minecollector.crew.promote_force");
    }
}
