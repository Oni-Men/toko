package dev.onimen.toko.command;

import dev.onimen.toko.Context;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class LicenseCommand implements Command {

    private static final Path LICENSE_PATH = Paths.get("./resources/LICENSE");

    @Override
    public String getName() {
        return "license";
    }

    @Override
    public String getUsage() {
        return "print license of this software.";
    }

    @Override
    public void execute(List<String> arguments) {
        var context = Context.getInstance();
        var writer = context.get(PrintWriter.class);

        try {
            var lines = Files.readAllLines(LICENSE_PATH);
            lines.forEach(writer::println);
        } catch (IOException e) {
            writer.println("Problem occurred while loading LICENSE.");
            writer.println("You can read LICENSE at the https://github.com/Oni-Men/toko/LICENSE");
        }
    }
}
