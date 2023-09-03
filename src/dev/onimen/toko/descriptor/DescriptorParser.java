package dev.onimen.toko.descriptor;

import dev.onimen.toko.util.StringUtils;

import java.util.regex.Pattern;

public class DescriptorParser {

    private static final Pattern METHOD_DESCRIPTOR = Pattern.compile("\\((.*)\\)(.+)");

    public record FieldParseResult(String type) {
    }

    public record MethodParseResult(String[] paramTypes, String returnType) {
    }

    public static FieldParseResult parseField(String descriptor) {
        return new FieldParseResult(parseType(descriptor));
    }

    public static MethodParseResult parseMethod(String descriptor) {
        var matcher = METHOD_DESCRIPTOR.matcher(descriptor);
        if (matcher.matches()) {
            var returnType = parseType(matcher.group(2));

            return new MethodParseResult(null, returnType);
        }
        return null;
    }

    public static String parseType(String typeDescriptor) {
        var arrayDimensions = 0;
        while (typeDescriptor.charAt(0) == '[') {
            arrayDimensions++;
            typeDescriptor = typeDescriptor.substring(1);
        }

        var type = "";
        switch (typeDescriptor.charAt(0)) {
            case 'B':
                type = "byte";
                break;
            case 'C':
                type = "char";
                break;
            case 'D':
                type = "double";
                break;
            case 'F':
                type = "float";
                break;
            case 'I':
                type = "int";
                break;
            case 'J':
                type = "long";
                break;
            case 'L':
                type = getClassName(typeDescriptor);
                break;
            case 'S':
                type = "short";
                break;
            case 'Z':
                type = "boolean";
                break;
        }

        return type + "[]".repeat(arrayDimensions);
    }

    private static String getClassName(String referenceDescriptor) {
        var internalForm = StringUtils.trim("L", ";", referenceDescriptor);
        var split = internalForm.split("/");
        return split[split.length - 1];
    }

}
