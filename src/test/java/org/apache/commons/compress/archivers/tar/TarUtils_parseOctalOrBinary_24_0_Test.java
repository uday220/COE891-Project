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
package org.apache.commons.compress.archivers.tar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class TarUtils_parseOctalOrBinary_24_0_Test {

    @Test
    void testParseOctalOrBinary_nullBuffer() {
        assertThrows(NullPointerException.class, () -> TarUtils.parseOctalOrBinary(null, 0, 1));
    }

    @Test
    void testParseOctalOrBinary_negativeOffset() {
        byte[] buffer = new byte[10];
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> TarUtils.parseOctalOrBinary(buffer, -1, 1));
    }

    @Test
    void testParseOctalOrBinary_lengthGreaterThanBufferSize() {
        byte[] buffer = new byte[10];
        assertEquals(0L, TarUtils.parseOctalOrBinary(buffer, 0, 11));
    }

    @Test
    void testParseOctalOrBinary_invalidOctalData() {
        byte[] buffer = new byte[10];
        Arrays.fill(buffer, (byte) 'x'); // Fill with invalid octal data
        assertThrows(IllegalArgumentException.class, () -> TarUtils.parseOctalOrBinary(buffer, 0, 8));
    }

    @Test
    void testParseOctalOrBinary_validOctalData() {
        byte[] buffer = new byte[10];
        Arrays.fill(buffer, (byte) '7'); // Fill with valid octal data
        assertEquals(16777215L, TarUtils.parseOctalOrBinary(buffer, 0, 8));
    }

    @Test
    void testParseOctalOrBinary_invalidBinaryData() {
        byte[] buffer = new byte[10];
        Arrays.fill(buffer, (byte) 'x'); // Fill with invalid binary data
        assertThrows(IllegalArgumentException.class, () -> TarUtils.parseOctalOrBinary(buffer, 0, 8));
    }

    @Test
    void testParseOctalOrBinary_validBinaryData() {
        byte[] buffer = new byte[10];
        Arrays.fill(buffer, (byte) '1'); // Fill with valid binary data
        assertEquals(2396745L, TarUtils.parseOctalOrBinary(buffer, 0, 8));
    }
}
