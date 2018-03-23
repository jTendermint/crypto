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
package com.github.jtendermint.crypto.gowire;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;

/**
 * Used to encode byte[] that represent special classes in tendermint into the
 * go-wire encoding required by tendermint
 * 
 * @author wolfposd
 */
public final class WireEncode {

    private WireEncode() {
        // private constructor for util class
    }

    /**
     * Encodes types as go-wire compatbile format
     * 
     * [typebyte][length-of-length][lenght-of-input][input]
     * 
     * @param typebyte
     *            the type byte used to describe the input from respective GO-class
     * @param inputbytes
     *            the actual inputbytes (no preformatting, raw input)
     * @return encoded byte, empty array when exception occured
     */
    public static byte[] encode(byte typebyte, byte[] inputbytes) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            bos.write(typebyte);
            bos.write(writeWithVarint(inputbytes));
            return bos.toByteArray();
        } catch (Exception e) {
            return new byte[] {};
        }
    }

    /**
     * Encodes anything in a go-wire compatbile format
     * 
     * [length-of-length][lenght-of-input][input]
     * 
     * @param inputbytes
     *            the actual inputbytes (no preformatting, raw input)
     * @return encoded byte, empty array when exception occured
     */
    public static byte[] writeWithVarint(byte[] inputbytes) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            if (inputbytes == null) {
                inputbytes = new byte[0];
            }
            long length = inputbytes.length;
            byte[] varint = BigInteger.valueOf(length).toByteArray();
            long varintLength = varint.length;
            byte[] varintPrefix = BigInteger.valueOf(varintLength).toByteArray();
            bos.write(varintPrefix);
            if (inputbytes.length > 0) {
                bos.write(varint);
                bos.write(inputbytes);
            }

            return bos.toByteArray();
        } catch (Exception e) {
            return new byte[] {};
        }

    }

}
