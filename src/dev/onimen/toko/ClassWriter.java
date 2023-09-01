package dev.onimen.toko;

import java.io.DataOutputStream;
import java.io.OutputStream;

public class ClassWriter {

    private final ClassFile classFile;
    private DataOutputStream dataOutputStream;

    public ClassWriter(ClassFile classFile) {
        this.classFile = classFile;
    }

    public void write(OutputStream outputStream) {
        this.dataOutputStream = new DataOutputStream(outputStream);
    }

    private void writeMagic() {

    }

    private void writeVersions() {

    }

    private void writeConstantPool() {

    }

    private void writeConstantPoolEntry() {

    }

    private void writeAccessFlags() {

    }

    private void writeClassIndices() {

    }

    private void writeInterfaces() {

    }

    private void writeFields() {

    }

    private void writeMethods() {

    }

    private void writeAttributes() {

    }
}

