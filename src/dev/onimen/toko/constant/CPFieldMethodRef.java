package dev.onimen.toko.constant;

public class CPFieldMethodRef extends CPEntry {
    public int classIndex;
    public int nameAndTypeIndex;

    public CPFieldMethodRef(CPEntryType type, int classIndex, int nameAndTypeIndex) {
        super(type);
        this.classIndex = classIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }
}
