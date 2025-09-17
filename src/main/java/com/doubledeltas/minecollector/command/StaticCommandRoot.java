package com.doubledeltas.minecollector.command;

import com.doubledeltas.minecollector.CommandRegistrationContext;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * command root for static commands. (defined with {@code plugin.yml})
 */
public abstract class StaticCommandRoot extends AbstractCommandNode implements TabExecutor, CommandRoot {

    @Override
    public void registerThis(CommandRegistrationContext context) {
        super.registerThis(context);
        PluginCommand pluginCommand = plugin.getCommand(getName());
        pluginCommand.setExecutor(this);
        pluginCommand.setTabCompleter(this);
    }

    @Override
    public final List<String> getAliases() {
        return getCommand().getAliases();
    }

    @Override
    public final boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        return resolveCommand(sender, command, label, args);
    }

    @Override
    public final List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        return resolveTabCompletion(sender, command, label, args);
    }

    @Override
    public Command getCommand() {
        return plugin.getCommand(getName());
    }

    @Override
    public String getRequiredPermissionKey() {
        return getCommand().getPermission();
    }
}
