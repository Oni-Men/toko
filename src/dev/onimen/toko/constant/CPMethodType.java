package dev.onimen.toko.constant;

import java.io.DataOutputStream;
import java.io.IOException;

public class CPMethodType extends CPEntry {

    public int descriptorIndex;

    public CPMethodType(CPEntryType type, int descriptorIndex) {
        super(type);
        this.descriptorIndex = descriptorIndex;
    }

    @Override
    public void write(DataOutputStream dataOutputStream) throws IOException {
        super.write(dataOutputStream);
        dataOutputStream.writeShort(descriptorIndex);
    }

    @Override
    public String toString() {
        return String.format("#%d", descriptorIndex);
    }
}
