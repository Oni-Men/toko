package dev.onimen.toko.constant;

import java.io.DataOutputStream;
import java.io.IOException;

public class CPString extends CPEntry {
    public int stringIndex;

    public CPString(CPEntryType type, int stringIndex) {
        super(type);
        this.stringIndex = stringIndex;
    }

    @Override
    public void write(DataOutputStream dataOutputStream) throws IOException {
        super.write(dataOutputStream);
        dataOutputStream.writeShort(stringIndex);
    }
}
