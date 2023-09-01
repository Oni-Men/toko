package dev.onimen.toko.command;

import dev.onimen.toko.Context;
import dev.onimen.toko.cli.InteractiveShell;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class HelpCommand implements Command {
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public void execute(List<String> arguments) {
        var context = Context.getInstance();
        var writer = context.get(PrintWriter.class);
        var shell = context.get(InteractiveShell.class);

        var commands = shell.getCommands();

        //TODO The current indentation may be incorrect, We need to make the output format prettier.
        for (var command: commands) {
            writer.print(String.format("%s:\t\t", command.getName()));
            var lines = Arrays.stream(command.getUsage().split("\n")).toList();

            writer.println(lines.get(0));
            for (int i = 1; i < lines.size(); i++) {
                writer.println(String.format("\t\t\t%s", lines.get(i)));
            }
        }
    }
}
