package dev.onimen.toko.command;

import dev.onimen.toko.ClassFile;
import dev.onimen.toko.Context;
import dev.onimen.toko.cli.InteractiveShell;

import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ExitCommand implements Command {

    private static final Pattern YES_PATTERN = Pattern.compile("y|Y|[Yy]es");

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public void execute(List<String> arguments) {
        var context = Context.getInstance();
        var classFile = context.get(ClassFile.class);
        var shell = context.get(InteractiveShell.class);

        if (classFile != null) {
            if (classFile.isDirty() && !askSaveClassFile()) {
                return;
            }
        }

        shell.requireExit();
    }

    private boolean askSaveClassFile() {
        var context = Context.getInstance();
        var writer = context.get(PrintWriter.class);
        var scanner = context.get(Scanner.class);

        writer.println("Modified file is not saved. Are you sure want to exit?");
        writer.println("type \"yes/y\" to exit without save.");
        writer.print(">> ");
        writer.flush();
        var matcher = YES_PATTERN.matcher(scanner.nextLine());
        return matcher.matches();
    }
}
