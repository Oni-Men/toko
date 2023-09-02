package dev.onimen.toko.constant;

import java.io.DataOutputStream;
import java.io.IOException;

public class CPModule extends CPEntry {
    public int nameIndex;

    public CPModule(CPEntryType type, int nameIndex) {
        super(type);
        this.nameIndex = nameIndex;
    }

    @Override
    public void write(DataOutputStream dataOutputStream) throws IOException {
        super.write(dataOutputStream);
        dataOutputStream.writeShort(nameIndex);
    }

    @Override
    public String toString() {
        return String.format("#%d", nameIndex);
    }
}
