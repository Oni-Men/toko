package dev.onimen.toko.constant;

public class CPPackage extends CPEntry {
    public int nameIndex;

    public CPPackage(CPEntryType type, int nameIndex) {
        super(type);
        this.nameIndex = nameIndex;
    }
}
