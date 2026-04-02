package org.apache.commons.compress.archivers.tar;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TarUtils_parseOctalOrBinary_24_0_Test {

    @Test
    void testParseOctalOrBinaryCoversOctalAndBinaryPaths() {
        final byte[] octal = "00000000007 ".getBytes(UTF_8);
        assertEquals(7L, TarUtils.parseOctalOrBinary(octal, 0, octal.length));

        final byte[] binary8 = {
                (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
                (byte) 0xff, (byte) 0xff, (byte) 0xf1, (byte) 0xef
        };
        assertEquals(-3601L, TarUtils.parseOctalOrBinary(binary8, 0, binary8.length));
    }
}
