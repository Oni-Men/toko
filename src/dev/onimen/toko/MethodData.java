package dev.onimen.toko;

import java.util.ArrayList;
import java.util.List;

public class MethodData {
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

    public MethodData() {
        this.attributes = new ArrayList<>();
    }
}
