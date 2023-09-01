package dev.onimen.toko.constant;

public class CPFloat extends CPEntry {
    public float value;

    public CPFloat(CPEntryType type, float value) {
        super(type);
        this.value = value;
    }
}
