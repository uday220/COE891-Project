package org.apache.commons.compress.archivers.zip;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ZipArchiveEntry_setUnixMode_75_0_Test {

    @Test
    void testSetUnixModeRegularFileAndDirectory() {
        final ZipArchiveEntry fileEntry = new ZipArchiveEntry("file.txt");
        fileEntry.setUnixMode(0755);
        assertEquals(ZipArchiveEntry.PLATFORM_UNIX, fileEntry.getPlatform());
        assertEquals(0755, fileEntry.getUnixMode());
        assertEquals(0755L << 16, fileEntry.getExternalAttributes());

        final ZipArchiveEntry dirEntry = new ZipArchiveEntry("folder/");
        dirEntry.setUnixMode(0555);
        assertEquals(ZipArchiveEntry.PLATFORM_UNIX, dirEntry.getPlatform());
        assertEquals(0555, dirEntry.getUnixMode());
        assertEquals((0555L << 16) | 0x10L | 1L, dirEntry.getExternalAttributes());
    }
}
