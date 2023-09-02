package dev.onimen.toko.command;

import dev.onimen.toko.ClassFile;
import dev.onimen.toko.Context;
import dev.onimen.toko.FieldAccessFlag;
import dev.onimen.toko.FieldData;
import dev.onimen.toko.constant.CPClass;
import dev.onimen.toko.constant.CPUtf8;
import dev.onimen.toko.util.StringUtils;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ShowCommand implements Command {

    private enum Operation {
        ALL,
        CLASS,
        CONSTANT_POOL,
        INTERFACES,
        FIELDS,
        METHODS,
        ATTRIBUTES,
        UNKNOWN;

        public static Operation getByName(String name) {
            try {
                return Operation.valueOf(name.toUpperCase());
            } catch (IllegalArgumentException e) {
                return Operation.UNKNOWN;
            }
        }
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getUsage() {
        return """
               show [item-name]
               You can use below words as item-name.
               all, class, constant_pool, interfaces, fields, methods, attributes
               
               if "item-name" were not specified, 
               """;
    }

    @Override
    public void execute(List<String> arguments) {
        var context = Context.getInstance();
        var writer = context.get(PrintWriter.class);
        var classFile = context.get(ClassFile.class);

        if (classFile == null) {
            writer.println("Class file is not opened.");
            return;
        }

        var op = Operation.ALL;
        if (arguments.size() >= 1) {
            var name = arguments.get(0);
            op = Operation.getByName(name);
            if (op == Operation.UNKNOWN) {
                writer.println(String.format("show: unknown operation %s", name));
                return;
            }
        }

        switch (op) {
            case ALL -> this.printAll(classFile, writer);
            case CLASS -> this.printClass(classFile, writer);
            case CONSTANT_POOL -> this.printConstantPool(classFile, writer);
            case INTERFACES -> this.printInterfaces(classFile, writer);
            case FIELDS -> this.printFields(classFile, writer);
            case METHODS -> this.printMethods(classFile, writer);
            case ATTRIBUTES -> this.printAttributes(classFile, writer);
        }
    }

    private void printAll(ClassFile classFile, PrintWriter writer) {
        this.printClass(classFile, writer);
        this.printConstantPool(classFile, writer);
        this.printInterfaces(classFile, writer);
        this.printFields(classFile, writer);
        this.printMethods(classFile, writer);
        this.printAttributes(classFile, writer);
    }

    private void printClass(ClassFile classFile, PrintWriter writer) {
        var minorVersion = classFile.minorVersion;
        var majorVersion = classFile.majorVersion;
        var constantPoolLength = classFile.getConstantPoolLength();
        var accessFlags = classFile.accessFlags;
        var thisClassName = classFile.getThisClassName();
        var superClassName = classFile.getSuperClassName();
        var interfacesCount = classFile.interfaces.size();
        var fieldsCount = classFile.fields.size();
        var methodsCount = classFile.methods.size();
        var attributesCount = classFile.attributes.size();

        writer.println("<< class " + thisClassName + " >>");
        writer.printf("\tminor version: %d%n", minorVersion);
        writer.printf("\tmajor version: %d%n", majorVersion);
        writer.printf("\tflags: %s%n", accessFlags.toString());
        writer.printf("\tthis_class: #%d\t\t// %s%n", classFile.thisClassIndex, thisClassName);
        writer.printf("\tsuper_class: #%d\t\t// %s%n", classFile.superClassIndex, superClassName);
        writer.printf("\tconstant_pool: %d%n", constantPoolLength);
        writer.printf("\tinterfaces: %d, fields: %d, methods: %d, attributes: %d%n", interfacesCount, fieldsCount, methodsCount, attributesCount);
    }

    private void printConstantPool(ClassFile classFile, PrintWriter writer) {
        writer.println("<< Constant pool >>");

        var length = classFile.getConstantPoolLength();
        var padLength = String.valueOf(length).length();
        for (int i = 1; i < length; i++) {
            var entry = classFile.getConstantPoolEntry(i);
            var index = "#" + i;
            var entryTypeName = entry.type.getName();
            writer.printf("\t%s = %-16s\t%s%n", StringUtils.padding(index, " ", padLength), entryTypeName, entry);
        }
    }

    private void printInterfaces(ClassFile classFile, PrintWriter writer) {
        writer.println("<< Interfaces >>");
        var interfacesText = classFile.interfaces.stream().map(i ->  {
            var entry = classFile.getConstantPoolEntry(i.classInfoIndex);
            if (entry instanceof CPClass classInfo) {
                entry = classFile.getConstantPoolEntry(classInfo.nameIndex);
                if (entry instanceof CPUtf8 utf8) {
                    return utf8.value;
                }
                throw new RuntimeException(String.format("#%d is not CONSTANT_Utf8", classInfo.nameIndex));
            }
            throw new RuntimeException(String.format("#%d is not CONSTANT_Class_info", i.classInfoIndex));
        }).collect(Collectors.joining(", "));
        writer.println(String.format("\t%s%n", interfacesText));
    }

    private void printFields(ClassFile classFile, PrintWriter writer) {
        writer.println("<< Fields >>");

        for (var field : classFile.fields) {
            var accessFlags = field.accessFlags;
            var nameEntry  = (CPUtf8) classFile.getConstantPoolEntry(field.nameIndex);
            var descriptorEntry = (CPUtf8) classFile.getConstantPoolEntry(field.descriptorIndex);
            var modifier = this.getFieldModifierText(accessFlags);

            writer.printf("\t%s %s%n", modifier, nameEntry.value);
            writer.printf("\t\tdescriptor: %s%n", descriptorEntry.value);
            writer.printf("\t\tflags: %s%n", accessFlags);

            if (accessFlags.has(FieldAccessFlag.ACC_STATIC.mask | FieldAccessFlag.ACC_FINAL.mask)) {
                // TODO print constant value
            }
        }
    }

    private void printMethods(ClassFile classFile, PrintWriter writer) {

    }

    private void printAttributes(ClassFile classFile, PrintWriter writer) {

    }

    private String getFieldModifierText(FieldData.AccessFlags accessFlags) {
        return Arrays.asList(FieldAccessFlag.values())
                .stream()
                .filter(flag -> accessFlags.has(flag.mask))
                .map(flag -> flag.keyword)
                .collect(Collectors.joining(" "));
    }
}
