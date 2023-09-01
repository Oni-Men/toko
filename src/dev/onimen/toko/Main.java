package dev.onimen.toko;

import dev.onimen.toko.cli.InteractiveShell;

public class Main {

    public static String NAME = "toko";
    public static String VERSION = "0.0.1";
    public static String COPYRIGHT = "Copyright 2023 Onimen";
    public static String DESCRIPTION = "a CLI based bytecode editor";

    public static void main(String[] args) {
        var shell = new InteractiveShell(System.in, System.out);
        int returnCode = shell.start(args);
        System.exit(returnCode);
    }

}