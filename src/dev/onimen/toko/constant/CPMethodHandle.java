package dev.onimen.toko.constant;

import java.io.DataOutputStream;
import java.io.IOException;

public class CPMethodHandle extends CPEntry {

    public int referenceKind;
    public int referenceIndex;

    public CPMethodHandle(CPEntryType type, int referenceKind, int referenceIndex) {
        super(type);
        this.referenceKind = referenceKind;
        this.referenceIndex = referenceIndex;
    }

    @Override
    public void write(DataOutputStream dataOutputStream) throws IOException {
        super.write(dataOutputStream);
        dataOutputStream.writeByte(referenceKind);
        dataOutputStream.writeShort(referenceIndex);
    }
}
