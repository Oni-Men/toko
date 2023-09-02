package dev.onimen.toko.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class StringUtilsTest {

    @Test
    void richSplit() {
        var test = "Hello World! \"This is Test\" . Good Bye!";
        var expected = Arrays.asList("Hello", "World!", "This is Test", ".", "Good", "Bye!");

        assertIterableEquals(expected, StringUtils.richSplit(test));
    }

    @Test
    void toSnakeCase() {
        record SnakeCaseTest(String original, String expected, StringUtils.CapitalizeType type) {}
        var tests = new SnakeCaseTest[] {
                new SnakeCaseTest("MakeMeSnakeCase", "make_me_snake_case", StringUtils.CapitalizeType.LOWER_ALL),
                new SnakeCaseTest("MakeMeSnakeCase", "MAKE_ME_SNAKE_CASE", StringUtils.CapitalizeType.UPPER_ALL),
                new SnakeCaseTest("MakeMeSnakeCase", "Make_Me_Snake_Case", StringUtils.CapitalizeType.UPPER_FIRST),
        };

        for (var test : tests) {
            assertEquals(test.expected, StringUtils.toSnakeCase(test.original, test.type));
        }
    }

    @Test
    void toHexString() {
        record HexStringTest(int original, int length, String expected) {}
        var tests = new HexStringTest[] {
                new HexStringTest(0xCAFE, 2, "0xCAFE"),
                new HexStringTest(0x00DA, 4, "0x00DA"),
                new HexStringTest(0xBABE, 4, "0xBABE")
        };

        for (var test : tests) {
            assertEquals(test.expected, StringUtils.toHexString(test.original, test.length));
        }
    }
}