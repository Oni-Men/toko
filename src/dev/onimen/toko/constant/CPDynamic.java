package dev.onimen.toko.constant;

public class CPDynamic extends CPEntry {

    public int bootstrapMethodAttrIndex;
    public int nameAndTypeIndex;

    public CPDynamic(CPEntryType type, int bootstrapMethodAttrIndex, int nameAndTypeIndex) {
        super(type);
        this.bootstrapMethodAttrIndex = bootstrapMethodAttrIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

}
