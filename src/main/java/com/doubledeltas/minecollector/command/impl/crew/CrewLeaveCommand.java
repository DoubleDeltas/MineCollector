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

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CrewLeaveCommand extends CommandNode {
    @Override
    public List<String> getAliases() {
        return List.of("leave", "quit", "kick", "탈퇴", "내보내기");
    }

    @Override
    public boolean onRawCommand(CommandSender sender, Command command, String label, String[] args) {
        CrewManager crewManager = plugin.getCrewManager();

        OfflinePlayer leaver;
        boolean self;

        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                MessageUtil.send(sender, "command.generic.player_only");
                return true;
            }
            leaver = (Player) sender;
            self = true;
        }
        else {
            var playerOptional = Arrays.stream(plugin.getServer().getOfflinePlayers())
                    .filter(offlinePlayer -> Objects.equals(offlinePlayer.getName(), args[0]))
                    .findAny();
            if (playerOptional.isEmpty()) {
                MessageUtil.send(sender, "command.generic.no_player_found");
                return true;
            }
            leaver = playerOptional.get();
            self = sender == leaver;
        }

        if (!crewManager.hasCrew(leaver)) {
            MessageUtil.send(sender, "command.crew.leave.no_crew");
            return true;
        }

        boolean permitted = self
                || (sender instanceof Player player && crewManager.getCrew(player) == crewManager.getCrew(leaver))
                || hasForceKickPermission(sender);

        if (!permitted) {
            MessageUtil.send(sender, "command.crew.leave.not_permitted");
            return true;
        }

        Crew leftCrew = crewManager.leaveCrew(leaver);
        if (leftCrew == null) {
            return true;
        }

        if (self) {
            MessageUtil.send(sender, "command.crew.leave.left_self");
            SoundUtil.playHighRing((Player) leaver);
        }
        else {
            MessageUtil.send(sender, "command.crew.leave.kick", leaver.getName());
            if (sender instanceof Player player)
                SoundUtil.playHighRing(player);

            if (leaver instanceof Player leftPlayer) {
                MessageUtil.send(leftPlayer, "command.crew.leave.kicked", leftCrew.getName());
                SoundUtil.playHighRing(leftPlayer);
            }
        }

        return true;
    }

    @Override
    public List<String> getTabRecommendation(CommandSender sender, Command command, String label, String[] args) {
        CrewManager crewManager = plugin.getCrewManager();
        if (args.length == 1) {
            if (hasForceKickPermission(sender)) {
                return Arrays.stream(plugin.getServer().getOfflinePlayers())
                        .filter(crewManager::hasCrew)
                        .map(OfflinePlayer::getName)
                        .toList();
            }
            if (sender instanceof Player player && crewManager.hasCrew(player)) {
                return crewManager.getCrew(player).getMembers().getOfflinePlayers().stream()
                        .map(OfflinePlayer::getName)
                        .toList();
            }
            return List.of(sender.getName());
        }
        else {
            return List.of();
        }
    }

    @Override
    public String getRequiredPermissionKey() {
        return "minecollector.crew.leave";
    }

    private boolean hasForceKickPermission(CommandSender sender) {
        return sender.hasPermission("minecollector.crew.kick_force");
    }
}
