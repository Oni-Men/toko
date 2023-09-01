package dev.onimen.toko.constant;

import java.io.DataOutputStream;
import java.io.IOException;

public class CPClass extends CPEntry {
    public int nameIndex;

    public CPClass(CPEntryType type, int nameIndex) {
        super(type);
        this.nameIndex = nameIndex;
    }

    @Override
    public void write(DataOutputStream dataOutputStream) throws IOException {
        super.write(dataOutputStream);
        dataOutputStream.writeShort(nameIndex);
    }
}
