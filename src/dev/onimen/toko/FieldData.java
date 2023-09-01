package dev.onimen.toko;

import java.util.ArrayList;
import java.util.List;

public class FieldData {

    public static class AccessFlags {

        private int accessFlags;

        public AccessFlags(int accessFlags) {
            this.accessFlags = accessFlags;
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
