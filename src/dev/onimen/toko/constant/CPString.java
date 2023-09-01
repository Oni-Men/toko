package dev.onimen.toko.constant;

public class CPString extends CPEntry {
    public int stringIndex;

    public CPString(CPEntryType type, int stringIndex) {
        super(type);
        this.stringIndex = stringIndex;
    }
}
