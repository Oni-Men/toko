package dev.onimen.toko.constant;

import java.io.DataOutputStream;
import java.io.IOException;

public class CPDynamic extends CPEntry {

    public int bootstrapMethodAttrIndex;
    public int nameAndTypeIndex;

    public CPDynamic(CPEntryType type, int bootstrapMethodAttrIndex, int nameAndTypeIndex) {
        super(type);
        this.bootstrapMethodAttrIndex = bootstrapMethodAttrIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    @Override
    public void write(DataOutputStream dataOutputStream) throws IOException {
        super.write(dataOutputStream);
        dataOutputStream.writeShort(bootstrapMethodAttrIndex);
        dataOutputStream.writeShort(nameAndTypeIndex);
    }

    @Override
    public String toString() {
        return String.format("#%d.#%d", bootstrapMethodAttrIndex, nameAndTypeIndex);
    }
}
