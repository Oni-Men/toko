package dev.onimen.toko.command;

import dev.onimen.toko.ClassFile;
import dev.onimen.toko.ClassReader;
import dev.onimen.toko.Context;

import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.List;

public class OpenCommand implements Command {

    @Override
    public String getName() {
        return "open";
    }

    @Override
    public String getUsage() {
        return """
               open [file] 
               open specified class file. you must specify the path to file.
               if the specified file were not "Class File". an error will thrown.
               """;
    }

    @Override
    public void execute(List<String> arguments) {
        if (arguments.isEmpty()) {
            throw new IllegalArgumentException("You must specify a file to open!");
        }

        var classFilePath = Paths.get(arguments.get(0));
        try (var classReader = new ClassReader(classFilePath);) {
            var classFile = classReader.read();

            var context = Context.getInstance();
            context.put(ClassFile.class, classFile);

            var writer = context.get(PrintWriter.class);
            writer.println(String.format("Class \"%s\" loaded successfully.", classFilePath.getFileName()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
