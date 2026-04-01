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

package org.apache.commons.compress;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.utils.ArchiveUtils;
import org.junit.jupiter.api.Test;

/**
 * Abdullah Arif Testing ArchiveUtils
 */
class ArchiveUtilsTest extends AbstractTest {

    // =========================
    // checkEntryNameLength tests
    // =========================

    @Test
    void testCheckEntryNameLengthAcceptsValueWithinLimit() throws Exception {
        int result = ArchiveUtils.checkEntryNameLength(10, 20, "zip");
        assertEquals(10, result);
    }

    @Test
    void testCheckEntryNameLengthAcceptsExactBoundary() throws Exception {
        int result = ArchiveUtils.checkEntryNameLength(20, 20, "zip");
        assertEquals(20, result);
    }

    @Test
    void testCheckEntryNameLengthRejectsAboveLimit() {
        assertThrows(ArchiveException.class,
                () -> ArchiveUtils.checkEntryNameLength(21, 20, "zip"));
    }

    @Test
    void testCheckEntryNameLengthRejectsVeryLargeLength() {
        assertThrows(MemoryLimitException.class,
                () -> ArchiveUtils.checkEntryNameLength((long) Integer.MAX_VALUE, Integer.MAX_VALUE, "zip"));
    }

    // =========================
    // isEqual tests
    // =========================

    @Test
    void testIsEqualSameContentSameLength() {
        byte[] a = {1, 2, 3};
        byte[] b = {1, 2, 3};

        assertTrue(ArchiveUtils.isEqual(a, 0, a.length, b, 0, b.length, false));
    }

    @Test
    void testIsEqualDifferentByteInOverlap() {
        byte[] a = {1, 2, 3};
        byte[] b = {1, 9, 3};

        assertFalse(ArchiveUtils.isEqual(a, 0, a.length, b, 0, b.length, false));
    }

    @Test
    void testIsEqualDifferentLengthsIgnoreTrailingNullsFalse() {
        byte[] a = {1, 2, 3};
        byte[] b = {1, 2, 3, 0, 0};

        assertFalse(ArchiveUtils.isEqual(a, 0, a.length, b, 0, b.length, false));
    }

    @Test
    void testIsEqualDifferentLengthsIgnoreTrailingNullsTrueWithOnlyNulls() {
        byte[] a = {1, 2, 3};
        byte[] b = {1, 2, 3, 0, 0};

        assertTrue(ArchiveUtils.isEqual(a, 0, a.length, b, 0, b.length, true));
    }

    @Test
    void testIsEqualDifferentLengthsIgnoreTrailingNullsTrueWithNonNullExtraByte() {
        byte[] a = {1, 2, 3};
        byte[] b = {1, 2, 3, 4};

        assertFalse(ArchiveUtils.isEqual(a, 0, a.length, b, 0, b.length, true));
    }

    @Test
    void testIsEqualWithOffsets() {
        byte[] a = {9, 1, 2, 3, 9};
        byte[] b = {8, 1, 2, 3, 8};

        assertTrue(ArchiveUtils.isEqual(a, 1, 3, b, 1, 3, false));
    }
}