package dev.onimen.toko;

import dev.onimen.toko.constant.*;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ClassReader {
    private final Path classFilePath;
    private final DataInputStream dataInputStream;

    public ClassReader(Path classFilePath) throws IOException {
        this.classFilePath = classFilePath;
        var bufferedInputStream = new BufferedInputStream(Files.newInputStream(this.classFilePath));
        this.dataInputStream = new DataInputStream(bufferedInputStream);
    }

    public ClassFile read() throws IOException {
        var classFile = new ClassFile();
        this.readMagic();
        this.readVersions(classFile);
        this.readConstantPool(classFile);
        this.readAccessFlags(classFile);
        this.readClassIndices(classFile);
        this.readInterfaces(classFile);
        this.readFields(classFile);
        this.readMethods(classFile);
        this.readAttributes(classFile);
        return classFile;
    }

    void readMagic() throws IOException {
        // for all class file, these have magic code which is "0xFAFEBABE" at the top of the class file.
        // magic code requires 4 bytes and throw an exception if the read 4 bytes was not "0xFAFEBABE".
        var magicCode = dataInputStream.readInt();
        if (magicCode != ClassFile.MAGIC_CODE) {
            throw new RuntimeException(String.format("illegal magic: 0x%s", Integer.toHexString(magicCode)));
        }
    }

    void readVersions(ClassFile classFile) throws IOException {
        // minor_version and major_version requires 2 bytes.
        int minorVersion = dataInputStream.readUnsignedShort();
        int majorVersion = dataInputStream.readUnsignedShort();

        classFile.minorVersion = minorVersion;
        classFile.majorVersion = majorVersion;
    }

    void readConstantPool(ClassFile classFile) throws IOException {
        // First, we should know the length of the constant pool. it requires 2 bytes.
        int length = dataInputStream.readUnsignedShort();

        // The actual length of constant pool is 1 less than "length".
        // Therefore, the loop starts from index 1.
        for (int i = 1; i < length; i++) {
            this.readConstantPoolEntry(classFile);
        }
    }

    void readConstantPoolEntry(ClassFile classFile) throws IOException {
        // "tag" requires 1 byte. the length of following bytes depends on "tag".
        int tag = dataInputStream.readUnsignedByte();
        var type = CPEntryType.getTypeByTag(tag);

        CPEntry entry = null;
        int nameIndex, descriptorIndex, nameAndTypeIndex;
        int index;

        // Read entry information depending on entry type.
        // And then, append entry read to constant pool.
        switch (type) {
            case CLASS:
                nameIndex = this.dataInputStream.readUnsignedShort();
                entry = new CPClass(type, nameIndex);
                break;
            case FIELD_REF:
            case METHOD_REF:
            case INTERFACE_METHOD_REF:
                index = this.dataInputStream.readUnsignedShort();
                nameAndTypeIndex = this.dataInputStream.readUnsignedShort();
                entry = new CPFieldMethodRef(type, index, nameAndTypeIndex);
                break;
            case STRING:
                index = this.dataInputStream.readUnsignedShort();
                entry = new CPString(type, index);
                break;
            case INTEGER:
                int intValue = this.dataInputStream.readInt();
                entry = new CPInteger(type, intValue);
                break;
            case FLOAT:
                float floatValue = this.dataInputStream.readFloat();
                entry = new CPFloat(type, floatValue);
                break;
            case LONG:
                long longValue = this.dataInputStream.readLong();
                entry = new CPLong(type, longValue);
                break;
            case DOUBLE:
                double doubleValue = this.dataInputStream.readDouble();
                entry = new CPDouble(type, doubleValue);
                break;
            case NAME_AND_TYPE:
                nameIndex = this.dataInputStream.readUnsignedShort();
                descriptorIndex = this.dataInputStream.readUnsignedShort();
                entry = new CPNameAndType(type, nameIndex, descriptorIndex);
                break;
            case UTF8:
                var str = this.dataInputStream.readUTF();
                entry = new CPUtf8(type, str);
                break;
            case METHOD_HANDLE:
                int refKind = this.dataInputStream.readUnsignedByte();
                int refIndex = this.dataInputStream.readUnsignedShort();
                entry = new CPMethodHandle(type, refKind, refIndex);
                break;
            case METHOD_TYPE:
                descriptorIndex = this.dataInputStream.readUnsignedShort();
                entry = new CPMethodType(type, descriptorIndex);
                break;
            case DYNAMIC:
            case INVOKE_DYNAMIC:
                index = this.dataInputStream.readUnsignedShort();
                nameAndTypeIndex = this.dataInputStream.readUnsignedShort();
                entry = new CPDynamic(type, index, nameAndTypeIndex);
                break;
            case MODULE:
                nameIndex = this.dataInputStream.readUnsignedShort();
                entry = new CPModule(type, nameIndex);
                break;
            case PACKAGE:
                nameIndex = this.dataInputStream.readUnsignedShort();
                entry = new CPPackage(type, nameIndex);
                break;
        }

        classFile.constantPool.add(entry);
    }

    void readAccessFlags(ClassFile classFile) throws IOException {
        // The "Access flags" require 2 bytes.
        int flags = dataInputStream.readUnsignedShort();
        classFile.accessFlags = new ClassFile.AccessFlags(flags);
    }

    void readClassIndices(ClassFile classFile) throws IOException {
        // Each class index requires 2 bytes.
        int thisClassIndex = dataInputStream.readUnsignedShort();
        int superClassIndex = dataInputStream.readUnsignedShort();

        classFile.thisClassIndex = thisClassIndex;
        classFile.superClassIndex = superClassIndex;
    }

    void readInterfaces(ClassFile classFile) throws IOException {
        // First, we should know the length of the interfaces. it requires 2 bytes.
        int interfacesCount = dataInputStream.readUnsignedShort();

        for (int i = 0; i < interfacesCount; i++) {
            int classInfoIndex = dataInputStream.readUnsignedShort();
            var interfaceData = new InterfaceData(classInfoIndex);
            classFile.interfaces.add(interfaceData);
        }
    }

    void readFields(ClassFile classFile) throws IOException {
        // First, we should know the length of the fields. it requires 2 bytes.
        int fieldsCount = dataInputStream.readUnsignedShort();

        for (int i = 0; i < fieldsCount; i++) {
            this.readField(classFile);
        }
    }

    void readField(ClassFile classFile) throws IOException {
        int accessFlags = dataInputStream.readUnsignedShort();
        int nameIndex = dataInputStream.readUnsignedShort();
        int descriptorIndex = dataInputStream.readUnsignedShort();
        int attributesCount = dataInputStream.readUnsignedShort();

        var fieldData = new FieldData();
        fieldData.accessFlags = new FieldData.AccessFlags(accessFlags);
        fieldData.nameIndex = nameIndex;
        fieldData.descriptorIndex = descriptorIndex;

        // Read all attribute in this field.
        for (int i = 0; i < attributesCount; i++) {
            var attribute = this.readAttribute();
            fieldData.attributes.add(attribute);
        }

        classFile.fields.add(fieldData);
    }

    void readMethods(ClassFile classFile) throws IOException {
        // First, we should know the length of the methods. it requires 2 bytes.
        int methodCount = dataInputStream.readUnsignedShort();

        for (int i = 0; i < methodCount; i++) {
            this.readMethod(classFile);
        }
    }

    void readMethod(ClassFile classFile) throws IOException {
        int accessFlag = dataInputStream.readUnsignedShort();
        int nameIndex = dataInputStream.readUnsignedShort();
        int descriptorIndex = dataInputStream.readUnsignedShort();
        int attributeCount = dataInputStream.readUnsignedShort();

        var methodData = new MethodData();
        methodData.accessFlags = new MethodData.AccessFlags(accessFlag);
        methodData.nameIndex = nameIndex;
        methodData.descriptorIndex = descriptorIndex;

        // Read all attribute in this method.
        for (int i = 0; i < attributeCount; i++) {
            var attribute = this.readAttribute();
            methodData.attributes.add(attribute);
        }
    }

    void readAttributes(ClassFile classFile) throws IOException {
        // First, we should know the length of the attributes. it requires 2 bytes.
        int attributesCount = dataInputStream.readUnsignedShort();

        for (int i = 0; i < attributesCount; i++) {
            var attribute = this.readAttribute();
            classFile.attributes.add(attribute);
        }
    }

    AttributeData readAttribute() throws IOException {
        var attribute = new AttributeData();
        int attributeNameIndex = dataInputStream.readUnsignedShort();
        int attributeLength = dataInputStream.readInt();
        byte[] attributeData = dataInputStream.readNBytes(attributeLength);

        attribute.nameIndex = attributeNameIndex;
        attribute.length = attributeLength;
        attribute.data = attributeData;

        return attribute;
    }

}
