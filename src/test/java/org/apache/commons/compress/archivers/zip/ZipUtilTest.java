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

/**
 *
 * Benjamin Corbet testing ZipUtil.java
 * methods to test:
 * 
 * reverse(final byte[] array)
 * unsignedIntToSignedByte(final int i) 
 */
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.apache.commons.compress.archivers.zip.ZipUtil;

public class ZipUtilTest {
    
	private final ZipUtil util = new ZipUtil() {};

    @Test
    void testReverseEvenLengthArray() {
        byte[] arr = {1, 2, 3, 4};
        byte[] result = ZipUtil.reverse(arr);
        assertArrayEquals(new byte[]{4, 3, 2, 1}, result);
    }

    @Test
    void testReverseOddLengthArray() {
        byte[] arr = {10, 20, 30};
        byte[] result = ZipUtil.reverse(arr);
        assertArrayEquals(new byte[]{30, 20, 10}, result);
    }

    @Test
    void testReverseSingleElementArray() {
        byte[] arr = {7};
        byte[] result = ZipUtil.reverse(arr);
        assertArrayEquals(new byte[]{7}, result);
    }

    @Test
    void testReverseEmptyArray() {
        byte[] arr = {};
        byte[] result = ZipUtil.reverse(arr);
        assertArrayEquals(new byte[]{}, result);
    }

    @Test
    void testWithinPositiveRange() {
        byte result = util.unsignedIntToSignedByte(100);
        assertEquals((byte) 100, result);
    }

    @Test
    void testAt128() {
        byte result = util.unsignedIntToSignedByte(128);
        assertEquals((byte) -128, result);
    }

    @Test
    void testAt255() {
        byte result = util.unsignedIntToSignedByte(255);
        assertEquals((byte) -1, result);
    }

    @Test
    void testOutOfRange() {
        assertThrows(IllegalArgumentException.class, () -> util.unsignedIntToSignedByte(256));
    }
}
