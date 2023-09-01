package dev.onimen.toko;

import dev.onimen.toko.constant.CPClass;
import dev.onimen.toko.constant.CPEntry;
import dev.onimen.toko.constant.CPUtf8;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ClassFile {

    public static class AccessFlags {

        public static final int PUBLIC = 0x0001;
        public static final int FINAL = 0x0010;
        public static final int SUPER = 0x0020;
        public static final int INTERFACE = 0x0200;
        public static final int ABSTRACT = 0x0400;
        public static final int SYNTHETIC = 0x1000;
        public static final int ANNOTATION = 0x2000;
        public static final int ENUM = 0x4000;
        public static final int MODULE = 0x8000;

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

    public static final int MAGIC_CODE = 0xCAFEBABE;

    public int majorVersion;

    public int minorVersion;

    public List<CPEntry> constantPool;

    public AccessFlags accessFlags;

    public int thisClassIndex;

    public int superClassIndex;

    public List<InterfaceData> interfaces;

    public List<FieldData> fields;

    public List<MethodData> methods;

    public List<AttributeData> attributes;

    private boolean isDirty = true;

    private Path classFilePath;

    public ClassFile() {
        this(null);
    }

    public ClassFile(Path classFilePath) {
        this.classFilePath = classFilePath;
        constantPool = new ArrayList<>();
        interfaces = new ArrayList<>();
        fields = new ArrayList<>();
        methods = new ArrayList<>();
        attributes = new ArrayList<>();
    }

    public Path getClassFilePath() {
        return classFilePath;
    }

    private String getNameOfClass(int classIndex) {
        var entry = constantPool.get(classIndex);

        if (entry instanceof CPClass classEntry) {
            var nameEntry = constantPool.get(classEntry.nameIndex);
            if (nameEntry instanceof CPUtf8 utf8Entry) {
                return utf8Entry.value;
            }

            throw new RuntimeException(String.format("#%d is not CONSTANT_Utf8", classEntry.nameIndex));
        }

        throw new RuntimeException((String.format("#%d is not CONSTANT_Class_info", classIndex)));
    }

    public String getThisClassName() {
        return getNameOfClass(thisClassIndex);
    }

    public String getSuperClassName() {
        return getNameOfClass(superClassIndex);
    }

    public int getConstantPoolLength() {
        // The "length" wrote in Class file is 1 greater than the actual size of constant pool.
        return constantPool.size() + 1;
    }

    public CPEntry getConstantPoolEntry(int index) {
        return constantPool.get(index - 1);
    }

    public boolean isDirty() {
        return this.isDirty;
    }

}
