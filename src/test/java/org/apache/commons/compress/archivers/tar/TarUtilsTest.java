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

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

class TarUtilsTest {

    @Test
    public void testFormatUnsignedOctalStringFormatsZero() {
        final byte[] buffer = new byte[6];

        TarUtils.formatUnsignedOctalString(0, buffer, 0, buffer.length);

        assertEquals("000000", new String(buffer, UTF_8));
    }

    @Test
    public void testFormatUnsignedOctalStringFormatsMaximumFittingValue() {
        final byte[] buffer = new byte[7];

        TarUtils.formatUnsignedOctalString(07777777L, buffer, 0, buffer.length);

        assertEquals("7777777", new String(buffer, UTF_8));
    }

    @Test
    public void testFormatUnsignedOctalStringRejectsOverflow() {
        final byte[] buffer = new byte[7];

        try {
            TarUtils.formatUnsignedOctalString(017777777L, buffer, 0, buffer.length);
            fail("Expected IllegalArgumentException");
        } catch (final IllegalArgumentException expected) {
            // expected
        }
    }

    @Test
    public void testParseOctalOrBinaryUsesOctalPathWhenHighBitIsUnset() {
        final byte[] buffer = "00000000007 ".getBytes(UTF_8);

        assertEquals(7L, TarUtils.parseOctalOrBinary(buffer, 0, buffer.length));
    }

    @Test
    public void testParseOctalOrBinaryUsesBinaryLongPathForEightByteInput() {
        final byte[] buffer = {
                (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
                (byte) 0xff, (byte) 0xff, (byte) 0xf1, (byte) 0xef
        };

        assertEquals(-3601L, TarUtils.parseOctalOrBinary(buffer, 0, buffer.length));
    }

    @Test
    public void testParseOctalOrBinaryUsesBinaryBigIntegerPathForTwelveByteInput() {
        final byte[] buffer = {
                (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
                (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
                (byte) 0xff, (byte) 0xff, (byte) 0xf1, (byte) 0xef
        };

        assertEquals(-3601L, TarUtils.parseOctalOrBinary(buffer, 0, buffer.length));
    }
}
