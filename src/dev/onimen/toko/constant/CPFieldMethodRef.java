package dev.onimen.toko.constant;

import java.io.DataOutputStream;
import java.io.IOException;

public class CPFieldMethodRef extends CPEntry {
    public int classIndex;
    public int nameAndTypeIndex;

    public CPFieldMethodRef(CPEntryType type, int classIndex, int nameAndTypeIndex) {
        super(type);
        this.classIndex = classIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    @Override
    public void write(DataOutputStream dataOutputStream) throws IOException {
        super.write(dataOutputStream);
        dataOutputStream.writeShort(classIndex);
        dataOutputStream.writeShort(nameAndTypeIndex);
    }

    @Override
    public String toString() {
        return String.format("#%d.#%d", classIndex, nameAndTypeIndex);
    }
}
