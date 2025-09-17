package com.doubledeltas.minecollector.command;

import com.doubledeltas.minecollector.CommandRegistrationContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

/**
 * command root for dynamic command (defined with runtime code)
 */
@Getter
public abstract class DynamicCommandRoot extends AbstractCommandNode implements CommandRoot {
    private static final Command DEFAULT_COMMAND = new Command(UUID.randomUUID().toString()) {
        @Override
        public boolean execute(@Nonnull CommandSender sender, @Nonnull String commandLabel, @Nonnull String[] args) {
            return false;
        }
    };

    private final Command command;

    public DynamicCommandRoot() {
        this(DEFAULT_COMMAND);
    }

    public DynamicCommandRoot(String label) {
        this(new Command(label) {
            @Override
            public boolean execute(CommandSender sender, String commandLabel, String[] args) {
                return false;
            }
        });
    }

    public DynamicCommandRoot(Command command) {
        this.command = command;
    }

    @Override
    public void registerThis(CommandRegistrationContext context) {
        super.registerThis(context);
        context.commandMap().register(getName(), context.prefix(), command);
    }

    @Override
    public List<String> getAliases() {
        return command.getAliases();
    }

    @Override
    public String getRequiredPermissionKey() {
        return command.getPermission();
    }
}
