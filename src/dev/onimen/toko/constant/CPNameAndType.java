package dev.onimen.toko.constant;

public class CPNameAndType extends CPEntry {

    public int nameIndex;
    public int descriptorIndex;

    public CPNameAndType(CPEntryType type, int nameIndex, int descriptorIndex) {
        super(type);
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
    }
}
