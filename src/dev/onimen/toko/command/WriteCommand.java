package dev.onimen.toko.command;

import dev.onimen.toko.ClassFile;
import dev.onimen.toko.Context;

import java.io.PrintWriter;
import java.util.List;

public class WriteCommand implements Command {
    @Override
    public String getName() {
        return "write";
    }

    @Override
    public String getUsage() {
        return """
               write [file]          
               """;
    }

    @Override
    public void execute(List<String> arguments) {
        var context = Context.getInstance();
        var writer = context.get(PrintWriter.class);
        var classFile = context.get(ClassFile.class);

        if (classFile == null) {
            writer.println("Class file is not opened.");
        }
    }
}
