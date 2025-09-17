package com.doubledeltas.minecollector.ui.book.component;

import lombok.AllArgsConstructor;
import lombok.Setter;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;

import java.util.Arrays;

@AllArgsConstructor @Setter
public class CommandButton implements BookComponent {
    private BaseComponent[] contents;
    private Command command;

    public static CommandButton of(BaseComponent[] contents, Command command) {
        return new CommandButton(contents, command);
    }

    public static CommandButton of(String content, boolean underlined, Command command) {
        TextComponent textComponent = new TextComponent(content);
        textComponent.setUnderlined(underlined);
        return of(new BaseComponent[]{textComponent}, command);
    }

    @Override
    public BaseComponent[] render() {
        BaseComponent[] components = Arrays.copyOf(contents, contents.length);
        if (command == null)
            return components;
        return components;  // todo: test
    }
}
