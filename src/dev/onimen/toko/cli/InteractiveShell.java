package dev.onimen.toko.cli;

import dev.onimen.toko.Context;
import dev.onimen.toko.Main;
import dev.onimen.toko.command.*;
import dev.onimen.toko.util.StringUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

public class InteractiveShell {

    static final int
            EXIT_OK = 0,
            EXIT_ERROR = 1;

    private final Scanner scanner;
    private final PrintWriter writer;

    private final Map<String, Command> commandMap;

    private boolean exit;

    public InteractiveShell(InputStream inputStream, OutputStream outputStream) {
        this.scanner = new Scanner(inputStream);
        this.writer = new PrintWriter(outputStream, true);
        this.commandMap = new HashMap<>();

        this.init();
    }

    void init() {
        var context = Context.getInstance();
        context.put(InteractiveShell.class, this);
        context.put(Scanner.class, this.scanner);
        context.put(PrintWriter.class, this.writer);

        registerCommands();
    }

    void registerCommands() {
        this.registerCommand(new OpenCommand());
        this.registerCommand(new WriteCommand());
        this.registerCommand(new ExitCommand());
        this.registerCommand(new HelpCommand());
        this.registerCommand(new LicenseCommand());
        this.registerCommand(new ShowCommand());
    }

    public Collection<Command> getCommands() {
        return this.commandMap.values();
    }

    void handleOptions(String[] args) {

    }

    void printInitialMessage() {
        var message = """
                %s %s %s
                %s
                Type "help", "license" for more information.
                """;

        writer.println(String.format(message, Main.NAME, Main.VERSION, Main.COPYRIGHT, Main.DESCRIPTION));
    }

    public int start(String[] args) {
        handleOptions(args);
        return start();
    }

    public int start() {
        this.printInitialMessage();

        int code = EXIT_OK;
        while (!exit) {
            writer.printf(">> ");

            try {
                Optional.ofNullable(scanner.nextLine())
                        .ifPresent(s -> this.evaluateLine(s));
            } catch (NoSuchElementException e) {
            } catch (IllegalStateException e) {
                code = EXIT_ERROR;
                break;
            } catch (Exception e) {
                code = EXIT_ERROR;
                break;
            }
        }
        return code;
    }

    public void registerCommand(Command command) {
        if (commandMap.containsKey(command.getName())) {
            throw new IllegalArgumentException(command.getName() + " has already been exist");
        }
        commandMap.put(command.getName().toLowerCase(), command);
    }

    private void evaluateLine(String inputText) {
        var tokens = StringUtils.richSplit(inputText.trim());
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("You must specify command to execute!");
        }

        var commandName = tokens.get(0).toLowerCase();
        var arguments = tokens.subList(1, tokens.size());

        Optional.ofNullable(commandMap.get(commandName))
                .ifPresentOrElse(command -> {
                    try {
                        command.execute(arguments);
                    } catch (Exception e) {
                     writer.println(e.getMessage());
                    }
                }, () -> {
                    writer.println(String.format("unknown operation: %s", commandName));
                });
    }

    public void requireExit() {
        this.exit = true;
    }

}
