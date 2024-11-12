package org.example;

import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.Base64;

public class ManualHash {

    public static String hash(String algorithm, byte[] dataBytes) throws Exception {
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        byte[] hashBytes = digest.digest(dataBytes);
        byte[] hashEncoded = Base64.getEncoder().encode(hashBytes);
        return new String(hashEncoded);
    }

    public static String sign(String algorithm, PrivateKey privateKey, byte[] hashBytes) throws Exception {
        Signature signature = Signature.getInstance(algorithm);
        signature.initSign(privateKey);
        signature.update(hashBytes);
        byte[] signatureBytes = signature.sign();
        byte[] signatureEncodedBytes = Base64.getEncoder().encode(signatureBytes);
        return new String(signatureEncodedBytes);
    }

    public static byte[] padding(String algorithm, byte[] hashBytes) throws Exception {

        //PREPARE PADDING
        byte[] padding = null;
        if (algorithm.equals("SHA-1"  )) { padding = new byte[] { 0x30, 0x21, 0x30, 0x09, 0x06, 0x05, 0x2b, (byte) 0x0e, 0x03, 0x02, 0x1a, 0x05, 0x00, 0x04, 0x14                         }; }
        if (algorithm.equals("SHA-256")) { padding = new byte[] { 0x30, 0x31, 0x30, 0x0d, 0x06, 0x09, 0x60, (byte) 0x86, 0x48, 0x01, 0x65, 0x03, 0x04, 0x02, 0x01, 0x05, 0x00, 0x04, 0x20 }; }

        //ADD PADDING & HASH TO RESULTING ARRAY
        byte[] paddingHash = new byte[padding.length + hashBytes.length];
        System.arraycopy(padding  , 0, paddingHash, 0             , padding.length  );
        System.arraycopy(hashBytes, 0, paddingHash, padding.length, hashBytes.length);

        //RETURN HASH
        return paddingHash;

    }


//    public static Boolean verify(String algorithm, PublicKey publicKey, byte[] hashBytes, byte[] signatureBytes) throws Exception {
//
//        //INITIALIZE SIGNATURE
//        Signature signature = Signature.getInstance(algorithm);
//        signature.initVerify(publicKey);
//        signature.update(hashBytes);
//
//        //VERIFY SIGNATURE
//        boolean   verified = signature.verify(signatureBytes);
//
//        //DISPLAY VERIFICATION
//        System.out.println("VERIFIED  = " + verified);
//
//        //RETURN SIGNATURE
//        return verified;
//
//    }
}