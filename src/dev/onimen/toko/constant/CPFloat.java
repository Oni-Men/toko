package dev.onimen.toko.constant;

import java.io.DataOutputStream;
import java.io.IOException;

public class CPFloat extends CPEntry {
    public float value;

    public CPFloat(CPEntryType type, float value) {
        super(type);
        this.value = value;
    }

    @Override
    public void write(DataOutputStream dataOutputStream) throws IOException {
        super.write(dataOutputStream);
        dataOutputStream.writeFloat(value);
    }
}
