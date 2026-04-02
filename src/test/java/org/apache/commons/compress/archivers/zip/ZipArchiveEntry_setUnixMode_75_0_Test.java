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
