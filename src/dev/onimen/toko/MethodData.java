package dev.onimen.toko;

import java.util.ArrayList;
import java.util.List;

public class MethodData {
    public static class AccessFlags {

        public static final int PUBLIC = 0x0001;
        public static final int PRIVATE = 0x0002;
        public static final int PROTECTED = 0x0004;
        public static final int STATIC = 0x0008;
        public static final int FINAL = 0x0010;
        public static final int SYNCHRONIZED = 0x0020;
        public static final int BRIDGE = 0x0040;
        public static final int VARARGS = 0x0080;
        public static final int NATIVE = 0x0100;
        public static final int ABSTRACT = 0x0400;
        public static final int STRICT = 0x0800;
        public static final int SYNTHETIC = 0x1000;

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

    public MethodData() {
        this.attributes = new ArrayList<>();
    }
}
