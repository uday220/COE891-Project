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

import static org.apache.commons.compress.AbstractTest.getFile;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;

import org.apache.commons.compress.utils.TimeUtils;
import org.apache.commons.io.file.attribute.FileTimes;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

/**
 * Uday Modifed Files
 */


public class ZipArchiveEntryTest {

    @Test
    public void testSetSizeNegative() {
        ZipArchiveEntry entry = new ZipArchiveEntry("test");

        assertThrows(IllegalArgumentException.class, () -> {
            entry.setSize(-1);
        });
    }

    @Test
    public void testSetSizeZero() {
        ZipArchiveEntry entry = new ZipArchiveEntry("test");
        entry.setSize(0);
        assertEquals(0, entry.getSize());
    }

    @Test
    public void testSetSizePositive() {
        ZipArchiveEntry entry = new ZipArchiveEntry("test");
        entry.setSize(100);
        assertEquals(100, entry.getSize());
    }
}