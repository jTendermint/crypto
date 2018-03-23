package com.github.jtendermint.crypto.gowire;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import com.github.jtendermint.crypto.ByteUtil;

public class WireEncodeTest {

    @Test
    public void testWireEncode() {

        final byte[] expectedEncoding = ByteUtil.fromString00("0101020A0A");

        byte[] actual = WireEncode.encode((byte) 0x1, new byte[] { 0xA, 0xA });

        assertArrayEquals(expectedEncoding, actual);

    }

}
