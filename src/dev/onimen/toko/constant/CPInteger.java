package dev.onimen.toko.constant;

import java.io.DataOutputStream;
import java.io.IOException;

public class CPInteger extends CPEntry {
    public int value;

    public CPInteger(CPEntryType type, int value) {
        super(type);
        this.value = value;
    }

    @Override
    public void write(DataOutputStream dataOutputStream) throws IOException {
        super.write(dataOutputStream);
        dataOutputStream.writeInt(value);
    }
}
