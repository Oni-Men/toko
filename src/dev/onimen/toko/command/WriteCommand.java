package dev.onimen.toko.command;

import dev.onimen.toko.ClassFile;
import dev.onimen.toko.ClassWriter;
import dev.onimen.toko.Context;

import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class WriteCommand implements Command {
    @Override
    public String getName() {
        return "write";
    }

    @Override
    public String getUsage() {
        return """
               write
               write [file] 
               if the file were specified, write output into the file.
               otherwise, the original file will be overwritten. 
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

        Path classFilePath = classFile.getClassFilePath();
        if (arguments.size() >= 1) {
            classFilePath = Paths.get(arguments.get(0));
        }

        try (var classWriter = new ClassWriter(classFilePath)) {
            classWriter.write(classFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
