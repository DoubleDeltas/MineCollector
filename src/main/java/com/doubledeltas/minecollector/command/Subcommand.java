package com.doubledeltas.minecollector.command;

import java.util.List;

public abstract class Subcommand extends GameCommand {

    public abstract List<String> getAliases();

}
