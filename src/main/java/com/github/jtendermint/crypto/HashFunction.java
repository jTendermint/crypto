package com.github.jtendermint.crypto;

/**
 * Common Hashing interface
 */
public interface HashFunction {

    /**
     * Hashes a byte[]
     * 
     * @param bytesToHash
     *            arbitrary byte array
     * @return byte[] containing hash
     */
    public byte[] hashBytes(byte[] byteArray);

}
