package dev.onimen.toko.command;

public class CommandException extends Exception {

    public final Command command;

    public CommandException(Command command) {
        this.command = command;
    }
}
