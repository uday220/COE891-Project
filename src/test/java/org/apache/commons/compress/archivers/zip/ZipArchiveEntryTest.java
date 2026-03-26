/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.commons.compress.archivers.zip;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Uday Shergill Testing ZipArchiveEntry
 */
public class ZipArchiveEntryTest {

    @Test
    public void testSetAlignmentAcceptsDefaultZero() {
        final ZipArchiveEntry entry = new ZipArchiveEntry("file.txt");

        entry.setAlignment(0);

        assertEquals(0, entry.getAlignment());
    }

    @Test
    public void testSetAlignmentAcceptsPowerOfTwoWithinLimit() {
        final ZipArchiveEntry entry = new ZipArchiveEntry("file.txt");

        entry.setAlignment(32_768);

        assertEquals(32_768, entry.getAlignment());
    }

    @Test
    public void testSetAlignmentRejectsNonPowerOfTwo() {
        final ZipArchiveEntry entry = new ZipArchiveEntry("file.txt");

        try {
            entry.setAlignment(3);
            fail("Expected IllegalArgumentException");
        } catch (final IllegalArgumentException expected) {
            // expected
        }
    }

    @Test
    public void testSetAlignmentRejectsValueAboveLimit() {
        final ZipArchiveEntry entry = new ZipArchiveEntry("file.txt");

        try {
            entry.setAlignment(65_536);
            fail("Expected IllegalArgumentException");
        } catch (final IllegalArgumentException expected) {
            // expected
        }
    }

    @Test
    public void testSetUnixModeSetsUnixPlatformAndModeForRegularFile() {
        final ZipArchiveEntry entry = new ZipArchiveEntry("file.txt");

        entry.setUnixMode(0755);

        assertEquals(ZipArchiveEntry.PLATFORM_UNIX, entry.getPlatform());
        assertEquals(0755, entry.getUnixMode());
        assertEquals(0755L << 16, entry.getExternalAttributes());
    }

    @Test
    public void testSetUnixModeSetsReadOnlyFlagWhenOwnerWriteMissing() {
        final ZipArchiveEntry entry = new ZipArchiveEntry("file.txt");

        entry.setUnixMode(0555);

        assertEquals(ZipArchiveEntry.PLATFORM_UNIX, entry.getPlatform());
        assertEquals(0555, entry.getUnixMode());
        assertEquals((0555L << 16) | 1L, entry.getExternalAttributes());
    }

    @Test
    public void testSetUnixModeSetsDirectoryFlagForDirectoryEntries() {
        final ZipArchiveEntry entry = new ZipArchiveEntry("folder/");

        entry.setUnixMode(0755);

        assertEquals(ZipArchiveEntry.PLATFORM_UNIX, entry.getPlatform());
        assertEquals(0755, entry.getUnixMode());
        assertEquals((0755L << 16) | 0x10L, entry.getExternalAttributes());
    }

    @Test
    public void testSetUnixModeSetsBothDirectoryAndReadOnlyFlagsWhenNeeded() {
        final ZipArchiveEntry entry = new ZipArchiveEntry("folder/");

        entry.setUnixMode(0555);

        assertEquals(ZipArchiveEntry.PLATFORM_UNIX, entry.getPlatform());
        assertEquals(0555, entry.getUnixMode());
        assertEquals((0555L << 16) | 0x10L | 1L, entry.getExternalAttributes());
    }
}
