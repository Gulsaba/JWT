package org.example;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Application {

	public static byte[] convertPlainTextToHash(String plainText, byte[] privateKey)
			throws NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidKeySpecException,
			IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, SignatureException {
//		byte[] messageBytes = plainText.getBytes();
//		MessageDigest md = MessageDigest.getInstance("SHA-256");
//		byte[] messageHash = md.digest(messageBytes);
//
////    DigestAlgorithmIdentifierFinder hashAlgorithmFinder = new DefaultDigestAlgorithmIdentifierFinder();
////    AlgorithmIdentifier hashingAlgorithmIdentifier = hashAlgorithmFinder.find("SHA-256");
//
//		AlgorithmIdentifier hashingAlgorithmIdentifier = AlgorithmIdentifier.getInstance("SHA-256");
//		DigestInfo digestInfo = new DigestInfo(hashingAlgorithmIdentifier, messageHash);
//		byte[] hashToEncrypt = digestInfo.getEncoded();
//
//		Cipher cipher = Cipher.getInstance("RSA");
//		cipher.init(Cipher.ENCRYPT_MODE, getPrivateKey(privateKey));
//		return cipher.doFinal(hashToEncrypt);

		Signature signature = Signature.getInstance("SHA256withRSA");
		signature.initSign(getPrivateKey(privateKey));
		signature.update(plainText.getBytes());
		return signature.sign();
	}

//
	private static PrivateKey getPrivateKey(byte[] privateKey)
			throws InvalidKeySpecException, NoSuchAlgorithmException {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey)));
	}
}
