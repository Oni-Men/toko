package dev.onimen.toko.constant;

public enum CPEntryType {

    CLASS(7, "Class"),
    FIELD_REF(9, "Fieldref"),
    METHOD_REF(10, "Methodref"),
    INTERFACE_METHOD_REF(11, "InterfaceMethodref"),
    STRING(8, "String"),
    INTEGER(3, "Integer"),
    FLOAT(4, "Float"),
    LONG(5, "Long"),
    DOUBLE(6, "Double"),
    NAME_AND_TYPE(12, "NameAndType"),
    UTF8(1, "Utf8"),
    METHOD_HANDLE(15, "MethodHandle"),
    METHOD_TYPE(16, "MethodType"),
    DYNAMIC(17, "Dynamic"),
    INVOKE_DYNAMIC(18, "InvokeDynamic"),
    MODULE(19, "Module"),
    PACKAGE(20, "Package");

    private final int tag;
    public final String name;

    CPEntryType(int tag, String name) {
        this.tag = tag;
        this.name = name;
    }

    public int getTag() {
        return this.tag;
    }

    public String getName() {
        return this.name;
    }

    public static CPEntryType getTypeByTag(int tag) {
        for (var type: CPEntryType.values()) {
            if (type.getTag() == tag) {
                return type;
            }
        }

        throw new IllegalArgumentException("invalid tag: " + tag);
    }
}
