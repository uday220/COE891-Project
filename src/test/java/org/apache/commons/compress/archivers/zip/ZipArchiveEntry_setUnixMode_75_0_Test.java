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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ZipArchiveEntry_setUnixMode_75_0_Test {

    private ZipArchiveEntry zipArchiveEntry;

    @BeforeEach
    void setUp() {
        zipArchiveEntry = new ZipArchiveEntry("test");
    }

    @Test
    void testSetUnixMode_validInputs() {
        // Test with valid Unix mode values
        int unixMode1 = 0755; // Directory with read, write and execute permissions for owner
        zipArchiveEntry.setUnixMode(unixMode1);
        assertEquals(unixMode1, zipArchiveEntry.getUnixMode());

        int unixMode2 = 0644; // Regular file with read and write permissions for owner
        zipArchiveEntry.setUnixMode(unixMode2);
        assertEquals(unixMode2, zipArchiveEntry.getUnixMode());

        int unixMode3 = 0100; // Executable file with execute permission for owner
        zipArchiveEntry.setUnixMode(unixMode3);
        assertEquals(unixMode3, zipArchiveEntry.getUnixMode());
    }

    @Test
    void testSetUnixMode_invalidInputs() {
        // Test with invalid Unix mode values
        int invalidUnixMode1 = -1; // Negative value
        zipArchiveEntry.setUnixMode(invalidUnixMode1);
        assertNotEquals(invalidUnixMode1, zipArchiveEntry.getUnixMode());

        int invalidUnixMode2 = 0; // Zero value
        zipArchiveEntry.setUnixMode(invalidUnixMode2);
        assertEquals(invalidUnixMode2, zipArchiveEntry.getUnixMode());
    }

    @Test
    void testSetUnixMode_edgeCases() {
        // Test with edge cases
        int edgeCase1 = 0xFFFF; // Maximum value for a short in Java
        zipArchiveEntry.setUnixMode(edgeCase1);
        assertEquals(edgeCase1, zipArchiveEntry.getUnixMode());

        int edgeCase2 = 0x0000; // Minimum value for a short in Java
        zipArchiveEntry.setUnixMode(edgeCase2);
        assertEquals(edgeCase2, zipArchiveEntry.getUnixMode());
    }
}
