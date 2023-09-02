package dev.onimen.toko;

import dev.onimen.toko.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FieldData {

    public static class AccessFlags {
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

        public String toString() {
            var hex = StringUtils.toHexString(accessFlags, 4);
            var flagNames = Arrays.stream(FieldAccessFlag.values())
                    .filter(flagType -> has(flagType.mask))
                    .map(flagType -> flagType.name())
                    .toList();
            return String.format("%s: (%s)", hex, String.join(", ", flagNames));
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
