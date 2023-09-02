package dev.onimen.toko.constant;

import java.io.DataOutputStream;
import java.io.IOException;

public class CPLong extends CPEntry {
    public long value;

    public CPLong(CPEntryType type, long value) {
        super(type);
        this.value = value;
    }

    @Override
    public void write(DataOutputStream dataOutputStream) throws IOException {
        super.write(dataOutputStream);
        dataOutputStream.writeLong(value);
    }

    @Override
    public String toString() {
        return Long.toString(value);
    }
}
