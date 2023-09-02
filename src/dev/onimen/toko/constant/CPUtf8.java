package dev.onimen.toko.constant;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class CPUtf8 extends CPEntry {

    public String value;

    public CPUtf8(CPEntryType type, String value) {
        super(type);
        this.value = value;
    }

    public byte[] toByteArray() {
        try {
            var byteArrayOutputStream = new ByteArrayOutputStream();
            var dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            dataOutputStream.writeUTF(value);
            return byteArrayOutputStream.toByteArray();
        }catch (Exception e) {
            return new byte[0];
        }
    }

    @Override
    public void write(DataOutputStream dataOutputStream) throws IOException {
        super.write(dataOutputStream);
        dataOutputStream.write(this.toByteArray());
    }

    @Override
    public String toString() {
        return value;
    }
}
