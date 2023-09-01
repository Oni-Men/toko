package dev.onimen.toko.command;

import java.util.List;

public interface Command {

    String getName();

    default String getUsage() {
        return "";
    }

    void execute(List<String> arguments);
}
