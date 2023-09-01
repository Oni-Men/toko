package dev.onimen.toko.constant;

public class CPClass extends CPEntry{
    public int nameIndex;

    public CPClass(CPEntryType type, int nameIndex) {
        super(type);
        this.nameIndex = nameIndex;
    }
}
