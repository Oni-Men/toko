package dev.onimen.toko.constant;

public class CPMethodHandle extends CPEntry {

    public int referenceKind;
    public int referenceIndex;

    public CPMethodHandle(CPEntryType type, int referenceKind, int referenceIndex) {
        super(type);
        this.referenceKind = referenceKind;
        this.referenceIndex = referenceIndex;
    }
}
