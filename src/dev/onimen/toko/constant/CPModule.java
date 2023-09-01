package dev.onimen.toko.constant;

public class CPModule extends CPEntry {
    public int nameIndex;

    public CPModule(CPEntryType type, int nameIndex) {
        super(type);
        this.nameIndex = nameIndex;
    }
}
