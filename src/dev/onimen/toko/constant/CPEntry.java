package dev.onimen.toko.constant;

import java.io.DataOutputStream;
import java.io.IOException;

public abstract class CPEntry {

    public final CPEntryType type;

    public CPEntry(CPEntryType type) {
        this.type = type;
    }

    public void write(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeByte(type.getTag());
    }
}
