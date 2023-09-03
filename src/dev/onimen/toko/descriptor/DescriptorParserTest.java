package dev.onimen.toko.descriptor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DescriptorParserTest {

    @Test
    void parseField() {

    }

    @Test
    void parseMethod() {
        record ParseMethodTest(String original, DescriptorParser.MethodParseResult expected){}
        var tests = new ParseMethodTest[] {
                new ParseMethodTest(
                        "(IILjava/lang/String;)V",
                        new DescriptorParser.MethodParseResult(
                                new String[] {"int", "int", "String"},
                                "void"
                        )),
        };

        for (var test : tests) {
            assertEquals(test.expected, DescriptorParser.parseMethod(test.original));
        }
    }

    @Test
    void parseType() {
        record ParseTypeTest(String original, String expected) {
        }

        var tests = new ParseTypeTest[]{
                new ParseTypeTest("I", "int"),
                new ParseTypeTest("[Z", "boolean[]"),
                new ParseTypeTest("[[[D", "double[][][]"),
                new ParseTypeTest("Ljava/lang/Object;", "Object"),
                new ParseTypeTest("[Ljava/lang/String;", "String[]"),
        };

        for (var test : tests) {
            assertEquals(test.expected, DescriptorParser.parseType(test.original));
        }
    }
}