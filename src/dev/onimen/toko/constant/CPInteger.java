package dev.onimen.toko.constant;

public class CPInteger extends CPEntry {
    public int value;

    public CPInteger(CPEntryType type, int value) {
        super(type);
        this.value = value;
    }
}
