package dev.onimen.toko.constant;

public class CPLong extends CPEntry {
    public long value;

    public CPLong(CPEntryType type, long value) {
        super(type);
        this.value = value;
    }
}
