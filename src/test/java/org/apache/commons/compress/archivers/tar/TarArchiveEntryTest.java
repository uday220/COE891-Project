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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.attribute.FileTime;
import java.time.Instant;

import org.apache.commons.compress.archivers.ArchiveException;
import org.junit.jupiter.api.Test;


class TarArchiveEntryTest implements TarConstants {

	private static final int TAR_HEADER_SIZE = DEFAULT_RCDSIZE;

	private static int getSizeFieldOffset() {
		return NAMELEN + MODELEN + UIDLEN + GIDLEN;
	}

	private static byte[] writeHeader(final TarArchiveEntry entry, final boolean starMode) throws IOException {
		final byte[] header = new byte[TAR_HEADER_SIZE];
		entry.writeEntryHeader(header, TarUtils.DEFAULT_ENCODING, starMode);
		return header;
	}

	@Test
	void testParseTarHeader_ISP_regularFileRoundTrip() throws IOException {
		final TarArchiveEntry original = new TarArchiveEntry("isp-file.txt");
		original.setSize(1234L);
		original.setUserId(1000);
		original.setGroupId(1001);
		original.setMode(0644);
		original.setModTime(FileTime.from(Instant.ofEpochSecond(1_700_000_000L)));

		final byte[] header = writeHeader(original, false);

		final TarArchiveEntry parsed = new TarArchiveEntry("placeholder");
		parsed.parseTarHeader(header, TarUtils.DEFAULT_ENCODING);

		assertEquals("isp-file.txt", parsed.getName());
		assertEquals(1234L, parsed.getSize());
		assertEquals(1000L, parsed.getLongUserId());
		assertEquals(1001L, parsed.getLongGroupId());
		assertEquals(0644, parsed.getMode());
	}

	@Test
	void testParseTarHeader_CFG_DFG_characterDevicePath() throws IOException {
		final TarArchiveEntry original = new TarArchiveEntry("char-device", LF_CHR);
		original.setDevMajor(10);
		original.setDevMinor(20);
		original.setSize(0);

		final byte[] header = writeHeader(original, false);

		final TarArchiveEntry parsed = new TarArchiveEntry("placeholder");
		parsed.parseTarHeader(header, TarUtils.DEFAULT_ENCODING);

		assertTrue(parsed.isCharacterDevice());
		assertEquals(10, parsed.getDevMajor());
		assertEquals(20, parsed.getDevMinor());
	}

	@Test
	void testParseTarHeader_logic_negativeSizeRejected() throws IOException {
		final TarArchiveEntry base = new TarArchiveEntry("negative-size.bin");
		base.setSize(1);
		final byte[] header = writeHeader(base, false);

		TarUtils.formatLongOctalOrBinaryBytes(-1L, header, getSizeFieldOffset(), SIZELEN);

		final TarArchiveEntry parsed = new TarArchiveEntry("placeholder");
		assertThrows(ArchiveException.class, () -> parsed.parseTarHeader(header, TarUtils.DEFAULT_ENCODING));
	}

	@Test
	void testParseTarHeader_mutation_lenientVersusStrictNumericParsing() throws IOException {
		final TarArchiveEntry base = new TarArchiveEntry("bad-mode.txt");
		final byte[] header = writeHeader(base, false);

		final int modeOffset = NAMELEN;
		header[modeOffset] = (byte) 'X';

		final TarArchiveEntry strictParsed = new TarArchiveEntry("placeholder");
		assertThrows(ArchiveException.class, () -> strictParsed.parseTarHeader(header, TarUtils.DEFAULT_ENCODING));


		final TarArchiveEntry lenientParsed = new TarArchiveEntry(header, TarUtils.DEFAULT_ENCODING, true);
		assertEquals((int) TarArchiveEntry.UNKNOWN, lenientParsed.getMode());
	}

	@Test
	void testWriteEntryHeader_ISP_starModeFalseRoundTrip() throws IOException {
		final TarArchiveEntry entry = new TarArchiveEntry("write-isp.txt");
		entry.setSize(2048L);
		entry.setUserId(501);
		entry.setGroupId(20);
		entry.setMode(0640);
		entry.setModTime(FileTime.from(Instant.ofEpochSecond(1_710_000_000L)));

		final byte[] header = writeHeader(entry, false);
		final TarArchiveEntry parsed = new TarArchiveEntry("placeholder");
		parsed.parseTarHeader(header, TarUtils.DEFAULT_ENCODING);

		assertEquals("write-isp.txt", parsed.getName());
		assertEquals(2048L, parsed.getSize());
		assertEquals(501L, parsed.getLongUserId());
		assertEquals(20L, parsed.getLongGroupId());
		assertEquals(0640, parsed.getMode());
	}

	@Test
	void testWriteEntryHeader_CFG_DFG_starModeTrueWithOptionalTimes() throws IOException {
		final TarArchiveEntry entry = new TarArchiveEntry("write-cfg-dfg.txt");
		entry.setLastAccessTime(FileTime.from(Instant.ofEpochSecond(1_720_000_000L)));
		entry.setStatusChangeTime(FileTime.from(Instant.ofEpochSecond(1_720_000_100L)));
		entry.setSize(10);

		final byte[] header = writeHeader(entry, true);
		final TarArchiveEntry parsed = new TarArchiveEntry("placeholder");
		parsed.parseTarHeader(header, TarUtils.DEFAULT_ENCODING);

		assertEquals(FileTime.from(Instant.ofEpochSecond(1_720_000_000L)), parsed.getLastAccessTime());
		assertEquals(FileTime.from(Instant.ofEpochSecond(1_720_000_100L)), parsed.getStatusChangeTime());
	}

	@Test
	void testWriteEntryHeader_logic_starModeFalseOverflowedSizeBecomesZeroInHeader() throws IOException {
		final TarArchiveEntry entry = new TarArchiveEntry("overflow-size.bin");
		entry.setSize(1L << 40);

		final byte[] header = writeHeader(entry, false);
		final TarArchiveEntry parsed = new TarArchiveEntry("placeholder");
		parsed.parseTarHeader(header, TarUtils.DEFAULT_ENCODING);

		assertEquals(0L, parsed.getSize());
	}

	@Test
	void testWriteEntryHeader_mutation_checksumAndFlagsStayValid() throws IOException {
		final TarArchiveEntry entry = new TarArchiveEntry("dir/");
		entry.setMode(0755);
		entry.setSize(0);

		final byte[] header = writeHeader(entry, false);
		final TarArchiveEntry parsed = new TarArchiveEntry("placeholder");
		parsed.parseTarHeader(header, TarUtils.DEFAULT_ENCODING);

		assertTrue(parsed.isCheckSumOK());
		assertTrue(parsed.isDirectory());
		assertEquals("dir/", parsed.getName());
	}
}
