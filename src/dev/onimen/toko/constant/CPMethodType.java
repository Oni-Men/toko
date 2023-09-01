package dev.onimen.toko.constant;

public class CPMethodType extends CPEntry {

    public int descriptorIndex;

    public CPMethodType(CPEntryType type, int descriptorIndex) {
        super(type);
        this.descriptorIndex = descriptorIndex;
    }
}
