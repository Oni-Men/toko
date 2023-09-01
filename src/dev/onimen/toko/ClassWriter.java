package dev.onimen.toko;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ClassWriter implements AutoCloseable{

    private final DataOutputStream dataOutputStream;

    public ClassWriter(Path classFilePath) throws IOException {
        var bufferedOutputStream = new BufferedOutputStream(Files.newOutputStream(classFilePath));
        this.dataOutputStream = new DataOutputStream(bufferedOutputStream);
    }

    public void write(ClassFile classFile) throws IOException {
        this.writeMagic();
        this.writeVersions(classFile);
        this.writeConstantPool(classFile);
        this.writeAccessFlags(classFile);
        this.writeClassIndices(classFile);
        this.writeInterfaces(classFile);
        this.writeFields(classFile);
        this.writeMethods(classFile);
        this.writeAttributes(classFile.attributes);
    }

    private void writeMagic() throws IOException {
        dataOutputStream.writeInt(ClassFile.MAGIC_CODE);
    }

    private void writeVersions(ClassFile classFile) throws IOException {
        dataOutputStream.writeShort(classFile.minorVersion);
        dataOutputStream.writeShort(classFile.majorVersion);
    }

    private void writeConstantPool(ClassFile classFile) throws IOException {
        var length = classFile.getConstantPoolLength();
        dataOutputStream.writeShort(length);

        for (int i = 1; i < length; i++) {
            var entry = classFile.getConstantPoolEntry(i);
            entry.write(dataOutputStream);
        }
    }

    private void writeAccessFlags(ClassFile classFile) throws IOException {
        dataOutputStream.writeShort(classFile.accessFlags.get());
    }

    private void writeClassIndices(ClassFile classFile) throws IOException {
        dataOutputStream.writeShort(classFile.thisClassIndex);
        dataOutputStream.writeShort(classFile.superClassIndex);
    }

    private void writeInterfaces(ClassFile classFile) throws IOException {
        var interfaces = classFile.interfaces;
        var size = interfaces.size();
        dataOutputStream.writeShort(size);

        for (int i = 0; i < size; i++) {
            var interfaceData = interfaces.get(i);
            dataOutputStream.writeShort(interfaceData.classInfoIndex);
        }
    }

    private void writeFields(ClassFile classFile) throws IOException {
        var fields = classFile.fields;
        var size = fields.size();
        dataOutputStream.writeShort(size);

        for (int i = 0; i < size; i++) {
            var field = fields.get(i);
            dataOutputStream.writeShort(field.accessFlags.get());
            dataOutputStream.writeShort(field.nameIndex);
            dataOutputStream.writeShort(field.descriptorIndex);

            this.writeAttributes(field.attributes);
        }
    }

    private void writeMethods(ClassFile classFile) throws IOException {
        var methods = classFile.methods;
        var size = methods.size();
        dataOutputStream.writeShort(size);

        for (int i = 0; i < size; i++) {
            var method = methods.get(i);
            dataOutputStream.writeShort(method.accessFlags.get());
            dataOutputStream.writeShort(method.nameIndex);
            dataOutputStream.writeShort(method.descriptorIndex);

            this.writeAttributes(method.attributes);
        }
    }

    private void writeAttributes(List<AttributeData> attributes) throws IOException {
        var size = attributes.size();
        dataOutputStream.writeShort(size);

        for (int i = 0; i < size; i++) {
            this.writeAttribute(attributes.get(i));
        }
    }

    private void writeAttribute(AttributeData attribute) throws IOException {
        dataOutputStream.writeShort(attribute.nameIndex);
        dataOutputStream.writeInt(attribute.length);
        dataOutputStream.write(attribute.data);
    }

    @Override
    public void close() throws Exception {
        this.dataOutputStream.close();
    }
}

