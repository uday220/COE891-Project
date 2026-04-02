package org.apache.commons.compress.archivers.zip;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ZipArchiveEntry_setAlignment_59_0_Test {

    @Test
    void testSetAlignment() {
        final ZipArchiveEntry entry = new ZipArchiveEntry("file.txt");

        entry.setAlignment(0);
        assertEquals(0, entry.getAlignment());

        entry.setAlignment(8);
        assertEquals(8, entry.getAlignment());

        assertThrows(IllegalArgumentException.class, () -> entry.setAlignment(3));
        assertThrows(IllegalArgumentException.class, () -> entry.setAlignment(65536));
    }
}
