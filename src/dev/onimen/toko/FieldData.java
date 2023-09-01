package dev.onimen.toko;

import java.util.ArrayList;
import java.util.List;

public class FieldData {

    public static class AccessFlags {

        public static final int PUBLIC = 0x0001;
        public static final int PRIVATE = 0x0002;
        public static final int PROTECTED = 0x0004;
        public static final int STATIC = 0x0008;
        public static final int FINAL = 0x0010;
        public static final int VOLATILE = 0x0040;
        public static final int TRANSIENT = 0x0080;
        public static final int SYNTHETIC = 0x1000;
        public static final int ENUM = 0x4000;

        private int accessFlags;

        public AccessFlags(int accessFlags) {
            this.accessFlags = accessFlags;
        }

        public boolean has(int mask) {
            return (accessFlags & mask) == mask;
        }

        public void set(int mask, boolean value) {
            if (value) {
                accessFlags |= mask;
            } else {
                accessFlags &= ~mask;
            }
        }

        public int get() {
            return accessFlags;
        }

    }

    public AccessFlags accessFlags;

    public int nameIndex;

    public int descriptorIndex;

    public List<AttributeData> attributes;

    public FieldData() {
        this.attributes = new ArrayList<>();
    }

}
