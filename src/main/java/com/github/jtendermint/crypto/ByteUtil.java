/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2017 - 2018
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.jtendermint.crypto;

import java.nio.ByteBuffer;

/**
 * 
 * Byte Util class for conversion to Tendermint formats
 * 
 * @author wolfposd
 */
public final class ByteUtil {

    public enum ByteFormat {
        FORMAT_0x00, FORMAT_00
    }

    private ByteUtil() {
        // empty constructor for util class
    }

    /**
     * Converts an integer into a byte-array
     * 
     * @param value
     * @return
     */
    public static byte[] toBytes(int value) {
        return ByteBuffer.allocate(4).putInt(value).array();
    }

    /**
     * Writes a bytearray into a string with specified byteformat
     * 
     * @param bArr
     *            target array
     * @param format
     *            target format
     * @return formatted string
     */
    public static String toString(byte[] bArr, ByteFormat format) {
        switch (format) {
        case FORMAT_00:
            return toString00(bArr);
        case FORMAT_0x00:
        default:
            return toString0x00(bArr);
        }
    }

    /**
     * Formats the byte array into separated 0x00 blocks <br>
     * [ca,fe,ba,be] =&gt; 0xca 0xfe 0xba 0xbe
     * 
     * @param bArr
     * @return formatted string
     */
    public static String toString0x00(byte[] bArr) {
        if (bArr == null)
            return null;

        StringBuilder buf = new StringBuilder();
        for (byte b : bArr) {
            buf.append(String.format("0x%x ", b));
        }
        return buf.toString();
    }

    /**
     * Formats the byte array into uppercase blocks <br>
     * [ca,fe,ba,be] =&gt; CAFEBABE
     * 
     * @param bArr
     * @return formatted string
     */
    public static String toString00(byte[] bArr) {
        if (bArr == null)
            return null;

        final StringBuilder builder = new StringBuilder();
        for (byte b : bArr) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString().toUpperCase();
    }

    /**
     * Parses a byte-array-string into an actual byte[]
     * 
     * @param string00
     *            must be in form of "00AAFF11"
     * @return the byte[] representation of the input string
     */
    public static byte[] fromString00(String string00) {
        int len = string00.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(string00.charAt(i), 16) << 4) + Character.digit(string00.charAt(i + 1), 16));
        }
        return data;
    }

}
