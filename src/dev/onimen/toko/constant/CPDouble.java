package dev.onimen.toko.constant;

public class CPDouble extends CPEntry {
    public double value;

    public CPDouble(CPEntryType type, double value) {
        super(type);
        this.value = value;
    }
}
