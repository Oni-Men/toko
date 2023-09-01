package dev.onimen.toko.constant;

import java.io.DataOutputStream;
import java.io.IOException;

public class CPDouble extends CPEntry {
    public double value;

    public CPDouble(CPEntryType type, double value) {
        super(type);
        this.value = value;
    }

    @Override
    public void write(DataOutputStream dataOutputStream) throws IOException {
        super.write(dataOutputStream);
        dataOutputStream.writeDouble(value);
    }
}
