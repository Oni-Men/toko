package dev.onimen.toko.constant;

import java.io.DataOutputStream;
import java.io.IOException;

public class CPNameAndType extends CPEntry {

    public int nameIndex;
    public int descriptorIndex;

    public CPNameAndType(CPEntryType type, int nameIndex, int descriptorIndex) {
        super(type);
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
    }

    @Override
    public void write(DataOutputStream dataOutputStream) throws IOException {
        super.write(dataOutputStream);
        dataOutputStream.writeShort(nameIndex);
        dataOutputStream.writeShort(descriptorIndex);
    }
}
