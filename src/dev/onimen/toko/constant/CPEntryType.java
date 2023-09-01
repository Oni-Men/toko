package dev.onimen.toko.constant;

import dev.onimen.toko.util.StringUtils;

public enum CPEntryType {

    CLASS(7),
    FIELD_REF(9),
    METHOD_REF(10),
    INTERFACE_METHOD_REF(11),
    STRING(8),
    INTEGER(3),
    FLOAT(4),
    LONG(5),
    DOUBLE(6),
    NAME_AND_TYPE(12),
    UTF8(1),
    METHOD_HANDLE(15),
    METHOD_TYPE(16),
    DYNAMIC(17),
    INVOKE_DYNAMIC(18),
    MODULE(19),
    PACKAGE(20);

    private final int tag;
    private final String name;

    CPEntryType(int tag) {
        this.tag = tag;
        this.name = StringUtils.toSnakeCase(this.name(), StringUtils.CapitalizeType.UPPER_FIRST);
    }

    public int getTag() {
        return this.tag;
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
